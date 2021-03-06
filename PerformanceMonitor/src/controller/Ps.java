package controller;
/*
 * Copyright (c) 2006-2007 Hyperic, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import model.SystemProcessInfo;

import org.hyperic.sigar.cmd.*;
import org.hyperic.sigar.ptql.ProcessFinder;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcTime;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Show process status.
 */
public class Ps extends SigarCommandBase {

	public Ps(Shell shell) {
		super(shell);
	}

	public Ps() {
		super();
	}

	protected boolean validateArgs(String[] args) {
		return true;
	}

	public String getSyntaxArgs() {
		return "[pid|query]";
	}

	public String getUsageShort() {
		return "Show process status";
	}

	public boolean isPidCompleter() {
		return true;
	}

	public void output(String[] args) throws SigarException {
		long[] pids;
		if (args.length == 0) {
			pids = this.proxy.getProcList();
		}
		else {
			pids = this.shell.findPids(args);
		}

		for (int i=0; i<pids.length; i++) {
			long pid = pids[i];
			try {
				output(pid);
			} catch (SigarException e) {
				this.err.println("Exception getting process info for " +
						pid + ": " + e.getMessage());
			}
		}
	}

	public static String join(List info) {
		StringBuffer buf = new StringBuffer();
		Iterator i = info.iterator();
		boolean hasNext = i.hasNext();
		while (hasNext) {
			buf.append((String)i.next());
			hasNext = i.hasNext();
			if (hasNext)
				buf.append("\t");
		}

		return buf.toString();
	}

	public static List getInfo(SigarProxy sigar, long pid)
			throws SigarException {

		ProcState state = sigar.getProcState(pid);
		ProcTime time = null;
		String unknown = "???";

		List info = new ArrayList();
		info.add(String.valueOf(pid));

		try {
			ProcCredName cred = sigar.getProcCredName(pid);
			info.add(cred.getUser());

		} catch (SigarException e) {
			info.add(unknown);
		}

		try {
			time = sigar.getProcTime(pid);
			info.add(getStartTime(time.getStartTime()));
		} catch (SigarException e) {
			info.add(unknown);
		}

		try {
			ProcMem mem = sigar.getProcMem(pid);
			info.add(Sigar.formatSize(mem.getSize()));
			info.add(Sigar.formatSize(mem.getRss()));
			info.add(Sigar.formatSize(mem.getShare()));
		} catch (SigarException e) {
			info.add(unknown);
		}

		info.add(String.valueOf(state.getState()));

		if (time != null) {
			info.add(getCpuTime(time));
		}
		else {
			info.add(unknown);
		}

		String name = ProcUtil.getDescription(sigar, pid);
		info.add(name);

		return info;
	}

	public void output(long pid) throws SigarException {
		println(join(getInfo(this.proxy, pid)));
	}


	public void trialOutput ()
	{


	}
	public static String getCpuTime(long total) {
		long t = total / 1000;
		return t/60 + ":" + t%60;
	}

	public static String getCpuTime(ProcTime time) {
		return getCpuTime(time.getTotal());
	}

	private static String getStartTime(long time) {
		if (time == 0) {
			return "00:00";
		}
		long timeNow = System.currentTimeMillis();
		String fmt = "MMMd";

		if ((timeNow - time) < ((60*60*24) * 1000)) {
			fmt = "HH:mm";
		}

		return new SimpleDateFormat(fmt).format(new Date(time));
	}

	public static void main(String[] args) throws Exception {

		new Ps().processCommand(args);

	}
	public  ArrayList<String> getAllProcessNames()
	{
		
		ArrayList<String> allProcessNames= new ArrayList<String>();
		try{
			long[] pids;
			pids = this.proxy.getProcList();
			for (int i=0; i<pids.length; i++) {
				long pid = pids[i];
				String unknown = "???";
				String name = ProcUtil.getDescription(sigar, pid);
	            allProcessNames.add(name);         
			}

		}catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
			System.out.println("access getting denied here");
		}
		return allProcessNames;
		
	}
	public  ArrayList<String> getDistinctProcessesName()
	{    ArrayList<String> info = new ArrayList<String>(); 
	try{
		long[] pids;
		pids = this.proxy.getProcList();
		for (int i=0; i<pids.length; i++) {
			long pid = pids[i];
			String unknown = "???";
			String name = ProcUtil.getDescription(sigar, pid);
			//group=domain, group =system and user being printed here 
/*		
			try{
				System.out.println(proxy.getProcCredName(pid));
			}catch(Exception e)
			{
				System.out.println(unknown);
			}
*/
			//only distinct processes added
			if(!info.contains(name))
				info.add(name);	          
		}

	}catch(Exception e)
	{
		System.out.println(e.getLocalizedMessage());
		System.out.println("access getting denied here");
	}
	return info;
	}
	public SystemProcessInfo getProcessUsage(String processName)
	{
		String processFinderQuery = "Exe.Name.ct="+processName;
		SystemProcessInfo processInfo = null;
		try{		    
			long pids[]=ProcessFinder.find(sigar,processFinderQuery);
			ProcCpu cpu = sigar.getMultiProcCpu(processFinderQuery);
			ProcMem mem=sigar.getMultiProcMem(processFinderQuery);            
			processInfo = new SystemProcessInfo(processName, pids, cpu, mem);
		}
		catch(Exception e)
		{
			System.out.println("Exception thrown"+e.getMessage());
		}
		return processInfo;
	}


}


