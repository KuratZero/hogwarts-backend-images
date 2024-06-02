package com.hogwarts.imagess3.repositories.s3;

import com.hogwarts.imagess3.exceptions.NoSuchSubject;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;

public abstract class AbstractS3Repository<T> {
    private final S3Client client;
    private final S3Presigner presigner;
    private final String bucketName;
    private final String subjectName;

    public AbstractS3Repository(S3Client client,
                                S3Presigner presigner,
                                String bucketName,
                                String subjectName) {
        this.client = client;
        this.presigner = presigner;
        this.bucketName = bucketName;
        this.subjectName = subjectName;
    }

    protected abstract String toToken(T rawToken);

    public Boolean subjectIsPresent(T rawToken) {
        String token = toToken(rawToken);

        try {
            client.headObject(
                    HeadObjectRequest.builder()
                            .bucket(bucketName).key(token).build());
        } catch (NoSuchKeyException ignored) {
            return false;
        }
        return true;
    }

    public void deleteSubject(T rawToken) throws NoSuchSubject {
        String token = toToken(rawToken);

        if (!subjectIsPresent(rawToken)) {
            throw new NoSuchSubject(subjectName, "Not present.");
        }

        client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName).key(token).build());
    }

    public void updateSubject(T rawToken, byte[] newImage) {
        String token = toToken(rawToken);

        try {
            deleteSubject(rawToken);
        } catch (NoSuchSubject ignored) {
        }

        client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName).key(token).build(),
                RequestBody.fromBytes(newImage));
    }

    public String getSubject(T rawToken) throws NoSuchSubject {
        String token = toToken(rawToken);
        if (!subjectIsPresent(rawToken)) {
            throw new NoSuchSubject(subjectName, "Not present.");
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
