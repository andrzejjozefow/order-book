package pl.andrzejjozefow.orderbook;

import static org.assertj.core.api.Assertions.*;

import com.sun.org.apache.xpath.internal.operations.Or;
import javax.jws.soap.SOAPBinding.Use;
import org.junit.Test;

public class MarketTest {

    @Test
    public void shouldAddAskWhenMarketIsEmpty() {
        //given
        final Market market = new Market();
        final User john = new User("John");
        final Order ask = new Order(john, 2.5, 10);
        //when
        market.submitAsk(ask);
        //then
        assertThat(market.getAsks()).containsExactly(ask);
    }

    @Test
    public void shouldAddBidWhenMarketIsEmpty() {
        //given
        final Market market = new Market();
        final User john = new User("John");
        final Order bid = new Order(john, 2.5, 10);
        //when
        market.submitBid(bid);
        //then
        assertThat(market.getBids()).containsExactly(bid);
    }

    @Test
    public void shouldAddBidToMarketWithAskHavingSamePriceAndQuantity() {
        //given
        final Market market = new Market();
        final User john = new User("John");
        final User peter = new User("Peter");
        final Order ask = new Order(john, 2.5, 10);
        final Order bid = new Order(peter, 2.5, 10);
        //when
        market.submitAsk(ask);
        market.submitBid(bid);
        //then
        assertThat(market.getAsks()).isEmpty();
        assertThat(market.getBids()).isEmpty();
        assertThat(market.getDeals()).containsExactly(new Deal(peter, john, 2.5, 10));
    }

    @Test
    public void shouldAddBidToMarketWithAskHavingLowerPriceAndSameQuantity() {
        //given
        final Market market = new Market();
        final User john = new User("John");
        final User peter = new User("Peter");
        final Order ask = new Order(john, 2.0, 10);
        final Order bid = new Order(peter, 2.5, 10);
        //when
        market.submitAsk(ask);
        market.submitBid(bid);
        //then
        assertThat(market.getAsks()).isEmpty();
        assertThat(market.getBids()).isEmpty();
        assertThat(market.getDeals()).containsExactly(new Deal(peter, john, 2.0, 10));
    }

    @Test
    public void shouldAddBidToMarketWithAskHavingSamePriceAndLowerQuantity() {
        //given
        final Market market = new Market();
        final User john = new User("John");
        final User peter = new User("Peter");
        final Order ask = new Order(john, 2.5, 8);
        final Order bid = new Order(peter, 2.5, 10);
        //when
        market.submitAsk(ask);
        market.submitBid(bid);
        //then
        assertThat(market.getAsks()).isEmpty();
        assertThat(market.getBids()).containsExactly(new Order(peter, 2.5, 2));
        assertThat(market.getDeals()).containsExactly(new Deal(peter, john, 2.5, 8));
    }

    @Test
    public void shouldAddBidToMarketWithAskHavingSamePriceAndBiggerQuantity() {
        //given
        final Market market = new Market();
        final User john = new User("John");
        final User peter = new User("Peter");
        final Order ask = new Order(john, 2.5, 13);
        final Order bid = new Order(peter, 2.5, 10);
        //when
        market.submitAsk(ask);
        market.submitBid(bid);
        //then
        assertThat(market.getAsks()).containsExactly(new Order(john, 2.5, 3));
        assertThat(market.getBids()).isEmpty();
        assertThat(market.getDeals()).containsExactly(new Deal(peter, john, 2.5, 10));
    }

    @Test
    public void shouldAddBidToMarketWithAskHavingBiggerPrice() {
        //given
        final Market market = new Market();
        final User john = new User("John");
        final User peter = new User("Peter");
        final Order ask = new Order(john, 2.5, 13);
        final Order bid = new Order(peter, 2.0, 10);
        //when
        market.submitAsk(ask);
        market.submitBid(bid);
        //then
        assertThat(market.getAsks()).containsExactly(ask);
        assertThat(market.getBids()).containsExactly(bid);
        assertThat(market.getDeals()).isEmpty();
    }

    @Test
    public void shouldAddAskToMarketWithAskHavingLoverQuantity() {
        //given
        final Market market = new Market();
        final User john = new User("John");
        final User peter = new User("Peter");
        final Order bid = new Order(peter, 2.5, 10);
        final Order ask = new Order(john, 2.0, 7);

        //when
        market.submitBid(bid);
        market.submitAsk(ask);

        //then
        assertThat(market.getDeals()).containsExactly(new Deal(peter, john, 2.0, 7));
        assertThat(market.getBids()).containsExactly(new Order(peter, 2.5, 3));
        assertThat(market.getAsks()).isEmpty();
    }

