/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
    public String index(ModelMap modelMap, @RequestParam(value = "s", required = false) String keyword) throws IOException, ParseException {
        Document document = Jsoup.connect("https://www.lazada.vn/catalog/?q=" + URLEncoder.encode(keyword, "UTF-8")).get();
        Element last_script = document.select("script").last();
        String jsonStr = last_script.html();
        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(jsonStr);
        JSONArray list_products = (JSONArray) json.get("itemListElement");
        
        System.out.println(list_products);
        
        modelMap.put("keyword", keyword);
        modelMap.put("list_products", list_products);
        return "search";
    }
}
