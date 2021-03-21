package com.bodaganj;

import com.bodaganj.containers.schema.SchemaRegistryContainer;
import com.bodaganj.containers.zk.ZookeeperContainer;
import com.bodaganj.kafka.helper.KafkaTestHelper;
import com.bodaganj.kafka.helper.consumer.ConsumerProperties;
import com.bodaganj.kafka.helper.consumer.KafkaTestConsumerFactory;
import com.bodaganj.kafka.helper.producer.KafkaTestProducerFactory;
import com.bodaganj.kafka.helper.producer.ProducerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.Stream;

@Slf4j
@Configuration
public class KafkaConfig {

   private final String confluentVersion = "5.2.1";

   @Bean
   public KafkaContainer kafkaContainer(ZookeeperContainer zookeeperContainer) {
      KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:" + confluentVersion))
         .withExternalZookeeper(zookeeperContainer.getInternalZookeeperUrl())
         .withNetwork(Network.SHARED)
         .withNetworkAliases("kafka");

      Startables.deepStart(Stream.of(kafkaContainer)).join();
      return kafkaContainer;
   }

   @Bean
   public ZookeeperContainer zookeeperContainer() {
      ZookeeperContainer zookeeperContainer = new ZookeeperContainer(confluentVersion);
      Startables.deepStart(Stream.of(zookeeperContainer)).join();
      return zookeeperContainer;
   }

   @Bean
   public SchemaRegistryContainer schemaRegistryContainer() {
      SchemaRegistryContainer schemaRegistryContainer = new SchemaRegistryContainer();
      Startables.deepStart(Stream.of(schemaRegistryContainer)).join();
      return schemaRegistryContainer;
   }

   @Lazy
   @Bean(destroyMethod = "close")
   KafkaTestHelper<String> kafkaTestHelper(KafkaContainer kafkaContainer, @Value("${kafka.topic}") String topic) {
      log.info("Creating KafkaTestHelper...");
      return new KafkaTestHelper<>(
         KafkaTestProducerFactory.create(
            ProducerProperties.<String>builder()
               .brokers(kafkaContainer.getBootstrapServers())
               .serializer(new StringSerializer())
               .topic(topic)
               .build()
         ),
         KafkaTestConsumerFactory.create(
            ConsumerProperties.<String>builder()
               .brokers(kafkaContainer.getBootstrapServers())
               .deserializer(new StringDeserializer())
               .topic(topic)
               .build()
         )
      );
   }
}
