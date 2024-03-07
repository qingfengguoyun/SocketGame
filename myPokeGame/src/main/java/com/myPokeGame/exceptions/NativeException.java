package com.myPokeGame.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class NativeException extends RuntimeException{

    public NativeException(String message) {
        super(message);
    }
}
