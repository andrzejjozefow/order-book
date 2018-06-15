package pl.andrzejjozefow.softja.newversion;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class MarketTest {

    @Test
    public void shouldAddAskWhenMarketIsEmpty(){
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
    public void shouldAddBidWhenMarketIsEmpty(){
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
    public void shouldAddBidToMarketWithAskHavingSamePriceAndQuantity(){
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
    public void shouldAddBidToMarketWithAskHavingLowerPriceAndSameQuantity(){
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
    public void shouldAddBidToMarketWithAskHavingSamePriceAndLowerQuantity(){
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
    public void shouldAddBidToMarketWithAskHavingSamePriceAndBiggerQuantity(){
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
    public void shouldAddBidToMarketWithAskHavingBiggerPrice(){
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
    public void  shouldAddAskToMarketWithAskHavingLoverQuantity(){
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



}