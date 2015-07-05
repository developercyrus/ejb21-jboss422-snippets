package stateful.session.bean;

import javax.ejb.EJBLocalObject;

public interface HelloWorldLocalObject extends EJBLocalObject  {
    public String sayHello(String message);
}