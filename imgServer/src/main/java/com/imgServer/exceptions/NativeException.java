package com.imgServer.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NativeException extends RuntimeException{

    public NativeException(String message) {
        super(message);
    }
}
