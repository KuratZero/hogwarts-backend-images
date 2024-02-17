package com.hogwarts.imagess3.services;

import com.hogwarts.imagess3.exceptions.ImageUploadException;
import com.hogwarts.imagess3.repositories.employee.JWTEmployeeRepository;
import com.hogwarts.imagess3.repositories.s3.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final JWTEmployeeRepository jwtRepository;

    public void deleteUserAvatar(String jwt) {
        Long id = jwtRepository.getUserIdByJwt(jwt);
        avatarRepository.deleteAvatarById(id);
    }

    public String findUserAvatar(Long id) {
        return avatarRepository.getAvatarById(id);
    }

    public void updateUserAvatar(String jwt, MultipartFile imageFile) {
        Long id = jwtRepository.getUserIdByJwt(jwt);

        if (imageFile.getSize() > 10 * 1024) {
            throw new ImageUploadException("Image's weight is too large!");
        }

        try {
            BufferedImage image = ImageIO.read(imageFile.getInputStream());
            if (image.getHeight() != image.getWidth() || image.getWidth() != 120) {
                throw new ImageUploadException("Image must be 120x120 pixels.");
            }

            avatarRepository.updateAvatarById(id, imageFile.getBytes());
        } catch (IOException e) {
            throw new ImageUploadException("Image read failed.");
        }
    }
}
