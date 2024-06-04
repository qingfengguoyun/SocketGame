package com.myPokeGame.service.profilePhotoService;

import com.myPokeGame.entity.ProfilePhoto;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilePhotoService {
    public ProfilePhoto ChangeProfilePhoto(MultipartFile file);
}
