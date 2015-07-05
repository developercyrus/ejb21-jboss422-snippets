package bean.managed.persistence.entity.bean;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface HelloWorldHome extends EJBHome {
	public HelloWorldObject create(String name, Date createDate) throws CreateException, RemoteException;    
    public HelloWorldObject findByPrimaryKey(Integer id) throws FinderException, RemoteException;     
}