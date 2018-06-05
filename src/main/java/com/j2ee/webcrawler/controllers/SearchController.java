/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2ee.webcrawler.controllers;

import com.j2ee.webcrawler.models.Product;
import com.j2ee.webcrawler.models.ProductDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
            @RequestParam(value = "minPrice", defaultValue = "0", required = false) String minPrice,
            @RequestParam(value = "maxPrice", defaultValue = "-1", required = false) String maxPrice,
            @RequestParam(value = "ratingScore", defaultValue = "-1", required = false) String ratingScore,
            @RequestParam(value = "order", defaultValue = "1", required = false) String order,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page
    ) throws UnsupportedEncodingException, IOException{
        JSONParser jsonParser = new JSONParser();
        List<Product> list_products = new ArrayList<>();
        
        //sendo
        String sendoUrl = "https://www.sendo.vn/tim-kiem?q=" + URLEncoder.encode(keyword, "UTF-8") + "&p=" + page;
        Document sendoDocument = Jsoup.connect(sendoUrl).get();
        Elements box_products = sendoDocument.select("div.box_product");
        for (Element box_product : box_products) {
            Element name = box_product.select("a.name_product").first();
            Element image = box_product.select("img.imgtodrag").first();
            Element current_price = box_product.select("span.current_price").first();
            Element old_price = box_product.select("span.old_price").first();
            
            if(name==null)
                continue;
            
            Product product = new Product();
            product.setId("sendo"+box_product.attr("id"));
            product.setName(name.html());
            product.setUrl(name.attr("href"));
            product.setImage(image.attr("src"));
            
            if (current_price!=null) {
                String price = current_price.html().replaceAll(",", "").replaceAll("\\.", "");
                price = price.substring(0, price.length()-2);
                
                product.setPrice(Integer.parseInt(price));
                
            }
            
            if(old_price!=null){
                String originalPrice = old_price.html().replaceAll(",", "").replaceAll("\\.", "");
                originalPrice = originalPrice.substring(0, originalPrice.length()-2);
                
                product.setOriginalPrice(Integer.parseInt(originalPrice));
            } else if(current_price!=null){
                product.setOriginalPrice(product.getPrice());
            }
            
            list_products.add(product);
        }
        
        //lazada
        String lazadaUrl = "https://www.lazada.vn/catalog/?q=" + URLEncoder.encode(keyword, "UTF-8") + "&page=" + page;
        Document lazadaDocument = Jsoup.connect(lazadaUrl).get();
        String lazadaHTML = lazadaDocument.html();
        int dataIndex1 = lazadaHTML.indexOf("window.pageData=")+"window.pageData=".length();
        int dataIndex2 = lazadaHTML.indexOf("</script>", dataIndex1);
        
        String lazadaJsonStr = lazadaHTML.substring(dataIndex1, dataIndex2);
        try {
            JSONObject lazadaJson = (JSONObject) jsonParser.parse(lazadaJsonStr);
            JSONArray lazada_list_products = (JSONArray) ((JSONObject) lazadaJson.get("mods")).get("listItems");
            
            for (Object lazada_product : lazada_list_products) {
                JSONObject x = (JSONObject)lazada_product;
                
                String price = ((String)x.get("priceShow")).replaceAll(",", "").replaceAll("\\.", "");
                price = price.substring(0, price.length()-2);
                
                String originalPrice = (String)x.get("originalPrice");
                if(originalPrice==null)
                    originalPrice = price;
                else
                    originalPrice = originalPrice.substring(0, originalPrice.indexOf("."));
                
                Product product = new Product();
                product.setId("lazada"+x.get("nid"));
                product.setName(x.get("name").toString());
                product.setImage(x.get("image").toString());
                product.setPrice(Integer.parseInt(price));
                product.setOriginalPrice(Integer.parseInt(originalPrice));
                product.setUrl(x.get("productUrl").toString());
                product.setRatingScore(Float.parseFloat(x.get("ratingScore").toString()));
                list_products.add(product);
            }
            
            System.out.println(list_products);
        } catch (ParseException ex) {}
        
        //lưu vào db
        productDAO.createsIfNotExist(list_products);
        
        //query db
        int iMinPrice = Integer.parseInt(minPrice);
        int iMaxPrice = Integer.parseInt(maxPrice);
        int iRatingScore = Integer.parseInt(ratingScore);
        
        List<Product> searchProducts = productDAO.searchProducts(keyword, iMinPrice, iMaxPrice, iRatingScore, order);
        
        modelMap.put("keyword", keyword);
        modelMap.put("list_products", searchProducts);
        
        
        return "search";
    }
}
