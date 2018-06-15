package pl.andrzejjozefow.orderbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class Market {

    final List<Deal> deals = new ArrayList<>();
    final Collection<Order> bids = new TreeSet<>(
        Comparator.comparingDouble(Order::getPrice).reversed()
    );
    final Collection<Order> asks = new TreeSet<>(
        Comparator.comparingDouble(Order::getPrice)
    );

    public void submitBid(final Order bid) {
        for (Iterator<Order> askIterator = asks.iterator(); askIterator.hasNext(); ) {
            final Order ask = askIterator.next();
            if (ask.getPrice() <= bid.getPrice()) { //TODO wydzielić funkcje
                if (bid.getQuantity() >= ask.getQuantity()) {  //TODO wydzielić funkcje
                    deals.add(
                        new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), ask.getQuantity())
                    );
                    bid.setQuantity(bid.getQuantity() - ask.getQuantity());
                    askIterator.remove();
                } else {
                    deals.add(
                        new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), bid.getQuantity())
                    );
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
        for (Iterator<Order> bidIterator = bids.iterator(); bidIterator.hasNext(); ) {
            final Order bid = bidIterator.next();
            if (ask.getPrice() <= bid.getPrice()) {
                if (bid.getQuantity() >= ask.getQuantity()) {
                    deals.add(
                        new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), ask.getQuantity()));
                    bid.setQuantity(bid.getQuantity() - ask.getQuantity());
                    ask.setQuantity(0);
                    break;
                } else {
                    deals.add(
                        new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), bid.getQuantity()));
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

    @SneakyThrows(IOException.class)
    public void performOrdersFromTxtFile(String path) { //TODO przekazywqać beposrednio path
        Files.lines(Paths.get(path))
                .filter(line -> !line.startsWith("#"))
                .filter(line -> !line.contentEquals(""))
                .forEach(this::from);
    }

    private void from(String line){
        if (line.startsWith("K")) {
            List<String> orderPieces = Arrays.asList(line.split(":"));
            final Order bid = new Order(new User(orderPieces.get(1).trim()),
                Double.valueOf(orderPieces.get(3).trim()),
                Integer.valueOf(orderPieces.get(2).trim()));
            submitBid(bid);
        } else {
            List<String> orderPieces = Arrays.asList(line.split(":"));
            final Order ask = new Order(new User(orderPieces.get(1).trim()),
                Double.valueOf(orderPieces.get(3).trim()),
                Integer.valueOf(orderPieces.get(2).trim()));
            submitAsk(ask);
        }
    }

    @SneakyThrows(IOException.class)
    public void exportTransactionsToTxtFile(String path){
        final List<String> lines = deals.stream()
            .map(Deal::toString)
            .collect(Collectors.toList());
        Files.write(Paths.get(path), lines, StandardOpenOption.CREATE);
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

