package com.example.mynewsapp.repository.db

import androidx.room.TypeConverters
import com.example.mynewsapp.models.SourceStream

class Converters {
    @TypeConverters
    fun fromSource(sourceStream: SourceStream) : String? {
        return sourceStream.name
    }

    @TypeConverters
    fun toSource(name: String) : SourceStream {
        return SourceStream(name, name)
    }
}