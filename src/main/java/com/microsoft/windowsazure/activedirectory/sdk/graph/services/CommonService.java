package com.microsoft.windowsazure.activedirectory.sdk.graph.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.microsoft.windowsazure.activedirectory.sdk.graph.config.SdkConfig;
import com.microsoft.windowsazure.activedirectory.sdk.graph.config.TenantConfiguration;
import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SdkException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.helper.JSONHelper;
import com.microsoft.windowsazure.activedirectory.sdk.graph.http.RestClient;
import com.microsoft.windowsazure.activedirectory.sdk.graph.metadata.DirectoryObject;
import com.microsoft.windowsazure.activedirectory.sdk.graph.models.DirectoryObjectList;

/**
 * This class facilitates all the write functionalities to the REST Endpoint
 * such as creating, updating, deleting user objects, adding an user to a group/role,
 * deleting an user from a group/role.
 * @author Azure Active Directory Contributor
 *
 */
public class CommonService {
	
	private static final TenantConfiguration TENANTCONFIG = TenantConfiguration.getInstance();

	public static RestClient restClient = new RestClient(SdkConfig.PROTOCOL_NAME, 
														 SdkConfig.restServiceHost,
														 TENANTCONFIG.getTenantContextId());

	private static Logger logger = Logger.getLogger(CommonService.class);;	
	static{ 
	//	PropertyConfigurator.configure("log4j.properties");
	}

