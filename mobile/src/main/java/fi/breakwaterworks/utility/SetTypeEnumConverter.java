package fi.breakwaterworks.utility;

import androidx.room.TypeConverter;

public class SetTypeEnumConverter {

    @TypeConverter
    public static SetTypeEnum toSetTypeEnum(String value) {
        return value == null ? null : SetTypeEnum.valueOf(value) ;
    }

    @TypeConverter
    public static String setTypeEnumToString(SetTypeEnum setTypeEnum) {
        return setTypeEnum.toString();
    }
}
