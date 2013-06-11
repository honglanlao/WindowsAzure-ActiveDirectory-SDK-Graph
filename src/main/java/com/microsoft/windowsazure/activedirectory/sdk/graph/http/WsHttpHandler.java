package com.microsoft.windowsazure.activedirectory.sdk.graph.http;

import org.json.JSONObject;

/**
 * Interface WsHttpHandler provides the general methods for a Active Directory WebService HTTPClient
 * 
 * @author Azure Active Directory Contributor
 */
public interface WsHttpHandler{

	String getClientType();
	
	/**
	 * send a request to the Web Service and get back the response
	 * @param	controller	A String
	 * @param	paramString A JSONObject
	 * @throws	Exception
	 * @return	response from the WebServer as JSONObject
	 */
	public JSONObject GET(String controller, String paramString, String fragment) throws Exception;
	
	/**
	 * 
	 * @param controller
	 * @param paramString
	 * @param payLoad
	 * @param action
	 * @param fragment
	 * @return response from the WebServer as JSONObject
	 * @throws Exception
	 */
	public JSONObject POST(String controller, String paramString, String payLoad, String action, String fragment) throws Exception;
	
	/**
	 * send a request to the Web Service and get back the response
	 * @param	controller	A String
	 * @param	paramString A JSONObject
	 * @throws	Exception
	 * @return	response byte array
	 */
	public byte[] GET(String controller, String paramString) throws Exception;
	
	/**
	 * 
	 * @param controller
	 * @param paramString
	 * @param payLoad
	 * @param action
	 * @param fragment
	 * @return response from the WebServer as JSONObject
	 * @throws Exception
	 */
	public JSONObject DELETE(String controller, String paramString, String payLoad, String action, String fragment) throws Exception;

}
