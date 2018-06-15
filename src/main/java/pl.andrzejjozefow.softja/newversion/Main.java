package pl.andrzejjozefow.softja.newversion;

public class Main {

    public static void main(String[] args) {

        final Market market = new Market();

        market.performTransaktonsFromTxtFile("src/zlecenia.txt");

        System.out.println(market);
    }

}
