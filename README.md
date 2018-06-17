
# ORDER BOOK



## How to build



Clone

```
git clone https://github.com/andrzejjozefow/order-book.git
```

Build

```bash
cd order-book
mvn clean install

```



## How to setup

In order to use order-book you should instatiate MarketApp object:

```java
final MarketApp marketApp = new MarketApp(new Market(), new OrderParser());
```



Performing orders from txt file:

```java
marketApp.performOrdersFromTxtFile(Paths.get("src/zlecenia.txt"));
```



Writing matched options to txt file:

```java
marketApp.exportTransactionsToTxtFile(Paths.get("src/out.txt"));
```


## Test performing sample orders

```java
@Test
    public void shouldProcessAllExampleTransactions() {
        //given
        final Market market = new Market();

        //K: "Prokom": 10: 7.50
        final User prokom = new User("Prokom");
        final Order prokomBid = Order.bid(prokom, BigDecimal.valueOf(7.50), 10);

        // S: "Witold Wielki": 3: 8.25
        final User witoldWielki = new User("Witold Wielki");
        final Order witoldAsk = Order.ask(witoldWielki, BigDecimal.valueOf(8.25), 3);

        //S: "Korzeniowski": 8: 7.15
        final User korzeniowski = new User("Korzeniowski");
        final Order korzeniowskiAsk = Order.ask(korzeniowski, BigDecimal.valueOf(7.15), 8);

        //S: "Minkiewicz Software": 30: 7.00
        final User minkiewiczSoftware = new User("Minkiewicz Software");
        final Order minkiewiczAsk = Order.ask(minkiewiczSoftware, BigDecimal.valueOf(7.00), 30);

        //K: "Żabka Delikatesy": 20: 8.10
        final User zabkaDelikatesy = new User("Żabka Delikatesy");
        final Order zabkaBid = Order.bid(zabkaDelikatesy, BigDecimal.valueOf(8.10), 20);

        //K: "Polska Agencja Kosmiczna": 10: 5.80
        final User polskaAgencjaKosmiczna = new User("Polska Agencja Kosmiczna");
        final Order polskaAgencjaBid = Order.bid(polskaAgencjaKosmiczna, BigDecimal.valueOf(5.80), 10);

        //S: "Witold Wielki": 6: 7.40
        final Order witoldAsk2 = Order.ask(witoldWielki, BigDecimal.valueOf(7.40), 6);

        //K: "Softja": 10: 7.00
        final User softja = new User("Softja");
        final Order softjaBid = Order.bid(softja, BigDecimal.valueOf(7.00), 10);

        //K: "Wielkie Księstwo Litewskie": 12: 9.05
        final User wielkieKsiestwoLitewskie = new User("Wielkie Księstwo Litewskie");
        final Order wielkieKsiestwoBid = Order.bid(wielkieKsiestwoLitewskie, BigDecimal.valueOf(9.05), 12);

        //S: "Softja": 5: 8.80
        final Order softjaAsk = Order.ask(softja, BigDecimal.valueOf(8.80), 5);

        //S: "Prokom": 8: 10.00
        final Order prokomAsk = Order.ask(prokom, BigDecimal.valueOf(10.00), 8);

        //K: "Królestwo Polskie": 3: 8.50
        final User krolestwoPolskie = new User("Królestwo Polskie");
        final Order krolestwoBid = Order.bid(krolestwoPolskie, BigDecimal.valueOf(8.50), 3);

        //S: "Zbór niebiański": 7: 6.60
        final User zborNiebianski = new User("Zbór niebiański");
        final Order zborAsk = Order.ask(zborNiebianski, BigDecimal.valueOf(6.60), 7);

        //when
        market.submitOrder(prokomBid);
        market.submitOrder(witoldAsk);
        market.submitOrder(korzeniowskiAsk);
        market.submitOrder(minkiewiczAsk);
        market.submitOrder(zabkaBid);
        market.submitOrder(polskaAgencjaBid);
        market.submitOrder(witoldAsk2);
        market.submitOrder(softjaBid);
        market.submitOrder(wielkieKsiestwoBid);
        market.submitOrder(softjaAsk);
        market.submitOrder(prokomAsk);
        market.submitOrder(krolestwoBid);
        market.submitOrder(zborAsk);

        //then
        assertThat(market.getDeals()).containsExactly(

            //"Prokom" -> "Korzeniowski" 8 7.15
            new Deal(prokom, korzeniowski, BigDecimal.valueOf(7.15), 8),

            //"Prokom" -> "Minkiewicz Software" 2 7.00
            new Deal(prokom, minkiewiczSoftware, BigDecimal.valueOf(7.00), 2),

            //"Żabka Delikatesy" -> "Minkiewicz Software" 20 7.00
            new Deal(zabkaDelikatesy, minkiewiczSoftware, BigDecimal.valueOf(7.00), 20),

            //"Softja" -> "Minkiewicz Software" 8 7.00
            new Deal(softja, minkiewiczSoftware, BigDecimal.valueOf(7.00), 8),

            //"Wielkie Księstwo Litewskie" -> "Witold Wielki" 6 7.40
            new Deal(wielkieKsiestwoLitewskie, witoldWielki, BigDecimal.valueOf(7.40), 6),

            //"Wielkie Księstwo Litewskie" -> "Witold Wielki" 3 8.25
            new Deal(wielkieKsiestwoLitewskie, witoldWielki, BigDecimal.valueOf(8.25), 3),

            //"Wielkie Księstwo Litewskie" -> "Softja" 3 8.80
            new Deal(wielkieKsiestwoLitewskie, softja, BigDecimal.valueOf(8.80), 3),

            //"Królestwo Polskie" -> "Zbór niebiański" 3 6.60
            new Deal(krolestwoPolskie, zborNiebianski, BigDecimal.valueOf(6.60), 3)
        );
    }
```







