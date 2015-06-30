package session.beam.stateless.client;

import org.junit.Test;

import session.bean.stateless.client.HelloWorldRemoteClient;
import static org.junit.Assert.assertEquals;

public class HelloWorldRemoteClientIT {	
    @Test
    public void test1() throws Exception {
    	HelloWorldRemoteClient.main(null);        
        //assertEquals("", );
    }   
}