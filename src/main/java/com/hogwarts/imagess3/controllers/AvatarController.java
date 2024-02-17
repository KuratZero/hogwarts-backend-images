package com.hogwarts.imagess3.controllers;

import com.hogwarts.imagess3.services.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/1/images/avatars")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @GetMapping("/user")
    public String findUserAvatar(@RequestParam("id") Long id) {
        return avatarService.findUserAvatar(id);
    }

    @PutMapping("/user")
    public void updateUserAvatar(@RequestHeader("Authorization") String jwt,
                                 @RequestParam("file") MultipartFile image) {
        avatarService.updateUserAvatar(jwt, image);
    }

    @DeleteMapping("/user")
    public void deleteUserAvatar(@RequestHeader("Authorization") String jwt) {
        avatarService.deleteUserAvatar(jwt);
    }

}
