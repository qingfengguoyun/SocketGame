package com.myPokeGame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Image extends BaseEntity {

    String imageName;

    Long uploaderId;

    String imageType;

    String imageUrl;

}
