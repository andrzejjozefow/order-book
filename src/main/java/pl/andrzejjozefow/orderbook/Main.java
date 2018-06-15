package pl.andrzejjozefow.orderbook;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        final Market market = new Market();

        market.performOrdersFromTxtFile("src/zlecenia.txt");
        System.out.println(market.getAsks());

        market.exportTransactionsToTxtFile("src/out.txt");

    }

}