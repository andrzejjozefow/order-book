package pl.andrzejjozefow.orderbook;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class OrderParser {

    public Order parse(final String line) {
        final List<String> orderParts = Arrays.asList(line.split(":"));
        final String name = orderParts.get(1).trim();
        final BigDecimal price = BigDecimal.valueOf(Double.valueOf(orderParts.get(3).trim()));
        final Integer quantity = Integer.valueOf(orderParts.get(2).trim());
        return  orderParts.get(0).equals("K") ? Order.bid(new User(name), price, quantity)
            : Order.ask(new User(name), price, quantity);
    }

    public Stream<Order> parseOrderFile(final Path path) throws IOException {
        return Files.lines(path)
            .filter(this::isNotAComment)
            .filter(this::isNotBlankLine)
            .map(this::parse);
    }

    private boolean isNotBlankLine(final String line) {
        return !line.isEmpty();
    }

    private boolean isNotAComment(final String line) {
        return  !line.startsWith("#");
    }
}
