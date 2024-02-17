package com.hogwarts.imagess3.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.util.List;

@Configuration
public class S3Config {
    private final URI ip;
    private final Region region;
    private final List<String> bucketsNames;

    public S3Config(@Value("${s3.iphost}") String ip,
                    @Value("${s3.region}") String region,
                    @Value("${s3.buckets.names}") List<String> bucketsNames) {
        this.ip = URI.create(ip);
        this.region = Region.of(region);
        this.bucketsNames = bucketsNames;
    }

    private void initBucket(S3Client client, String bucketName) {
        CreateBucketRequest createAvaBucket = CreateBucketRequest.builder()
                .bucket(bucketName).build();
        try {
            client.createBucket(createAvaBucket);
        } catch (BucketAlreadyExistsException ignored) {
        } catch (S3Exception e) {
            System.err.println("WARN creating bucket: " + e.getMessage());
        }
    }

    @Bean
    public S3Client getS3Client() {
        S3Client client = S3Client.builder().region(region)
                .endpointOverride(ip).build();

        for (String bucketName : bucketsNames) {
            initBucket(client, bucketName);
        }

        return client;
    }

    @Bean
    public S3Presigner getS3Presigner() {
        return S3Presigner.builder().region(region)
                .endpointOverride(ip).build();
    }
}
