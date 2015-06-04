package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.DBProcessInfo;
import model.SystemProcessInfo;
import util.DB;

public class ProcessInfoDAO {


	public boolean storeProcessInfo(ArrayList<SystemProcessInfo>list)
	{
		PreparedStatement stmt=null;
		Connection con= null;
		
		try{

			con=DB.getConnection();

			//first field is the id field , it is indicated as null
			//because we have mentioned it to be auto incremented.
			
			String query="insert into process_info values (null,?,?,?,DEFAULT)";
				    	
			stmt = con.prepareStatement(query);
	    	
	    	for(int i=0;i<list.size();i++)
	    	{
	    	stmt.setString(1,list.get(i).getName());
	    	stmt.setString(2,list.get(i).getCpu().getPercent()+"");
	    	stmt.setString(3, list.get(i).getMem().getSize()+"");
	    	
	    	stmt.executeUpdate();
	    	}
	    	con.close();
			stmt.close();
		
	    	return true;
		}catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			
		
			return false;
		}

	   
	}
	
	
	public ArrayList<DBProcessInfo> retrieveAllFromDatabase()
	{
		ArrayList<DBProcessInfo> list = new ArrayList<DBProcessInfo>();
		DBProcessInfo processInfo;
	    Statement stmt=null;
		Connection con=DB.getConnection();		
		ResultSet rs;
		String query="select * from process_info";			    	
		try
		{
		 stmt=con.createStatement();	
		  rs=stmt.executeQuery(query);	
		  while(rs.next())
		  {			  
			 processInfo = new DBProcessInfo(rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5));;
			 list.add(processInfo);			 
		  }
		}
		catch(Exception e )
		{
			System.out.println("Exception in retrieving from database"+e.getLocalizedMessage());
			
		}
		return list;
		
	}

	
	public ArrayList<DBProcessInfo> retrieveOneProcessInfo(String processName)
	{
		ArrayList<DBProcessInfo> list = new ArrayList<DBProcessInfo>();
		DBProcessInfo processInfo;
	    Statement stmt=null;
		Connection con=DB.getConnection();		
		ResultSet rs;
		String query="select * from process_info where process_name like '%"+processName+"%'";			    	
		try
		{
		 stmt=con.createStatement();	
		  rs=stmt.executeQuery(query);	
		  while(rs.next())
		  {			  
			  processInfo = new DBProcessInfo(rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5));
			  list.add(processInfo);	  
		  }
		}
		catch(Exception e )
		{
			System.out.println("Exception in retrieving from database"+e.getLocalizedMessage());
			
		}
		return list;
		
	}


	public ArrayList<DBProcessInfo> retrieveOneProcessInfoPastWeek(
			String processName) {
		ArrayList<DBProcessInfo> list = new ArrayList<DBProcessInfo>();
		DBProcessInfo processInfo;
	    Statement stmt=null;
		Connection con=DB.getConnection();		
		ResultSet rs;
		String query="select * from process_info where process_name like '%"+processName+"%' and "
				+ "lastUpdated >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) ";			    	
		try
		{
		 stmt=con.createStatement();	
		  rs=stmt.executeQuery(query);	
		  while(rs.next())
		  {			  
			  processInfo = new DBProcessInfo(rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5));
			  list.add(processInfo);	  
		  }
		}
		catch(Exception e )
		{
			System.out.println("Exception in retrieving from database"+e.getLocalizedMessage());
			
		}
		return list;
	}


	public ArrayList<DBProcessInfo> retrieveOneProcessThisMonth(
			String processName) {
		ArrayList<DBProcessInfo> list = new ArrayList<DBProcessInfo>();
		DBProcessInfo processInfo;
	    Statement stmt=null;
		Connection con=DB.getConnection();		
		ResultSet rs;
		String query="select * from process_info where process_name like '%"+processName+"%' and "
				+ "lastUpdated >=  now()-INTERVAL 1 MONTH";			    	
		try
		{
		 stmt=con.createStatement();	
		  rs=stmt.executeQuery(query);	
		  while(rs.next())
		  {			  
			  processInfo = new DBProcessInfo(rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5));
			  list.add(processInfo);	  
		  }
		}
		catch(Exception e )
		{
			System.out.println("Exception in retrieving from database"+e.getLocalizedMessage());
			
		}
		return list;
	}


	public ArrayList<DBProcessInfo> retrieveOneProcessThisYear(
			String processName) {
		// TODO Auto-generated method stub
		ArrayList<DBProcessInfo> list = new ArrayList<DBProcessInfo>();
		DBProcessInfo processInfo;
	    Statement stmt=null;
		Connection con=DB.getConnection();		
		ResultSet rs;
		String query="select * from process_info where process_name like '%"+processName+"%' and "
				+ "lastUpdated >=  now()-INTERVAL 12 MONTH";			    	
		try
		{
		 stmt=con.createStatement();	
		  rs=stmt.executeQuery(query);	
		  while(rs.next())
		  {			  
			  processInfo = new DBProcessInfo(rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5));
			  list.add(processInfo);	  
		  }
		}
		catch(Exception e )
		{
			System.out.println("Exception in retrieving from database"+e.getLocalizedMessage());
			
		}
		return list;
	}

	
}
