package pl.andrzejjozefow.orderbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
public class MarketService {

    private final Market market = new Market();
    private final OrderFactory orderFactory = new OrderFactory();

    @SneakyThrows(IOException.class)
    public void performOrdersFromTxtFile(Path path){
        if(Files.exists(path)){
            Files.lines(path)
                .filter(line -> !line.startsWith("#"))
                .filter(line -> !line.contentEquals(""))
                .forEach(this::passToSubmit);
        } else {
            log.warning("File zlecenia.txt not found");
        }
    }

    @SneakyThrows(IOException.class )
    public void exportTransactionsToTxtFile(Path path){
        final List<String> lines = market.getDeals().stream()
            .map(Deal::toString)
            .collect(Collectors.toList());
        Files.write(path, lines, StandardOpenOption.CREATE);
        log.info("Deal list exported to out.txt file");
    }

    private void passToSubmit(String line){
        if (line.startsWith("K")) {
            Optional<Order> bid = orderFactory.getOrder(line);
            bid.ifPresent(market::submitBid);
        } else if (line.startsWith("S")){
            Optional<Order> ask = orderFactory.getOrder(line);
            ask.ifPresent(market::submitAsk);
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
