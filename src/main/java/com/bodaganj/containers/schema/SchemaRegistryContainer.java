package com.bodaganj.containers.schema;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.images.builder.ImageFromDockerfile;

public class SchemaRegistryContainer extends GenericContainer<SchemaRegistryContainer> {

   public static final String NETWORK_ALIAS = "schema.registry";
   public static final Integer PORT = 9090;

   public SchemaRegistryContainer() {
      super(new ImageFromDockerfile().withFileFromClasspath("Dockerfile", "src/main/resources/Dockerfile"));
      withExposedPorts(PORT);
      withNetwork(Network.SHARED);
      withNetworkAliases(NETWORK_ALIAS);
   }

   public String getSchemaUrl() {
      return "http://localhost" + ":" + getFirstMappedPort() + "/api/v1";
   }
}
