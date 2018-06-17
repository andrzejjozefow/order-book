package pl.andrzejjozefow.orderbook;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {
    private final  User user;
    private final BigDecimal price;
    private Integer quantity;
    private final OrderType orderType;

    public static Order bid(final User user, final BigDecimal price, final Integer quantity) {
        return new Order(user, price, quantity, OrderType.BID);
    }

    public static Order ask(final User user, final BigDecimal price, final Integer quantity) {
        return new Order(user, price, quantity, OrderType.ASK);
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f", user, quantity, price);
    }

    public enum OrderType {
        BID, ASK;
    }
}
