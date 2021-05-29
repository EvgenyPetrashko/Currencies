package com.numbers.currencies.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyApi {

    @GET("pair/{base_code}/{target_code}")
    Call<PairInfo> getCurrency(@Path("base_code") String base_code, @Path("target_code") String target_code);
}
