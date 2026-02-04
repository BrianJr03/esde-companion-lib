package jr.brian.esdecompanionlib.data.database

import androidx.room.TypeConverter
import jr.brian.esdecompanionlib.data.model.ContentType

class Converters {
    @TypeConverter
    fun fromContentType(value: ContentType): String {
        return value.name
    }

    @TypeConverter
    fun toContentType(value: String): ContentType {
        return ContentType.valueOf(value)
    }
}
