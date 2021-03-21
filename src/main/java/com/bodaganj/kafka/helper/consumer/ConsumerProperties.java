package com.bodaganj.kafka.helper.consumer;

import lombok.Builder;
import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;

@Data
@Builder
public class ConsumerProperties<V> {

   @Builder.Default
   private final String groupId = "TestGroupId";
   private final String brokers;
   private final Deserializer<V> deserializer;
   private final String topic;
   @Builder.Default
   private final Properties securityProperties = new Properties();
   @Builder.Default
   private final Duration duration = Duration.ofSeconds(2);

   public Properties getConsumerProperties() {
      Properties props = new Properties();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.name().toLowerCase());
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer.getClass());
      props.putAll(securityProperties);
      return props;
   }
}
