package com.hogwarts.imagess3.repositories.s3;

import com.hogwarts.imagess3.exceptions.NoSuchImage;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;

@Getter
abstract public class AbstractS3Repository {
    private final S3Client client;
    private final S3Presigner presigner;
    private final String bucketName;

    public AbstractS3Repository(S3Client client,
                                S3Presigner presigner,
                                String bucketName) {
        this.client = client;
        this.presigner = presigner;
        this.bucketName = bucketName;
    }

    @PreDestroy
    public void closeAll() {
        client.close();
        presigner.close();
    }

    public Boolean imageIsPresent(String token) {
        try {
            client.headObject(
                    HeadObjectRequest.builder()
                            .bucket(bucketName).key(token).build());
        } catch (NoSuchKeyException ignored) {
            return false;
        }
        return true;
    }

    public void deleteImage(String token) throws NoSuchImage {
        if (!imageIsPresent(token)) {
            throw new NoSuchImage("Image is not present.");
        }
        client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName).key(token).build());
    }

    public void updateImage(String token, byte[] newImage) {
        try {
            deleteImage(token);
        } catch (NoSuchImage ignored) {
        }

        client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName).key(token).build(),
                RequestBody.fromBytes(newImage));
    }

    public String getImage(String token) throws NoSuchImage {
        if (!imageIsPresent(token)) {
            throw new NoSuchImage("Not found this image.");
        }

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketName).key(token).build();

        GetObjectPresignRequest getObjectPresignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();

        PresignedGetObjectRequest presignedGetObjectRequest =
                presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }
}
