package com.gecko.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlieu on 02/26/17.
 *
 * In order to use ssl protocol, the following jvm properties need to be
 * defined in your executable. You must have created these public and private keys,
 * see ActiveMQ in Action, ch 3/4.
 *
 *  -Djavax.net.ssl.keyStore=/Users/hlieu/Security/KeyStores/jms.client.ks
 *  -Djavax.net.ssl.keyStorePassword=jms123
 *  -Djavax.net.ssl.trustStore=/Users/hlieu/Security/KeyStores/jms.client.ts
 *
 */
public class InMemoryRepository {
   private static Map<String, String> brokerUrlMap = new HashMap<String, String>();

   static {
      // tcp
      brokerUrlMap.put ("tcp", "tcp://localhost:61616?trace=true");

      //nio
      brokerUrlMap.put ("nio", "nio://localhost:61620?trace=true");

      //udp
      //brokerUrlMap.put ("udp", "udp://localhost:6717?trace=true");

      //http
      brokerUrlMap.put ("http", "http://localhost:8080?trace=true");

      //http
      //brokerUrlMap.put ("https", "https://localhost:1443?trace=true");

      //ssl
      brokerUrlMap.put ("ssl", "ssl://localhost:61618?trace=true");

      // using an embedded broker - don't need to start up jms broker separately
      // the embed broker can listen to other clients using nio on 61618
      // see embedded brokers in ActiveMq in Action (ch 4)
      // embedded brokers are configured with vm:broker:(...)
      // where ... is the other protocol that different clients connect
      // to the embedded broker
      brokerUrlMap.put ("vm", "vm:broker:(nio://localhost:61618)?brokerName=crazy");
      brokerUrlMap.put ("vm_producer", "nio://localhost:61618?trace=true");

      // an example of a network connector, producer is connecting to broker 2
      // where the protocol port number is broker1's port + 1, so for ssl, broker 2
      // port number is 61619
      brokerUrlMap.put ("producer", "nio://localhost:61621?trace=true");

      brokerUrlMap.put ("failover", "failover:(nio://localhost:61620,tcp://localhost:61617)");

      brokerUrlMap.put ("multicast", "discovery:(multicast://default)");
   }

   private static final String DEFAULT_PROTOCOL = "ssl";

   public static String getBrokerUrl () {
      return getBrokerUrl (DEFAULT_PROTOCOL);
   }

   public static String getProducerBrokerUrl () {
      return getBrokerUrl ("producer");
   }

   public static String getBrokerUrl (String protocol) {
      String brokerUrl = getBrokerMap ().get(protocol);
      if (brokerUrl == null) {
         throw new NullPointerException("No such consumer protocol exists.");
      }

      return brokerUrl;
   }

   private static Map<String, String> getBrokerMap () { return brokerUrlMap; }

}
