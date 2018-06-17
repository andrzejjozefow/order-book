package pl.andrzejjozefow.orderbook;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private User user;
    private BigDecimal price;
    private Integer quantity;


    @Override
    public String toString() {
        return String.format("%s %s %.2f", user, quantity, price);
    }
}
