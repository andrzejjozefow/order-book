package pl.andrzejjozefow.softja.newversion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private final User user;
    private final Double price;
    private Integer quantity;
}
