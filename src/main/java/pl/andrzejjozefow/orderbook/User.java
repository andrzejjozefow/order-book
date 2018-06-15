package pl.andrzejjozefow.orderbook;

import lombok.Data;

@Data
public class User {
    final String name;

    @Override
    public String toString() {
        return name;
    }
}