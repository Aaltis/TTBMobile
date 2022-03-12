package fi.breakwaterworks.utility;

import androidx.room.TypeConverter;

import java.sql.Timestamp;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Timestamp fromTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}