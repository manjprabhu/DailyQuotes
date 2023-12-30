package com.mnj.dailyquotes.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @SerializedName("q") @ColumnInfo(name="Quote") val quote: String = "",
    @SerializedName("a") @ColumnInfo(name = "Author") val author: String = "",
    @SerializedName("h") val formatted: String = ""
) {
}