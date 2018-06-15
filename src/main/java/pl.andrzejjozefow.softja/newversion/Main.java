package pl.andrzejjozefow.softja.newversion;

public class Main {

    public static void main(String[] args) {

        final Market market = new Market();

        market.performTransactionsFromTxtFile("src/zlecenia.txt");
        
    }

}
