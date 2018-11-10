package com.shaff.carshop.db.entity;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CartTest {

    private static final int TEST_ID = 1;
    private static final String TEST_MARK = "Toyota";
    private static final String TEST_MODEL = "Supra";
    private static final String TEST_BODY_TYPE = "sedan";
    private static final int TEST_YEAR = 1996;
    private static final int TEST_MAX_SPEED = 260;
    private static final int TEST_PRICE = 22500;
    private Cart cart;
    private Car testCar = new Car(TEST_ID, TEST_MARK, TEST_MODEL, TEST_BODY_TYPE, TEST_YEAR, TEST_MAX_SPEED, new BigDecimal(TEST_PRICE));

    @Before
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void shouldReturnTrueWhenElementWasAdded() {
        int expectedSizeBefore = 0;
        int expectedSizeAfter = 1;

        assertEquals(expectedSizeBefore, cart.getCart().size());
        cart.addItemToCart(testCar);
        assertEquals(expectedSizeAfter, cart.getCart().size());
    }

    @Test
    public void shouldIncrementValueWhenTryToAddEqualElementToCart() {
        int expectedValueAfterFirstAdding = 1;
        int expectedValueAfterSecondAdding = 2;

        cart.addItemToCart(testCar);
        int returnedValueAfterFirstAdding = cart.getCart().get(testCar);
        assertEquals(expectedValueAfterFirstAdding, returnedValueAfterFirstAdding);

        cart.addItemToCart(testCar);
        int returnedValueAfterSecondAdding = cart.getCart().get(testCar);
        assertEquals(expectedValueAfterSecondAdding, returnedValueAfterSecondAdding);
    }

    @Test
    public void shouldReturnExpectedTotalCostWhenMethodWasCalled() {
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);
        BigDecimal expectedValue = new BigDecimal(45000);
        assertEquals(expectedValue, cart.getTotalCost());
    }

    @Test
    public void shouldReturnExpectedItemTotalCostWhenMethodWasCalled() {
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);

        int expectedCount = 4;
        assertEquals(expectedCount, cart.getTotalItemCount());
    }

    @Test
    public void shouldRemoveItemFromCartWhenMethodWasCalled() {
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);

        int expectedSizeBeforeRemoving = 1;
        assertEquals(expectedSizeBeforeRemoving, cart.getCart().size());
        cart.removeItemFromCart(testCar);

        int expectedSizeAfterRemoving = 0;
        int actualSize = cart.getCart().size();
        assertEquals(expectedSizeAfterRemoving, actualSize);
    }

    @Test
    public void shouldReduceItemQuantityWhenMethodWasCalled() {
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);
        cart.addItemToCart(testCar);

        int expectedQuantityBeforeReducing = cart.getCart().get(testCar);
        assertTrue(expectedQuantityBeforeReducing == 4);

        int expectedQuantityAfterReducing = 3;
        cart.reduceItemQuantity(testCar);
        int returnedQuantityAfterReducing = cart.getCart().get(testCar);
        assertEquals(expectedQuantityAfterReducing, returnedQuantityAfterReducing);
    }

    @Test
    public void shouldRemoveItemFromCartWhenQuantityIsEqualToOne() {
        cart.addItemToCart(testCar);

        int expectedSizeAfterReducing = 0;
        cart.reduceItemQuantity(testCar);
        int realSizeAfterReducing = cart.getCart().size();
        assertEquals(expectedSizeAfterReducing, realSizeAfterReducing);
    }
}