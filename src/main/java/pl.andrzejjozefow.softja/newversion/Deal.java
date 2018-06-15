package pl.andrzejjozefow.softja.newversion;

import lombok.Data;

@Data
public class Deal {
    final User buyer;
    final User seller;
    final Double price;
    final Integer quantity;

    @Override
    public String toString() {
        return buyer + " -> " +
            seller +
            " " + quantity +
            " " + price;
    }
}