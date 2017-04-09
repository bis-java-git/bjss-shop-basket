package shopping.service.price;

import shopping.domain.Item;

import java.util.Map;

/**
 * Reads items with price and discount from comma separated file
 */
public interface PriceReaderService {

    /**
     * Validates if item name is valid
     * @param itemName item name
     * @return true if item contains in the main list of shop items
     */
    boolean validate(String itemName);

    /**
     * List of all the items data
     * @return list of all the items available
     */
    Map<String, Item> getItemMap();
}
