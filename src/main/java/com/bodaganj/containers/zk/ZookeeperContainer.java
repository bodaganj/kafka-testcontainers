package com.bodaganj.containers.zk;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

public class ZookeeperContainer extends GenericContainer<ZookeeperContainer> {

   public static final String DOCKER_IMAGE_NAME = "confluentinc/cp-zookeeper:";
   public static final String NETWORK_ALIAS = "zookeeper";
   public static final Integer PORT = 2181;

   public ZookeeperContainer(String version) {
      super(DOCKER_IMAGE_NAME + version);
      withExposedPorts(PORT);
      withNetwork(Network.SHARED);
      withNetworkAliases(NETWORK_ALIAS);
      withEnv("ZOOKEEPER_CLIENT_PORT", PORT.toString());
   }

   public String getInternalZookeeperUrl() {
      return NETWORK_ALIAS + ":" + PORT.toString();
   }
}
