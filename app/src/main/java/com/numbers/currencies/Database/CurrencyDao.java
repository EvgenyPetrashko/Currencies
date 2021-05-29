package com.numbers.currencies.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Query("SELECT * FROM CurrencyEntity")
    List<CurrencyEntity> selectAll();

    @Update
    void update(CurrencyEntity currencyEntity);

    @Insert
    void insert(CurrencyEntity currencyEntity);

    @Delete
    void delete(CurrencyEntity currencyEntity);

}
