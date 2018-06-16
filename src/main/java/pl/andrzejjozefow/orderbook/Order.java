package pl.andrzejjozefow.orderbook;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private final User user;
    private final BigDecimal price;
    private Integer quantity;


    @Override
    public String toString() {
        return " " + price;
    }
}
