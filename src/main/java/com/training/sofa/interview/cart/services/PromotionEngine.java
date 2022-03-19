package com.training.sofa.interview.cart.services;


import java.util.Map;

import com.training.sofa.interview.cart.model.Cart;

/**
 * If one or more product with code "1000" is purchase, ONE product with code 9000 is offer
 * For each 10 products purchased a gift with code 7000 is offer.
 */
public class PromotionEngine {

    private static final String PROMOTION = "9000";
    private static final String PRODUCT_WITH_PROMOTION = "1000";
    private static final String GIFT = "7000";
    private static final int QUANTITY_PRODUCT_ELIGIBLE = 10;


    public Cart apply(Cart cart) {
        final Cart newCart = new Cart(cart.getId(), cart.getProducts());
        applyPromotion(newCart.getProducts());
        applyGifts(newCart.getProducts());
        return newCart;
    }

    /**
     * If one or more product with code "1000" is purchased, ONE product with code 9000 is offered
     */
    private void applyPromotion(Map<String, Integer> products){
        boolean productWithPromotionExists = products.containsKey(PRODUCT_WITH_PROMOTION);

        if(productWithPromotionExists){
            products.put(PROMOTION,1);
        }else{
            products.remove(PROMOTION);
        }

    }

    /**
     * For each 10 products purchased a gift with code 7000 is offered.
     */
    private void applyGifts(Map<String, Integer> products){
        int quantityOfGifts = 0;

        for(Map.Entry<String,Integer> product: products.entrySet()){
            String productCode = product.getKey();
            Integer productQuantity = product.getValue();
            if(!productCode.equals(GIFT)){
                int quotient = productQuantity / QUANTITY_PRODUCT_ELIGIBLE;
                if(quotient > 0){
                    quantityOfGifts += quotient;
                }
            }
        }

        if(quantityOfGifts > 0){
            products.put(GIFT, quantityOfGifts);
        }else{
            products.remove(GIFT);
        }

    }


}
