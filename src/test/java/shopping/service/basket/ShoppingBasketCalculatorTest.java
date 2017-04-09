package shopping.service.basket;

import org.junit.Test;
import shopping.service.basket.ShoppingBasketCalculatorImpl;
import shopping.service.price.PriceReaderServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

/**
 * Test Shopping basket with number of items
 */
public class ShoppingBasketCalculatorTest {

    private ShoppingBasketCalculatorImpl shoppingBasket = new ShoppingBasketCalculatorImpl();

    @Test
    public void calculatePriceTest() {
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl("testpricelist.csv");
        double price = shoppingBasket.calculateTotalPrice(priceReaderService.getItemMap(), Arrays.asList("Apples", "Milk", "Bread"));
        assertThat(price, closeTo(3.10, 0.00001));
    }

    @Test
    public void calculateWithDiscountPriceBasicRuleTest() {
        List<String> itemList = Arrays.asList("Apples", "Apples", "Milk", "Bread");
        ShoppingBasketCalculatorImpl shoppingBasket = new ShoppingBasketCalculatorImpl();
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl("testdiscountedpricelist.csv");
        double price = shoppingBasket.calculateTotalPrice(priceReaderService.getItemMap(), itemList);
        assertThat(price, closeTo(4.10, 0.00001));
        double discountedPrice = shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountPriceList(priceReaderService.getItemMap(), itemList));
        assertThat(price - discountedPrice, closeTo(3.90, 0.00001));
    }

    @Test
    public void calculateWithDiscountPriceRule1Test() {
        List<String> itemList = Arrays.asList("Soup", "Apples", "Milk", "Bread");
        ShoppingBasketCalculatorImpl shoppingBasket = new ShoppingBasketCalculatorImpl();
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl("testdiscountedpricelist.csv");
        double price = shoppingBasket.calculateTotalPrice(priceReaderService.getItemMap(), itemList);
        assertThat(price, closeTo(3.75, 0.00001));

        double discountedPrice = shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountPriceList(priceReaderService.getItemMap(), itemList));
        assertThat(price - discountedPrice, closeTo(3.65, 0.00001));
    }

    @Test
    public void calculateWithDiscountPriceRule1WithOddItemsTest() {
        ShoppingBasketCalculatorImpl shoppingBasket = new ShoppingBasketCalculatorImpl();
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl("testdiscountedpricelist.csv");

        List<String> itemList = Arrays.asList("Soup", "Soup", "Apples", "Milk", "Bread");
        double totalPrice = shoppingBasket.calculateTotalPrice(priceReaderService.getItemMap(), itemList);
        assertThat(totalPrice, closeTo(4.40, 0.00001));
        double discountedPrice = shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountPriceList(priceReaderService.getItemMap(), itemList));
        discountedPrice = discountedPrice + shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountRule1PriceList(priceReaderService.getItemMap(), itemList));
        assertThat(totalPrice - discountedPrice, closeTo(3.90, 0.00001));

        itemList = Arrays.asList("Soup", "Soup", "Soup", "Apples", "Milk");
        totalPrice = shoppingBasket.calculateTotalPrice(priceReaderService.getItemMap(), itemList);
        assertThat(totalPrice, closeTo(4.25, 0.00001));
        discountedPrice = shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountPriceList(priceReaderService.getItemMap(), itemList));
        discountedPrice = discountedPrice + shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountRule1PriceList(priceReaderService.getItemMap(), itemList));
        assertThat(totalPrice - discountedPrice, closeTo(4.15, 0.00001));

        itemList = Arrays.asList("Soup", "Soup", "Soup", "Apples", "Milk", "Bread");
        totalPrice = shoppingBasket.calculateTotalPrice(priceReaderService.getItemMap(), itemList);
        assertThat(totalPrice, closeTo(5.05, 0.00001));
        discountedPrice = shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountPriceList(priceReaderService.getItemMap(), itemList));
        discountedPrice = discountedPrice + shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountRule1PriceList(priceReaderService.getItemMap(), itemList));
        assertThat(totalPrice - discountedPrice, closeTo(4.55, 0.00001));

        itemList  = Arrays.asList("Soup", "Soup", "Soup", "Soup", "Apples", "Milk", "Bread");
        totalPrice = shoppingBasket.calculateTotalPrice(priceReaderService.getItemMap(), itemList);
        assertThat(totalPrice, closeTo(5.70, 0.00001));
        discountedPrice = shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountPriceList(priceReaderService.getItemMap(), itemList));
        discountedPrice = discountedPrice + shoppingBasket.calculateDiscountedPrice(shoppingBasket.calculateDiscountRule1PriceList(priceReaderService.getItemMap(), itemList));
        assertThat(totalPrice - discountedPrice, closeTo(4.80, 0.00001));
    }
}
