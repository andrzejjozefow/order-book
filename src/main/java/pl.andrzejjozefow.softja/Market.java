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
        List<BuyOrder> buyOrdersTemp = new ArrayList<>();
        List<SellOrder> sellOrdersTemp = new ArrayList<>();
        buyOrdersTemp.addAll(buyOrders);
        sellOrdersTemp.addAll(sellOrders);

        for(BuyOrder buyOrderTemp: buyOrdersTemp) {
            for (SellOrder sellOrder : sellOrdersTemp) {
                if (sellOrder.getPrice().compareTo(buyOrderTemp.getPrice()) <=0 && sellOrder.getQuantity() > 0) {
                    if (buyOrderTemp.getQuantity() > 0 ) {
                        if (sellOrder.getQuantity() <= buyOrderTemp.getQuantity()) {
                            final Deal deal = new Deal(
                                sellOrder.getName(),
                                buyOrderTemp.getName(),
                                sellOrder.getQuantity(),
                                sellOrder.getPrice()
                            );
                            deals.add(deal);
                            buyOrderTemp.setQuantity(
                                buyOrderTemp.getQuantity() - sellOrder.getQuantity());
                            sellOrder.setQuantity(0);
                        } else {
                            final Deal deal = new Deal(
                                sellOrder.getName(),
                                buyOrderTemp.getName(),
                                buyOrderTemp.getQuantity(),
                                sellOrder.getPrice());
                            deals.add(deal);
                            sellOrder.setQuantity(
                                sellOrder.getQuantity() - buyOrderTemp.getQuantity());
                            buyOrderTemp.setQuantity(0);
                        }
                    }
                }
            }
        }
        return deals;
    }
}
