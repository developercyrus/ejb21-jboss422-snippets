package stateless.session.bean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.dgc.VMID;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class HelloWorldImpl implements SessionBean {
    private static final long serialVersionUID = 669544470499688249L;

    public void ejbActivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub        
    }

    public void ejbPassivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub
       
    }

    public void ejbRemove() throws EJBException, RemoteException {
        // TODO Auto-generated method stub       
    }

    public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {
        // TODO Auto-generated method stub        
    }
    
    public void ejbCreate() {
        System.out.println("Created Hello EJB.");
    }

    public String sayHello(String message) {
        String hostname = null;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        VMID vmid = new VMID();       
        return message 
        		+ System.getProperty("line.separator") + "received via stateless session bean sayHello() Impl" 
        		+ System.getProperty("line.separator") + "at hostname=" + hostname 
        		+ System.getProperty("line.separator") + "at PID=" + getPID()
        		+ System.getProperty("line.separator") + "at VMID=" + vmid.toString();         		
    }
    
    public long getPID() {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		return Long.parseLong(processName.split("@")[0]);
	}
}
