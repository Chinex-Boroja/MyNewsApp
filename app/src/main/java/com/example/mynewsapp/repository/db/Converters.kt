package com.example.mynewsapp.repository.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mynewsapp.models.SourceStream
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun fromSource(source: SourceStream): String {
        return JSONObject().apply {
            put("id", source.id)
            put("name", source.name)
        }.toString()
    }
//    fun fromSource(sourceStream: SourceStream) :String? {
//        return sourceStream.name
//    }

    @TypeConverter
    fun toSource(source: String): SourceStream {
        val json = JSONObject(source)
        return SourceStream(json.getString("id"), json.getString("name"))
    }
//    fun toSource(name: String) : SourceStream {
//        return SourceStream(name, name)
//    }
}