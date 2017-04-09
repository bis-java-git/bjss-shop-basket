package shopping.service.checkout;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Testing Price basket for validation
 */
public class PriceBasketTest {

    @Test
    public void validateTest() {
        PriceBasket priceBasket = new PriceBasketImpl(new String[] {"Apples", "Soup"});
        List<String> itemList = priceBasket.validate();
        assertThat(itemList, is (notNullValue()));
        assertThat(itemList, hasSize(0));
    }

    @Test
    public void validateWithErrorTest() {
        PriceBasket priceBasket = new PriceBasketImpl(new String[] {"Apples", "Sop"});
        List<String> itemList = priceBasket.validate();
        assertThat(itemList, is (notNullValue()));
        assertThat(itemList, hasSize(1));
    }

    @Test
    public void checkOutTest() {
        PriceBasket priceBasket = new PriceBasketImpl(new String[] {"Apples", "Soup"});
        double totalPrice = priceBasket.checkOut();
        assertThat(totalPrice, closeTo(1.55, 0.00001));
    }

    @Test
    public void checkOutWithNoDiscountTest() {
        PriceBasket priceBasket = new PriceBasketImpl(new String[] {"Soup", "Bread"});
        double totalPrice = priceBasket.checkOut();
        assertThat(totalPrice, closeTo(1.45, 0.00001));
    }

    @Test
    public void checkOutWithDiscountRule1Test() {
        PriceBasket priceBasket = new PriceBasketImpl(new String[] {"Apples", "Soup","Soup","Bread"});
        double totalPrice = priceBasket.checkOut();
        System.out.println("dddd "+totalPrice);
        assertThat(totalPrice, closeTo(2.60, 0.00001));
    }
}
