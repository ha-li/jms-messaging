package com.gecko.broker.security.authorization;


import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.Message;
import org.apache.activemq.security.MessageAuthorizationPolicy;
import org.apache.camel.spi.AuthorizationPolicy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.Destination;

/**
 * A simple message authorization policy implemenation requiring messages have a
 * TransactionId token. The policy does not validate the value of the token, not
 * that it is non null.
 *
 * To install the policy on a activemq broker,
 *   1. compile and jar and copy to $ACTIVEMQ_HOME/lib
 *   2. in $ACTIVEMQ_HOME/conf/activemq add the following to the broker element
 *      <messageAuthorizationPolicy>
 *         <bean class="...this bean..."
 *               xmlns="http://www.springframework.org/schema/beans" />
 *      </messageAuthorizationPolicy>
 *   3. restart the broker
 *   4. ensure that each message contains the TransactionId, otherwise it will not get delivered.
 *
 *
 * Created by hlieu on 03/19/17.
 */
public class MessageAuthorization implements MessageAuthorizationPolicy {
   private static final Log LOG = LogFactory.getLog (AuthorizationPolicy.class);

   @Override
   public boolean isAllowedToConsume (ConnectionContext context, Message message) {
      // LOG.info ("Remote Address: " + context.getConnection ().getRemoteAddress ());
      // LOG.info ("Message: " + (String)message.getProperty ("UnknownProperty"));

      String TransactionId = "TransactionId";
      try {
         String tIdToken = (String) message.getProperty (TransactionId);
         Destination destination = message.getDestination ();
         LOG.info ("Message: " + message.getMessage () );
         //LOG.info ("Destination: " + destination.toString ());
         LOG.info ("Transaction Id: " + tIdToken);

         if (tIdToken != null) {
            LOG.info ("Permission to consume granted");
            return true;
         } else {
            LOG.info ("Permission to consume denied");
            return false;
         }

      } catch (Throwable t) {
         LOG.error ("MessageAuthorization error: ", t);
         return false;
      }
   }
}
