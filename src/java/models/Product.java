/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Phan Hieu
 */
public class Product {
    private String id;
    private String name;
    private String url;
    private String image;
    private Integer price;
    private Integer originalPrice;
    private Float ratingScore;

    public Product() {
        this.ratingScore = 0f;
    }

    public Product(String id, String name, Integer price, Integer originalPrice, String url, String image, Float ratingScore) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.originalPrice = originalPrice;
        this.url = url;
        this.image = image;
        this.ratingScore = ratingScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }
    
    public String getPriceDisplay() {
        return price + "đ";
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Float getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(Float ratingScore) {
        this.ratingScore = ratingScore;
    }
    
    public Float getRatingScorePercent() {
        return ratingScore*100/5;
    }
}