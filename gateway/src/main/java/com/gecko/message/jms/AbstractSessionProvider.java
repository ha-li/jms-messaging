package com.gecko.message.jms;

import com.gecko.message.jms.SessionProvider;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by hlieu on 03/1/17.
 */
public abstract class AbstractSessionProvider implements SessionProvider {

   public Session session (Connection connection) throws JMSException {
      return connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
   }
}
