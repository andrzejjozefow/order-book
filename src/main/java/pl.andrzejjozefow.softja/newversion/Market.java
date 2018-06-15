package pl.andrzejjozefow.softja.newversion;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;
import lombok.Data;

@Data
public class Market {

    final List<Deal> deals = new ArrayList<>();
    final PriorityQueue<Order> bids = new PriorityQueue<>(
        Comparator.comparingDouble(Order::getPrice)
    );
    final PriorityQueue<Order> asks = new PriorityQueue<>(
        Comparator.comparingDouble(Order::getPrice)
    );

    public void submitBid(final Order bid) {
        for (Iterator<Order> askIterator = asks.iterator(); askIterator.hasNext(); ) {
            final Order ask = askIterator.next();
            if (ask.getPrice() <= bid.getPrice()) {
                if (bid.getQuantity() >= ask.getQuantity()) {
                    deals.add(
                        new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), ask.getQuantity()));
                    bid.setQuantity(bid.getQuantity() - ask.getQuantity());
                    askIterator.remove();
                    break;
                } else {
                    deals.add(
                        new Deal(bid.getUser(), ask.getUser(), ask.getPrice(), bid.getQuantity()));
                    ask.setQuantity(ask.getQuantity() - bid.getQuantity());
                    bid.setQuantity(0);
                }
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
                }
            }
        }
        if (ask.getQuantity() > 0) {
            asks.add(ask);
        }
    }

    public void performOrdersFromTxtFile(String path) {

        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream
                .filter(line -> !line.startsWith("#"))
                .filter(line -> !line.contentEquals(""))
                .forEach(line -> {
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
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportTransactionsToTxtFile(String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        for (Deal str : deals) {
            writer.write(String.valueOf(str));
        }
        writer.close();

    }
    
    public List<Deal> getDeals() {
        return deals;
    }

    public PriorityQueue<Order> getBids() {
        return bids;
    }

    public PriorityQueue<Order> getAsks() {
        return asks;
    }
}

