package shopping.service.price;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import shopping.domain.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Reads items with price and discount from comma separated file
 */
public class PriceReaderServiceImpl implements PriceReaderService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(PriceReaderServiceImpl.class);

    private static final String COMMA = ",";

    private static Integer MINIMUM_TOTAL_COLUMNS = 2;

    private static Integer BASIC_DISCOUNT_COLUMNS = 3;

    private static Integer RULE_1_DISCOUNT_COLUMNS = 6;

    private final String fileName;

    private Map<String, Item> itemMap;

    /**
     * List of all the items data
     * @return list of all the items available
     */
    public Map<String, Item> getItemMap() {
        return this.itemMap;
    }

    public PriceReaderServiceImpl(String fileName) {
        this.fileName = fileName;
        itemMap = process();
    }

    /**
     * Trim spaces
     * @param value item name
     * @return item name
     */
    private String getString(String value) {
        return value.trim();
    }

    /**
     * Converts value to Integer
     * @param value item value
     * @return converted value
     */
    private int getInteger(String value) {
        return Integer.parseInt(value.trim());
    }

    /**
     * Converts value to Double
     * @param value item value
     * @return converted value
     */
    private double getDouble(String value) {
        return Double.parseDouble(value.trim());
    }

    /**
     * Validates if item name is valid
     * @param itemName item name
     * @return true if item contains in the main list of shop items
     */
    public boolean validate(String itemName) {
        return itemMap.containsKey(itemName);
    }

    /**
     * Check whether value can be converted to double
     * @param value value
     * @return Boolean valid or no
     */
    private Boolean validateDouble(String value) {
        try {
            getDouble(value);
        } catch (NumberFormatException exc) {
            return false;
        }
        return true;
    }

    /**
     * Check whether value can be converted to integer
     * @param value value
     * @return Boolean valid or no
     */
    private Boolean validateInteger(String value) {
        try {
            getInteger(value);
        } catch (NumberFormatException exc) {
            return false;
        }
        return true;
    }

    /**
     * Retrieve item row by row
     */
    private Function<String, Item> mapToItem = (String line) -> {
        String[] p = line.split(COMMA);
        if (p.length < MINIMUM_TOTAL_COLUMNS) {
            logger.error("Number of required columns not present {}", Arrays.asList(p));
        } else if (p.length == MINIMUM_TOTAL_COLUMNS) {
            if (validateDouble(getString(p[1]))) {
                return new Item(getString(p[0]), getDouble(getString(p[1])));
            } else {
                logger.error("Price format is not valid {}", Arrays.asList(p));
            }
        } else if (p.length == BASIC_DISCOUNT_COLUMNS) {
            if (validateDouble(getString(p[1])) && validateInteger(getString(p[2]))) {
                return new Item(getString(p[0]), getDouble(getString(p[1])), getInteger(getString(p[2])));
            } else {
                logger.error("Price or discount format is not valid {}", Arrays.asList(p));
            }
        } else if (p.length == RULE_1_DISCOUNT_COLUMNS) {
            if (validateDouble(getString(p[1])) && validateDouble(getString(p[2])) && validateInteger(getString(p[5]))) {
                return new Item(getString(p[0]), getDouble(getString(p[1])), getDouble(getString(p[2])), getString(p[3]), getString(p[4]), getInteger(getString(p[5])));
            }
        }
        return null;
    };

    /**
     * Build item list dynamically
     * @return list of items
     */
    private Map<String, Item> process() {
        List<Item> itemList = new ArrayList<>();
        try (
                InputStream resourceAsStream = getClass().getResourceAsStream("/" + fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))
        ) {
            // skip the header of the csv
            Integer TOTAL_HEADER_ROWS = 1;
            itemList = br.lines().skip(TOTAL_HEADER_ROWS).map(mapToItem).filter(Objects::nonNull).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            logger.error("File error, cannot read products and their prices!");
        }
        logger.info("Final item list {} ", itemList);
        return itemList.stream().collect(Collectors.toMap(Item::getName, item -> item));
    }
}

