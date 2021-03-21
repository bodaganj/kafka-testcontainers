package com.bodaganj.kafka.helper;

import com.bodaganj.kafka.helper.consumer.KafkaTestConsumer;
import com.bodaganj.kafka.helper.producer.KafkaTestProducer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class KafkaTestHelper<V> implements AutoCloseable {

   private final KafkaTestProducer<V> producer;
   private final KafkaTestConsumer<V> consumer;
   public Duration duration = Duration.ofSeconds(2);

   public void sendMessageAndWaitToAppear(String key, V value) {
      producer.send(key, value);
      waitForMessage(key, value);
   }

   @Override
   public void close() {
      producer.close();
      consumer.close();
   }

   private void waitForMessage(String key, V value) {
      Awaitility.await().atMost(duration).untilAsserted(() -> {
         List<KafkaMessage<V>> messages = consumer.consumeAll();
         assert anyKey(messages, key);
         assert anyValue(messages, value);
      });
   }

   private boolean anyKey(List<KafkaMessage<V>> messages, String key) {
      return messages.stream()
                     .anyMatch(km -> km.key.equals(key));
   }

   private boolean anyValue(List<KafkaMessage<V>> messages, V value) {
      return messages.stream()
                     .anyMatch(km -> {
                        if (km.value == null) {
                           return km.value == value;
                        }
                        return km.value.equals(value);
                     });
   }
}
