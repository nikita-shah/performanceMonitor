package controller;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import model.DBProcessInfo;
import model.SystemProcessInfo;
import service.ProcessInfoService;

@Controller
public class ProcessController {
	ProcessInfoService processInfoService;
	
	public ProcessController() {
	
		processInfoService = new ProcessInfoService();

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
		System.out.println(" @RequestMapping(value=/showProcessUsage ,method=RequestMethod.POST) ");
		SystemProcessInfo processInfo ;
		processInfo = processInfoService.getASingleProcessUsage(processName);
		ModelAndView model =  new ModelAndView("ProcessInfo");
		model.addObject("processName",processName);
		model.addObject("cpuPercentage", processInfo.getCpu().getPercent());
		model.addObject("memorySize", processInfo.getMem().getSize());
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value="/showProcessUsage" ,method=RequestMethod.GET)
	public SystemProcessInfo getASingleProcessUsage1()
	{
		//see the concept of model attribute 
		//and upgrade this method code to model attribute.
	    System.out.println("@RequestMapping(value=/showProcessUsage ,method=RequestMethod.GET)");
		String processName = "chrome";
		SystemProcessInfo processInfo ;
		processInfo = processInfoService.getASingleProcessUsage(processName);
		return processInfo;
	}
	
	@ResponseBody
	@RequestMapping(value="/showProcessUsage/{processName}" ,method=RequestMethod.GET)
	public SystemProcessInfo getASingleProcessInfo(@PathVariable("processName")String processName)
	{

		SystemProcessInfo processInfo ;
		processInfo = processInfoService.getASingleProcessUsage(processName);
		System.out.println("the received process name mapped by /showProcessUsage/{processName} :"+processName);
		System.out.println("memory to be plot:"+processInfo.getMem().getSize());
		return processInfo;
	}
 
	

	
	//for json output rest api
		@ResponseBody
		@RequestMapping("/getEntireDB")
		public ArrayList<DBProcessInfo> getProcessInfoListJSON() {	
			return retrieveAllFromDB();
			
		}

		@ResponseBody
		@RequestMapping("/getOneProcessFromDB/{processName}")
		public ArrayList<DBProcessInfo> getProcessInfoListJSON
		(@PathVariable("processName")String processName) {	
			//sample url for utilising this code :
			// http://localhost:8080/PerformanceMonitor/getOneProcessFromDB/chrome
			return processInfoService.retrieveOneProcessInfo(processName);
			
		}
	
	public ArrayList<DBProcessInfo> retrieveAllFromDB()
	{
		return processInfoService.retrieveEntireDB();
	}

	public  ArrayList<String> getRepeatedProcessList()
	{
		//repeated process names also considered 
		return processInfoService.getAllProcessNames();
	}

	
	public  ArrayList<String> getList()
	{
		return processInfoService.getProcessNames();
	}

	public ArrayList<SystemProcessInfo> getAllProcessInfo() {
		return processInfoService.formProcessInfoList(null);
	}

	public void persistCurrentProcessesInfo() {
		processInfoService.storeProcessInfoInDB(null);
	}

	public ArrayList<DBProcessInfo> getASingleProcessUsageHistory(String processName)
	{
	    return processInfoService.getASingleProcessUsageHistory(processName);	
	}



}
