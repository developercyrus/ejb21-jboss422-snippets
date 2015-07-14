package message.driven.bean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.dgc.VMID;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import bean.managed.persistence.entity.bean.HelloWorldHome;
import bean.managed.persistence.entity.bean.HelloWorldObject;

public class HelloWorldImpl implements MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 341069222778024567L;
	private MessageDrivenContext context = null;
	
	public void ejbCreate() {}
	
	public void ejbRemove() throws EJBException {
		this.context = null;
		
	}

	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException {
		System.out.println("MessageDrivenContext is set");	
		this.context = ctx;
	}

	public void onMessage(Message message) {
		TextMessage txtMsg = (TextMessage) message;
		String storedText=null;
		try {
			String hostname = InetAddress.getLocalHost().getHostName();
			VMID vmid = new VMID();   
			
			/*
			 	It will NOT display in JUnit console.
			 	Instead, it can be found in \ejb-jboss422-snippets-client\server\jboss-4.2.2.GA\server\default\log\server.log
			 */
			storedText = txtMsg.getText()
						+ System.getProperty("line.separator") + "at hostname=" + hostname 
						+ System.getProperty("line.separator") + "at PID=" + getPID()
						+ System.getProperty("line.separator") + "at VMID=" + vmid.toString();
			
			System.out.println(storedText);
			
		} catch (UnknownHostException e1) {			
			e1.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	    	
		try {
			Context ctx = new InitialContext();
			Object ref = ctx.lookup("HelloWorldBMP");
			HelloWorldHome h = (HelloWorldHome)PortableRemoteObject.narrow(ref, HelloWorldHome.class);
			HelloWorldObject hw = h.create(storedText, new Date());
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		}
	}
	
	public long getPID() {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		return Long.parseLong(processName.split("@")[0]);
	}
}
