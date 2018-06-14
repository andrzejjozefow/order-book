package pl.andrzejjozefow.softja;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        List<String> orders = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get("src/zlecenia.txt"))) {
            orders = stream
                .filter(line -> !line.startsWith("#"))
                .filter(line -> !line.contentEquals(""))
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<BuyOrder> buyOrders = mapToBuyOrder(orders);
        List<SellOrder> sellOrders = mapToSellOrder(orders) ;


        final Market market = new Market();
        List<Deal> deals = market.getDeals(buyOrders, sellOrders);
        deals.forEach(System.out::println);


    }

    private static List<SellOrder> mapToSellOrder(List<String> zlecenia) {
        List<String> sellOrders = zlecenia.stream()
            .filter(line -> line.startsWith("S"))
            .collect(Collectors.toList());
        List<SellOrder> orders = new ArrayList<>();

        for(String a:sellOrders){
            List<String> orderPieces = Arrays.asList(a.split(":"));
            final SellOrder sellOrder = new SellOrder(
                orderPieces.get(1).trim(),
                Integer.valueOf(orderPieces.get(2).trim()),
                BigDecimal.valueOf(Double.valueOf(orderPieces.get(3).trim()))
            );
            orders.add(sellOrder);
        }
        return orders;
    }

    private static List <BuyOrder> mapToBuyOrder(List<String> zlecenia) {
        List<String> buyOrders = zlecenia.stream()
            .filter(line -> line.startsWith("K"))
            .collect(Collectors.toList());
        List<BuyOrder> orders = new ArrayList<>();
        for(String a:buyOrders){
            List<String> orderPieces = Arrays.asList(a.split(":"));
            final BuyOrder buyOrder = new BuyOrder(
                orderPieces.get(1).trim(),
                Integer.valueOf(orderPieces.get(2).trim()),
                BigDecimal.valueOf(Double.valueOf(orderPieces.get(3).trim()))
            );
            orders.add(buyOrder);
        }
        return orders;
    }

//    private static Function<String, Order> mapLineToOrder = new Function<String, Order>() {
//
//        public Order apply(String line) {
//
//            Order order = new Order();
//
//            List<String> orderPieces = Arrays.asList(line.split(":"));
//
//            order.setName(orderPieces.get(0));
//            order.setQuantity(Integer.valueOf(orderPieces.get(1)));
//            order.setPrice(Double.parseDouble(orderPieces.get(2)));
//
//            return order;
//        }
//    };

}
