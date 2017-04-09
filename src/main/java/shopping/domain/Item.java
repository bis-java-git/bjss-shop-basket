package shopping.domain;

import java.util.Objects;

import static java.util.Objects.*;

/**
 * Item to hold item name, item price and item discount
 */
public final class Item {

    /**
     * Item name
     */
    private final String name;

    /**
     * Item price
     */
    private final double price;

    /**
     * Any special rule
     */
    private final String rule;

    /**
     * Discount related to alternate product
     */
    private final String alternateItemName;

    /**
     * Discount applied on quantity
     */
    private final Integer alternateItemQuantity;

    /**
     * Discount applied on item
     */
    private final double discount;

    public Item (String name,
                 double price,
                 double discount,
                 String rule,
                 String alternateItemName,
                 Integer alternateItemQuantity )
    {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.rule=rule;
        this.alternateItemName=alternateItemName;
        this.alternateItemQuantity=alternateItemQuantity;
    }

    public Item(String name,
                double price,
                double discount) {
        this(name,price, discount, "0", "", 0);
    }

    public Item(String name,
                double price
                ) {
        this(name,price,0);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public String getRule() {
        return rule;
    }

    public String getAlternateItemName() {
        return alternateItemName;
    }

    public Integer getAlternateItemQuantity() {
        return alternateItemQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Double.compare(item.getPrice(), getPrice()) == 0 &&
                Double.compare(item.getDiscount(), getDiscount()) == 0 &&
                Objects.equals(getName(), item.getName()) &&
                Objects.equals(getRule(), item.getRule()) &&
                Objects.equals(getAlternateItemName(), item.getAlternateItemName()) &&
                Objects.equals(getAlternateItemQuantity(), item.getAlternateItemQuantity());
    }

    @Override
    public int hashCode() {
        return hash(getName(), getPrice(), getRule(), getAlternateItemName(), getAlternateItemQuantity(), getDiscount());
    }

    @Override
    public String toString() {
        return "Item{" + "name='" + name + '\'' +
                ", price=" + price +
                ", rule='" + rule + '\'' +
                ", alternateItemName='" + alternateItemName + '\'' +
                ", alternateItemQuantity=" + alternateItemQuantity +
                ", discount=" + discount +
                '}';
    }
}
