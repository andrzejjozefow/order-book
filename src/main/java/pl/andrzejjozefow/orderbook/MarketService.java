package pl.andrzejjozefow.orderbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class MarketService {

    private final Market market = new Market();
    private final OrderFactory orderFactory = new OrderFactory();

    @SneakyThrows(IOException.class)
    public void performOrdersFromTxtFile(String path) { //TODO przekazywqać beposrednio path
        Files.lines(Paths.get(path))
            .filter(line -> !line.startsWith("#"))
            .filter(line -> !line.contentEquals(""))
            .forEach(this::from);
    }

    private void from(String line){
        if (line.startsWith("K")) {
            market.submitBid(orderFactory.getOrder(line));
        } else {
            market.submitAsk(orderFactory.getOrder(line));
        }
    }

    @SneakyThrows(IOException.class)
    public void exportTransactionsToTxtFile(String path){
        final List<String> lines = market.getDeals().stream()
            .map(Deal::toString)
            .collect(Collectors.toList());
        Files.write(Paths.get(path), lines, StandardOpenOption.CREATE);
    }

    public List<Deal> getDeals(){
        return market.getDeals();
    }
}
