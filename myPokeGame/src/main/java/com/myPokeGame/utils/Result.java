package com.myPokeGame.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    public Object data;
    public Integer code;
    public String status;

    public static Result success(Object data, Integer code, String status){
        Result result =new Result(data,code,status);
        return result;
    }
    public static Result success(Object data){
        Result result =new Result();
        result.setCode(ResultEnum.success.code);
        result.setStatus(ResultEnum.success.stauts);
        result.setData(data);
        return result;
    }
    public static Result success(Object data, Integer code){
        Result result =new Result(data,code,"success");
        return result;
    }

    public static Result fail(String message){
        Result result =new Result();
        result.code=ResultEnum.error.code;
        result.status=ResultEnum.error.stauts;
        result.setData(message);
        return result;
    }

}
