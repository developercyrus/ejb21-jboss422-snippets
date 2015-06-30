package session.bean.stateful;

import javax.ejb.EJBLocalObject;

public interface HelloWorldLocalObject extends EJBLocalObject  {
    public String sayHello(String message);
}