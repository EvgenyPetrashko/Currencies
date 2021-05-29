package com.numbers.currencies;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Room;

import com.numbers.currencies.Database.CurrenciesDatabase;
import com.numbers.currencies.Database.CurrencyDao;
import com.numbers.currencies.Database.CurrencyEntity;
import com.numbers.currencies.Network.CurrencyApi;

import com.numbers.currencies.Network.DaggerNetworkComponent;
import com.numbers.currencies.Network.NetworkComponent;
import com.numbers.currencies.Network.PairInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {
    @Inject
    CurrencyApi networkService;

    private CurrencyDao currencyDao;

    private NetworkComponent networkComponent;

    private HashMap<String, Double> currencies_map = new HashMap<>();

    public MainController(Context context){

        networkComponent = DaggerNetworkComponent.create();

        networkComponent.inject(this);

        CurrenciesDatabase db =  Room.databaseBuilder(context,
                CurrenciesDatabase.class, "Currency_database").allowMainThreadQueries().build();
        currencyDao = db.currencyDao();
        initializeMap();
    }



    public double getCurrencyRate(final String base_code, final String target_code){
        String label = base_code + "/" + target_code;

        Call<PairInfo> call = networkService.getCurrency(base_code, target_code);


        call.enqueue(new Callback<PairInfo>() {
            @Override
            public void onResponse(Call<PairInfo> call, Response<PairInfo> response) {
                if (!response.isSuccessful()){
                    return;
                }
                PairInfo pairs = response.body();
                String label = base_code + "/" + target_code;
                currencies_map.put(label, pairs.conversion_rate);
            }

            @Override
            public void onFailure(Call<PairInfo> call, Throwable t) {

            }
        });

        try {
            return currencies_map.get(label);
        }catch (NullPointerException e){
           return -1d;
        }
    }

    private void initializeMap(){
        List<CurrencyEntity> currencyEntities = currencyDao.selectAll();
        for (CurrencyEntity entity : currencyEntities) {
            String label = entity.base_code + "/" + entity.target_code;
            currencies_map.put(label, entity.rate);
        }
    }

    public void insertCurrency(String base_code, String target_code){
        try {
            CurrencyEntity currencyEntity = new CurrencyEntity();
            currencyEntity.base_code = base_code;
            currencyEntity.target_code = target_code;
            currencyEntity.last_update = Calendar.getInstance().getTimeInMillis();
            currencyDao.insert(currencyEntity);
        }catch (SQLiteConstraintException exception){

        }
    }

    public void deleteCurrency(String base_code, String target_code){
        CurrencyEntity entity = new CurrencyEntity();
        entity.base_code = base_code;
        entity.target_code = target_code;
        currencyDao.delete(entity);
    }

    public void updateCurrency(String base_code, String target_code, double rate, long last_update){
        CurrencyEntity entity = new CurrencyEntity();
        entity.base_code = base_code;
        entity.target_code = target_code;
        entity.rate = rate;
        entity.last_update = last_update;
        currencyDao.update(entity);
    }

    public ArrayList<CurrencyEntity> getAll(){
        return (ArrayList<CurrencyEntity>) currencyDao.selectAll();
    }

}
