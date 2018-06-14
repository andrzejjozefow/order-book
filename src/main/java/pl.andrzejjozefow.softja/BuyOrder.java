package pl.andrzejjozefow.softja;

import java.math.BigDecimal;

public class BuyOrder implements Order{

    private String name;
    private Integer quantity;
    private BigDecimal price;

    public BuyOrder(String name, Integer quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BuyOrder{" +
            "name='" + name + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            '}';
    }
}
