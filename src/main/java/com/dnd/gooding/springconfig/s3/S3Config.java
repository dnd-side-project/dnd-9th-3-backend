package com.dnd.gooding.springconfig.s3;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${cloud.aws.endpoint}")
    private String awsEndpoint;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretAccessKey;

    /**
     * AWS SDK에서 AWS 서비스를 사용하기 위해 별도의 인증 과정을 거친다.
     *
     * @return
     */
    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return AwsCredentialsProviderChain.builder()
                .reuseLastProviderEnabled(true)
                .credentialsProviders(
                        List.of(
                                DefaultCredentialsProvider.create(),
                                StaticCredentialsProvider.create(
                                        AwsBasicCredentials.create(accessKey, secretAccessKey))))
                .build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(awsCredentialsProvider())
                .region(Region.AP_NORTHEAST_2)
                .endpointOverride(URI.create(awsEndpoint))
                .build();
    }
}
