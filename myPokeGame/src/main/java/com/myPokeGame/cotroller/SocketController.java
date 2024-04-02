package com.myPokeGame.cotroller;

import com.myPokeGame.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/socket")
@Slf4j
public class SocketController {

    @Value("${socketio.port}")
    Integer port;

    @GetMapping("/getSocketIoPort")
    public Result getSocketIoPort(HttpServletRequest request){
        log.info("is done");
        //获取前端的访问路径
        String requestUrl=request.getRequestURL().toString();
        //截取“地址：端口”子串
        String address=requestUrl.substring(7).split("/")[0];
        String host=address.split(":")[0];
        log.info("host:"+host);
        return Result.success(host+":"+port);
    }
}
