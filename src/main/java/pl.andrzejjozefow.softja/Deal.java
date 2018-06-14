package pl.andrzejjozefow.softja;

import java.math.BigDecimal;

public class Deal {

    private String sellerName;
    private String buyerName;
    private Integer quantity;
    private BigDecimal price;

    public Deal() {
    }

    public Deal(String sellerName, String buyerName, Integer quantity, BigDecimal price) {
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return buyerName + " -> " +
            sellerName +
            " " + quantity +
            " " + price;
    }
}
