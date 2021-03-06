package com.trievosoftware.cryptocompareapi.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trievosoftware.cryptocompareapi.config.CryptoCompareAPIConstant;
import com.trievosoftware.cryptocompareapi.Exceptions.OutOfCallsException;
import com.trievosoftware.cryptocompareapi.models.news.NewsProvider;
import com.trievosoftware.cryptocompareapi.models.news.NewsStory;
import com.trievosoftware.cryptocompareapi.utils.Connection;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Contains methods for requesting information about news listed on CryptoCompare
 * @author Josh McFarlin
 */
public class News {
    /**
     * Gets a list of news providers on the CryptoCompare website
     * @return List containing different news providers
     * @throws IOException when a connection cannot be made
     * @throws OutOfCallsException when no more API calls are available
     */
    public List<NewsProvider> newsProviderList() throws IOException, OutOfCallsException {
        Reader r = Connection.getJSONWithLimitVerif(CryptoCompareAPIConstant.MINI_CRYPTO_API_URL + "/news/providers");

        Type newsListType = new TypeToken<List<NewsProvider>>() {}.getType();
        return new Gson().fromJson(r, newsListType);
    }

    /**
     * Gets a list of news stories on the CryptoCompare website
     * @return List containing different news stories
     * @throws IOException when a connection cannot be made
     * @throws OutOfCallsException when no more API calls are available
     */
    public List<NewsStory> newsList() throws IOException, OutOfCallsException {
        Reader r = Connection.getJSONWithLimitVerif(CryptoCompareAPIConstant.MINI_CRYPTO_API_URL + "/news/");

        Type newsListType = new TypeToken<List<NewsStory>>() {}.getType();
        return new Gson().fromJson(r, newsListType);
    }
}
