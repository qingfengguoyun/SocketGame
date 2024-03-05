package com.project_02_28.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    Integer code;
    Object data;
    String message;

    public static Result success(Object data){
        Result result = new Result();
        result.setCode(200);
        result.setData(data);
        return result;
    }

    public static Result error(Integer code){
        Result result = new Result();
        result.setCode(200);
//        result.setData(data);
        return result;
    }

    public static Result error(Integer code,String message){
        Result result = new Result();
        result.setCode(200);
        result.setMessage(message);
//        result.setData(data);
        return result;
    }
}
