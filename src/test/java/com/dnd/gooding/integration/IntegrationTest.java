package com.dnd.gooding.integration;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

@Ignore
@Transactional
@SpringBootTest
@ContextConfiguration(initializers = IntegrationTest.IntegrationTestInitializer.class)
public class IntegrationTest {

    static DockerComposeContainer rdbms;
    static LocalStackContainer aws;

    static {
        rdbms =
                new DockerComposeContainer(new File("infra/test/docker-compose.yml"))
                        .withExposedService(
                                "local-db",
                                3306,
                                Wait.forLogMessage(".*ready for connections.*", 1)
                                        .withStartupTimeout(Duration.ofSeconds(180L)))
                        .withExposedService(
                                "local-db-migrate",
                                0,
                                Wait.forLogMessage("(.*Successfully applied.*)|(.*Successfully validated.*)", 1)
                                        .withStartupTimeout(Duration.ofSeconds(180L)));

        rdbms.start();

        aws =
                (new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.11.2")))
                        .withServices(LocalStackContainer.Service.S3)
                        .withStartupTimeout(Duration.ofSeconds(600));
        aws.start();
    }

    static class IntegrationTestInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            Map<String, String> properties = new HashMap<>();
            String rdbmsMasterHost = rdbms.getServiceHost("local-db", 3306);
            Integer rdbmsMasterPort = rdbms.getServicePort("local-db", 3306);
            String rdbmsSlaveHost = rdbms.getServiceHost("local-db", 3306);
            Integer rdbmsSlavePort = rdbms.getServicePort("local-db", 3306);

            properties.put(
                    "spring.datasource.url",
                    "jdbc:mysql://" + rdbmsMasterHost + ":" + rdbmsMasterPort + "/gooding");

            properties.put(
                    "spring.datasource.slaves.slave1.url",
                    "jdbc:mysql://" + rdbmsSlaveHost + ":" + rdbmsSlavePort + "/gooding");

            try {
                aws.execInContainer("awslocal", "s3api", "create-bucket", "--bucket", "record-bucket");

                properties.put("cloud.aws.endpoint", aws.getEndpoint().toString());
                properties.put("cloud.aws.credentials.access-key", "localstack-access-key");
                properties.put("cloud.aws.credentials.secret-key", "localstack-secret-key");
                properties.put("cloud.aws.s3.bucket", "record-bucket");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            TestPropertyValues.of(properties).applyTo(applicationContext);
        }
    }
}
