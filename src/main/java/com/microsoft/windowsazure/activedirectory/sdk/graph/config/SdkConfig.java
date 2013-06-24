package com.microsoft.windowsazure.activedirectory.sdk.graph.config;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * The class SdkConfig holds the important parameters for this application.
 * The parameters are read in from the web.xml configuration file in the
 * {@link com.microsoft.windowsazure.activedirectory.sample.controllers.GroupServlet#init() init} method. These parameters 
 * are used throughout the application.
 * @author Azure Active Directory Contributor
 */

public class SdkConfig {
	
	
	private static SdkConfig instance;
	private SdkConfig(){
		
	}; 
	
	
	// The number of users shown per page.
	public static int userSizePerList = 9;
	
	// Access token received from the ACS.
//	private static String accessToken = null;
	
	// Current Data Contract Version for the REST Service. Read from the web.xml file. 
	public static String apiVersion = "2013-04-05";
	
	
	// Verified domain name for the tenant, read from the web.xml file.
//	private static String tenantDomainName = null;
	
	
	// Protected resource is REST API Service in this case, that is the host name for this registered with ACS.
	// Read from the web.xml file.
	public static String protectedResourceHostName = "graph.windows.net";
	
	
	// ACS Principal ID, used to get JWT token from ACS, read from the web.xml file.
//	private static String protectedResourcePrincipalId = null;
	
	
	// This is the host for REST Service, read from the web.xml file.
	public static String restServiceHost = "graph.windows.net";
	
	/**
	 * The Service Principal Id for this application service principal.
	 * Read from the web.xml file.
	 */
//	private static String appPrincipalId = null;
	
	/**
	 * The authentication URL for ACS, read from the web.xml file.
	 */
//	private static String acsPrincipalId = null;
	
	/**
	 * The symmetric key that would be used to create the JWT Token.
	 */
//	private static String symmetricKey = null;
	
//	private static String password = null;
	
	/**
	 * Tenant Context Id, read from the web.xml file. 
	 */
//	private static String tenantContextId = null;
	
	/**
	 * The url of the ACS authentication service.
	 */
	public static String stsUrl = "https://accounts.accesscontrol.windows.net/tokens/OAuth/2";
	
	public static String acsUrl = "https://login.windows.net";
	
	/**
	 * The protocol that would be used to connect to the WAAD Rest service.
	 */
	public static final String PROTOCOL_NAME = "https";
	
	/**
	 * The session key Header value.
	 */
	public static String sessionKeyHeader = "x-ms-dirapi-session-key";

	/**
	 * This is the 'About' Message that would be displayed if the user clicks the 'About' Button.
	 */
	public static final String ABOUT_MESSAGE = "This is a sample Java application showing how to access the preview of the " +
				"\"Windows Azure Active Directory Graph API\"," +
				" which is a new RESTful interface allowing customers to build applications to access their Windows Azure AD tenant’s directory data";

	// The authorization header name that would be added in the http request header.
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	// The Data Contract header that would be added in the http request header.
	public static final String APIVERSION_HEADER = "api-version";	

	// The Accept header field that would be added in the http request header.
	public static final String ACCEPT_HEADER = "Accept";
	
	// The Accept Header field value that would be added in the http request header.
	public static final String ACCEPT_HEADER_VALUE = "application/json;odata=minimalmetadata";
	
	// The Content-Type Header field that would be added in the http request header.
	public static final String CONTENTTYPE_HEADER = "Content-Type";
	
	// The Content-Type Header field value that would be added in the http request header.
	public static final String CONTENTTYPE_HEADER_VALUE = "application/json;odata=minimalmetadata";

	// Maximum retry attempt for a retryable exception from REST Service.
	public static final int MAX_RETRY_ATTEMPTS = 1;
	
    //  Message Id for unauthorized request.
    public static final String MessageIdUnauthorized = "Authentication_Unauthorized";

    // Message id for expired tokens.
    public static final String MessageIdExpired = "Authentication_ExpiredToken";

    // Message id for unknown authentication failures.
    public static final String MessageIdUnknown = "Authentication_Unknown";

    // Message id for unsupported token type.
    public static final String MessageIdUnsupportedToken = "Authentication_UnsupportedTokenType";

    // Message id for the data contract missing error message
    public static final String MessageIdContractVersionHeaderMissing = "Headers_ApiVersionMissing";

    // Message id for an invalid data contract version.
    public static final String MessageIdInvalidApiVersion = "Headers_InvalidApiVersion";

    // Message id for the data contract missing error message
    public static final String MessageIdHeaderNotSupported = "Headers_HeaderNotSupported";

    // When the company object could not be read.
    public static final String MessageIdObjectNotFound = "Directory_ObjectNotFound";

    // The most generic message id, when the fault is due to a server error.
    public static final String MessageIdInternalServerError = "Service_InternalServerError";

    // The replica session key provided in the request is invalid.
    public static final String MessageIdInvalidReplicaSessionKey = "Request_InvalidReplicaSessionKey";

    // The replica session key provided in the request is invalid.
    public static final String MessageIdBadRequest = "Request_BadRequest";

    // The replica session key provided in the request is unavailable.
    public static final String MessageIdReplicaUnavailable = "Directory_ReplicaUnavailable";

    // User's data is not in the current datacenter.
    public static final String MessageIdBindingRedirection = "Directory_BindingRedirection";

    // Calling principal's information could not be read from the directory.
    public static final String MessageIdAuthorizationIdentityNotFound = "Authorization_IdentityNotFound";

    // Calling principal is disabled.
    public static final String MessageIdAuthorizationIdentityDisabled = "Authorization_IdentityDisabled";

    // Request is denied due to insufficient privileges.
    public static final String MessageIdAuthorizationRequestDenied = "Authorization_RequestDenied";

    // Encountered an internal error when trying to populate the nearest datacenter endpoints.
    public static final String MessageIdBindingRedirectionInternalServerError
        = "Directory_BindingRedirectionInternalServerError";

    // The request is throttled temporarily
    public static final String MessageIdThrottledTemporarily = "Request_ThrottledTemporarily";

    // The request is throttled permanently
    public static final String MessageIdThrottledPermanently = "Request_ThrottledPermanently";

    // The request query was rejected because it was either invalid or unsupported.
    public static final String MessageIdUnsupportedQuery = "Request_UnsupportedQuery";

    // Request is denied due to insufficient privileges.
    public static final String MessageIdInvalidRequestUrl = "Request_InvalidRequestUrl";

    // Request failed because a target object could not be found.
    public static final String MessageIdResourceNotFound = "Request_ResourceNotFound";

    // Request failed due to the presence of objects with duplicate values in key-valued properties.
    public static final String MessageIdMultipleObjectsWithSameKeyValue = "Request_MultipleObjectsWithSameKeyValue";

    
    // The requested media type is not supported - the $format parameter value is not supported.
    public static final String MessageIdMediaTypeNotSupported = "Request_MediaTypeNotSupported";

    
    // An error occured during connecting to the REST Service and the application could not get the error code..
    public static final String ErrorCodeNotReceived = "Error Code Could Not Be Received!";
    
    
    // The Error Message that would be shown to the User if ErrorCodeNotReceived error is encountered.
    public static final String ErrorCodeNotReceivedMessage = "Sorry! An Error Occured while connecting to the server." +
    		"The Client could not acquire an Error Message.";
	
    // A valid URI could not be built from the request received.
    public static final String UriSyntaxException = "Uri_Build_Error";
    
    
    // The Error Message that would be shown to the User if UriSyntaxException error is encountered.
    public static final String UriSyntaxExceptionMessage = "Sorry! An Error Occured while connecting to the server." +
    		"The Client could not acquire an Error Message.";
	
    // The http request was not successful.
    public static final String ErrorConnectingRestService = "Error_Connecting_Rest_Service";
	
    // The Error Message that would be shown to the User if ErrorConnectingRestService error is encountered.
    public static final String ErrorConnectingRestServiceMessage = "Sorry! Your request couldn't be successfully fulfilled. The server connection was not successful"; 

    // Error occured during generating access tokens.
    public static final String ErrorGeneratingToken = "Could Not Generate Access Token";	
    
    // The Error Message that would be shown to the User if ErrorConnectingRestService error is encountered.
    public static final String ErrorGeneratingTokenMessage = "Sorry! Error Generation was not successful. Please try again."; 
    
    // Error occured during generating xml doc.
    public static final String ErrorCreatingXML = "Could Not Generate Access Token";
    
    public static final String ErrorParsingJSONException = "Error! Could not parse json data.";
    
    // XML Namespace for entry
    public static final String xmlNameSpaceforEntry = "http://www.w3.org/2005/Atom";
    
    // XML Namespace for the prefix m
    public static final String xmlNameSpaceforM = "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata";
    
    // XML Namespace for the prefix m
    public static final String xmlNameSpaceforD = "http://schemas.microsoft.com/ado/2007/08/dataservices";

    public static final String contentTypeXML = "application/xml";
    
    // This is the error code if the program meets some unexpected error.
    public static final String internalError = "Internal Error!";
    
    // The error message to be shown if such error occurs.
    public static final String internalErrorMessage = "Sorry, an unexpected error occured.";
    
    // This is the error code if there is no company administrator role found.
    public static final String NoCompanyAdminRole = "No Company Admin Role!";
    
    // The error message to be shown if such error occurs.
    public static final String NoCompanyAdminRoleMessage = "Sorry, No Company Administrator Role was Found.";
    
    // get AD API mapped from controller
    public static String getGraphAPI(String controller) {
    	
    	try {
			return new JSONObject("{\"User\":\"/users\", \"Group\":\"/groups\", \"Role\":\"/roles\"}").optString(controller);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
    }

    /**
	 * @return the userSizePerList
	 */
//	public static int getUserSizePerList() {
//		return userSizePerList;
//	}
//	
//	/**
//	 * @param userSizePerList the userSizePerList to set
//	 */
//	public static void setUserSizePerList(int userSizePerList) {
//		SdkConfig.userSizePerList = userSizePerList;
//	}
	
	/**
	 * @return the accessToken
	 */
//	public static String getAccessToken() {
//		return accessToken;
//	}
	
	/**
	 * @param accessToken the accessToken to set
	 */
//	public static void setAccessToken(String accessToken) {
//		SdkConfig.accessToken = accessToken;
//	}
//	
	/**
	 * @return the apiVersion
	 */
	
//	public static String getApiVersion() {
//		return apiVersion;
//	}
//	
//	/**
//	 * @param apiVersion the apiVersion to set
//	 */
//	public static void setApiVersion(String apiVersion) {
//		SdkConfig.apiVersion = apiVersion;
//	}
	
	/**
	 * @return the tenantDomainName
	 */
//	public static String getTenantDomainName() {
//		return tenantDomainName;
//	}
	
	/**
	 * @param tenantDomainName the tenantDomainName to set
	 */
//	public static void setTenantDomainName(String tenantDomainName) {
//		SdkConfig.tenantDomainName = tenantDomainName;
//	}
	
	/**
	 * @return the restServiceHost
	 */
//	public static String getRestServiceHost() {
//		return restServiceHost;
//	}
//	
//	/**
//	 * @param restServiceHost the restServiceHost to set
//	 */
//	public static void setRestServiceHost(String restServiceHost) {
//		SdkConfig.restServiceHost = restServiceHost;
//	}
//	
//	/**
//	 * @return the protectedResourceHostName
//	 */
//	public static String getProtectedResourceHostName() {
//		return protectedResourceHostName;
//	}
//	
//	/**
//	 * @param protectedResourceHostName the protectedResourceHostName to set
//	 */
//	public static void setProtectedResourceHostName(
//			String protectedResourceHostName) {
//		SdkConfig.protectedResourceHostName = protectedResourceHostName;
//	}
	
	/**
	 * @return the appPrincipalId
	 */
//	public static String getAppPrincipalId() {
//		return appPrincipalId;
//	}
//	
//	/**
//	 * @param appPrincipalId the appPrincipalId to set
//	 */
//	public static void setAppPrincipalId(String appPrincipalId) {
//		SdkConfig.appPrincipalId = appPrincipalId;
//	}
	
	/**
	 * @return the acsPrincipalId
	 */
//	public static String getAcsPrincipalId() {
//		return acsPrincipalId;
//	}
//	
//	/**
//	 * @param acsPrincipalId the acsPrincipalId to set
//	 */
//	public static void setAcsPrincipalId(String acsPrincipalId) {
//		SdkConfig.acsPrincipalId = acsPrincipalId;
//	}
//	
	/**
	 * @return the symmetricKey
	 */
//	public static String getSymmetricKey() {
//		return symmetricKey;
//	}
	
	/**
	 * @param symmetricKey the symmetricKey to set
	 */
//	public static void setSymmetricKey(String symmetricKey) {
//		SdkConfig.symmetricKey = symmetricKey;
//	}
	
	/**
	 * @return
	 */
//	public static String getPassword() {
//		return password;
//	}
	
	/**
	 * @param password
	 */
//	public static void setPassword(String password){
//		SdkConfig.password = password;
//	}
	
	/**
	 * @return the tenantContextId
	 */
//	public static String getTenantContextId() {
//		return tenantContextId;
//	}
	
	/**
	 * @param tenantContextId the tenantContextId to set
	 */
//	public static void setTenantContextId(String tenantContextId) {
//		SdkConfig.tenantContextId = tenantContextId;
//	}
	
	/**
	 * @return the stsUrl
	 */
//	public static String getStsUrl() {
//		return stsUrl;
//	}
//	
//	/**
//	 * @param stsUrl the stsUrl to set
//	 */
//	public static void setStsUrl(String stsUrl) {
//		SdkConfig.stsUrl = stsUrl;
//	}
//	
//	/**
//	 * @return
//	 */
//	public static String getAcsUrl() {
//		return acsUrl;
//	}
//	
//	/**
//	 * @param acsUrl
//	 */
//	public static void setAcsUrl(String acsUrl) {
//		SdkConfig.acsUrl = acsUrl;
//	}
//	
//	
//	/**
//	 * @return the protectedResourcePrincipalId
//	 */
//	public static String getProtectedResourcePrincipalId() {
//		return protectedResourcePrincipalId;
//	}
//	
//	/**
//	 * @param protectedResourcePrincipalId the protectedResourcePrincipalId to set
//	 */
//	public static void setProtectedResourcePrincipalId(String protectedResourcePrincipalId) {
//		SdkConfig.protectedResourcePrincipalId = protectedResourcePrincipalId;
//	}
	
	// 
	public static SdkConfig getInstance(){ 
		if (instance == null) {  
			synchronized(SdkConfig.class){
				if (instance == null){
					instance = new SdkConfig(); 
				} 
			}
		}
		return instance;
	}
}
