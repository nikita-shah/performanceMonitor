package controller;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.SystemProcessInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class HomeController {

	ProcessController processController ;
	public static boolean isDllLocationSet = false;

	public HomeController()
	{
	    if(!isDllLocationSet)
		setDll();
		processController =  new ProcessController();
	}
	
	

	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {
		ModelAndView model = new ModelAndView("HelloPage");
		model.addObject("msg","List of processes currently running");
		model.addObject("list", processController.getRepeatedProcessList());
	    processController.persistCurrentProcessesInfo();
		return model;
	}

	@RequestMapping(value = "/ZingFeed", method = RequestMethod.GET)
	   public String redirect() {
	     
	      return "redirect:static/ZingFeed.html";
	   }

	//for json output rest api
	@ResponseBody
	@RequestMapping("/allProcesses")
	public ArrayList<SystemProcessInfo> getProcessInfoListJSON() {	
		return processController.getAllProcessInfo();
		
	}



	public static boolean setDll ()	
	{
		try
		{
			//String path = "C:/softwares/SIGAR_API/hyperic-sigar-1.6.4/hyperic-sigar-1.6.4/sigar-bin/lib";
            //String path = "/sigarlib" ;
			//System.setProperty("java.library.path", System.getProperty("java.library.path")+File.pathSeparator+path);

			//set sys_paths to null
			//final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
			//sysPathsField.setAccessible(true);
			//sysPathsField.set(null, null);

		}
		catch(Exception e)
		{
			//System.out.println(e.getLocalizedMessage());
			//return false;
		}
		isDllLocationSet = true;
		return true;

	}


}
