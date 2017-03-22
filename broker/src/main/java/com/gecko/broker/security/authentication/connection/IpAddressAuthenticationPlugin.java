package com.gecko.broker.security.authentication.connection;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;

import java.util.List;

/**
 * A plugin for ActiveMQ that instantiates the class with the desired behavior.
 * That desired behavior class should in turn implement BrokerFilter, which
 * extends Broker, and is the return type of Plugin.installPlugin (...)
 *
 * To install this to ActiveMQ broker:
 *  1. create jar with this plugin class and the Broker implementation
 *  2. install jar to $ACTIVEMQ_HOME/lib
 *  3. in $ACTIVEMQ_HOME/conf/activemq.xml add the following to the broker element
 *     <bean xmlns="http://www.springframework.org/schema/beans" id="ipAuthenticationPlugin"
 *         class="com.gecko.broker.security.authentication.connection.IpAddressAuthenticationPlugin">
 *        <property name="allowedIpAddresses">
 *            <list>
 *                <value>127.0.0.1</value>
 *            </list>
 *        </property>
 *     </bean>
 *
 * Now when a client connects to the broker, the ip address of the
 * client will be authenticated, and only those from local host (127.0.0.1)
 * will be allowed to connect. All others will be denied connection.
 *
 * Created by hlieu on 03/21/17.
 */
public class IpAddressAuthenticationPlugin implements BrokerPlugin {

   private List<String> allowedIpAddresses;

   public Broker installPlugin (Broker broker) throws Exception {
      return new IpAddressAuthenticationFilter (broker, allowedIpAddresses);
   }

   public List<String> getAllowedIpAddresses () {
      return allowedIpAddresses;
   }

   public void setAllowedIpAddresses (List<String> allowedIpAddresses) {
      this.allowedIpAddresses = allowedIpAddresses;
   }
}
