package service;

import java.util.ArrayList;

import controller.ProcessController;
import dao.ProcessInfoDAO;
import model.ProcessInfo;

public class ProcessInfoService {

	ProcessController processController;
	ProcessInfoDAO processInfoDAO;
	public ProcessInfoService()
	{
		processController =  new ProcessController();
	    processInfoDAO =  new ProcessInfoDAO();
	}

public void storeProcessNames(ArrayList<ProcessInfo>list)
{
    processInfoDAO.storeProcessName(list);
	
	/* this logic is causing a lot of problems ... going into an infinte loop i guess
    if(!processInfoDAO.storeProcessName(list))
	{
	   processController.getProcessInfoList();
	}	
*/
}
	
	
}
