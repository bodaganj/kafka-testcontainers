package com.bodaganj.kafka.helper.consumer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaTestConsumerFactory {

   public static <V> KafkaTestConsumer<V> create(ConsumerProperties<V> consumerProperties) {
      return new KafkaTestConsumer<>(createConsumer(consumerProperties), consumerProperties.getTopic(), consumerProperties.getDuration());
   }

   private static <V> KafkaConsumer<String, V> createConsumer(ConsumerProperties<V> consumerProperties) {
      return new KafkaConsumer<>(consumerProperties.getConsumerProperties());
   }
}