	/**
	 * 
	 * @param <S>
	 * @param <T>
	 * @param listType
	 * @param elementType
	 * @param paging
	 * @param skiptoken
	 * @return
	 * @throws SdkException
	 */
	 public static <T extends DirectoryObjectList<? extends DirectoryObject>, S> T getDirectoryObjectList(Class<T> listType, Class<?> elementType, boolean paging, String skiptoken) throws SdkException{
		
		String paramString = null;
		if(paging == true){
			if(skiptoken == null){
				paramString = String.format("$top=%d", SdkConfig.userSizePerList);
			}else{
				paramString = String.format("$top=%d&$skiptoken=%s", SdkConfig.userSizePerList, skiptoken);
			}
		}
		JSONObject response = restClient.GET(SdkConfig.getGraphAPI(elementType.getSimpleName()), paramString, null);
		logger.info("response in getDirectoryObjectList ->" + response);
		// Create a new DirectoryObjectList 
		T thisList = null;
		try{
			if(response.optInt("responseCode") != 200){
				Constructor<T> con;
				con = listType.getConstructor();
				thisList = (T) con.newInstance();			
				thisList.setErrorObject(response);
				return thisList;
			}else{
				JSONArray directoryObjectJSONArr = JSONHelper.fetchDirectoryObjectJSONArray(response);
				logger.info("directoryObjectJSONArr ->" + directoryObjectJSONArr);
				// Retrieve the skiptoken for the next page from response
				String nextSkiptoken = JSONHelper.fetchNextSkiptoken(response);

				Constructor<T> con = listType.getConstructor(JSONArray.class);
				thisList = (T) con.newInstance(directoryObjectJSONArr);				
				thisList.setCurrSkiptoken(skiptoken);
				thisList.setNextSkiptoken(nextSkiptoken);
				logger.info("thisList ->" + thisList);

				return thisList;	
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return thisList;	
	}
	
	/**
	 * This method gets an particular user identified by its user ID.
	 * @param objectId ObjectId of the user to be retrieved.
	 * @return An user object populated with the relevant attributes.
	 * @throws SdkException 
	 */
	public static <S extends DirectoryObject> S getSingleDirectoryObject(Class<S> elementType, String objectId) throws SdkException{
				
		// Construct param
		String paramString = String.format("/%s", objectId);

		JSONObject response = restClient.GET(SdkConfig.getGraphAPI(elementType.getSimpleName()) + paramString, null, null);
		JSONObject directoryObjectJSON = response.optJSONObject("responseMsg");
		
		// Creates a new DirectoryObject
		S destDirectoryObject = null;
		try {
			destDirectoryObject = elementType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		JSONHelper.convertJSONObjectToDirectoryObject(directoryObjectJSON, destDirectoryObject);
			
		return destDirectoryObject;
	}
	
	
	/**
	 * This method creates a new directoryObject - user or group.
	 * @param request The httpservletrequest object that contains the description
	 * @return 
	 * @throws SdkException if the operation can not be successfully created.
	 */
	public static JSONObject createDirectoryObject(HttpServletRequest request, String controller) throws SdkException{		

		// Send the http POST request to the appropriate url and
		// using an appropriate message body.
		String payLoad = JSONHelper.createJSONString(request, controller);
		String paramString = null;
		// POST(controller, queryParam, payLoad, action, fragment)
		JSONObject response = restClient.POST(SdkConfig.getGraphAPI(controller), paramString, payLoad, "create" + controller, null);
		return response;
	}
	
	public static JSONObject deleteDirectoryObject(HttpServletRequest request, String controller) throws SdkException{		

		// Send the http DELETE request to the appropriate url and
		// using an appropriate message body.
		String payLoad = "{}";
		String paramString = String.format("/%s", request.getParameter("objectId"));
		
		JSONObject response = restClient.DELETE(SdkConfig.getGraphAPI(controller), paramString, payLoad, "delete" + controller, null);
		return response;
	}
	
	public static JSONObject updateDirectoryObject(String objectId, HttpServletRequest request, String controller) throws SdkException{		

		// construct 
		String payLoad = JSONHelper.createJSONString(request, controller);
		String paramString = String.format("/%s", objectId);		 
		
		JSONObject response = restClient.PATCH(SdkConfig.getGraphAPI(controller), paramString, payLoad, "update" + controller, null);
		return response;
	}

	/**
	 * This method adds an user to a group/role.
	 * @param directoryObjectObjectIds The objectId of the role/group to be added.
	 * @param memberOfObjectId The objectId of user object where to be added.
	 * @param controller Whether user to be added in a group or a role.
	 * @throws SdkException If the operation can not be successfully carried out.
	 * @throws JSONException 
	 */	
	public static JSONObject addDirectoryObjectToMemberOf(String directoryObjectObjectIds, String memberOfObjectId, String controller) throws SdkException {

		String directoryObject = String.format("%s://%s/%s/directoryObjects/%s", 
				SdkConfig.PROTOCOL_NAME, SdkConfig.restServiceHost, TENANTCONFIG.getTenantContextId(), directoryObjectObjectIds);
		
		String payLoad = JSONHelper.createJSONString("url", directoryObject);
		String paramString = String.format("/%s/$links/members", memberOfObjectId);
		JSONObject response = restClient.POST(SdkConfig.getGraphAPI(controller), paramString, payLoad, "addUserToMemberOf", null);
		return response; 
	}
	
	/**
	 * This method adds multi-users to a group/role.
	 * @param directoryObjectObjectIds
	 * @param memberOfObjectId
	 * @param controller
	 * @return
	 * @throws SdkException
	 */
	public static JSONArray addDirectoryObjectsToMemberOf(String[] directoryObjectObjectIds, String memberOfObjectId , String controller) throws SdkException {
		
		JSONArray array = new JSONArray();
		if(directoryObjectObjectIds == null) return array;
		for(int i = 0 ; i < directoryObjectObjectIds.length; i ++){
			String each = directoryObjectObjectIds[i];
			JSONObject obj = addDirectoryObjectToMemberOf(each, memberOfObjectId, controller);
			array.put(obj);
		}
		

		return array; 
	}
	
	
	public static JSONArray addDirectoryObjectsToMemberOf( String directoryObjectObjectId, String[] memberOfObjectId, String controller) throws SdkException {
		
		JSONArray array = new JSONArray();
		if(memberOfObjectId == null) return array;
		for(int i = 0 ; i < memberOfObjectId.length; i ++){
			String each = memberOfObjectId[i];
			JSONObject obj = addDirectoryObjectToMemberOf(directoryObjectObjectId, each, controller);
			array.put(obj);
		}
		

		return array; 
	}
	
	
	 
	/**
	 * This method remove an user from a group/role.
	 * @param directoryObjectObjectId The objectId of the role/group to be removed.
	 * @param memberOfObjectId The objectId of user object where to be removed.
	 * @param controller Whether user to be removed from a group or a role.
	 * @throws SdkException If the operation can not be successfully carried out.
	 */	
	public static JSONObject removeDirectoryObjectFromMemberOf(String directoryObjectObjectId, String memberOfObjectId , String controller) throws SdkException {

		String paramString = String.format("/%s/$links/members/%s", memberOfObjectId, directoryObjectObjectId);
		String payLoad = "";
		JSONObject response = restClient.DELETE(SdkConfig.getGraphAPI(controller), paramString, payLoad, "removeUserFromMemberOf", null);
		return response; 
	}
	
	/**
	 * This method remove multi-users from a group/role.
	 * @param directoryObjectObjectIds
	 * @param memberOfObjectId
	 * @param controller
	 * @return
	 * @throws SdkException
	 */
	public static JSONArray removeDirectoryObjectsFromMemberOf(String[]  directoryObjectObjectIds, String memberOfObjectId , String controller) throws SdkException {

		JSONArray array = new JSONArray();
		if(directoryObjectObjectIds == null) return array;
		for(int i = 0 ; i < directoryObjectObjectIds.length; i ++){
			String each = directoryObjectObjectIds[i];
			JSONObject obj = removeDirectoryObjectFromMemberOf(each, memberOfObjectId, controller);
			array.put(obj);
		}
		return array; 
	}
	
	public static JSONArray removeDirectoryObjectsFromMemberOf( String directoryObjectObjectIDs, String[] memberOfObjectId, String controller) throws SdkException {

		JSONArray array = new JSONArray();
		if(memberOfObjectId == null) return array;
		for(int i = 0 ; i < memberOfObjectId.length; i ++){
			String each = memberOfObjectId[i];
			JSONObject obj = removeDirectoryObjectFromMemberOf(directoryObjectObjectIDs, each, controller);
			array.put(obj);
		}
		return array; 
	}
	
	public static boolean isMemberOf( String objectId, String memberOfName) throws SdkException {
		
		String paramStr = String.format("/%s/memberOf", objectId);

		JSONObject response = restClient.GET("/users" + paramStr, null, null);
		logger.info("in isMemberOf ->" + response);
		JSONArray directoryObjectJSONArr = JSONHelper.fetchDirectoryObjectJSONArray(response);
		for(int i = 0; i < directoryObjectJSONArr.length(); i ++){
			if(directoryObjectJSONArr.optJSONObject(i).optString("displayName").equals(memberOfName)){
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * @param listType
	 * @param elementType
	 * @param deltaLink
	 * @return
	 */
	public static <T extends DirectoryObjectList<? extends DirectoryObject>, S> JSONObject getDifferentialDirectoryObjectList(Class<T> listType, Class<?> elementType, String deltaLink) throws SdkException{
		
		
		String paramStr = String.format("deltaLink=%s", deltaLink);
		
		JSONObject response = restClient.GET(SdkConfig.getGraphAPI(elementType.getSimpleName()), paramStr, null);
		logger.info("response in getDifferentialDirectoryObjectList ->" + response);
		// Create a new DirectoryObjectList 
		return response;
		
	}
	
	
	 
}
