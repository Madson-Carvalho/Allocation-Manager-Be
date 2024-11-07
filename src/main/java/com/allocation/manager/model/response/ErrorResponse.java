package com.allocation.manager.model.response;

import java.time.Instant;

public class ErrorResponse {

    public String message;
    public int status;
    public Instant timestamp;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = Instant.now();
    }
}
