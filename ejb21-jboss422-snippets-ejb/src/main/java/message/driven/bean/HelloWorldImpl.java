package message.driven.bean;

import java.rmi.RemoteException;
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
		System.out.println("MDB consumed message: " + message.toString());	
				
		try {
			Context ctx = new InitialContext();
			Object ref = ctx.lookup("HelloWorldBMP");
			HelloWorldHome h = (HelloWorldHome)PortableRemoteObject.narrow(ref, HelloWorldHome.class);
			HelloWorldObject hw = h.create(txtMsg.getText(), new Date());
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
