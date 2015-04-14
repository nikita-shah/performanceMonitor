package controller;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.ProcessInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import service.ProcessInfoService;

@Controller

public class HomeController {

	ProcessController processController ;
	public static boolean isDllLocationSet = false;

	public HomeController()
	{
		HomeController.setDll();
		processController =  new ProcessController();
	}
	
	

	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {
		ModelAndView model = new ModelAndView("HelloPage");
		model.addObject("msg","List of processes currently running");
		model.addObject("list", processController.getList());
		System.out.println("/welcome success");
		return model;
	}

  

	@ResponseBody
	@RequestMapping("/allProcesses")
	public List getProcessInfoList() {	
		/*if(!isDllLocationSet)
			setDll();*/
		processController.getProcessInfoList();
		return processController.getList();
	}



	public static boolean setDll ()	
	{
		try
		{
			String path = "C:/softwares/SIGAR API/hyperic-sigar-1.6.4/hyperic-sigar-1.6.4/sigar-bin/lib";

			System.setProperty("java.library.path", System.getProperty("java.library.path")+File.pathSeparator+path);

			//set sys_paths to null
			final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
			sysPathsField.setAccessible(true);
			sysPathsField.set(null, null);

		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
			return false;
		}
		isDllLocationSet = true;
		return true;

	}


}
