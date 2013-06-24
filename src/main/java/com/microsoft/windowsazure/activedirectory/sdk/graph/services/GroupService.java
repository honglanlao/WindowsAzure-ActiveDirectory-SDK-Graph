package com.microsoft.windowsazure.activedirectory.sdk.graph.services;

import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.windowsazure.activedirectory.sdk.graph.config.SdkConfig;
import com.microsoft.windowsazure.activedirectory.sdk.graph.config.TenantConfiguration;
import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SdkException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.helper.JSONHelper;
import com.microsoft.windowsazure.activedirectory.sdk.graph.http.RestClient;
import com.microsoft.windowsazure.activedirectory.sdk.graph.models.Group;
import com.microsoft.windowsazure.activedirectory.sdk.graph.models.GroupList;
import com.microsoft.windowsazure.activedirectory.sdk.graph.models.User;
import com.microsoft.windowsazure.activedirectory.sdk.graph.models.UserList;

/**
 * This class provides all API for group related
 * @author Azure Active Directory Contributor
 *
 */
public class GroupService {

//	private static final TenantConfiguration TENANTCONFIG = TenantConfiguration.getInstance();

	public static RestClient restClient = new RestClient(SdkConfig.PROTOCOL_NAME, 
														 SdkConfig.restServiceHost,
														 TenantConfiguration.getTenantContextId());

	/**
	 * @param objectId
	 * @return
	 * @throws SdkException
	 */
	public static UserList getUsersForGroup(String objectId)  throws SdkException {
		String paramString = String.format("/%s/members", objectId);
		JSONObject response = restClient.GET("/groups" + paramString, null, null);
		
		JSONArray memberArray = JSONHelper.fetchDirectoryObjectJSONArray(response);
		
		// Creates a new user object and get the data copied over from the JSONObject
		// to this object.		
		UserList thisPage = new UserList();	
		
		for(int i = 0; i < memberArray.length(); i++){
			JSONObject thisMemberJSONObject = memberArray.optJSONObject(i);
			User user = new User();
			if(thisMemberJSONObject.optString("objectType").equalsIgnoreCase("User")){
				JSONHelper.convertJSONObjectToDirectoryObject(thisMemberJSONObject, user);
				thisPage.add(user);
			}
		}
		return thisPage;
	}
	
	
	/**
	 * This method returns the results of a query for groups.
	 * @param attributeName The attribute on which the queries are made of.
	 * @param opName The operator name that would be applied to the attribute.
	 * @param searchString The string that would be searched for this attribute.
	 * @return A page of groups satisfying this query criteria.
	 * @throws SdkException If the operation can not be carried out successfully.
	 */
	public static GroupList queryGroups(String attributeName, 
									  String opName, 
									  String searchString) throws SdkException {

		// This object would hold all the group information. 
		GroupList thisPage = new GroupList();
		
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
		JSONObject response = restClient.GET("/groups", paramString, null);
		 
		JSONArray groups = new JSONArray();
	
		groups = JSONHelper.fetchDirectoryObjectJSONArray(response);

		// Get the DisplayName, ObjectId, UserPrincipalName from the JSON Array and populate them in the page object.
		for(int i = 0; i < groups.length(); i++){		
				JSONObject thisUserJSONObject = groups.optJSONObject(i);
				Group group = new Group();
				JSONHelper.convertJSONObjectToDirectoryObject(thisUserJSONObject, group);
				thisPage.add(group);							
		}
		return thisPage;
	}

}
