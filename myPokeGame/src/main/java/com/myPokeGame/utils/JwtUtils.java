package com.myPokeGame.utils;

import cn.hutool.jwt.JWTUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.myPokeGame.models.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component

public class JwtUtils {

    @Autowired
    HttpServletRequest request;
    @Value("${jwt.header}")
    private String conHeader;
    @Value("${jwt.tokenPrefix}")
    private String conTokenPrefix;
    @Value("${jwt.secret}")
    private String conSecret;
    @Value("${jwt.expireTime}")
    private Integer conExpireTime;

    private static String header;

    private static String tokenPrefix;

    private static String secret;

    private static Long expireTime;

    @PostConstruct
    private void jwtUtilInit(){
        JwtUtils.header=conHeader;
        JwtUtils.secret=conSecret;
        JwtUtils.tokenPrefix=conTokenPrefix;
        setExpireTime(conExpireTime);
    }

    public static final String CLAIM_ACCOUNT="account";
    public static final String CLAIM_USER_NAME="userName";
    public static final String CLAIM_USER_ID="userId";
    //用户角色（权限）
    public static final String CLAIM_USER_ROLES="userRoles";
    //其他属性


    public void setHeader(String header){
        header=header;
    }

    public void setTokenPrifix(String tokenPrifix){
        JwtUtils.tokenPrefix=tokenPrifix;
    }

    public void setSecret(String secret){
        JwtUtils.secret=secret;
    }

    public void setExpireTime(Integer expireTimeInt){
        JwtUtils.expireTime=expireTimeInt*1000L*60;
    }

    public static String createToken(Long userId,String userName){
        log.info("secret:"+secret);
        return tokenPrefix+ JWT.create().withClaim(CLAIM_USER_ID,userId).withClaim(CLAIM_USER_NAME,userName)
                .withExpiresAt(new Date(System.currentTimeMillis()+expireTime)).sign(Algorithm.HMAC512(secret));
    }

    public static UserVo validateToken(String token){

        try{
            DecodedJWT jwt=JWT.require(Algorithm.HMAC512(secret)).build().verify(token.replaceAll(tokenPrefix,""));
            Long userId= jwt.getClaim(CLAIM_USER_ID).asLong();
            String userName=jwt.getClaim(CLAIM_USER_NAME).asString();
            return UserVo.builder().userId(userId).userName(userName).build();
        }catch (TokenExpiredException e){
            throw new RuntimeException("token过期");
        }catch (Exception e){
            throw new RuntimeException("token非法");
        }
    }

    public  UserVo validateToken(){
        try{
            String token=request.getHeader(header);
            if(ObjectUtils.isEmpty(token)){
                throw new RuntimeException("token不存在");
            }
            DecodedJWT jwt=JWT.require(Algorithm.HMAC512(secret)).build().verify(token.replaceAll(tokenPrefix,""));
            Long userId= jwt.getClaim(CLAIM_USER_ID).asLong();
            String userName=jwt.getClaim(CLAIM_USER_NAME).asString();
            return UserVo.builder().userId(userId).userName(userName).build();
        }catch (TokenExpiredException e){
            throw new RuntimeException("token过期");
        }catch (Exception e){
            throw new RuntimeException("token非法");
        }
    }
}
