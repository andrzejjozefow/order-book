package pl.andrzejjozefow.orderbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MarketApp {

    private final Market market;
    private final OrderParser orderParser;

    @SneakyThrows(IOException.class)
    public void performOrdersFromTxtFile(final Path path) {
        if (Files.exists(path)) {
            orderParser.parseOrderFile(path).forEach(market::submitOrder);
        } else {
            log.warn("File not found: {}", path);
        }
    }

    @SneakyThrows(IOException.class)
    public void exportTransactionsToTxtFile(final Path path) {
        final List<String> lines = market.getDeals().stream()
            .map(Deal::toString)
            .collect(Collectors.toList());
        Files.write(path, lines, StandardOpenOption.CREATE);
        log.info("Deals exported into: {}.txt", path);
    }
}
