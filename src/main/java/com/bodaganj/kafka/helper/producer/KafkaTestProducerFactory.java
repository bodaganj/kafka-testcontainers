package com.bodaganj.kafka.helper.producer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaTestProducerFactory {

   public static <V> KafkaTestProducer<V> create(ProducerProperties<V> producerProperties) {
      return new KafkaTestProducer<>(createProducer(producerProperties), producerProperties.getTopic());
   }

   private static <V> KafkaProducer<String, V> createProducer(ProducerProperties<V> producerProperties) {
      return new KafkaProducer<>(producerProperties.getProducerProperties());
   }
}
