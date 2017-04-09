package shopping.service.checkout;

import shopping.domain.Item;
import shopping.service.basket.ShoppingBasketCalculator;
import shopping.service.basket.ShoppingBasketCalculatorImpl;
import shopping.service.price.PriceReaderService;
import shopping.service.price.PriceReaderServiceImpl;

import java.text.NumberFormat;
import java.util.*;

/**
 * Checkout basket
 */
public class PriceBasketImpl implements PriceBasket {

    private List<String> shoppingList;

    private ShoppingBasketCalculator shoppingBasketCalculator = new ShoppingBasketCalculatorImpl();

    private PriceReaderService priceReaderService = new PriceReaderServiceImpl("pricelist.csv");

    private NumberFormat currencyFormat;

    /**
     * PriceBasket constructor to retain item list
     * @param args shopping list
     */
    public PriceBasketImpl(String[] args) {
        this.shoppingList = Arrays.asList(args);
        currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        currencyFormat.setMaximumFractionDigits(2);
        currencyFormat.setMinimumFractionDigits(2);
    }

    /**
     * Validate all the shopping items
     * @return true if item is valid otherwise false
     */
    public List<String> validate() {
        List<String> errorList = new ArrayList<>();

        for (String item : shoppingList) {
            if (!priceReaderService.validate(item)) {
                errorList.add("Item " + item + " not present\n");
            }
        }
        return errorList;
    }

    /**
     * Print normal price
     * @param totalNormalPrice item normal price
     */
    private void printNormalPrice(double totalNormalPrice) {
        System.out.printf("Subtotal: %s\n", currencyFormat.format(totalNormalPrice));
    }

    /**
     * Prints discounted price list
     * @param totalNormalPriceMap discounted price map
     */
    private void printDiscountedPrice(Map<Item, List<Double>> totalNormalPriceMap) {
        totalNormalPriceMap.entrySet().forEach(dp -> dp.getValue().forEach(v -> {
             System.out.printf("%s %s%% off -%dp\n", dp.getKey().getName(), dp.getKey().getDiscount(), (int) (v * 100.0));
         }));
    }

    /**
     * Prints discounted price list
     * @param totalNormalPriceMap discounted price map
     */
    private void printDiscountedRulePrice(Map<Item, List<Double>> totalNormalPriceMap) {
        totalNormalPriceMap.entrySet().forEach(dp -> dp.getValue().forEach(v -> {
            System.out.printf("%s %s%% off -%dp\n", dp.getKey().getAlternateItemName(), dp.getKey().getDiscount(), (int) (v * 100.0));
        }));
    }

    /**
     * Prints final price
     * @param discountPrice    discounted price
     * @param totalNormalPrice total price
     */
    private void printFinalPrice(Double discountPrice, Double totalNormalPrice) {
        if (discountPrice==0) {
            System.out.println("No offers available.");
        }
        System.out.printf("Total is %s \n", currencyFormat.format(totalNormalPrice - discountPrice));
    }

    /**
     * Checks out all the items
     * @return final price to pay
     */
    public double checkOut() {
        double totalNormalPrice = shoppingBasketCalculator.calculateTotalPrice(priceReaderService.getItemMap(), shoppingList);
        printNormalPrice(totalNormalPrice);
        Map<Item, List<Double>> discountedPriceMap = shoppingBasketCalculator.calculateDiscountPriceList(priceReaderService.getItemMap(), shoppingList);
        Map<Item, List<Double>> discountedRulePriceMap = shoppingBasketCalculator.calculateDiscountRule1PriceList(priceReaderService.getItemMap(), shoppingList);
        Double basicDiscountPrice = shoppingBasketCalculator.calculateDiscountedPrice(discountedPriceMap);
        Double discountRulePrice = shoppingBasketCalculator.calculateDiscountedPrice(discountedRulePriceMap);
        printDiscountedPrice(discountedPriceMap);
        printDiscountedRulePrice(discountedRulePriceMap);
        printFinalPrice(basicDiscountPrice + discountRulePrice, totalNormalPrice);
        return totalNormalPrice - (basicDiscountPrice + discountRulePrice);
    }
}
