package com.tempest.sentinelapi;

import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Process {

	@JsonProperty
	private String token;
	@JsonProperty(value = "created_at")
	private Date createdAt;
	@JsonProperty(value = "updated_at")
	private Date updatedAt;
	@JsonProperty
	private Map<String,String> data;
	@JsonProperty(value = "passcode_attempts_left")
	private int passcodeAttemptsLeft;
	@JsonProperty(value = "auth_attempts_left")
	private int authAttemptsLeft;
	@JsonProperty
	private boolean authorized;
	@JsonProperty
	private User user;

	public String getToken() {
		return token;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public int getAuthAttemptsLeft() {
		return authAttemptsLeft;
	}
	public int getPasscodeAttemptsLeft() {
		return passcodeAttemptsLeft;
	}
	public boolean isAuthorized() {
		return authorized;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public User getUser(){
		return user;
	}

}
