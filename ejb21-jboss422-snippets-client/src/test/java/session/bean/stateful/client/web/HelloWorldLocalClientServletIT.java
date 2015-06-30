package session.bean.stateful.client.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloWorldLocalClientServletIT {
	@Test
    public void test1() throws Exception {		
		try {
			Document doc = Jsoup.parse(new URL("http://localhost:8080/ejb21-jboss422-snippets-web/HelloWorldLocalClientServlet"), 60000);
			Elements elements = doc.select("div");
			Element e = elements.get(0);
			String actual = e.html();
			//System.out.println(actual.replaceAll("<br />", System.getProperty("line.separator")));			
			System.out.println(actual.replaceAll("<br />", ""));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }  
}
