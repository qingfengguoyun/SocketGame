package com.myPokeGame.handler;

import com.myPokeGame.exceptions.NativeException;
import com.myPokeGame.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.omg.SendingContext.RunTime;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Configuration
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex){
        log.info(ex.getMessage());
        return "拦截的运行时错误信息："+ex.getMessage();
    }

    @ExceptionHandler(NativeException.class)
    public Result handleNativeException(NativeException ex){
        log.info(ex.getMessage());
        return Result.fail(ex.getMessage());
    }
}
