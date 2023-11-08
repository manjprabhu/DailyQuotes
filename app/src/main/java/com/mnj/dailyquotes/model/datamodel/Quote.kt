package com.mnj.dailyquotes.model.datamodel

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "quote")
data class Quote(
    var id: Int,
    @SerializedName("q") val quote: String = "",
    @SerializedName("a") val author: String = "",
    @SerializedName("h") val formatted: String = ""
)
