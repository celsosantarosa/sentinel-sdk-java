package com.tempest.sentinelapi;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;

public class HttpService {
	
	
	private String key;
	private String secret;
	private String host = "https://api.sentinelapp.com.br";
	
	private WebResource service;

	public HttpService(String key, String secret) {
		ClientConfig config = new DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		Client client = Client.create(config);
		service = client.resource(host);
		service.addFilter(new HTTPBasicAuthFilter(key, secret));
	}
	
	public HttpService(String key, String secret, String host) {
		this(key, secret);
		this.host = host;
	}
	
	public String get(String path) {
		return getContent(service.path(path).type(MediaType.APPLICATION_JSON).get(ClientResponse.class));
	}
	
	public String post(String path, Object body) {
		return getContent(service.path(path).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, body));
	}
	
	public String put(String path, Object body) {
		return getContent(service.path(path).type(MediaType.APPLICATION_JSON).put(ClientResponse.class, body));
	}
	
	public String delete(String path) {
		return getContent(service.path(path).type(MediaType.APPLICATION_JSON).delete(ClientResponse.class));
	}
	
	private String getContent(ClientResponse response){
		if (Status.OK.getStatusCode() == response.getStatus()) {
			return response.getEntity(String.class);
		} else if (response.getStatus() == 401) {
			throw new UnauthorizedRequestException();
		} else if (response.getStatus() == 404) {
			throw new ResourceNotFoundException();
		} else if (response.getStatus() == 422) {
			ErrorCollection errors = response.getEntity(ErrorCollection.class);
			throw new DomainException(errors.getErrors());
		} else {
			throw new InfraException("HTTP error: " + response.getStatus());
		}
	}
}
