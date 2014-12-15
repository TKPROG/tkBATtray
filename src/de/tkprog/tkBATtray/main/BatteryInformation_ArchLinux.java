package de.tkprog.tkBATtray.main;

public class BatteryInformation_ArchLinux {
	private int charge_full = 0;
	private int current_now = 0;
	private String manufacturer = null;
	private boolean present = false;
	private boolean alarm = false;
	private int capacity = 0;
	private int charge_full_design = 0;
	private int cycle_count = 0;
	private String model_name = null;
	private int serial_number = 0;
	private String technology = null;
	private int voltage_min_design = 0;
	private int capacity_level = 0;
	private int charge_now = 0;
	private String status = null;
	private String type = null;
	private int voltage_now = 0;
	public int getCharge_full() {
		return charge_full;
	}
	private void setCharge_full(int charge_full) {
		this.charge_full = charge_full;
	}
	public int getCurrent_now() {
		return current_now;
	}
	private void setCurrent_now(int current_now) {
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
	public int getCapacity() {
		return capacity;
	}
	private void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getCharge_full_design() {
		return charge_full_design;
	}
	private void setCharge_full_design(int charge_full_design) {
		this.charge_full_design = charge_full_design;
	}
	public int getCycle_count() {
		return cycle_count;
	}
	private void setCycle_count(int cycle_count) {
		this.cycle_count = cycle_count;
	}
	public String getModel_name() {
		return model_name;
	}
	private void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	public int getSerial_number() {
		return serial_number;
	}
	private void setSerial_number(int serial_number) {
		this.serial_number = serial_number;
	}
	public String getTechnology() {
		return technology;
	}
	private void setTechnology(String technology) {
		this.technology = technology;
	}
	public int getVoltage_min_design() {
		return voltage_min_design;
	}
	private void setVoltage_min_design(int voltage_min_design) {
		this.voltage_min_design = voltage_min_design;
	}
	public int getCapacity_level() {
		return capacity_level;
	}
	private void setCapacity_level(int capacity_level) {
		this.capacity_level = capacity_level;
	}
	public String getStatus() {
		return status;
	}
	private void setStatus(String status) {
		this.status = status;
	}
	public int getCharge_now() {
		return charge_now;
	}
	private void setCharge_now(int charge_now) {
		this.charge_now = charge_now;
	}
	public String getType() {
		return type;
	}
	private void setType(String type) {
		this.type = type;
	}
	public int getVoltage_now() {
		return voltage_now;
	}
	private void setVoltage_now(int voltage_now) {
		this.voltage_now = voltage_now;
	}
}
