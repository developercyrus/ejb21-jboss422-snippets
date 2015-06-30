package session.bean.stateful;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface HelloWorldLocalHome extends EJBLocalHome {
    public HelloWorldLocalObject create() throws CreateException;
}