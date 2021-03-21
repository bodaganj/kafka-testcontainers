package com.bodaganj.kafka.helper.producer;

import lombok.Builder;
import lombok.Data;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@Data
@Builder
public class ProducerProperties<V> {

   private final String brokers;
   private final Serializer<V> serializer;
   private final String topic;
   @Builder.Default
   private final Properties securityProperties = new Properties();

   public Properties getProducerProperties() {
      Properties props = new Properties();
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
      props.put(ProducerConfig.RETRIES_CONFIG, 0);
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializer.getClass());
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
      props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
      props.putAll(securityProperties);
      return props;
   }
}
