package pl.andrzejjozefow.orderbook;

import java.nio.file.Paths;

public class Main {

    public static void main(final String[] args) {

        final MarketApp marketApp = new MarketApp(new Market(), new OrderParser());

        marketApp.performOrdersFromTxtFile(Paths.get("src/zlecenia.txt"));
        marketApp.exportTransactionsToTxtFile(Paths.get("src/out"));
    }
}
