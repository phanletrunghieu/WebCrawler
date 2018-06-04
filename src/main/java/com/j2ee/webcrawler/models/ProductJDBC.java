/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2ee.webcrawler.models;

import com.j2ee.webcrawler.utils.MyUtils;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Phan Hieu
 */
@Repository
@Transactional
public class ProductJDBC {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    public void create(Product product) {
        String SQL = "insert into Products (id, name, url, image, price, originalPrice, ratingScore) values (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplateObject.update(SQL, product.getId(), product.getName(), product.getUrl(), product.getImage(), product.getPrice(), product.getOriginalPrice(), product.getRatingScore());
    }
    
    public void createIfNotExist(Product product) {
        Product product1 = this.getProduct(product.getId());
        if(product1==null)
            this.create(product);
    }
    
    public void creates(List<Product> products){
        for (Product product : products) {
            this.create(product);
        }
    }
    
    public void createsIfNotExist(List<Product> products){
        for (Product product : products) {
            this.createIfNotExist(product);
        }
    }

    public Product getProduct(String id) {
        String SQL = "select * from Products where id = ?";
        try{
            Product product = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new ProductMapper());
            return product;
        } catch(DataAccessException e){
            return null;
        }
    }

    public List<Product> listProducts() {
        String SQL = "select * from Products";
        List<Product> students = jdbcTemplateObject.query(SQL, new ProductMapper());
        return students;
    }

    public void delete(String id) {
        String SQL = "delete from Products where id = ?";
        jdbcTemplateObject.update(SQL, id);
    }
    
    public List<Product> searchProducts(String keyword, int minPrice, int maxPrice, int ratingScore, String order) {
        if(minPrice < 0){
            minPrice = 0;
        }
        
        String SQL = "select * from Products where name like ? collate utf8_unicode_ci AND price >= ?";
        Object[] params = new Object[]{"%"+keyword+"%", minPrice};
        
        if(maxPrice >= 0){
            SQL += " AND price <= ?";
            params = MyUtils.concat(params, new Object[]{maxPrice});
        }
        
        if (ratingScore >= 0) {
            SQL += " AND FLOOR(ratingScore) = ?";
            params = MyUtils.concat(params, new Object[]{ratingScore});
        }
        
        switch(order){
            case "2":
                SQL += " order by price desc";
                break;
            case "3":
                SQL += " order by ratingScore desc, price asc";
                break;
            case "4":
                SQL += " order by ratingScore asc, price asc";
                break;
            default:
                SQL += " order by price asc";
                break;
        }
        
        List<Product> students = jdbcTemplateObject.query(SQL, params, new ProductMapper());
        return students;
    }
}