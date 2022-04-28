package com.example.suvantocare_application

import com.google.gson.annotations.SerializedName


data class MuRata (

    @SerializedName("Time"           ) var Time           : String? = null,
    @SerializedName("HR"             ) var HR             : String? = null,
    @SerializedName("RR"             ) var RR             : String? = null,
    @SerializedName("SV"             ) var SV             : String? = null,
    @SerializedName("HRV"            ) var HRV            : String? = null,
    @SerializedName("SignalStrength" ) var SignalStrength : String? = null,
    @SerializedName("Status"         ) var Status         : String? = null,
    @SerializedName("B2B1"           ) var B2B1           : String? = null,
    @SerializedName("B2B2"           ) var B2B2           : String? = null,
    @SerializedName("B2B3"           ) var B2B3           : String? = null

)