    @Test
    public void shouldProcessAllExampleTransactions(){
        //given
        final Market market = new Market();

        //K: "Prokom": 10: 7.50
        final User prokom = new User("Prokom");
        final Order prokomBid = new Order(prokom, 7.50, 10);

        // S: "Witold Wielki": 3: 8.25
        final User witoldWielki = new User("Witold Wielki");
        final Order witoldAsk = new Order(witoldWielki, 8.25, 3);

        //S: "Korzeniowski": 8: 7.15
        final User korzeniowski = new User("Korzeniowski");
        final Order korzeniowskiAsk = new Order(korzeniowski, 7.15, 8);

        //S: "Minkiewicz Software": 30: 7.00
        final User minkiewiczSoftware = new User("Minkiewicz Software");
        final Order minkiewiczAsk = new Order(minkiewiczSoftware, 7.00, 30);

        //K: "Żabka Delikatesy": 20: 8.10
        final User zabkaDelikatesy = new User("Żabka Delikatesy");
        final Order zabkaBid = new Order(zabkaDelikatesy, 8.10, 20);

        //K: "Polska Agencja Kosmiczna": 10: 5.80
        final User polskaAgencjaKosmiczna = new User("Polska Agencja Kosmiczna");
        final Order polskaAgencjaBid = new Order(polskaAgencjaKosmiczna, 5.80, 10);

        //S: "Witold Wielki": 6: 7.40
        final Order witoldAsk2 = new Order(witoldWielki, 7.40, 6);

        //K: "Softja": 10: 7.00
        final User softja = new User("Softja");
        final Order softjaBid = new Order(softja, 7.00, 10);

        //K: "Wielkie Księstwo Litewskie": 12: 9.05
        final User wielkieKsiestwoLitewskie = new User("Wielkie Księstwo Litewskie");
        final Order wielkieKsiestwoBid = new Order(wielkieKsiestwoLitewskie, 9.05, 12);

        //S: "Softja": 5: 8.80
        final Order softjaAsk = new Order(softja, 8.80, 5);

        //S: "Prokom": 8: 10.00
        final Order prokomAsk = new Order(prokom, 10.00, 8);

        //K: "Królestwo Polskie": 3: 8.50
        final User krolestwoPolskie = new User("Królestwo Polskie");
        final Order krolestwoBid = new Order(krolestwoPolskie, 8.50, 3);

        //S: "Zbór niebiański": 7: 6.60
        final User zborNiebianski = new User("Zbór niebiański");
        final Order zborAsk = new Order(zborNiebianski, 6.60, 7);

        //when
        market.submitBid(prokomBid);
        market.submitAsk(witoldAsk);
        market.submitAsk(korzeniowskiAsk);
        market.submitAsk(minkiewiczAsk);
        market.submitBid(zabkaBid);
        market.submitBid(polskaAgencjaBid);
        market.submitAsk(witoldAsk2);
        market.submitBid(softjaBid);
        market.submitBid(wielkieKsiestwoBid);
        market.submitAsk(softjaAsk);
        market.submitAsk(prokomAsk);
        market.submitBid(krolestwoBid);
        market.submitAsk(zborAsk);

        //then
        assertThat(market.getDeals()).containsExactly(

            //"Prokom" -> "Korzeniowski" 8 7.15
            new Deal(prokom, korzeniowski, 7.15, 8),

            //"Prokom" -> "Minkiewicz Software" 2 7.00
            new Deal(prokom, minkiewiczSoftware, 7.00, 2),

            //"Żabka Delikatesy" -> "Minkiewicz Software" 20 7.00
            new Deal(zabkaDelikatesy, minkiewiczSoftware, 7.00, 20),

            //"Softja" -> "Minkiewicz Software" 8 7.00
            new Deal(softja, minkiewiczSoftware, 7.00, 8),

            //"Wielkie Księstwo Litewskie" -> "Witold Wielki" 6 7.40
            new Deal(wielkieKsiestwoLitewskie, witoldWielki, 7.40, 6),

            //"Wielkie Księstwo Litewskie" -> "Witold Wielki" 3 8.25
            new Deal(wielkieKsiestwoLitewskie, witoldWielki, 8.25, 3),

            //"Wielkie Księstwo Litewskie" -> "Softja" 3 8.80
            new Deal(wielkieKsiestwoLitewskie, softja, 8.80, 3),

            //"Królestwo Polskie" -> "Zbór niebiański" 3 6.60
            new Deal(krolestwoPolskie, zborNiebianski, 6.60, 3)
        );
    }
}