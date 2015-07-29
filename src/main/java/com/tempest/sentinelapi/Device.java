package com.tempest.sentinelapi;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {

	private int id;
	private String number;
	@JsonProperty(value = "confirmed_at")
	private Date confirmedAt;
	private Capabilities[] capabilities;
	
	public Device(){}
	
	public Device(String number, Capabilities[] capabilities){
		this.number = number;
		this.capabilities = capabilities;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public Date getConfirmedAt() {
		return confirmedAt;
	}
	public void setConfirmedAt(Date confirmedAt) {
		this.confirmedAt = confirmedAt;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Capabilities[] getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(Capabilities[] capabilities) {
		this.capabilities = capabilities;
	}
}
