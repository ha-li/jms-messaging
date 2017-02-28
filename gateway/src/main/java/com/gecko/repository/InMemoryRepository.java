package com.gecko.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlieu on 02/26/17.
 */
public class InMemoryRepository {
   private static Map<String, String> brokerUrlMap = new HashMap<String, String>();
   private static Map<String, String> producerMap = new HashMap<String, String> ();

   static {
      // tcp
      brokerUrlMap.put ("tcp", "tcp://localhost:61616?trace=true");
      //producerMap.put ("tcp", "tcp://localhost:61616?trace=true");

      //nio
      brokerUrlMap.put ("nio", "nio://localhost:61618?trace=true");
      //producerMap.put ("nio", "nio://localhost:61618?trace=true");

      //udp
      brokerUrlMap.put ("udp", "udp://localhost:6717?trace=true");
      //producerMap.put ("udp", "udp://localhost:6717?trace=true");

      //http
      brokerUrlMap.put ("http", "http://localhost:8080?trace=true");
      //producerMap.put ("http", "http://localhost:8080?trace=true");

      //http
      brokerUrlMap.put ("https", "https://localhost:1443?trace=true");
      //producerMap.put ("https", "https://localhost:1443?trace=true");

      //ssl
      brokerUrlMap.put ("ssl", "ssl://localhost:61617?trace=true");
      //producerMap.put ("ssl", "ssl://localhost:61617?trace=true");

      // using an embedded broker - don't need to start up jms broker separately
      // the embed broker can listen to other clients using nio on 61618
      // see embedded brokers in ActiveMq in Action (ch 4)
      brokerUrlMap.put ("vm", "vm:broker:(nio://localhost:61618)?brokerName=crazy");

   }

   private static final String DEFAULT_PROTOCOL = "ssl";

   public static String getBrokerUrl () {
      return getBrokerUrl (DEFAULT_PROTOCOL);
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
