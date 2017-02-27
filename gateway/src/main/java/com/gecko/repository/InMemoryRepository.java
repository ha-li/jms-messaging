package com.gecko.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlieu on 02/26/17.
 */
public class InMemoryRepository {
   private static Map<String, String> consumerMap = new HashMap<String, String>();
   private static Map<String, String> producerMap = new HashMap<String, String> ();

   static {
      // tcp
      consumerMap.put ("tcp", "tcp://localhost:61616?trace=true");
      producerMap.put ("tcp", "tcp://localhost:61616?trace=true");

      //nio
      consumerMap.put ("nio", "nio://localhost:61618?trace=true");
      producerMap.put ("nio", "nio://localhost:61618?trace=true");

      //udp
      consumerMap.put ("udp", "udp://localhost:6717?trace=true");
      producerMap.put ("udp", "udp://localhost:6717?trace=true");

      //http
      consumerMap.put ("http", "http://localhost:8080?trace=true");
      producerMap.put ("http", "http://localhost:8080?trace=true");

      //http
      consumerMap.put ("https", "https://localhost:1443?trace=true");
      producerMap.put ("https", "https://localhost:1443?trace=true");

      //ssl
      consumerMap.put ("ssl", "ssl://localhost:61617?trace=true");
      producerMap.put ("ssl", "ssl://localhost:61617?trace=true");

   }

   private static final String DEFAULT_PRODUCER_PROTOCOL = "nio";
   private static final String DEFAULT_CONSUMER_PROTOCOL = "ssl";

   // uses producer's default protocol which is nio
   public static String getProducerBrokerUrl () {
      return getProducerBrokerUrl (DEFAULT_PRODUCER_PROTOCOL);
   }

   // uses the consumer default ssl protocol, which is ssl
   public static String getConsumerBrokerUrl () {
      return getConsumerBrokerUrl (DEFAULT_CONSUMER_PROTOCOL);
   }

   public static String getConsumerBrokerUrl (String protocol) {
      String brokerUrl = getConsumerMap ().get(protocol);
      if (brokerUrl == null) {
         throw new NullPointerException("No such consumer protocol exists.");
      }

      return brokerUrl;
   }

   public static String getProducerBrokerUrl (String protocol) {
      String brokerUrl = getProducerMap ().get(protocol);
      if (brokerUrl == null) {
         throw new NullPointerException ("No such producer protocol exists.");
      }
      return brokerUrl;
   }

   private static Map<String, String> getConsumerMap () { return consumerMap; }

   private static Map<String, String> getProducerMap () { return producerMap; }
}
