package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.*;

import fi.breakwaterworks.utility.SetTypeEnum;

public class SetType {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "set_type_id")
    private Long specialMethodTypeId;
    @NonNull
    private SetTypeEnum setTypeEnum;
    private String name;
    private String explanation;

    public SetType(SetTypeEnum setTypeEnum){}

    public SetType(String name, String explanation){
        this.name = name;
        this.explanation=explanation;
    }

    public Long getId() {
        return specialMethodTypeId;
    }

    public void setId(Long specialMethodTypeId) {
        this.specialMethodTypeId = specialMethodTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public SetTypeEnum getSetTypeEnum() {
        return setTypeEnum;
    }

}
