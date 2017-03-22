package com.gecko.broker.security.authentication.connection;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hlieu on 03/21/17.
 */
public class IpAddressAuthenticationFilter extends BrokerFilter {

   private static final Log LOG = LogFactory.getLog (IpAddressAuthenticationFilter.class);

   private List<String> allowedIpAddresses;

   private Pattern pattern = Pattern.compile (".*\\/\\/([0-9\\.]*):(.*)");


   public IpAddressAuthenticationFilter (Broker next, List<String> allowedAddresses) {
      super(next);
      this.allowedIpAddresses = allowedAddresses;
   }

   public void addConnection (ConnectionContext context, ConnectionInfo info)
         throws Exception {
      String remoteAddress = context.getConnection ().getRemoteAddress ();
      Matcher matcher = pattern.matcher(remoteAddress);

      if (matcher.matches ()) {
         String ip = matcher.group (1);
         if (!allowedIpAddresses.contains(ip)) {
            throw new SecurityException ("Connecting from IP address " + ip + " is not allowed");
         }
      } else {
         throw new SecurityException ("Invalid remote address " + remoteAddress);
      }

      LOG.info ("Connection permitted." );
      super.addConnection (context, info);
   }
}
