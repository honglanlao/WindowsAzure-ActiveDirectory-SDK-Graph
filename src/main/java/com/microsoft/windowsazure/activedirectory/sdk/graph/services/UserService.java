/**
 * 
 */
package com.microsoft.windowsazure.activedirectory.sdk.graph.services;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.windowsazure.activedirectory.sdk.graph.config.SdkConfig;
import com.microsoft.windowsazure.activedirectory.sdk.graph.config.TenantConfiguration;
import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SdkException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.helper.JSONHelper;
import com.microsoft.windowsazure.activedirectory.sdk.graph.http.RestClient;
import com.microsoft.windowsazure.activedirectory.sdk.graph.models.Role;
import com.microsoft.windowsazure.activedirectory.sdk.graph.models.RoleList;
import com.microsoft.windowsazure.activedirectory.sdk.graph.models.User;
import com.microsoft.windowsazure.activedirectory.sdk.graph.models.UserList;

/**
 * This class provides API for users related.
 * @author Azure Active Directory Contributor
 *
 */
public class UserService {
	
//	private static final TenantConfiguration TENANTCONFIG = TenantConfiguration.getInstance();
	private TenantConfiguration tenant;
	private RestClient restClient; 
	public UserService(TenantConfiguration tenant){
		this.tenant = tenant;
		restClient = new RestClient(SdkConfig.PROTOCOL_NAME, 
				 SdkConfig.restServiceHost,
				 this.tenant.getTenantContextId());
	}
	
	private static Logger logger;
	
	static{
		logger = Logger.getLogger(UserService.class);	
	//	PropertyConfigurator.configure("log4j.properties");
	}
	
	/**
	 * This method gets an user thumbnail identified by its user objectId.
	 * @param objectId objectId of the user to be retrieved.
	 * @return An byteArray for the image
	 * @throws SdkException 
	 * @throws IOException 
	 */
	public byte[] getThumbnail(String objectId) throws SdkException, IOException {
		String path = String.format("/%s/thumbnailPhoto", objectId);

		byte[] image = this.restClient.GET("/users" + path, null, this.tenant.getAccessToken());
		return image;
	}
	
	public RoleList getRolesForUser(String objectId)  throws SdkException {
		String paramStr = String.format("/%s/memberOf", objectId);
;		JSONObject response = this.restClient.GET("/users" + paramStr, null, null, this.tenant.getAccessToken());

		JSONArray memberArray = JSONHelper.fetchDirectoryObjectJSONArray(response);

		// Creates a new user object and get the data copied over from the JSONObject
		// to this object.		
		RoleList thisList = new RoleList();	
		
		for(int i = 0; i < memberArray.length(); i++){
			JSONObject thisMemberJSONObject = memberArray.optJSONObject(i);
			Role role = new Role();
			if(thisMemberJSONObject.optString("objectType").equalsIgnoreCase("Role")){
				JSONHelper.convertJSONObjectToDirectoryObject(thisMemberJSONObject, role);
				thisList.addNewRole(role);
			}
		}
		return thisList;
	}

	/**
	 * This method returns the results of a query for users.
	 * @param attributeName The attribute on which the queries are made of.
	 * @param opName The operator name that would be applied to the attribute.
	 * @param searchString The string that would be searched for this attribute.
	 * @return A page of users satisfying this query criteria.
	 * @throws SdkException If the operation can not be carried out successfully.
	 */
	public UserList queryUsers(String attributeName, 
									  String opName, 
									  String searchString) throws SdkException {

		// This object would hold all the user information. 
		UserList thisPage = new UserList();
		
		if( attributeName.trim().isEmpty() 
			|| opName.trim().isEmpty() 
			|| searchString.trim().isEmpty() 
			|| (attributeName == null) 
			|| (opName == null) 
			|| (searchString == null)){
				
				// If any of the agruments are empty or null, throw an exception. In the ideal case,
				// this case should never happen since this case should be taken care of in the client side. 	
				throw new SdkException(SdkConfig.internalError, SdkConfig.internalErrorMessage, null);
		}
		
		// Build the paramString.
		String paramString = "";
		
		// If this is an account Enabled query.
		if(attributeName.trim().equalsIgnoreCase("accountEnabled")){
			paramString = String.format("$filter=%s %s %s", attributeName, opName, searchString);
		}else{
			// If this is an general query.
			paramString = String.format("$filter=%s %s '%s'", attributeName, opName, searchString);
		}

		
		// Send the query with the built queryOption.
		JSONObject response = new JSONObject();
		
		response = this.restClient.GET("/users", paramString, null, this.tenant.getAccessToken());
		logger.info("response -> " + response);
		JSONArray users = new JSONArray();
	
		users = JSONHelper.fetchDirectoryObjectJSONArray(response);

		// Get the DisplayName, ObjectId, UserPrincipalName from the JSON Array and populate them in the page object.
		for(int i = 0; i < users.length(); i++){			
				JSONObject thisUserJSONObject = users.optJSONObject(i);
				User user = new User();
				JSONHelper.convertJSONObjectToDirectoryObject(thisUserJSONObject, user);
				thisPage.add(user);							
			
		}
		return thisPage;
	}
	
	/**
	 * @param upn
	 * @return
	 * @throws SdkException
	 */
	public String getObjectIdByUpn(String upn) throws SdkException{
		
		String paramString = String.format("/%s/", upn);

		JSONObject response = this.restClient.GET("/users" + paramString, null, null, this.tenant.getAccessToken());
		logger.info("getobjectid from upn response ->" + response);
		JSONObject directoryObjectJSON = response.optJSONObject("responseMsg");
		
		return directoryObjectJSON.optString("objectId");
	}
	
	/**
	 * @param objectId
	 * @return
	 * @throws SdkException
	 */
	public UserList getDirectReportsByObjectId(String objectId) throws SdkException {
		
		String paramStr = String.format("/%s/directReports", objectId);
		JSONObject response = this.restClient.GET("/users" + paramStr, null, null, this.tenant.getAccessToken());
		logger.info("response ->" + response);
		JSONArray usersJSONArray = JSONHelper.fetchDirectoryObjectJSONArray(response);
		
		// This object would hold all the user information. 
		UserList userList = new UserList();
				
		// Get the DisplayName, ObjectId, UserPrincipalName from the JSON Array and populate them in the page object.
		for(int i = 0; i < usersJSONArray.length(); i++){			
				JSONObject thisUserJSONObject = usersJSONArray.optJSONObject(i);
				User user = new User();
				JSONHelper.convertJSONObjectToDirectoryObject(thisUserJSONObject, user);
				userList.add(user);									
		}
		return userList;
	}
	
	
	/**
	 * @param objectId
	 * @return
	 * @throws SdkException
	 */
	public User getManagerByObjectId(String objectId) throws SdkException {
		
		String paramStr = String.format("/%s/manager", objectId);
		JSONObject response = restClient.GET("/users" + paramStr, null, null, this.tenant.getAccessToken());
		logger.info("response ->" + response);
		JSONObject userJSONObject = JSONHelper.fetchDirectoryObjectJSONObject(response);
		
		// This object would hold all the user information. 
		User user = new User();
				
		JSONHelper.convertJSONObjectToDirectoryObject(userJSONObject, user);
		
		return user;
	}


}
