package fi.breakwaterworks.networking.server.response;

import androidx.annotation.NonNull;

public class MovementResponse {

    private Long id;
    private Long serverId;
    private String name;
    private String type;
    private String stance;
    private String grip;
    private int reps;
    private Long weight;

    public MovementResponse() {
    }

    public MovementResponse(String name, String details, String type, long serverId) {
        this.name = name;
        this.type = type;
        this.stance = details;
        this.serverId = serverId;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        id = id;
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

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;

    }
}
