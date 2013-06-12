package com.microsoft.windowsazure.activedirectory.sdk.graph.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONException;
import org.json.JSONObject;

import com.microsoft.windowsazure.activedirectory.sdk.graph.config.SdkConfig;
import com.microsoft.windowsazure.activedirectory.sdk.graph.config.TenantConfiguration;
import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SdkException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.helper.HttpClientHelper;

/**
 * Class RestClient implements WsHttpHandler
 * 
 * @author Azure Active Directory Contributor
 * 
 */
public class RestClient implements WsHttpHandler {
	
	private String protocol;
	private String endPoint;
	private String TenantContextId;
	private Logger logger  = Logger.getLogger(RestClient.class);
//	private static final SdkConfig CONFIG = SdkConfig.getInstance();

	
	public RestClient(String protocol, String endpoint, String TenantContextId) {
	
		this.protocol = protocol;
		this.endPoint = endpoint;
		this.TenantContextId = TenantContextId;
	//	PropertyConfigurator.configure("log4j.properties");

	}
	
	public byte[] GET(String controller, String paramStr) throws SdkException {
		
		HttpURLConnection conn = null;
		JSONObject response = new JSONObject();	
		if(paramStr == null){
			paramStr = SdkConfig.APIVERSION_HEADER + "=" + SdkConfig.apiVersion;
		}else{
			paramStr = SdkConfig.APIVERSION_HEADER + "=" + SdkConfig.apiVersion + "&" + paramStr;
		}
		try {
			// Construct the URI from the different parts.
			URI uri = new URI(this.protocol, 
							  this.endPoint,
							  "/" + this.TenantContextId + controller,
							   paramStr, 
					          null);
		
			// Get the url and open an http connection.
			URL url = uri.toURL();
			logger.info("url ->" + url);
			conn = (HttpURLConnection) url.openConnection();

			// Set the appropriate header fields in the request header.  below should be in property field of this class
			conn.setRequestProperty(SdkConfig.APIVERSION_HEADER, SdkConfig.apiVersion);
			conn.setRequestProperty(SdkConfig.AUTHORIZATION_HEADER, TenantConfiguration.getAccessToken());
			conn.setRequestProperty(SdkConfig.ACCEPT_HEADER, "*/*");
			conn.setRequestProperty(SdkConfig.ACCEPT_HEADER, SdkConfig.ACCEPT_HEADER_VALUE);

			byte[] imageInByte = HttpClientHelper.getByteaArrayFromConn(conn, true);
			return imageInByte;
			
		}catch (IOException e) {
			try {							
				// Get error string
				String badRespStr = HttpClientHelper.getResponseStringFromConn(conn, false);
				// Parse the JSON String and retrieve the error code and message				
				int responseCode = conn.getResponseCode();	
				response = HttpClientHelper.processBadRespStr(responseCode, badRespStr);
				throw new SdkException(response.optString("errorCode"), response.optString("errorMsg"), e);
	
			} catch (IOException e1) {
				throw new SdkException(SdkConfig.ErrorCodeNotReceived, SdkConfig.ErrorCodeNotReceivedMessage, e1 );
			} catch(JSONException e2){
				// If the error message couldn't sucessfully parsed. 
				throw new SdkException(SdkConfig.ErrorConnectingRestService, SdkConfig.ErrorConnectingRestServiceMessage, e2);
			}
			
		} catch (URISyntaxException e) {
			throw new SdkException(SdkConfig.UriSyntaxException, SdkConfig.UriSyntaxExceptionMessage, e);
		}	
	}
	
