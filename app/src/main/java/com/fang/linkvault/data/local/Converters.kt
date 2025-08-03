import androidx.room.TypeConverters
import java.util.Date

class Converters {
    @TypeConverters
    fun fromTimestamp(value:Long?): Date?{
        return value?.let { Date(it) }
    }

    @TypeConverters
    fun dateToTimestamp(date:Date?):Long?{
        return date?.time
    }

    @TypeConverters
    fun fromStringList(value:String) :List<String>{
        return value.split(",").map { it.trim() }
    }
    @TypeConverters
    fun toStringList(list:List<String>):String{
        return list.joinToString(",")
    }


}