package com.bodaganj.kafka.helper.consumer;

import com.bodaganj.kafka.helper.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class KafkaTestConsumer<V> implements AutoCloseable {

   private final KafkaConsumer<String, V> consumer;
   private final String topic;
   private final Duration defaultPollDuration;

   /**
    * Method consume all messages from current offset to end offset.
    * End offset is read once - after this method is called
    * @return list with consumed messages
    */
   public List<KafkaMessage<V>> consumeAll() {
      assignToPartitionAndSeekToBeginning(topic);

      List<KafkaMessage<V>> expectedMessages = new ArrayList<>();
      Map<TopicPartition, Long> endOffsets = consumer.endOffsets(getTopicPartitions(topic));

      getTopicPartitions(topic).forEach(partition -> {
         long endOffset = endOffsets.get(partition);
         log.info("Started polling messages from topic: [{}], partition: [{}] to offset: [{}]", topic, partition.partition(), endOffset);
         List<KafkaMessage<V>> partitionMessages = new ArrayList<>();
         while (consumer.position(partition) < endOffset) {
            consumer.poll(defaultPollDuration).forEach(consumerRecord -> partitionMessages.add(new KafkaMessage<>(consumerRecord)));
         }

         expectedMessages.addAll(partitionMessages);
         log.info("Consumed [{}] messages from topic: [{}], partition: [{}]", partitionMessages.size(), topic, partition.partition());
      });
      return expectedMessages;
   }

   @Override
   public void close() {
      consumer.close();
   }

   private void assignToPartitionAndSeekToBeginning(String topic) {
      List<TopicPartition> partitions = getTopicPartitions(topic);
      consumer.assign(partitions);
      consumer.seekToBeginning(partitions);
   }

   private List<TopicPartition> getTopicPartitions(String topic) {
      return consumer.partitionsFor(topic)
                     .stream()
                     .map(pi -> new TopicPartition(pi.topic(), pi.partition()))
                     .collect(Collectors.toList());
   }
}
