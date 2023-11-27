package com.mnj.dailyquotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey val id: Int,
    @SerializedName("q") val quote: String = "",
    @SerializedName("a") val author: String = "",
    @SerializedName("h") val formatted: String = ""
) {
}