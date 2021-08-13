package fi.breakwaterworks.utility;

public enum Status {
    /**
     * Request has succeeded and response contains data
     */
    SUCCESS,
    /**
     * Request failed
     */
    FAILURE,
    /**
     * Request is sent. Waiting for a response
     */
    RUNNING
}
