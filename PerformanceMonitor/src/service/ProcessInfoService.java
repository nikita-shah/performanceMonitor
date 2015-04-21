package service;

import java.util.ArrayList;

import controller.Ps;
import dao.ProcessInfoDAO;
import model.DBProcessInfo;
import model.SystemProcessInfo;

public class ProcessInfoService {
	Ps ps ;

	ProcessInfoDAO processInfoDAO;
	public ProcessInfoService()
	{
	
	    processInfoDAO =  new ProcessInfoDAO();
	    ps=new Ps();
	}

	public SystemProcessInfo getASingleProcessUsage(String processName)
	{
		return ps.getProcessUsage(processName);
	}

public void storeProcessInfoInDB(ArrayList<SystemProcessInfo>processInfoList)
{
	if(processInfoList == null)
		processInfoList = formProcessInfoList(getProcessNames());
	processInfoDAO.storeProcessInfo(processInfoList);
}



public ArrayList<SystemProcessInfo> formProcessInfoList(ArrayList<String> nameList)
{
	//format the list to get the actual application name..not needed as we are using the 
	//.ct(meaning .contains method to do it);
	
	if (nameList == null)
		nameList = getProcessNames();
	
	ArrayList<SystemProcessInfo>processInfoList=new ArrayList<SystemProcessInfo>(nameList.size()); 
	SystemProcessInfo processInfo;
	for(int i=0;i<nameList.size();i++)
	{
		processInfo = ps.getProcessUsage(nameList.get(i));
        processInfoList.add(processInfo);
	}
   return processInfoList;
}


public ArrayList<DBProcessInfo> retrieveOneProcessInfo(String processName)
{
 return processInfoDAO.retrieveOneProcessInfo(processName);	
}


public ArrayList<DBProcessInfo> retrieveEntireDB()
{
 return processInfoDAO.retrieveAllFromDatabase();	
}
	
public ArrayList<String> getProcessNames()
{
	return ps.getDistinctProcessesName();	
}

public ArrayList<String> getAllProcessNames() {
	
	return ps.getAllProcessNames();
}

	
}
