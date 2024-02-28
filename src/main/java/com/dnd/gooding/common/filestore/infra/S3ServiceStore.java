package com.dnd.gooding.common.filestore.infra;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class S3ServiceStore {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3ServiceStore(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Transactional
    public void delete(String key) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    @Transactional
    public String create(File file) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, file.getName(), file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, file.getName()).toString();
    }
}
