package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.*;

import fi.breakwaterworks.networking.server.response.MovementResponse;


@Entity(tableName = "movements")
public class Movement {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long Id;
    private Long serverId;
    private String name;
    private String type;
    private String stance;
    private String grip;

    public Movement() {
    }


    public Movement(String name, String stance, String type, long serverId) {
        this.name = name;
        this.type = type;
        this.stance = stance;
        this.serverId = serverId;
    }

    public Movement(MovementResponse remoteMovement) {
        this.serverId = remoteMovement.getId();
        this.name = remoteMovement.getName();
        this.type = remoteMovement.getType();
        this.stance = remoteMovement.getStance();
        this.serverId = remoteMovement.getId();
        this.grip = remoteMovement.getGrip();
    }

    @NonNull
    public Long getId() {
        return Id;
    }

    public void setId(@NonNull Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStance() {
        return stance;
    }

    public void setStance(String stance) {
        this.stance = stance;
    }

    public String getGrip() {
        return grip;
    }

    public void setGrip(String grip) {
        this.grip = grip;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}