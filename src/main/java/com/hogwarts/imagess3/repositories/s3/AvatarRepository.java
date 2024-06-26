package com.hogwarts.imagess3.repositories.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Repository
public class AvatarRepository extends AbstractS3Repository<Long> {
    public AvatarRepository(S3Client client,
                            S3Presigner presigner,
                            @Value("${s3.bucket.avatar}") String avatarBucket) {
        super(client, presigner, avatarBucket, "Avatar");
    }

    @Override
    protected String toToken(Long rawToken) {
        return rawToken.toString();
    }
}
