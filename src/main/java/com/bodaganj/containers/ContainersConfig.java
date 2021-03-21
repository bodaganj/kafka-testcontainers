package com.bodaganj.containers;

import com.bodaganj.containers.schema.SchemaRegistryContainer;
import com.bodaganj.containers.zk.ZookeeperContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.Stream;

@Configuration
public class ContainersConfig {

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
}
