package shopping.service.basket;

import shopping.domain.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Shopping Calculator
 */
public class ShoppingBasketCalculatorImpl implements ShoppingBasketCalculator {

    private static final String PRICING_RULE_0 = "0";

    private static final String PRICING_RULE_1 = "1";

    /**
     * Calculate total basket price
     * @param itemMap All the items
     * @param itemList shopping list
     * @return total price
     */
    @Override
    public double calculateTotalPrice(Map<String, Item> itemMap, List<String> itemList) {
        return itemList.stream().mapToDouble(i -> itemMap.get(i).getPrice()).sum();
    }

    /**
     * Get list of discounted items
     * @param itemMap All the items
     * @param itemList shopping list
     * @return discounted items
     */
    @Override
    public Map<Item, List<Double>> calculateDiscountPriceList(Map<String, Item> itemMap, List<String> itemList) {
        Map<Item, List<Double>> discountedMap = new HashMap<>();
        List<String> discountItemList = itemList.stream().filter(i -> itemMap.get(i).getDiscount() > 0 && itemMap.get(i).getRule().equals(PRICING_RULE_0)).collect(Collectors.toList());
        for (String itemName : discountItemList) {
            List<Double> value = discountedMap.computeIfAbsent(itemMap.get(itemName), k -> new ArrayList<>());
            value.add(itemMap.get(itemName).getDiscount() * itemMap.get(itemName).getPrice() / 100);
        }
        return discountedMap;
    }

    /**
     * Gets total discount
     * @param discountedList discounted list
     * @return total discounted price
     */
    public double calculateDiscountedPrice(Map<Item, List<Double>> discountedList) {
        return discountedList.entrySet().stream().mapToDouble(v -> v.getValue().stream().reduce(0.0, Double::sum)).sum();
    }

    /**
     * Get list of discounted items based on the rule
     * @param itemMap All the items
     * @param itemList shopping list
     * @return discounted items
     */
    @Override
    public Map<Item, List<Double>> calculateDiscountRule1PriceList(Map<String, Item> itemMap, List<String> itemList) {
        Map<Item, List<Double>> discountedMap = new HashMap<>();
        List<String> discountAlternateItemList = itemList.stream().filter(i -> itemMap.get(i).getDiscount() > 0
                && itemMap.get(i).getRule().equals(PRICING_RULE_1)  && itemList.contains(itemMap.get(i).getAlternateItemName())).distinct().collect(Collectors.toList());

        for (String itemName : discountAlternateItemList) {
            long totalAlternateNameItemsCount = itemList.stream().filter(i -> itemMap.get(i).getDiscount() > 0
                    && itemMap.get(i).getRule().equals(PRICING_RULE_1)).count();

            int quantityCountForDiscount = (int) (totalAlternateNameItemsCount / itemMap.get(itemName).getAlternateItemQuantity());

            for (int count = 0; count < quantityCountForDiscount; count++) {
                List<Double> discountList = discountedMap.computeIfAbsent(itemMap.get(itemMap.get(itemName).getName()), k -> new ArrayList<>());
                discountList.add(itemMap.get(itemName).getDiscount() * itemMap.get(itemMap.get(itemName).getAlternateItemName()).getPrice() / 100);
            }
        }
        return discountedMap;
    }
}
