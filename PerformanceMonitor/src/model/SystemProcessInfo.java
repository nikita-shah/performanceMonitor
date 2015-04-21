package model;

import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;

public class SystemProcessInfo {
String name;
long pid[];
ProcCpu cpu;
ProcMem mem;

public SystemProcessInfo()
{

}

public SystemProcessInfo(String name)
{
	this.name = name;
    pid=null;
    cpu=null;
    mem=null;    		
}
public SystemProcessInfo(String name,long[]pid)
{
	this.name = name;
    this.pid=pid;
    cpu=null;
    mem=null;    		
}


public SystemProcessInfo(String name, long[] pids, ProcCpu cpu, ProcMem mem) {
	super();
	this.name = name;
	this.pid = pids;
	this.cpu = cpu;
	this.mem = mem;
}


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

/**
 * @return the pid
 */
public long[] getPid() {
	return pid;
}

/**
 * @param pid the pid to set
 */
public void setPid(long[] pid) {
	this.pid = pid;
}

/**
 * @return the cpu
 */
public ProcCpu getCpu() {
	return cpu;
}

/**
 * @param cpu the cpu to set
 */
public void setCpu(ProcCpu cpu) {
	this.cpu = cpu;
}

/**
 * @return the mem
 */
public ProcMem getMem() {
	return mem;
}

/**
 * @param mem the mem to set
 */
public void setMem(ProcMem mem) {
	this.mem = mem;
}



}
