package session.bean.stateless;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface HelloWorldHome extends EJBHome {
    public HelloWorldObject create() throws RemoteException, CreateException;
}