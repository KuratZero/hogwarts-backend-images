package com.hogwarts.imagess3.repositories.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.util.UUID;

@Repository
public class FileRepository extends AbstractS3Repository<UUID> {
    public FileRepository(S3Client client, S3Presigner presigner,
                          @Value("${s3.bucket.files}") String bucketName) {
        super(client, presigner, bucketName, "File");
    }

    @Override
    protected String toToken(UUID rawToken) {
        return rawToken.toString();
    }
}
