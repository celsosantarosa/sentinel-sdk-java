package br.com.tempest.sentinelapi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Assert;

import br.com.tempest.sentinelapi.Capabilities;
import br.com.tempest.sentinelapi.Context;
import br.com.tempest.sentinelapi.Device;
import br.com.tempest.sentinelapi.HttpService;
import br.com.tempest.sentinelapi.Process;
import br.com.tempest.sentinelapi.User;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ContextTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ContextTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ContextTest.class );
    }
    
    @org.junit.Test
    public void testCreateUser() {
    	List<Device> devices = new ArrayList<Device>();
    	devices.add(new Device("1234567890", new Capabilities[]{Capabilities.sms}));
    	
    	User user = new User("name", "username", devices);
    	
    	String json = "{\"id\":1,\"name\":\"name\",\"username\":\"username\",\"devices\":[{\"id\":1,\"capabilities\":[\"sms\"],\"confirmed_at\":\"2015-01-01T00:00:00.000Z\",\"number\":\"1234567890\"}]}";
    	
    	HttpService httpService = EasyMock.createMock(HttpService.class);
    	
    	Context context = new Context(httpService);
    	
    	EasyMock.expect(httpService.post(EasyMock.eq("api/v2/users"), EasyMock.anyObject(User.class))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	User userResponse = context.createUser(user.getName(), user.getUsername(), null);
    	
    	assertUser(userResponse);
    }
    
    public void testUpdateUser() {
    	List<Device> devices = new ArrayList<Device>();
    	devices.add(new Device("1234567890", new Capabilities[]{Capabilities.sms}));
    	
    	User user = new User("name", "username", devices);
    	user.setId(1);
    	
    	String json = "{\"id\":1,\"name\":\"name\",\"username\":\"username\",\"devices\":[{\"id\":1,\"capabilities\":[\"sms\"],\"confirmed_at\":\"2015-01-01T00:00:00.000Z\",\"number\":\"1234567890\"}]}";
    	    	
    	HttpService httpService = EasyMock.createMock(HttpService.class);
    	
    	Context context = new Context(httpService);
    	
    	EasyMock.expect(httpService.post(EasyMock.eq("api/v2/users/username"), EasyMock.anyObject(User.class))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	User userResponse = context.updateUser(user);
    	
    	assertUser(userResponse);
    }
    
    public void testFindUser(){
    	List<Device> devices = new ArrayList<Device>();
    	devices.add(new Device("1234567890", new Capabilities[]{Capabilities.sms}));
    	
    	User user = new User("name", "username", null);

    	String json = "{\"id\":1,\"name\":\"name\",\"username\":\"username\",\"devices\":[{\"id\":1,\"capabilities\":[\"sms\"],\"confirmed_at\":\"2015-01-01T00:00:00.000Z\",\"number\":\"1234567890\"}]}";
    	 	
    	HttpService httpService = EasyMock.createMock(HttpService.class);
    	
    	Context context = new Context(httpService);
    	
    	EasyMock.expect(httpService.get(EasyMock.eq("api/v2/users/username"))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	User userResponse = context.findUser(user.getUsername());
    	
    	assertUser(userResponse);
    }
    
    public void testCreateDevice(){
    	Device device = new Device("1234567890", new Capabilities[]{Capabilities.sms, Capabilities.call});
    	
    	String json = "{\"id\": \"1\", \"number\": \"1234567890\", \"capabilities\": [\"sms\", \"call\"]}";
    	
    	HttpService httpService = EasyMock.createMock(HttpService.class);
    	
    	Context context = new Context(httpService);
    	
    	EasyMock.expect(httpService.post(EasyMock.eq("api/v2/users/username/devices"), EasyMock.anyObject(Device.class))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	Device deviceResponse = context.createDevice("username", device.getNumber(), device.getCapabilities());
    	
    	assertEquals(deviceResponse.getId(), 1);
    	assertEquals(device.getNumber(), deviceResponse.getNumber());
    	Assert.assertArrayEquals(device.getCapabilities(), deviceResponse.getCapabilities());
    }
    
    public void testFindDevice(){
    	Device device = new Device("1234567890", new Capabilities[]{Capabilities.sms, Capabilities.call});
    	
    	String json = "{\"id\": \"1\", \"number\": \"1234567890\", \"capabilities\": [\"sms\", \"call\"]}";
    	
    	HttpService httpService = EasyMock.createMock(HttpService.class);
    	
    	Context context = new Context(httpService);
    	
    	EasyMock.expect(httpService.get(EasyMock.eq("api/v2/users/username/devices/1"))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	Device deviceResponse = context.findDevice("username", 1);
    	
    	assertEquals(deviceResponse.getId(), 1);
    	assertEquals(device.getNumber(), deviceResponse.getNumber());
    	Assert.assertArrayEquals(device.getCapabilities(), deviceResponse.getCapabilities());
    }
    
    public void testUpdateDevice(){
    	Device device = new Device("1234567890", new Capabilities[]{Capabilities.sms, Capabilities.call});
    	device.setId(1);
    	
    	String json = "{\"id\": \"1\", \"number\": \"1234567890\", \"capabilities\": [\"sms\", \"call\"]}";
    	
    	HttpService httpService = EasyMock.createMock(HttpService.class);
    	
    	Context context = new Context(httpService);
    	
    	EasyMock.expect(httpService.put(EasyMock.eq("api/v2/users/username/devices/1"), EasyMock.anyObject(Device.class))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	Device deviceResponse = context.updateDevice("username", device);
    	
    	assertEquals(deviceResponse.getId(), 1);
    	assertEquals(device.getNumber(), deviceResponse.getNumber());
    	Assert.assertArrayEquals(device.getCapabilities(), deviceResponse.getCapabilities());
    }
    
    public void testDeleteDevice(){
    	User user = new User("name", "username", null);

    	String json =  "{\"id\": \"1\", \"username\": \"username\", \"name\": \"name\"}";
    	
    	HttpService httpService = EasyMock.createMock(HttpService.class);
    	
    	Context context = new Context(httpService);
    	
    	EasyMock.expect(httpService.delete(EasyMock.eq("api/v2/users/username/devices/1"))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	User userResponse = context.deleteDevice("username", 1);
    	
    	assertEquals(userResponse.getId(), 1);
    	assertEquals(user.getName(), userResponse.getName());
    	assertEquals(user.getUsername(), userResponse.getUsername());
    }
    
    public void testCofirmDevice(){
    	Device device = new Device("1234567890", new Capabilities[]{Capabilities.sms, Capabilities.call});
    	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("token", "processToken");
		params.put("code", "0000");
    	
    	String json = "{\"id\": \"1\", \"number\": \"1234567890\", \"capabilities\": [\"sms\", \"call\"]}";
    	
    	HttpService httpService = EasyMock.createMock(HttpService.class);
    	
    	Context context = new Context(httpService);
    	
    	EasyMock.expect(httpService.post(EasyMock.eq("api/v2/users/username/devices/1/confirm"), EasyMock.eq(params))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	Device deviceResponse = context.confirmDevice("username", "processToken", "0000", 1);
    	
    	assertEquals(deviceResponse.getId(), 1);
    	assertEquals(device.getNumber(), deviceResponse.getNumber());
    	Assert.assertArrayEquals(device.getCapabilities(), deviceResponse.getCapabilities());
    }
    
    public void testCreateProcess(){
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "username");
		params.put("enrollment", false);
		
		String json = "{\"token\":\"d7c3e226-9002-4928-ac09-169f3c78a62a\",\"created_at\":\"2015-01-01T00:00:00.000Z\",\"updated_at\":\"2015-01-01T00:00:00.000Z\",\"data\":null,\"passcode_attempts_left\":1,\"auth_attempts_left\":1,\"authorized\":false,\"user\":{\"id\":1,\"name\":\"name\",\"username\":\"username\",\"devices\":[{\"id\":1,\"capabilities\":[\"sms\"],\"confirmed_at\":\"2015-01-01T00:00:00.000Z\",\"number\":\"1234567890\"}]}}";
		
		HttpService httpService = EasyMock.createMock(HttpService.class);
		
		Context context = new Context(httpService);
		
		EasyMock.expect(httpService.post(EasyMock.eq("api/v2/process"), EasyMock.eq(params))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	Process processResponse = context.createProcess("username", false);
    	
    	assertProcess(processResponse, false);
    }
    
    public void testFindProcess(){
		String json = "{\"token\":\"d7c3e226-9002-4928-ac09-169f3c78a62a\",\"created_at\":\"2015-01-01T00:00:00.000Z\",\"updated_at\":\"2015-01-01T00:00:00.000Z\",\"data\":null,\"passcode_attempts_left\":1,\"auth_attempts_left\":1,\"authorized\":false,\"user\":{\"id\":1,\"name\":\"name\",\"username\":\"username\",\"devices\":[{\"id\":1,\"capabilities\":[\"sms\"],\"confirmed_at\":\"2015-01-01T00:00:00.000Z\",\"number\":\"1234567890\"}]}}";
		
		HttpService httpService = EasyMock.createMock(HttpService.class);
		
		Context context = new Context(httpService);
		
		EasyMock.expect(httpService.get(EasyMock.eq("api/v2/process/d7c3e226-9002-4928-ac09-169f3c78a62a"))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	Process processResponse = context.findProcess("d7c3e226-9002-4928-ac09-169f3c78a62a");
    	
    	assertProcess(processResponse, false);
    }
    
    public void testSendPasscode(){
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("device_id", "1");
		params.put("capability", "sms");
		params.put("username", "username");
		
		String json = "{\"token\":\"d7c3e226-9002-4928-ac09-169f3c78a62a\",\"created_at\":\"2015-01-01T00:00:00.000Z\",\"updated_at\":\"2015-01-01T00:00:00.000Z\",\"data\":null,\"passcode_attempts_left\":1,\"auth_attempts_left\":1,\"authorized\":false,\"user\":{\"id\":1,\"name\":\"name\",\"username\":\"username\",\"devices\":[{\"id\":1,\"capabilities\":[\"sms\"],\"confirmed_at\":\"2015-01-01T00:00:00.000Z\",\"number\":\"1234567890\"}]}}";
		
		HttpService httpService = EasyMock.createMock(HttpService.class);
		
		Context context = new Context(httpService);
		
		EasyMock.expect(httpService.post(EasyMock.eq("api/v2/process/d7c3e226-9002-4928-ac09-169f3c78a62a/send_passcode"), EasyMock.eq(params))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	Process processResponse = context.sendPasscode("d7c3e226-9002-4928-ac09-169f3c78a62a", 1, "username", Capabilities.sms);
    	
    	assertProcess(processResponse, false);
    }
    
    public void testAuth(){
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", "0000");
		params.put("username", "username");
		
		String json = "{\"token\":\"d7c3e226-9002-4928-ac09-169f3c78a62a\",\"created_at\":\"2015-01-01T00:00:00.000Z\",\"updated_at\":\"2015-01-01T00:00:00.000Z\",\"data\":null,\"passcode_attempts_left\":1,\"auth_attempts_left\":1,\"authorized\":true,\"user\":{\"id\":1,\"name\":\"name\",\"username\":\"username\",\"devices\":[{\"id\":1,\"capabilities\":[\"sms\"],\"confirmed_at\":\"2015-01-01T00:00:00.000Z\",\"number\":\"1234567890\"}]}}";
		
		HttpService httpService = EasyMock.createMock(HttpService.class);
		
		Context context = new Context(httpService);
		
		EasyMock.expect(httpService.post(EasyMock.eq("api/v2/process/d7c3e226-9002-4928-ac09-169f3c78a62a/auth"), EasyMock.eq(params))).andReturn(json);
    	
    	EasyMock.replay(httpService);
    	
    	Process processResponse = context.auth("d7c3e226-9002-4928-ac09-169f3c78a62a", "username", "0000");
    	
    	assertProcess(processResponse, true);
    }
    
    private void assertProcess(Process processResponse, boolean authorized){
    	assertEquals(processResponse.getToken(), "d7c3e226-9002-4928-ac09-169f3c78a62a");
    	assertEquals(processResponse.getCreatedAt(), new Date(1420070400000L));
    	assertEquals(processResponse.getUpdatedAt(), new Date(1420070400000L));
    	assertEquals(processResponse.getAuthAttemptsLeft(), 1);
    	assertEquals(processResponse.getPasscodeAttemptsLeft(), 1);
    	assertEquals(processResponse.isAuthorized(), authorized);
    	
    	User user = processResponse.getUser();
    	assertEquals(user.getId(), 1);
    	assertEquals(user.getName(), "name");
    	assertEquals(user.getUsername(), "username");
    	
    	Device device = processResponse.getUser().getDevices().get(0);
    	assertEquals(device.getId(), 1);
    	assertEquals(device.getNumber(), "1234567890");
    	assertEquals(device.getConfirmedAt(), new Date(1420070400000L));
    	Assert.assertArrayEquals(device.getCapabilities(), new Capabilities[]{Capabilities.sms});
    }
    
    private void assertUser(User userResponse){
    	assertEquals(userResponse.getId(), 1);
    	assertEquals("name", userResponse.getName());
    	assertEquals("username", userResponse.getUsername());
    	
    	Device device = userResponse.getDevices().get(0);
    	assertEquals(device.getNumber(), "1234567890");
    	Assert.assertArrayEquals(device.getCapabilities(), new Capabilities[]{Capabilities.sms});
    }
}
