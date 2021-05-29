package com.numbers.currencies.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = CurrencyEntity.class, version = 1)
public abstract class CurrenciesDatabase extends RoomDatabase {
    public abstract CurrencyDao currencyDao();
}
