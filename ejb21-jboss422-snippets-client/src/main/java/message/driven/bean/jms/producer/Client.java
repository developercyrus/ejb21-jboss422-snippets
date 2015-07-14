package message.driven.bean.jms.producer;

import java.net.InetAddress;
import java.rmi.dgc.VMID;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Client {
	public static void main(String args[]) throws Exception {
		Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        props.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");

        Context ctx = new InitialContext(props);
        Object o = ctx.lookup("ConnectionFactory");
        ConnectionFactory qcf = (ConnectionFactory) o;
        Connection conn = qcf.createConnection();
        Queue queue = (Queue) ctx.lookup("queue/consumingQueue");
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        conn.start();
                
        MessageProducer producer = session.createProducer(queue);
        for(int m = 0; m < 1; m ++) {
			String hostname = InetAddress.getLocalHost().getHostName();
			VMID vmid = new VMID();      
			String text = "sent by MDB client console"
							+ System.getProperty("line.separator") + "at hostname=" + hostname 
							+ System.getProperty("line.separator") + "at PID=" + getPID()
							+ System.getProperty("line.separator") + "at VMID=" + vmid.toString();        	 
            TextMessage tm = session.createTextMessage(text);
            producer.send(tm);
        }
        producer.close();
        session.close();
        conn.close();
	}
	
	public static long getPID() {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		return Long.parseLong(processName.split("@")[0]);
	}	
}
