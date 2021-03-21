package com.bodaganj.kafka.helper.producer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class KafkaTestProducer<V> implements AutoCloseable {

   private final KafkaProducer<String, V> producer;
   private final String topic;

   @Override
   public void close() {
      producer.close();
   }

   public RecordMetadata send(String key, V value) {
      try {
         return sendAsync(key, value).get();
      } catch (InterruptedException | ExecutionException e) {
         throw new RuntimeException(e);
      }
   }

   private Future<RecordMetadata> sendAsync(String key, V value) {
      final ProducerRecord<String, V> record = createRecord(key, value);
      log.info("Sending message with key: [{}], value: [{}] to topic: [{}]", record.key(), record.value(), this.topic);
      return producer.send(record);
   }

   private ProducerRecord<String, V> createRecord(String key, V value) {
      return new ProducerRecord<>(this.topic, key, value);
   }
}
