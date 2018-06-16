package pl.andrzejjozefow.orderbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class MarketService {

    private final Market market = new Market();
    private final OrderFactory orderFactory = new OrderFactory();

    @SneakyThrows(IOException.class)
    public void performOrdersFromTxtFile(Path path){
        Files.lines(path)
            .filter(line -> !line.startsWith("#"))
            .filter(line -> !line.contentEquals(""))
            .forEach(this::passToSubmit);
    }

    @SneakyThrows(IOException.class)
    public void exportTransactionsToTxtFile(Path path){
        final List<String> lines = market.getDeals().stream()
            .map(Deal::toString)
            .collect(Collectors.toList());
        Files.write(path, lines, StandardOpenOption.CREATE);
    }

    private void passToSubmit(String line){
        if (line.startsWith("K")) {
            market.submitBid(orderFactory.getOrder(line));
        } else if (line.startsWith("S")){
            market.submitAsk(orderFactory.getOrder(line));
        }
    }

    public List<Deal> getDeals(){
        return market.getDeals();
    }

    public Collection<Order> getBids(){
        return market.getBids();
    }

    public Collection<Order> getAsks(){
        return market.getAsks();
    }
}
