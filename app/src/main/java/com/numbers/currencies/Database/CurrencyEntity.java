package com.numbers.currencies.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"base_code", "target_code"})
public class CurrencyEntity {
    @NonNull
    @ColumnInfo(name = "base_code")
    public String base_code;

    @NonNull
    @ColumnInfo(name = "target_code")
    public String target_code;

    @ColumnInfo(name = "rate")
    public double rate;

    @ColumnInfo(name =  "last_update")
    public long last_update;


}
