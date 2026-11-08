package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import androidx.room.TypeConverter
import org.json.JSONArray

class TagConverter {

    @TypeConverter
    fun fromTags(tags: List<String>): String {
        return JSONArray(tags).toString()
    }

    @TypeConverter
    fun toTags(value: String): List<String> {
        val array = JSONArray(value)
        return (0 until array.length()).map { array.getString(it) }
    }
}
