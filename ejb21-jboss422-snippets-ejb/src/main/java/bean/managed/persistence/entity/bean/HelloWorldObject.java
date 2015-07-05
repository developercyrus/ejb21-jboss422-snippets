package bean.managed.persistence.entity.bean;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.EJBObject;

public interface HelloWorldObject extends EJBObject {
    public Integer getId() throws RemoteException;
    public String getName() throws RemoteException;
    public Date getCreateDate() throws RemoteException;

    public void setId(Integer id) throws RemoteException;
    public void setName(String name) throws RemoteException;
    public void setCreateDate(Date createDate) throws RemoteException;
}