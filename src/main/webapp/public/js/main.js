/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$( document ).ready(function() {
    $("#formControlOrder").on("change", (e)=>{
        var httpQuery = $.param({
           s: $.urlParam("s"),
           minPrice: $.urlParam("minPrice"),
           maxPrice: $.urlParam("maxPrice"),
           ratingScore: $.urlParam("ratingScore"),
           order: e.target.value,
        });
        
        window.location = window.location.pathname + "?" + httpQuery;
    })
    
    $("#btnChangeFilterPrice").on("click", (e)=>{
        var minPrice = $("#inputMinPrice").val();
        var maxPrice = $("#inputMaxPrice").val();
        
        var httpQuery = $.param({
           s: $.urlParam("s"),
           minPrice: minPrice,
           maxPrice: maxPrice,
           ratingScore: $.urlParam("ratingScore"),
           order: $.urlParam("order"),
        });
        
        window.location = window.location.pathname + "?" + httpQuery;
    })
    
    $(".filter .rating").on("click", e=>{
        var ratingScore=$(e.currentTarget).data("start");
        
        var httpQuery = $.param({
           s: $.urlParam("s"),
           minPrice: $.urlParam("minPrice"),
           maxPrice: $.urlParam("maxPrice"),
           ratingScore: ratingScore,
           order: $.urlParam("order"),
        });
        
        window.location = window.location.pathname + "?" + httpQuery;
    })
    
    $.urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results == null) {
            return null;
        } else {
            results[1] = results[1].replace(/\+/g, '%20');
            return decodeURI(results[1]) || null;
        }
    }
});