package pl.andrzejjozefow.softja;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Market {

    private List<BuyOrder> buyOrders;
    private List<SellOrder> sellOrders;
    private List<User> users;

    public Market() {
    }

    public List<Deal> getDeals(List<BuyOrder> buyOrders, List<SellOrder> sellOrders){

        List<Deal> deals = new ArrayList<>();
        List<BuyOrder> bids = new ArrayList<>();
        List<SellOrder> asks = new ArrayList<>();
        bids.addAll(buyOrders);
        asks.addAll(sellOrders);

        for(BuyOrder bid: bids) {
            for (SellOrder ask : asks) {
                if (ask.getPrice().compareTo(bid.getPrice()) <=0 && ask.getQuantity() > 0) {
                    if (bid.getQuantity() > 0 ) {
                        if (ask.getQuantity() <= bid.getQuantity()) {
                            final Deal deal = new Deal(
                                ask.getName(),
                                bid.getName(),
                                ask.getQuantity(),
                                ask.getPrice()
                            );
                            deals.add(deal);
                            bid.setQuantity(
                                bid.getQuantity() - ask.getQuantity());
                            ask.setQuantity(0);
                        } else {
                            final Deal deal = new Deal(
                                ask.getName(),
                                bid.getName(),
                                bid.getQuantity(),
                                ask.getPrice());
                            deals.add(deal);
                            ask.setQuantity(
                                ask.getQuantity() - bid.getQuantity());
                            bid.setQuantity(0);
                        }
                    }
                }
            }
        }
        return deals;
    }
}
