package fi.breakwaterworks.networking.server.response;

public class ServerResponse {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String status) {
        this.message = status;
    }

    public ServerResponse(String status) {
        this.message = status;

    }
}