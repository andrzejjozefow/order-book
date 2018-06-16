package pl.andrzejjozefow.orderbook;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Deal {
    final User buyer;
    final User seller;
    final BigDecimal price;
    final Integer quantity;

    @Override
    public String toString() {
        return buyer + " -> " +
            seller +
            " " + quantity +
            " " + String.format("%.2f", price);
    }
}