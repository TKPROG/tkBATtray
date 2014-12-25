package de.tkprog.tkBATtray.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;

public class Logger {
	
	private String path;
	private boolean logToFile, logToCLI, log_Warning, log_Error, log_Debug, log_Info, log_Clear, log, log_ErrorSpecial;
	private BufferedWriter bw;

	public Logger(String path){
		logAll(true);
		setLogToCLI(true);
		setLogToFile(false);
		setPath(path);
		if(path != null){
			if(openStream(path)){
				this.setLogToFile(true);
			}
		}
	}
	
	public void logAll(boolean all){
		this.setLog(all);
		this.setLog_Clear(all);
		this.setLog_Debug(all);
		this.setLog_Error(all);
		this.setLog_ErrorSpecial(all);
		this.setLog_Info(all);
		this.setLog_Warning(all);
	}
	
	private String time() {
		String out = "";
		Calendar c = Calendar.getInstance();
		int iday = c.get(Calendar.DAY_OF_MONTH);
		int imonth = c.get(Calendar.MONTH)+1;
		int iyear = c.get(Calendar.YEAR);
		int ihour = c.get(Calendar.HOUR_OF_DAY);
		int iminute = c.get(Calendar.MINUTE);
		int isecond = c.get(Calendar.SECOND);
		int imillisecond = c.get(Calendar.MILLISECOND);

		String sday = String.valueOf(iday);
		if(sday.length()<2){
			sday = "0"+sday;
		}
		
		String smonth = String.valueOf(imonth);
		if(smonth.length()<2){
			smonth = "0"+smonth;
		}
		
		String syear = String.valueOf(iyear);
		if(syear.length()<4){
			syear = "0"+syear;
		}
		if(syear.length()<3){
			syear = "0"+syear;
		}
		if(syear.length()<2){
			syear = "0"+syear;
		}
		if(syear.length()<1){
			syear = "0"+syear;
		}
		
		String shour = String.valueOf(ihour);
		if(shour.length()<2){
			shour = "0"+shour;
		}
		
		String sminute = String.valueOf(iminute);
		if(sminute.length()<2){
			sminute = "0"+sminute;
		}
		
		String ssecond = String.valueOf(isecond);
		if(ssecond.length()<2){
			ssecond = "0"+ssecond;
		}

		String smillisecond = String.valueOf(imillisecond);
		if(smillisecond.length()<3){
			smillisecond = "0"+smillisecond;
		}
		if(smillisecond.length()<2){
			smillisecond = "0"+smillisecond;
		}
		if(smillisecond.length()<1){
			smillisecond = "0"+smillisecond;
		}
		
		out = sday+"."+smonth+"."+syear+" "+shour+":"+sminute+":"+ssecond+"(:"+smillisecond+")";
		
		return out;
	}
	
	public void logWarning(String msg){
		logWarning(msg,false);
	}

	public void logWarningln(String msg){
		logWarning(msg,true);
	}
	
	public void logWarning(String msg, boolean nl){
		if(this.isLog_Warning()){
			this.logEND_(((nl)?("\r\n"):(""))+"["+time()+"] [WARNING] "+msg);
		}
	}
	
	public void logDebug(String msg){
		logDebug(msg,false);
	}
	
	public void logDebugln(String msg){
		logDebug(msg, true);
	}
	
	private void logDebug(String msg, boolean nl){
		if(this.isLog_Debug()){
			this.logEND_(((nl)?("\r\n"):(""))+"["+time()+"] [DEBUG]   "+msg);
		}
	}
	
	public void logError(String msg){
		logError(msg,false);
	}
	
	public void logErrorln(String msg){
		logError(msg,true);
	}
	
	public void logError(String msg, boolean nl){
		if(this.isLog_Error()){
			this.logEND_(((nl)?("\r\n"):(""))+"["+time()+"] [ERROR]   "+msg);
		}
	}
	
	public void logInfo(String msg){
		logInfo(msg, false);
	}
	
	public void logInfoln(String msg){
		logInfo(msg, true);
	}
	
	public void logInfo(String msg, boolean nl){
		if(this.isLog_Info()){
			this.logEND_(((nl)?("\r\n"):(""))+"["+time()+"] [INFO]    "+msg);
		}
	}
	
	public void logClear(String msg){
		logClear(msg, false);
	}
	
	public void logClearln(String msg){
		logClear(msg, true);
	}
	
	public void logClear(String msg, boolean nl){
		if(this.isLog_Clear()){
			this.logEND_(((nl)?("\r\n"):(""))+"["+time()+"]           "+msg);
		}
	}
	
	public void log(String msg){
		log(msg,false);
	}
	
	public void logln(String msg){
		log(msg,true);
	}
	
	public void log(String msg, boolean nl){
		if(this.isLog()){
			this.logEND_(((nl)?("\r\n"):(""))+msg);
		}
	}

	public void logError(Exception e) {
		if(this.isLog_ErrorSpecial()){
			if(this.isLogToCLI()){
				e.printStackTrace();
			}
			if(this.isLogToFile()){
				e.printStackTrace(new PrintWriter(bw));
			}
		}
	}
	
	private void logEND_(String msg){
		logEND_CLI(msg);
		logEND_file(msg);
	}
	
	private void logEND_file(String msg) {
		if(bw != null && this.isLogToFile()){
			try{
				bw.write(msg);
				bw.flush();
			} catch(Exception e){
				this.logError(e);
			}
		}
	}

	private void logEND_CLI(String msg) {
		if(this.isLogToCLI()){
			System.out.print(msg);
		}
	}

	private boolean openStream(String path) {
		try{
			File f = new File(path.substring(0, path.lastIndexOf("/")));
			f.mkdirs();
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path))));
			logDebugln("Opened Writer to \""+path+"\".");
			return true;
		} catch(Exception e){
			logError(e);
			bw = null;
		}
		return false;
	}

	public String getPath() {
		return path;
	}

	private void setPath(String path) {
		this.path = path;
	}

	public boolean isLog_Error() {
		return log_Error;
	}

	public void setLog_Error(boolean log_Error) {
		this.log_Error = log_Error;
	}

	public boolean isLog_Info() {
		return log_Info;
	}

	public void setLog_Info(boolean log_Info) {
		this.log_Info = log_Info;
	}

	public boolean isLogToCLI() {
		return logToCLI;
	}

	public void setLogToCLI(boolean logToCLI) {
		this.logToCLI = logToCLI;
	}

	public boolean isLogToFile() {
		return logToFile;
	}

	public void setLogToFile(boolean logToFile) {
		this.logToFile = logToFile;
	}

	public boolean isLog_Debug() {
		return log_Debug;
	}

	public void setLog_Debug(boolean log_Debug) {
		this.log_Debug = log_Debug;
	}

	public boolean isLog_Warning() {
		return log_Warning;
	}

	public void setLog_Warning(boolean log_Warning) {
		this.log_Warning = log_Warning;
	}

	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public boolean isLog_ErrorSpecial() {
		return log_ErrorSpecial;
	}

	public void setLog_ErrorSpecial(boolean log_ErrorSpecial) {
		this.log_ErrorSpecial = log_ErrorSpecial;
	}

	public boolean isLog_Clear() {
		return log_Clear;
	}

	public void setLog_Clear(boolean log_Clear) {
		this.log_Clear = log_Clear;
	}
}
