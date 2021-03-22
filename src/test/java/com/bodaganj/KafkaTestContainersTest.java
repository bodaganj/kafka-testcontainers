package com.bodaganj;

import com.bodaganj.data.TestMessage;
import com.bodaganj.kafka.helper.KafkaMessage;
import com.bodaganj.kafka.helper.KafkaTestHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = KafkaConfig.class)
@SpringBootTest(properties = {
   "kafka.topic=my_test_topic"
})
public class KafkaTestContainersTest {

   @Autowired
   private KafkaTestHelper<String> kafkaTestHelper;

   @Test
   void checkMessageIsProducedAndConsumedToDockerizedKafka() {
      // Given: generate test message
      TestMessage testMessage = TestMessage.builder()
                                           .id("Random_id_" + UUID.randomUUID().toString())
                                           .name("Bogdan")
                                           .surname("Ganzha")
                                           .build();

      // When: message is sent to dockerized kafka
      kafkaTestHelper.sendMessageAndWaitToAppear(testMessage.getId(), testMessage.toString());

      // Then: some simple checks are performed
      List<KafkaMessage<String>> kafkaMessages = kafkaTestHelper.getConsumer().consumeAll();

      assertThat(kafkaMessages)
         .as("The amount of messages is not correct.")
         .hasSize(1);
      assertThat(kafkaMessages.get(0).getKey())
         .as("Kafka key is not correct.")
         .isEqualTo(testMessage.getId());
   }
}
