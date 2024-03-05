package com.project_02_28.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    Integer id;
    String userName;


    public static class Builder{
        Integer Id;
        String userName;

        public Builder id(Integer id){
            this.Id=id;
            return this;
        }

        public Builder userName(String userName){
            this.userName=userName;
            return this;
        }

        public User build(){
            return new User(this.Id,this.userName);
        }
    }

    public static Builder Builder(){
        return new Builder();
    }
}
