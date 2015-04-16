package controller;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import model.ProcessInfo;
import service.ProcessInfoService;

@Controller
public class ProcessController {

Ps ps ;
	public ProcessController() {
		ps=new Ps();
		// TODO Auto-generated constructor stub
	}

	/*public static ArrayList<String> getList()
	{
		ArrayList<String> demoList =  new ArrayList<String>();
		demoList.add("HI");
		demoList.add("hELLO");
		demoList.add("WELCOME");
		return demoList;
	}*/
	  @RequestMapping(value="/showProcessUsage" ,method=RequestMethod.POST)
		public ModelAndView getASingleProcessUsage(@RequestParam("processName")String processName)
		{
		  //see the concept of model attribute 
		  //and upgrade this method code to model attribute.
		 ProcessInfo processInfo ;
		 processInfo=ps.getProcessUsage(processName);
		 System.out.println("the received process name is :"+processName);
		 ModelAndView model =  new ModelAndView("ProcessInfo");
		 model.addObject("processName",processName);
		 model.addObject("cpuPercentage", processInfo.getCpu().getPercent());
		 model.addObject("memorySize", processInfo.getMem().getSize());
		 return model;
		}

	public ArrayList<ProcessInfo> getProcessInfoList() {		

		ArrayList<ProcessInfo>processList =  new  ArrayList<ProcessInfo>();
		ProcessInfoService processInfoService =  new ProcessInfoService();
		for(String name :getList())
		{
			processList.add(new ProcessInfo(name));
		}

		//System.out.println("before storing in database size of arraylist:"+processList.size());
		//for(int i = 0;i<processList.size();i++)

		processInfoService.storeProcessNames(processList);
		System.out.println("successfully stored in the database");
		return processList;
		/*ModelAndView model = new ModelAndView("HelloPage");
		model.addObject("msg","List of processes currently running");
        model.addObject("list", ProcessController.getList());
		return model;		
		 */			}



	public  ArrayList<String> getList()
	{
	return ps.getProcessesName();
	}



}
