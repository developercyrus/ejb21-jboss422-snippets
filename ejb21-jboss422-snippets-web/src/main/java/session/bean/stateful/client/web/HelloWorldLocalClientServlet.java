package session.bean.stateful.client.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.rmi.dgc.VMID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import session.bean.stateful.HelloWorldLocalHome;
import session.bean.stateful.HelloWorldLocalObject;

public class HelloWorldLocalClientServlet extends HttpServlet  {
    private static final long serialVersionUID = 1258543514208840608L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }   
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        try {
            Context ctx = new InitialContext();
            Object ref = ctx.lookup("HelloWorldStatefulLocal");
            HelloWorldLocalHome h = (HelloWorldLocalHome)PortableRemoteObject.narrow(ref, HelloWorldLocalHome.class);
            HelloWorldLocalObject o = h.create();
            
            VMID vmid = new VMID();
            String hostname = InetAddress.getLocalHost().getHostName();   
            String result = o.sayHello("get Hello World by local client servlet"
            		+ System.getProperty("line.separator") + "at hostname=" + hostname 
					+ System.getProperty("line.separator") + "at PID=" + getPID()
					+ System.getProperty("line.separator") + "at VMID=" + vmid.toString());
            
            writer.println("<div>");
            writer.println(result.replaceAll(System.getProperty("line.separator"), "<br />"));
            writer.println("</div>");
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
	public long getPID() {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		return Long.parseLong(processName.split("@")[0]);
	}
}