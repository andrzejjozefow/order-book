package pl.andrzejjozefow.orderbook;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.java.Log;


@Log
public class OrderFactory {

    public Optional<Order> getOrder(String line) {
            List<String> orderPieces = Arrays.asList(line.split(":"));
            try {
                String name = orderPieces.get(1).trim();
                BigDecimal price = BigDecimal.valueOf(Double.valueOf(orderPieces.get(3).trim()));
                Integer quantity = Integer.valueOf(orderPieces.get(2).trim());
                return Optional.of(new Order(new User(name), price, quantity));
            } catch (NumberFormatException e){
                log.warning("Wrong number format");
            }
            return Optional.empty();
        }
}
