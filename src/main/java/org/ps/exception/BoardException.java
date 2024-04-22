package org.ps.exception;

import java.io.Serial;

public class BoardException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public BoardException(String message) {
        super(message);
    }
}
