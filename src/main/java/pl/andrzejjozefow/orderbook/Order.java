package pl.andrzejjozefow.orderbook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private final User user;
    private final Double price;
    private Integer quantity;


    @Override
    public String toString() {
        return " " + price;
    }
}
