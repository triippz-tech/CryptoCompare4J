package com.trievosoftware.cryptocompareapi;

import com.trievosoftware.cryptocompareapi.Exceptions.InvalidParameterException;
import com.trievosoftware.cryptocompareapi.Exceptions.OutOfCallsException;
import com.trievosoftware.cryptocompareapi.models.coin.CoinList;
import com.trievosoftware.cryptocompareapi.models.exchanges.ExchangeList;
import com.trievosoftware.cryptocompareapi.models.historic.History;
import com.trievosoftware.cryptocompareapi.models.market.Coin;
import com.trievosoftware.cryptocompareapi.models.market.ExchangeAverage;
import com.trievosoftware.cryptocompareapi.models.market.Pair;
import com.trievosoftware.cryptocompareapi.models.market.ToSym;
import com.trievosoftware.cryptocompareapi.models.mining.Contracts;
import com.trievosoftware.cryptocompareapi.models.mining.Equipment;
import com.trievosoftware.cryptocompareapi.models.news.NewsProvider;
import com.trievosoftware.cryptocompareapi.models.news.NewsStory;
import com.trievosoftware.cryptocompareapi.models.social.SocialStats;
import com.trievosoftware.cryptocompareapi.utils.RateLimiting;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class APITest {
    private CryptoCompare api = new CryptoCompare();

    @Test
    void shouldReturnValidAPIUsage() throws IOException {
        RateLimiting.TotalRate rates = RateLimiting.getRates();
        assertNotNull(rates);
    }

    @Test
    void shouldReturnCoinListWithCoins() throws IOException, OutOfCallsException {
        CoinList coinList = api.getCoinsApi().getCoinList();
        assertNotNull(coinList);
        assertFalse(coinList.getCoins().isEmpty());
        assertEquals("BTC", coinList.getCoins().get("BTC").getName());
    }

    @Test
    void shouldReturnExchangeListWithExchanges() throws IOException, OutOfCallsException {
        ExchangeList exchangeList = api.getExchangesApi().getAllExchanges();
        assertNotNull(exchangeList);
        assertFalse(exchangeList.getExchanges().isEmpty());
        assertNotNull(exchangeList.getExchanges().get("Cryptsy").getCoins().get("BTC"));
    }

    @Test
    void shouldNotReturnNullEmptyNewsList() throws IOException, OutOfCallsException {
        List<NewsProvider> newsProviders = api.getNewsApi().newsProviderList();
        assertNotNull(newsProviders);
        assertFalse(newsProviders.isEmpty());

        List<NewsStory> newsList = api.getNewsApi().newsList();
        assertNotNull(newsList);
        assertFalse(newsList.isEmpty());
    }

    @Test
    void shouldReturnNewsListWithNews() throws IOException, OutOfCallsException {
        List<NewsProvider> newsProviders = api.getNewsApi().newsProviderList();
        assertFalse(newsProviders.isEmpty());
        assertNotNull(newsProviders.get(0).getKey());

        List<NewsStory> newsList = api.getNewsApi().newsList();
        assertFalse(newsList.isEmpty());
        assertNotNull(newsList.get(0).getTitle());
    }

    @Test
    void shouldNotReturnNullSocialStats() throws IOException, OutOfCallsException {
        SocialStats socialStats = api.getSocialApi().getStats(1182);
        assertNotNull(socialStats);
        assertNotNull(socialStats.getData());
    }

    @Test
    void shouldNotReturnNullEmptyHistoricData() throws IOException, OutOfCallsException, InvalidParameterException {
        History history = api.getHistoricApi().getDay("BTC", "USD", 30);
        assertNotNull(history);
        assertFalse(history.getData().isEmpty());
    }

    @Test
    void shouldNotReturnNullEmptyHistoricPriceMap() throws IOException, OutOfCallsException, InvalidParameterException {
        Map bitcoinPriceAt = api.getHistoricApi().getPriceAtTime("BTC", "USD,EUR,ETH", 1515190000);
        assertNotNull(bitcoinPriceAt);
        assertFalse(bitcoinPriceAt.isEmpty());
    }

    @Test
    void shouldNotReturnNullAverage() throws IOException, OutOfCallsException, InvalidParameterException {
        double dayAverage = api.getHistoricApi().getDayAverage("BTC", "USD");
        assertTrue(dayAverage > 0);
    }

    @Test
    void shouldNotReturnNullEmptyExchangeAverage() throws IOException, OutOfCallsException, InvalidParameterException {
        ExchangeAverage exchangeAverage = api.getMarketApi().getExchangeAverage("BTC", "USD", "Coinbase,Kraken,Bitstamp");
        assertNotNull(exchangeAverage);
    }

    @Test
    void shouldNotReturnNullEmptyMarketAverage() throws IOException, OutOfCallsException, InvalidParameterException {
        Map<String, Double> btcPrice = api.getMarketApi().getPrice("BTC", "USD,EUR");
        assertNotNull(btcPrice);
        assertFalse(btcPrice.isEmpty());
    }

    @Test
    void shouldNotReturnNullEmptyMultiPrice() throws IOException, OutOfCallsException, InvalidParameterException {
        Map<String, Map<String, Double>> btcETHPrice = api.getMarketApi().getMultiPrice("BTC,ETH", "USD,EUR");
        assertNotNull(btcETHPrice);
        assertFalse(btcETHPrice.isEmpty());
    }

    @Test
    void shouldNotReturnNullEmptyMultiFull() throws IOException, OutOfCallsException, InvalidParameterException {
        Map<String, Map<String, ToSym>> multiFull = api.getMarketApi().getMultiFull("BTC,ETH", "USD,EUR");
        assertNotNull(multiFull);
        assertFalse(multiFull.isEmpty());
    }

    @Test
    void shouldNotReturnNullEmptyTopVolumes() throws IOException, OutOfCallsException, InvalidParameterException {
        List<Coin> usdTopVol = api.getMarketApi().getTopVolumes("USD");
        assertNotNull(usdTopVol);
        assertFalse(usdTopVol.isEmpty());
    }

    @Test
    void shouldNotReturnNullEmptyTopPairs() throws IOException, OutOfCallsException, InvalidParameterException {
        List<Pair> topPairs = api.getMarketApi().getTopPairs("BTC");
        assertNotNull(topPairs);
        assertFalse(topPairs.isEmpty());
    }

    @Test
    void shouldNotReturnNullEmptyMiningContracts() throws IOException, OutOfCallsException {
        Contracts contracts = api.getMiningApi().getContracts();
        assertNotNull(contracts);
        assertFalse(contracts.getContracts().isEmpty());
    }

    @Test
    void shouldNotReturnNullEmptyMiningHardware() throws IOException, OutOfCallsException {
        Equipment equipment = api.getMiningApi().getEquipment();
        assertNotNull(equipment);
        assertFalse(equipment.getEquipment().isEmpty());
    }
}
