/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Phan Hieu
 */
public class ProductJDBC {

    private static final ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

    private JdbcTemplate jdbcTemplateObject;

    public ProductJDBC() {
        DataSource dataSource = (DataSource) this.context.getBean("dataSource");
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

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
        products.forEach((product) -> {
            this.create(product);
        });
    }
    
    public void createsIfNotExist(List<Product> products){
        products.forEach((product) -> {
            this.createIfNotExist(product);
        });
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
    
    public List<Product> searchProducts(String keyword) {
        String SQL = "select * from Products where name like %?%";
        List<Product> students = jdbcTemplateObject.query(SQL, new Object[]{keyword}, new ProductMapper());
        return students;
    }
}