	public JSONObject GET(String controller, String paramStr, String fragment) throws SdkException {

		HttpURLConnection conn = null;
		JSONObject response = new JSONObject();
		if(paramStr == null){
			paramStr = SdkConfig.APIVERSION_HEADER + "=" + SdkConfig.apiVersion;
		}else{
			paramStr = SdkConfig.APIVERSION_HEADER + "=" + SdkConfig.apiVersion + "&" + paramStr;
		}
		try {
			// Construct the URI from the different parts.
			URI uri = new URI(this.protocol, 
							  this.endPoint,
							  "/" + this.TenantContextId + controller,
							  paramStr, 
					          fragment);
		
			// Get the url and open an http connection.
			URL url = uri.toURL();
			logger.info("url ->" + url);
		
			conn = (HttpURLConnection) url.openConnection();

			// Set the appropriate header fields in the request header.
			conn.setRequestProperty(SdkConfig.APIVERSION_HEADER, SdkConfig.apiVersion);
			conn.setRequestProperty(SdkConfig.AUTHORIZATION_HEADER, TenantConfiguration.getAccessToken());
			conn.setRequestProperty(SdkConfig.ACCEPT_HEADER, SdkConfig.ACCEPT_HEADER_VALUE);
			
			String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, true);
		//	logger.info("goodRespStr ->" + goodRespStr);
			int responseCode = conn.getResponseCode();
			response = HttpClientHelper.processGoodRespStr(responseCode, goodRespStr);
			 

		}catch (IOException e) {
			try {														
				// Get the error stream and save the error message in the String response.
				String badRespStr = HttpClientHelper.getResponseStringFromConn(conn, false);		
				logger.info("badRespStr ->" + badRespStr);
				int responseCode = conn.getResponseCode();				
				response = HttpClientHelper.processBadRespStr(responseCode, badRespStr);
			//	throw new SdkException(response.optString("errorCode"), response.optString("errorMsg"), e);
					
			} catch (IOException e1) {
				throw new SdkException(SdkConfig.ErrorCodeNotReceived, SdkConfig.ErrorCodeNotReceivedMessage, e1 );
			} catch(JSONException e2){
				// If the error message couldn't sucessfully parsed. 
				throw new SdkException(SdkConfig.ErrorConnectingRestService, SdkConfig.ErrorConnectingRestServiceMessage, e2);
			}		
		} catch (URISyntaxException e) {
			throw new SdkException(SdkConfig.UriSyntaxException, SdkConfig.UriSyntaxExceptionMessage, e);
		} catch (JSONException e) {
			throw new SdkException(SdkConfig.ErrorConnectingRestService, SdkConfig.ErrorConnectingRestServiceMessage, e);
		}
		return response;
	}

	/**
	 * This method would handle the http POST/PATCH request to the REST Endpoint.
	 * @param path The additional path part of the URL
	 * @param queryOption The response of the request sent
	 * @param data Data that would be put to the http request POST/PATCH body.
	 * @param opName The name of the operation that is invoking this method.
	 * @return response JSONObject
	 * @throws SdkException 
	 */
	public JSONObject POST(String controller, String paramStr, String payLoad, String action, String fragment) throws SdkException{

		URI uri = null;
		URL url = null;
		HttpURLConnection conn = null;
		JSONObject response = new JSONObject();
	
		try {				
			// Form the request uri by specifying the individual components of the URI.
			if(action.startsWith("create")){
				uri = new URI(this.protocol, 
								  this.endPoint,
								  "/" + this.TenantContextId + controller,
								  SdkConfig.APIVERSION_HEADER + "=" + SdkConfig.apiVersion + "&" + paramStr, 
						          fragment);
			}else if(action.startsWith("add")){
				uri = new URI(this.protocol, 
						  this.endPoint,
						  "/" + this.TenantContextId + controller + paramStr,
						  SdkConfig.APIVERSION_HEADER + "=" + SdkConfig.apiVersion,  // set null so ? will not be appended
				          fragment);
			}
					
			// Open an URL Connection.
			url = uri.toURL();
			logger.info("url ->" + url);
			logger.info("payLoad ->" + payLoad);

			conn = (HttpURLConnection) url.openConnection();
			
			// Set method to POST.
			conn.setRequestMethod("POST");
					
			// Set the appropriate request header fields.
		//	conn.setRequestProperty(SdkConfig.APIVERSION_HEADER, SdkConfig.getApiVersion());
			conn.setRequestProperty(SdkConfig.AUTHORIZATION_HEADER, TenantConfiguration.getAccessToken());
			conn.setRequestProperty(SdkConfig.ACCEPT_HEADER, SdkConfig.ACCEPT_HEADER_VALUE);	
			conn.setRequestProperty(SdkConfig.CONTENTTYPE_HEADER, SdkConfig.CONTENTTYPE_HEADER_VALUE);
			
			 
			String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, payLoad);
			 
			int responseCode = conn.getResponseCode();
			response = HttpClientHelper.processGoodRespStr(responseCode, goodRespStr);
		
			return response;

		} catch (Exception e2) {
			try {
				String badRespStr = HttpClientHelper.getResponseStringFromConn(conn, false);
				 
				// responseMsg is error msg
//				JSONObject errorObject =  new JSONObject(responseMsg).optJSONObject("odata.error");
//			
//				String errorCode = errorObject.optString("code");
//				String errorMsg = errorObject.optJSONObject("message").optString("value");
//				int responseCode = conn.getResponseCode();
//				response = HttpClientHelper.processResponse(responseCode, errorCode, errorMsg);
				
				logger.info("badRespStr ->" + badRespStr);
				int responseCode = conn.getResponseCode();				
				response = HttpClientHelper.processBadRespStr(responseCode, badRespStr);
				
	
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		 
		return response;
	}
	
	
	public JSONObject PATCH(String controller, String paramStr, String payLoad, String action, String fragment) throws SdkException{

		URI uri = null;
		URL url = null;
		HttpURLConnection conn = null;
		JSONObject response = new JSONObject();
		
		try{
			uri = new URI(this.protocol, 
					  this.endPoint,
					  "/" + this.TenantContextId + controller + paramStr,
					  SdkConfig.APIVERSION_HEADER + "=" + SdkConfig.apiVersion,  // set null so ? will not be appended
			          fragment);
					
			// Open an URL Connection.
			url = uri.toURL();
			logger.info("url ->" + url);
			logger.info("payLoad ->" + payLoad);

			conn = (HttpURLConnection) url.openConnection();
			
			// Set method to POST.
			conn.setRequestMethod("POST");
					
			// Set the appropriate request header fields.
		//	conn.setRequestProperty(SdkConfig.APIVERSION_HEADER, SdkConfig.getApiVersion());
			conn.setRequestProperty(SdkConfig.AUTHORIZATION_HEADER, TenantConfiguration.getAccessToken());
			conn.setRequestProperty(SdkConfig.ACCEPT_HEADER, SdkConfig.ACCEPT_HEADER_VALUE);	
			conn.setRequestProperty(SdkConfig.CONTENTTYPE_HEADER, SdkConfig.CONTENTTYPE_HEADER_VALUE);

			// Set X-HTTP-Method to PATCH for update request			
			conn.setRequestProperty("X-HTTP-Method", "PATCH");			
			
			String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, payLoad);
			 
			int responseCode = conn.getResponseCode();
			response = HttpClientHelper.processGoodRespStr(responseCode, goodRespStr);
			
			return response;

		} catch (Exception e2) {
			try {
				String badRespStr = HttpClientHelper.getResponseStringFromConn(conn, false);

				int responseCode = conn.getResponseCode();
				response = HttpClientHelper.processBadRespStr(responseCode, badRespStr);
	
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		 
		return response;
	}
	
	
	
	public JSONObject DELETE(String controller, String paramStr, String payLoad, String action, String fragment) throws SdkException{
		
		URL url = null;
		HttpURLConnection conn = null;
		JSONObject response = new JSONObject();
	
		try {
					
			// Form the request uri by specifying the individual components of the
			// URI.
			URI uri = new URI(this.protocol, 
							  this.endPoint,
							  "/" + this.TenantContextId + controller + paramStr,
							  SdkConfig.APIVERSION_HEADER + "=" + SdkConfig.apiVersion,  // set null so ? will not be appended
					          fragment);
					
			// Open an URL Connection.
			url = uri.toURL();
			
			conn = (HttpURLConnection) url.openConnection();
			
			// Set method to POST.
			conn.setRequestMethod("DELETE");
			// Set the appropriate request header fields.
		//	conn.setRequestProperty(SdkConfig.APIVERSION_HEADER, SdkConfig.getApiVersion());
			conn.setRequestProperty(SdkConfig.AUTHORIZATION_HEADER, TenantConfiguration.getAccessToken());
			conn.setRequestProperty(SdkConfig.CONTENTTYPE_HEADER, SdkConfig.CONTENTTYPE_HEADER_VALUE);

			conn.setRequestProperty(SdkConfig.ACCEPT_HEADER, SdkConfig.ACCEPT_HEADER_VALUE);

			int responseCode = conn.getResponseCode();
			// if success, responseMsg is empty string
			String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, true);
			 
			response = HttpClientHelper.processGoodRespStr(responseCode, goodRespStr);

			return response;

		} catch (Exception e2) {
			
			try {
				String badRespStr = HttpClientHelper.getResponseStringFromConn(conn, false);
				 
				
				int responseCode = conn.getResponseCode();
				response = HttpClientHelper.processBadRespStr(responseCode, badRespStr);

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		 
		return response;

	}
	
	@Override
	public String getClientType() {
		return "RestClient";
	}
	
}
