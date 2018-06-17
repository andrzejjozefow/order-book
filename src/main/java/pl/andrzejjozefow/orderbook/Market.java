package pl.andrzejjozefow.orderbook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Market {

    private final List<Deal> deals = new ArrayList<>();
    private final SortedSet<Order> bids = new TreeSet<>(
        Comparator.comparing(Order::getPrice).reversed()
    );
    private final SortedSet<Order> asks = new TreeSet<>(
        Comparator.comparing(Order::getPrice)
    );

    public void submitOrder(final Order order) {
        switch (order.getOrderType()) {
            case BID:
                submitBid(order);
                break;
            case ASK:
                submitAsk(order);
                break;
            default:
                throw new IllegalArgumentException("Illegal order:" + order);
        }
    }

    private void submitBid(final Order bid) {
        log.info("New buy order: {}", bid);
        for (Iterator<Order> askIterator = asks.iterator(); askIterator.hasNext(); ) {
            final Order ask = askIterator.next();
            if (isBidPriceEqualOrBiggerThanAskPrice(bid, ask)) {
                if (isBidQuantityEqualOrBiggerThanAskQuantity(bid, ask)) {
                    final Deal deal = new Deal(
                        bid.getUser(), ask.getUser(), ask.getPrice(), ask.getQuantity()
                    );
                    deals.add(deal);
                    log.info("New deal: {}", deal);
                    bid.setQuantity(bid.getQuantity() - ask.getQuantity());
                    askIterator.remove();
                } else {
                    final Deal deal = new Deal(
                        bid.getUser(), ask.getUser(), ask.getPrice(), bid.getQuantity()
                    );
                    deals.add(deal);
                    log.info("New deal: {}", deal);
                    ask.setQuantity(ask.getQuantity() - bid.getQuantity());
                    bid.setQuantity(0);
                    break;
                }
            } else {
                break;
            }
        }
        if (bid.getQuantity() > 0) {
            bids.add(bid);
        }
    }

    private void submitAsk(final Order ask) {
        log.info("New sell order: {}", ask);
        for (Iterator<Order> bidIterator = bids.iterator(); bidIterator.hasNext(); ) {
            final Order bid = bidIterator.next();
            if (isBidPriceEqualOrBiggerThanAskPrice(bid, ask)) {
                if (isBidQuantityEqualOrBiggerThanAskQuantity(bid, ask)) {
                    final Deal deal = new Deal(
                        bid.getUser(), ask.getUser(), ask.getPrice(), ask.getQuantity()
                    );
                    deals.add(deal);
                    log.info("New deal: {}", deal);
                    bid.setQuantity(bid.getQuantity() - ask.getQuantity());
                    ask.setQuantity(0);
                    break;
                } else {
                    final Deal deal = new Deal(
                        bid.getUser(), ask.getUser(), ask.getPrice(), bid.getQuantity()
                    );
                    deals.add(deal);
                    log.info("New deal: {}", deal);
                    ask.setQuantity(ask.getQuantity() - bid.getQuantity());
                    bid.setQuantity(0);
                    bidIterator.remove();
                    break;
                }
            } else {
                break;
            }
        }
        if (ask.getQuantity() > 0) {
            asks.add(ask);
        }
    }

    private boolean isBidQuantityEqualOrBiggerThanAskQuantity(final Order bid, final Order ask) {
        return bid.getQuantity() >= ask.getQuantity();
    }

    private boolean isBidPriceEqualOrBiggerThanAskPrice(final Order bid, final Order ask) {
        return ask.getPrice().compareTo(bid.getPrice()) <= 0;
    }
}

