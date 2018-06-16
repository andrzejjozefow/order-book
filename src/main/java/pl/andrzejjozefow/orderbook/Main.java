package pl.andrzejjozefow.orderbook;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        final MarketService marketService = new MarketService();

        marketService.performOrdersFromTxtFile("src/zlecenia.txt");

        System.out.println(marketService.getDeals());

        marketService.exportTransactionsToTxtFile("src/out.txt");
    }

}
