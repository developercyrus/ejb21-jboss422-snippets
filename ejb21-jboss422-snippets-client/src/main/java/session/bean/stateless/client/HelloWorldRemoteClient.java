package session.bean.stateless.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.dgc.VMID;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import session.bean.stateless.HelloWorldHome;
import session.bean.stateless.HelloWorldObject;

public class HelloWorldRemoteClient {
    public static void main(String[] args) throws NamingException, UnknownHostException, RemoteException, CreateException {
        
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        //different vm in different machine
        props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        props.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");

        Context ctx = new InitialContext(props);
        Object ref = ctx.lookup("HelloWorldStatelessRemote");
        HelloWorldHome h = (HelloWorldHome)PortableRemoteObject.narrow(ref, HelloWorldHome.class);
        HelloWorldObject o = h.create();

        String hostname = InetAddress.getLocalHost().getHostName();

        VMID vmid = new VMID();
        String result = o.sayHello("get Hello World by remote client"
        							+ System.getProperty("line.separator") + "at hostname=" + hostname 
        							+ System.getProperty("line.separator") + "at PID=" + getPID()
        							+ System.getProperty("line.separator") + "at VMID=" + vmid.toString());
        System.out.println(result); 
    }
    
	public static long getPID() {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		return Long.parseLong(processName.split("@")[0]);
	}
}
