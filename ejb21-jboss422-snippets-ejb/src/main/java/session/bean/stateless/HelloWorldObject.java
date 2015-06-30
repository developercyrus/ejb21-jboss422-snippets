package session.bean.stateless;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface HelloWorldObject extends EJBObject {
    public String sayHello(String message) throws RemoteException;
}