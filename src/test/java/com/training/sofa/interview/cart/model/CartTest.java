package com.training.sofa.interview.cart.model;

import static com.training.sofa.interview.cart.matcher.CartMatcher.containProduct;
import static java.util.UUID.randomUUID;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.training.sofa.interview.cart.matcher.CartMatcher;

public class CartTest {

    private final String productCode = randomUUID().toString();

    @Test
    public void empty() {
        Cart c = new Cart();
        assertThat(c, CartMatcher.emptyCart());
    }

    @Test
    public void product() {
        Map<String, Integer> products = ImmutableMap.<String, Integer>builder().put(productCode, 1).build();
        Cart cart = new Cart(products);
        assertThat(cart, CartMatcher.containProduct(productCode, 1));
    }

    @Test
    public void to_string() {
        Map<String, Integer> products = ImmutableMap.<String, Integer>builder().put("5555", 10).build();
        Cart cart = new Cart(products);
        assertThat(cart.toString(), is("Cart {id: " + cart.getId() + ", products: {5555=10}}"));
    }
}
