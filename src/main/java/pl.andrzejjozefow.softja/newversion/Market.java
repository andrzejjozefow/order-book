package pl.andrzejjozefow.softja.newversion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import lombok.Data;

@Data
public class Market {

    final List<Deal> deals = new ArrayList<>();
    final PriorityQueue<Order> bids = new PriorityQueue<>(
        Comparator.comparingDouble(Order::getPrice).reversed()
    );
    final PriorityQueue<Order> asks = new PriorityQueue<>(
        Comparator.comparingDouble(Order::getPrice)
    );

    public void submitBid(final Order bid){
        for(Iterator<Order> askIterator = asks.iterator(); askIterator.hasNext();){
            final Order ask = askIterator.next();
            if(ask.getPrice() <= bid.getPrice()){
                if(bid.getQuantity() >= ask.getQuantity()){
                    deals.add(new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), ask.getQuantity()));
                    bid.setQuantity(bid.getQuantity() - ask.getQuantity());
                    askIterator.remove();
                    break;
                } else {
                    deals.add(new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), bid.getQuantity()));
                    ask.setQuantity(ask.getQuantity() - bid.getQuantity());
                    bid.setQuantity(0);
                }
            }
        }
        if(bid.getQuantity() > 0) {
            bids.add(bid);
        }
    }
    public void submitAsk(final Order ask){
        asks.add(ask);
    }
}