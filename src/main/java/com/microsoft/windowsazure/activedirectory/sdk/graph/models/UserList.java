package com.microsoft.windowsazure.activedirectory.sdk.graph.models;

import java.util.ArrayList;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SampleAppException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.helper.JSONHelper;

/**
 * The class UserList holds the data of a page of users. It also contains information whether
 * this page any next page or previous page and also the contains the page number member.
 * @author Azure Active Directory Contributor
 * @param <T>
 */
public class UserList extends DirectoryObjectList<User> {
	

	/**
	 * The constructor of the UserList class. Initializes the user List.
	 */
	public UserList(){
		directoryObjectList = new ArrayList<User>();
	}
	
	public UserList(JSONArray array){
		directoryObjectList = new ArrayList<User>();
		for(int i = 0; i < array.length(); i++){
			User user = new User();
			try {
				JSONHelper.convertJSONObjectToDirectoryObject(array.optJSONObject(i), user);
			} catch (SampleAppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//user = new Gson().fromJson(array.optString(i), User.class);
			//	logger.info("user ->" + user);
			directoryObjectList.add(user);
		}
	}
	
	/**
	 * @param index The index of the requested user.
	 * @return Returns the UserPrincipalName of the user at index position in the List.
	 */
	public String getUserPrincipalName(int index){
		return this.directoryObjectList.get(index).getUserPrincipalName();
	}
	
	/**
	 * @param index The index of the requested user.
	 * @return Returns the AccountEnabled of the user at index position in the List.
	 */
	public String getUserAccountEnabled(int index){
		return this.directoryObjectList.get(index).getAccountEnabled();
	}

	/**
	 * @param index The index of the requested user.
	 * @return Returns the MailNickname of the user at index position in the List.
	 */
	public String getUserMailNickname(int index){
		return this.directoryObjectList.get(index).getMailNickname();
	}
		
}
