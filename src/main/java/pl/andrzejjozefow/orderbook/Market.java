package pl.andrzejjozefow.orderbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class Market {

    private final List<Deal> deals = new ArrayList<>();
    private final SortedSet<Order> bids = new TreeSet<>(
        Comparator.comparing(Order::getPrice).reversed()
    );
    private final SortedSet<Order> asks = new TreeSet<>(
        Comparator.comparing(Order::getPrice)
    );

    public void submitBid(final Order bid) {
        log.info("New buy order: " + bid);
        for (Iterator<Order> askIterator = asks.iterator(); askIterator.hasNext(); ) {
            final Order ask = askIterator.next();
            if (isBidPriceEqualOrBiggerThanAskPrice(bid, ask)) {
                if (isBidQuantityEqualOrBiggerThanAskQuantity(bid, ask)) {
                    final Deal deal = new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), ask.getQuantity());
                    deals.add(deal);
                    log.info("New deal: " + deal);
                    bid.setQuantity(bid.getQuantity() - ask.getQuantity());
                    askIterator.remove();
                } else {
                    final Deal deal = new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), bid.getQuantity());
                    deals.add(deal);
                    log.info("New deal: " + deal);
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

    public void submitAsk(final Order ask) {
        log.info("New sell order" + ask);
        for (Iterator<Order> bidIterator = bids.iterator(); bidIterator.hasNext(); ) {
            final Order bid = bidIterator.next();
            if (isBidPriceEqualOrBiggerThanAskPrice(bid, ask)) {
                if (isBidQuantityEqualOrBiggerThanAskQuantity(bid, ask)) {
                    final Deal deal = new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), ask.getQuantity());
                    deals.add(deal);
                    log.info("New deal: " + deal);
                    bid.setQuantity(bid.getQuantity() - ask.getQuantity());
                    ask.setQuantity(0);
                    break;
                } else {
                    final Deal deal = new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), bid.getQuantity());
                    deals.add(deal);
                    log.info("New deal: " + deal);
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

    private boolean isBidQuantityEqualOrBiggerThanAskQuantity(Order bid, Order ask) {
        return bid.getQuantity() >= ask.getQuantity();
    }

    private boolean isBidPriceEqualOrBiggerThanAskPrice(Order bid, Order ask) {
        return ask.getPrice().compareTo(bid.getPrice()) <= 0;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public Collection<Order> getBids() {
        return bids;
    }

    public Collection<Order> getAsks() {
        return asks;
    }
}

