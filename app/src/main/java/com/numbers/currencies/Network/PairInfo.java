package com.numbers.currencies.Network;

import com.google.gson.annotations.SerializedName;

public class PairInfo {
    @SerializedName("result")
    public String result;

    @SerializedName("documentation")
    public String documentation;

    @SerializedName("terms_of_use")
    public String terms_of_use;

    @SerializedName("time_last_update_unix")
    public long time_last_update_unix;

    @SerializedName("time_last_update_utc")
    public String time_last_update_utc;

    @SerializedName("time_next_update_unix")
    public long time_next_update_unix;

    @SerializedName("time_next_update_utc")
    public String time_next_update_utc;

    @SerializedName("base_code")
    public String base_code;

    @SerializedName("target_code")
    public String target_code;

    @SerializedName("conversion_rate")
    public double conversion_rate;
}
