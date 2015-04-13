package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

import model.ProcessInfo;
import util.DB;

public class ProcessInfoDAO {


	public boolean storeProcessName(ArrayList<ProcessInfo>list)
	{
		try{

           
			PreparedStatement stmt=null;
			Connection con=DB.getConnection();

			//first field is the id field , it is indicated as null
			//because we have mentioned it to be auto incremented.
			
			String query="insert into process_info values (null,?)";
				    	
			stmt = con.prepareStatement(query);
	    	
	    	for(int i=0;i<list.size();i++)
	    	{
	    	stmt.setString(1,list.get(i).getName());
	    	
	    	stmt.executeUpdate();
	    	}
			
			return true;
		}catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			return false;
		}

	}

}
