package com.gecko.message.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by hlieu on 03/1/17.
 */
public interface SessionProvider {
   public Session session (Connection connection) throws JMSException;
}
