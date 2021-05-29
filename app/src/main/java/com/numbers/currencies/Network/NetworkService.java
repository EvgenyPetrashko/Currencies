package com.numbers.currencies.Network;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkService {
    private static String API = "a32a1563eb626dcbc2ae3817";

    @Provides
    public CurrencyApi getNetworkService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/v6/" + API + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(CurrencyApi.class);
    }

}
