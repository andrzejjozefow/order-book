package pl.andrzejjozefow.orderbook;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {

        final MarketService marketService = new MarketService();

        marketService.performOrdersFromTxtFile(Paths.get("src/zlecenia.txt"));

        marketService.exportTransactionsToTxtFile(Paths.get("src/out.txt"));
    }

}
