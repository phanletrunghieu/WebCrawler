/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2ee.webcrawler.controllers;

import com.j2ee.webcrawler.models.Image;
import com.j2ee.webcrawler.models.Product;
import com.j2ee.webcrawler.models.ProductDAO;
import com.j2ee.webcrawler.services.Crawl;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Phan Hieu
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {
    
    @Autowired
    private ProductDAO productDAO;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String index(
            ModelMap modelMap,
            @RequestParam(value = "s", defaultValue = " ", required = false) String keyword,
            @RequestParam(value = "deep", defaultValue = "10", required = false) String deep,
            @RequestParam(value = "minPrice", defaultValue = "0", required = false) String minPrice,
            @RequestParam(value = "maxPrice", defaultValue = "-1", required = false) String maxPrice,
            @RequestParam(value = "ratingScore", defaultValue = "-1", required = false) String ratingScore,
            @RequestParam(value = "order", defaultValue = "1", required = false) String order,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page
    )throws UnsupportedEncodingException, IOException{
        long crawlStartTime = System.currentTimeMillis();
        
        List<Product> list_products = Crawl.crawlProduct(keyword, page);
        
        //lưu vào db
        productDAO.createsIfNotExist(list_products);
        
        //query db
        int iMinPrice = Integer.parseInt(minPrice);
        int iMaxPrice = Integer.parseInt(maxPrice);
        int iRatingScore = Integer.parseInt(ratingScore);
        
        List<Product> searchProducts = productDAO.searchProducts(keyword, iMinPrice, iMaxPrice, iRatingScore, order);
        
        long crawlEndTime = System.currentTimeMillis();
        
        modelMap.put("keyword", keyword);
        modelMap.put("crawlTime", crawlEndTime - crawlStartTime);
        modelMap.put("list_products", searchProducts);
        
        
        return "search_web";
    }
    
    @RequestMapping(value = "/images", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String searchImage(
            ModelMap modelMap,
            @RequestParam(value = "s", defaultValue = " ", required = false) String keyword,
            @RequestParam(value = "deep", defaultValue = "10", required = false) String deep,
            @RequestParam(value = "minPrice", defaultValue = "0", required = false) String minPrice,
            @RequestParam(value = "maxPrice", defaultValue = "-1", required = false) String maxPrice,
            @RequestParam(value = "ratingScore", defaultValue = "-1", required = false) String ratingScore,
            @RequestParam(value = "order", defaultValue = "1", required = false) String order,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page
    )throws UnsupportedEncodingException, IOException{
        long crawlStartTime = System.currentTimeMillis();
        
        List<Image> list_images = Crawl.crawlImageLazada(keyword);
        
        long crawlEndTime = System.currentTimeMillis();
        
        modelMap.put("keyword", keyword);
        modelMap.put("crawlTime", crawlEndTime - crawlStartTime);
        modelMap.put("list_images", list_images);
        
        
        return "search_images";
    }
}
