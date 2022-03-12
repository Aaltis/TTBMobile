package fi.breakwaterworks.networking.local;

import androidx.room.TypeConverter;

import fi.breakwaterworks.model.SetType;

public class Converters {

    @TypeConverter
    public String fromSetType(SetType setType){
        return setType.toString();
    }

    @TypeConverter
    public SetType ToSetType(String text) {
        return SetType.valueOf(text);
    }
}
