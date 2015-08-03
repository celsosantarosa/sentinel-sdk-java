package br.com.tempest.sentinelapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Context {

	private ObjectMapper mapper;
	private HttpService httpService;
	
	public Context(HttpService httpService) {
		this.httpService = httpService;
		this.mapper = new ObjectMapper();
	}
	
	public Context(String key, String secret){
		this(new HttpService(key, secret));
	}
	
	public User createUser(String name, String username, List<Device> devices) {
		User user = new User(name, username, devices);
		return deserialize(httpService.post("api/v2/users", user), User.class);
	}
	
	public User findUser(String username) {
		return deserialize(httpService.get("api/v2/users/" + username), User.class);
	}
	
	public User updateUser(User user) {
		return deserialize(httpService.post("api/v2/users/" + user.getUsername(), user), User.class);
	}
	
	public User updateUser(User user, boolean keepOtpDevices) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", user.getUsername());
		params.put("name", user.getName());
		params.put("devices", user.getDevices());
		params.put("keep_otp_devices", keepOtpDevices);
		
		return deserialize(httpService.post("api/v2/users/" + user.getUsername(), params), User.class);
	}
	
	public Device createDevice(String username, String number, Capabilities[] capabilities) {
		Device device = new Device();
		device.setNumber(number);
		device.setCapabilities(capabilities);
		return deserialize(httpService.post("api/v2/users/" + username + "/devices", device), Device.class);
	}

	public Device findDevice(String username, int deviceId) {
		return deserialize(httpService.get("api/v2/users/" + username + "/devices/" + deviceId), Device.class);
	}
	
	public Device updateDevice(String username, Device device) {
		return deserialize(httpService.put("api/v2/users/" + username + "/devices/" + device.getId(), device), Device.class);
	}
	
	public User deleteDevice(String username, int deviceId) {
		return deserialize(httpService.delete("api/v2/users/" + username + "/devices/" + deviceId), User.class);
	}
	
	public Device confirmDevice(String username, String token, String code, int deviceId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("code", code);
		return deserialize(httpService.post("api/v2/users/" + username + "/devices/" + deviceId + "/confirm", params), Device.class);
	}
	
	public Process createProcess(String username, boolean enrollment) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("enrollment", enrollment);
		return deserialize(httpService.post("api/v2/process", params), Process.class);
	}
	
	public Process createProcess(String username) {
		return createProcess(username, false);
	}
	
	public Process findProcess(String token) {
		return deserialize(httpService.get("api/v2/process/" + token),  Process.class);
	}
	
	public Process sendPasscode(String token, int deviceId, String username, Capabilities capabilitie){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("device_id", Integer.toString(deviceId));
		params.put("capability", capabilitie.toString());
		params.put("username", username);
		return deserialize(httpService.post("api/v2/process/" + token + "/send_passcode", params), Process.class);
	}
	
	public Process auth(String token, String username, String code){
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("code", code);
		return deserialize(httpService.post("api/v2/process/" + token + "/auth", params), Process.class);
	}
	
	private <A> A deserialize(String json, Class<A> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonGenerationException e) {
			throw new InfraException(e.toString());
		} catch (JsonMappingException e) {
			throw new InfraException(e.toString());
		} catch (IOException e) {
			throw new InfraException(e.toString());
		}
	}
}
