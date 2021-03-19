package com.bodaganj;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaTestContainersTest {

   private static KafkaContainer kafka;

   @BeforeAll
   public static void setUp() {
      kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
   }

   @Test
   void checkMessageIsProducedAndConsumedToDockerizedKafka() {
      // Given: generate avro message

      // When: message is sent to dockerized kafka

      // Then: message is consumed from dockerized kafka
   }
}
