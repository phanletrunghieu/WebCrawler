/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
public class SearchController {
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String index(ModelMap modelMap, @RequestParam(value = "s", defaultValue = " ", required = false) String keyword, @RequestParam(value = "page", required = false, defaultValue = "1") String page) throws UnsupportedEncodingException, IOException{
        JSONParser jsonParser = new JSONParser();
        
        //sendo
        String sendoUrl = "https://www.sendo.vn/tim-kiem?q=" + URLEncoder.encode(keyword, "UTF-8") + "&p=" + page;
        Document sendoDocument = Jsoup.connect(sendoUrl).get();
        Elements box_products = sendoDocument.select("div.box_product");
        JSONArray list_products = new JSONArray();
        for (Element box_product : box_products) {
            Element name = box_product.select("a.name_product").first();
            Element image = box_product.select("img.imgtodrag").first();
            Element current_price = box_product.select("span.current_price").first();
            if(name==null || current_price==null)
                continue;
            
            JSONObject priceJSON = new JSONObject();
            priceJSON.put("price", current_price.html());
            
            JSONObject productJSON = new JSONObject();
            productJSON.put("name", name.html());
            productJSON.put("offers", priceJSON);
            productJSON.put("url", name.attr("href"));
            productJSON.put("image", image.attr("src"));
            
            list_products.add(productJSON);
        }
        
        //lazada
        String lazadaUrl = "https://www.lazada.vn/catalog/?q=" + URLEncoder.encode(keyword, "UTF-8") + "&page=" + page;
        Document lazadaDocument = Jsoup.connect(lazadaUrl).get();
        Element lazada_last_script = lazadaDocument.select("script").last();
        String lazadaJsonStr = lazada_last_script.html();
        try {
            JSONObject lazadaJson = (JSONObject) jsonParser.parse(lazadaJsonStr);
            JSONArray lazada_list_products = (JSONArray) lazadaJson.get("itemListElement");
            list_products.addAll(lazada_list_products);
            
            System.out.println(list_products);
        } catch (ParseException ex) {}
        
        modelMap.put("keyword", keyword);
        modelMap.put("list_products", list_products);
        return "search";
    }
}
