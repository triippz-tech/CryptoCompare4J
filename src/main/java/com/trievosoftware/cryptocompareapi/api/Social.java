package com.trievosoftware.cryptocompareapi.api;

import com.google.gson.Gson;
import com.trievosoftware.cryptocompareapi.config.CryptoCompareAPIConstant;
import com.trievosoftware.cryptocompareapi.Exceptions.OutOfCallsException;
import com.trievosoftware.cryptocompareapi.models.social.SocialStats;
import com.trievosoftware.cryptocompareapi.utils.Connection;

import java.io.IOException;
import java.io.Reader;

/**
 * Contains methods for requesting information about social media accounts for cryptocurrencies and exchanges
 * @author Josh McFarlin
 */
public class Social {
    /**
     * Gets social media information about a provided cryptocurrency or exchange
     * @param id The CryptoCompare ID to find information for
     * @return SocialStats a object containing different API data
     * @throws IOException when a connection cannot be made
     * @throws OutOfCallsException when no more API calls are available
     */
    public SocialStats getStats(int id) throws IOException, OutOfCallsException {
        String formattedUrl = CryptoCompareAPIConstant.CRYPTO_API_URL + "/socialstats/?id=" + id;

        Reader r = Connection.getJSONWithLimitVerif(formattedUrl);
        return new Gson().fromJson(r, SocialStats.class);
    }

}
