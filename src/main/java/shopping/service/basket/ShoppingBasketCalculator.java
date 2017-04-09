package shopping.service.basket;

import shopping.domain.Item;

import java.util.List;
import java.util.Map;

/**
 * Public interface shopping calculator
 */
public interface ShoppingBasketCalculator {


    /**
     * Calculate total basket price
     * @param itemMap All the items
     * @param itemList shopping list
     * @return total price
     */
    double calculateTotalPrice(Map<String, Item> itemMap, List<String> itemList);

    /**
     * Get list of discounted items
     * @param itemMap All the items
     * @param itemList shopping list
     * @return discounted items
     */
    Map<Item, List<Double>> calculateDiscountPriceList(Map<String, Item> itemMap, List<String> itemList);

    /**
     * Gets total discount
     * @param discountedList discounted list
     * @return total discounted price
     */
    double calculateDiscountedPrice(Map<Item, List<Double>> discountedList);

    /**
     * Get list of discounted items based on the rule
     * @param itemMap All the items
     * @param itemList shopping list
     * @return discounted items
     */
    Map<Item, List<Double>> calculateDiscountRule1PriceList(Map<String, Item> itemMap, List<String> itemList);
}
