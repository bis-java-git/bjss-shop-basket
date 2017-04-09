package shopping;

import shopping.service.checkout.PriceBasketImpl;

import java.util.List;

/**
 * Main Application to run shopping basket
 */
public class Application {

    /**
     * Entry Point for Shopping Basket
     *
     * @param args item list
     */
    public static void main(String... args) {
        PriceBasketImpl priceBasketImpl = new PriceBasketImpl(args);
        List<String> errorList = priceBasketImpl.validate();
        if (errorList.size() != 0) {
            System.out.println(errorList);
        } else {
            priceBasketImpl.checkOut();
        }
    }
}
