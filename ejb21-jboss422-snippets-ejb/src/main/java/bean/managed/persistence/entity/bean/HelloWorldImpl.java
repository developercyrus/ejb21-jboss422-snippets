package bean.managed.persistence.entity.bean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class HelloWorldImpl implements EntityBean {
	private static final long serialVersionUID = 6953700906834418672L;

	private Integer id;
	private String name;
	private Date createDate;

	private EntityContext context = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Connection makeConnection() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("jdbc/mysql");
			return  ds.getConnection();	
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer ejbCreate(String name, Date createDate) throws CreateException {	
		String sqlStr = "insert into HelloWorld (name, create_date) values (?, ?)";
		try {
			Connection conn = this.makeConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlStr, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setTimestamp(2, new java.sql.Timestamp(createDate.getTime()));
			stmt.executeUpdate();			
			ResultSet keys = stmt.getGeneratedKeys();    
			keys.next();  
			int autoId = keys.getInt(1);
			stmt.close();
			conn.close();
			
			
			this.id = new Integer(autoId);
			this.name = name;
			this.createDate = createDate;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this.id;
	}
	
	public void ejbPostCreate(String name, Date createDate) throws CreateException {
	}
	
	public Integer ejbFindByPrimaryKey(Integer id) {
		String sqlStr = "select id from HelloWorld where id = ?";
		try {
			Connection conn = this.makeConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlStr);
			stmt.setInt(1, id.intValue());
			ResultSet rs = stmt.executeQuery();
			if (!rs.next()) {
                throw new ObjectNotFoundException("Cannot find with id = "+id);
            }
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	
	
	
	
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub	
	}

	public void ejbLoad() throws EJBException, RemoteException {
		// TODO Auto-generated method stub		
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub		
	}

	public void ejbRemove() throws RemoveException, EJBException, RemoteException {
		// TODO Auto-generated method stub		
	}

	public void ejbStore() throws EJBException, RemoteException {
		// TODO Auto-generated method stub		
	}

	public void setEntityContext(EntityContext context) throws EJBException, RemoteException {
		// TODO Auto-generated method stub
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
	}

}
