package model;

import java.sql.Timestamp;

public class DBProcessInfo {
String name,cpuPercent,memSize;
Timestamp lastUpdated;


public DBProcessInfo(String name, String cpuPercent, String memSize,Timestamp lastUpdated) {
	super();
	this.name = name;
	this.cpuPercent = cpuPercent;
	this.memSize = memSize;
	this.lastUpdated = lastUpdated;
}

/**
 * @return the name
 */
public String getName() {
	return name;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * @return the cpuPercent
 */
public String getCpuPercent() {
	return cpuPercent;
}

/**
 * @param cpuPercent the cpuPercent to set
 */
public void setCpuPercent(String cpuPercent) {
	this.cpuPercent = cpuPercent;
}

/**
 * @return the memSize
 */
public String getMemSize() {
	return memSize;
}

/**
 * @param memSize the memSize to set
 */
public void setMemSize(String memSize) {
	this.memSize = memSize;
}

/**
 * @return the lastUpdated
 */
public Timestamp getLastUpdated() {
	return lastUpdated;
}

/**
 * @param lastUpdated the lastUpdated to set
 */
public void setLastUpdated(Timestamp lastUpdated) {
	this.lastUpdated = lastUpdated;
}

}
