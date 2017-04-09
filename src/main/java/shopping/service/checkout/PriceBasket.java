package shopping.service.checkout;

import java.util.List;

/**
 * Checkout basket
 */
public interface PriceBasket {

    /**
     * Checks out all the items
     * @return final price to pay
     */
    double checkOut();

    /**
     * Validate all the shopping items
     * @return true if item is valid otherwise false
     */
    List<String> validate();
}
