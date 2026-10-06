package com.example.compose.kanji.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "kanji_table")
data class KanjiEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "character")
    val character: String,

    @ColumnInfo(name = "meaning_es")
    val meaningEs: String,

    @ColumnInfo(name = "on_yomi")
    val onYomi: String,

    @ColumnInfo(name = "kun_yomi")
    val kunYomi: String,

    @ColumnInfo(name = "jlpt_level")
    val jlptLevel: String?,

    @ColumnInfo(name = "stroke_count")
    val strokeCount: Int,

    @ColumnInfo(name = "radical")
    val radical: String,

    @ColumnInfo(name = "mnemonic")
    val mnemonic: String,

    @ColumnInfo(name = "notes")
    val notes: String,

    @ColumnInfo(name = "date_added")
    val dateAdded: LocalTime
    //@Embedded(prefix = "srs_") val srsState: SrsStateEmbedded
    )