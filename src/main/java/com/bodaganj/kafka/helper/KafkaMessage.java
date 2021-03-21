package com.bodaganj.kafka.helper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Getter
@EqualsAndHashCode
public class KafkaMessage<V> {

   final String key;
   final V value;

   public KafkaMessage(String key, V value) {
      this.key = key;
      this.value = value;
   }

   public KafkaMessage(ConsumerRecord<String, V> consumerRecord) {
      this.key = consumerRecord.key();
      this.value = consumerRecord.value();
   }
}
