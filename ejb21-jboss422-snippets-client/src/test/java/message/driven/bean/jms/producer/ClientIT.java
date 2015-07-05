package message.driven.bean.jms.producer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientIT {	
    @Test
    public void test1() throws Exception {
    	Client.main(null);        

    	
    	/*
    		make sure all messages are all sent by JMS
    		and all consumed by MDB
    		and all inserted into database by BMP
    		
    	 */    		
    	Thread.sleep(2000);
    	
	    try	{
	    	Properties props = new Properties();
	        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
	        props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
	        props.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");
			Context ctx = new InitialContext(props);
	
			DataSource ds = (DataSource) ctx.lookup("jdbc/mysql");
			Connection conn = ds.getConnection();        
	        Statement stmt = conn.createStatement();        
	        ResultSet rs = stmt.executeQuery("select count(*) as CNT from HelloWorld");
	        if (rs.next()) {
	            assertEquals(10, rs.getInt("CNT"));
	        }
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}
}