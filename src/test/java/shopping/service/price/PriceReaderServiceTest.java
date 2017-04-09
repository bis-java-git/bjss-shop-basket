package shopping.service.price;

import org.junit.Test;
import shopping.domain.Item;

import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * PriceReaderService test to check all the required fields inside the item can be collected
 */
public class PriceReaderServiceTest {

    private static final String GOOD_PRICE_LIST_FILENAME = "testpricelist.csv";

    private static final String DISCOUNTED_PRICE_LIST_FILENAME = "testdiscountedpricelist.csv";

    private static final String BAD_PRICE_LIST_FILENAME = "testerrorpricelist.csv";

    private static final String EMPTY_FILE_PRICE_LIST_FILENAME = "testemptyfile.csv";

    private static final String BAD_FILE_PRICE_LIST_FILENAME = "testbadpricelist.csv";

    private static final String BAD_FILENAME = "filenotfound-nullpointerexception.csv";

    private Item[] EXPECTED_ITEM_BASIC_RULE_LIST = new Item[]{new Item("Apples", 1.00),
            new Item("Milk", 1.30, 0, "0", "", 0),
            new Item("Bread", 0.80, 0, "0", "", 0)};

    private Item[] EXPECTED_ITEM_RULE_1_LIST = new Item[]{new Item("Apples", 1.00, 10, "0", "", 0),
            new Item("Milk", 1.30, 0, "0", "", 0),
            new Item("Bread", 0.80, 0, "0", "", 0),
            new Item("Soup", 0.65, 50, "1", "Bread", 2)};

    private Item[] EXPECTED_ERROR_ITEM_LIST = new Item[]{new Item("Apples", 1.20, 10, "0", "", 0),
            new Item("Banana", 1.24, 0, "0", "", 0),};

    @Test
    public void processBasicRuleTest() {
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl(GOOD_PRICE_LIST_FILENAME);
        Map<String, Item> itemMap = priceReaderService.getItemMap();
        assertThat(itemMap.size(), is(EXPECTED_ITEM_BASIC_RULE_LIST.length));
        assertThat(itemMap.values(), hasItems(EXPECTED_ITEM_BASIC_RULE_LIST));
    }

    @Test
    public void processRuleTest() {
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl(DISCOUNTED_PRICE_LIST_FILENAME);
        Map<String, Item> itemMap = priceReaderService.getItemMap();
        assertThat(itemMap.size(), is(EXPECTED_ITEM_RULE_1_LIST.length));
        assertThat(itemMap.values(), hasItems(EXPECTED_ITEM_RULE_1_LIST));
    }

    @Test
    public void processWithErrorTest() {
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl(BAD_PRICE_LIST_FILENAME);
        Map<String, Item> itemMap = priceReaderService.getItemMap();
        assertThat(itemMap.size(), is(EXPECTED_ERROR_ITEM_LIST.length));
        assertThat(itemMap.values(), hasItems(EXPECTED_ERROR_ITEM_LIST));
    }

    @Test
    public void processWithEmptyFileTest() {
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl(EMPTY_FILE_PRICE_LIST_FILENAME);
        Map<String, Item> itemMap = priceReaderService.getItemMap();
        assertThat(itemMap.values(), hasSize(0));
    }

    @Test
    public void processWithBadFileTest() {
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl(BAD_FILE_PRICE_LIST_FILENAME);
        Map<String, Item> itemMap = priceReaderService.getItemMap();
        assertThat(itemMap.size(), is(EXPECTED_ERROR_ITEM_LIST.length));
        assertThat(itemMap.values(), hasItems(EXPECTED_ERROR_ITEM_LIST));
    }

    @Test(expected = NullPointerException.class)
    public void processWithBadFileNameTest() {
        PriceReaderServiceImpl priceReaderService = new PriceReaderServiceImpl(BAD_FILENAME);
        Map<String, Item> itemMap = priceReaderService.getItemMap();
        assertThat(itemMap.values(), hasSize(0));
    }
}
