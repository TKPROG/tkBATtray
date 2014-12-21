package de.tkprog.tkBATtray.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class BatteryInformation_ArchLinux {
	private long charge_full = 0;
	private long current_now = 0;
	private String manufacturer = null;
	private boolean present = false;
	private boolean alarm = false;
	private long capacity = 0;
	private long charge_full_design = 0;
	private long cycle_count = 0;
	private String model_name = null;
	private long serial_number = 0;
	private String technology = null;
	private long voltage_min_design = 0;
	private String capacity_level = null;
	private long charge_now = 0;
	private String status = null;
	private String type = null;
	private long voltage_now = 0;
	
	public void update(String dir){
		try{
			this.setStatus(readFile(dir+"status"));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setAlarm(readFile(dir+"alarm").charAt(0)=='1');
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setCurrent_now(Long.parseLong(readFile(dir+"current_now")));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setPresent(readFile(dir+"present").charAt(0)=='1');
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setCapacity(Long.parseLong(readFile(dir+"capacity")));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setCapacity_level(readFile(dir+"capacity_level"));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setCharge_full(Long.parseLong(readFile(dir+"charge_full")));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setCharge_full_design(Long.parseLong(readFile(dir+"charge_full_design")));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setCharge_now(Long.parseLong(readFile(dir+"charge_now")));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setCycle_count(Long.parseLong(readFile(dir+"cycle_count")));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setManufacturer(readFile(dir+"manufacturer"));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setModel_name(readFile(dir+"model_name"));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setSerial_number(Long.parseLong(readFile(dir+"serial_number")));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setTechnology(readFile(dir+"technology"));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setType(readFile(dir+"type"));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setVoltage_min_design(Long.parseLong(readFile(dir+"voltage_min_design")));
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			this.setVoltage_now(Long.parseLong(readFile(dir+"voltage_now")));
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String readFile(String file){
		return readFile(file, false);
	}
	
	private String readFile(String file, boolean debug) {
		try{
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
			String d = "";
			int a = 0;
			while((a = bf.read())!=-1){
				if(a!=(int)'\n' && a!=(int)'\r'){
					d+=String.valueOf((char)a);
				}
			}
			if(debug)System.out.println("Read from \""+file+"\":\n\t"+d);
			return d;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public long getCharge_full() {
		return charge_full;
	}
	private void setCharge_full(long charge_full) {
		this.charge_full = charge_full;
	}
	public long getCurrent_now() {
		return current_now;
	}
	private void setCurrent_now(long current_now) {
		this.current_now = current_now;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	private void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public boolean isPresent() {
		return present;
	}
	private void setPresent(boolean present) {
		this.present = present;
	}
	public boolean isAlarm() {
		return alarm;
	}
	private void setAlarm(boolean alarm) {
		this.alarm = alarm;
	}
	public long getCapacity() {
		return capacity;
	}
	private void setCapacity(long capacity) {
		this.capacity = capacity;
	}
	public long getCharge_full_design() {
		return charge_full_design;
	}
	private void setCharge_full_design(long charge_full_design) {
		this.charge_full_design = charge_full_design;
	}
	public long getCycle_count() {
		return cycle_count;
	}
	private void setCycle_count(long l) {
		this.cycle_count = l;
	}
	public String getModel_name() {
		return model_name;
	}
	private void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	public long getSerial_number() {
		return serial_number;
	}
	private void setSerial_number(long serial_number) {
		this.serial_number = serial_number;
	}
	public String getTechnology() {
		return technology;
	}
	private void setTechnology(String technology) {
		this.technology = technology;
	}
	public long getVoltage_min_design() {
		return voltage_min_design;
	}
	private void setVoltage_min_design(long voltage_min_design) {
		this.voltage_min_design = voltage_min_design;
	}
	public String getCapacity_level() {
		return capacity_level;
	}
	private void setCapacity_level(String capacity_level) {
		this.capacity_level = capacity_level;
	}
	public String getStatus() {
		return status;
	}
	private void setStatus(String status) {
		this.status = status;
	}
	public long getCharge_now() {
		return charge_now;
	}
	private void setCharge_now(long charge_now) {
		this.charge_now = charge_now;
	}
	public String getType() {
		return type;
	}
	private void setType(String type) {
		this.type = type;
	}
	public long getVoltage_now() {
		return voltage_now;
	}
	private void setVoltage_now(long voltage_now) {
		this.voltage_now = voltage_now;
	}
}
