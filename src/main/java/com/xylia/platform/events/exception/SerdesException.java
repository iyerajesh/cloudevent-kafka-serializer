package com.xylia.platform.events.exception;

import java.net.ServerSocket;

/**
 * A generic serilization/deserialization exception that gets thrown on a serializtion/deserialization failure.
 *
 * @author rajeshiyer
 */

public class SerdesException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SerdesException() {
        super();
    }

    public SerdesException(String message) {
        super(message);
    }

    public SerdesException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerdesException(Throwable cause) {
        super(cause);
    }
}
