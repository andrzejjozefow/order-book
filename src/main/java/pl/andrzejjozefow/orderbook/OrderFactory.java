package pl.andrzejjozefow.orderbook;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class OrderFactory {

    public Order getOrder(String line) {
            List<String> orderPieces = Arrays.asList(line.split(":"));
            return new Order(new User(orderPieces.get(1).trim()),
                BigDecimal.valueOf(Double.valueOf(orderPieces.get(3).trim())),
                Integer.valueOf(orderPieces.get(2).trim()));
        }
}
