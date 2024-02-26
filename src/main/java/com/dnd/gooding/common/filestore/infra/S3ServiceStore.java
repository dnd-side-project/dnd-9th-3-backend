package com.dnd.gooding.common.filestore.infra;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dnd.gooding.common.filestore.api.FileStore;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class S3ServiceStore implements FileStore {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3ServiceStore(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Transactional
    @Override
    public void delete(String key) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    @Transactional
    @Override
    public String create(File file, String bucket, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
