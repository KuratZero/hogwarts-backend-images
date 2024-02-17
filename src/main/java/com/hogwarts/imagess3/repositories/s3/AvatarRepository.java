package com.hogwarts.imagess3.repositories.s3;

import com.hogwarts.imagess3.exceptions.NoSuchImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Repository
public class AvatarRepository extends AbstractS3Repository {

    public AvatarRepository(S3Client client,
                            S3Presigner presigner,
                            @Value("${s3.bucket.avatar}") String avatarBucket) {
        super(client, presigner, avatarBucket);
    }

    public void deleteAvatarById(Long id) {
        try {
            super.deleteImage(id.toString());
        } catch (NoSuchImage e) {
            throw new NoSuchImage("Avatar does not exist");
        }
    }

    public void updateAvatarById(Long id, byte[] avatar) {
        super.updateImage(id.toString(), avatar);
    }

    public String getAvatarById(Long id) {
        try {
            return super.getImage(id.toString());
        } catch (NoSuchImage e) {
            throw new NoSuchImage("No user avatar found.", e);
        }
    }
}
