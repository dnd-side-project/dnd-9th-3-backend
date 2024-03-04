package com.dnd.gooding.common.filestore.infra;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Slf4j
@Service
public class S3ServiceStore {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3ServiceStore(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String putFile(String key, File file) {
        s3Client.putObject(
                (req) -> {
                    req.bucket(bucket);
                    req.key(key);
                },
                RequestBody.fromFile(file));

        return s3Client.utilities().getUrl(request -> request.bucket(bucket).key(key)).toString();
    }

    public File getFile(String key) {
        File file = new File(key);
        ResponseInputStream<GetObjectResponse> s3Object =
                s3Client.getObject(
                        (req) -> {
                            req.bucket(bucket);
                            req.key(key);
                        });

        try {
            FileUtils.writeByteArrayToFile(file, s3Object.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest =
                DeleteObjectRequest.builder().bucket(bucket).key(key).build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}
