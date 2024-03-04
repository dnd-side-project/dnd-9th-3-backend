package com.dnd.gooding.integration.filestore;

import com.dnd.gooding.common.filestore.infra.S3ServiceStore;
import com.dnd.gooding.integration.IntegrationTest;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

@DisplayName("S3 서비스 통합 테스트")
class S3ServiceStoreTest extends IntegrationTest {

    @Autowired private S3ServiceStore s3ServiceStore;

    @DisplayName("S3 서비스에 파일을 넣고 가져온다.")
    @Test
    public void s3PutAndGetTest() throws Exception {
        // given
        String key = "sampleObject.txt";
        File sampleFile = new ClassPathResource("static/sample.txt").getFile();

        // when
        String url = s3ServiceStore.putFile(key, sampleFile);

        // then
        File resultFile = s3ServiceStore.getFile(key);

        List<String> sampleFileLines = FileUtils.readLines(sampleFile, "UTF-8");
        List<String> resultFileLines = FileUtils.readLines(resultFile, "UTF-8");

        Assertions.assertIterableEquals(sampleFileLines, resultFileLines);
    }
}
