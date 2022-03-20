package com.training.sofa.interview.cart.services;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.training.sofa.interview.cart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;

public class CartService {


    private final PromotionEngine promotionEngine;
    private final CartStorage cartStorage;
    
    @Autowired
    public CartService(PromotionEngine promotionEngine,CartStorage cartStorage){
    this.promotionEngine = promotionEngine;
    this.cartStorage = cartStorage;
    }

    /**
     * Add a quantity of a product to the cart and store the cart
     *
     * @param cartId
     *     The cart ID
     * @param productCode
     *     The product code
     * @param quantity
     *     Quantity must be added
     * @return True if card has been modified
     */
    public boolean add(UUID cartId, String productCode, int quantity) {
        boolean isOperationSuccessful = false;
        final Cart cart = cartStorage.loadCart(cartId);
        final Map<String, Integer> products = cart.getProducts();

        if(quantity > 0){
            products.merge(productCode, quantity, Integer::sum);
            isOperationSuccessful = true;
        }else if(quantity < 0){
            int oldProductQuantity = products.getOrDefault(productCode,0);
            int difference = oldProductQuantity + quantity;
            if(difference > 0){
                products.put(productCode,difference);
                isOperationSuccessful = true;
            }
        }

        if(isOperationSuccessful){
            Cart cartAfterPromotions = promotionEngine.apply(cart);
            cartStorage.saveCart(cartAfterPromotions);
        }

        return isOperationSuccessful;
    }

    /**
     * Set a quantity of a product to the cart and store the cart
     *
     * @param cartId
     *     The cart ID
     * @param productCode
     *     The product code
     * @param quantity
     *     The new quantity
     * @return True if card has been modified
     */
    public boolean set(UUID cartId, String productCode, int quantity) {
        final Cart cart = cartStorage.loadCart(cartId);
        final Map<String,Integer> products = cart.getProducts();

        if(quantity <= 0){
            products.remove(productCode);
        }else{
            Integer oldQuantity = products.getOrDefault(productCode,0);
            if(oldQuantity == quantity){
                return false;
            }
            products.put(productCode, quantity);
        }

        Cart cartAfterPromotions = promotionEngine.apply(cart);
        cartStorage.saveCart(cartAfterPromotions);
        return true;
    }

    /**
     * Return the card with the corresponding ID
     *
     * @param cartId
     * @return Cart
     */
    public Cart get(UUID cartId) {
        return cartStorage.loadCart(cartId);
    }
}
