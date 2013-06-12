package com.microsoft.windowsazure.activedirectory.sdk.graph.models;

import java.util.ArrayList;

import org.json.JSONArray;

import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SdkException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.helper.JSONHelper;


// The class RoleList holds the data of a page of roles. It also contains information whether
// this page any next page or previous page and also the contains the page number member.
// @author Azure Active Directory Contributor
 
public class RoleList extends DirectoryObjectList<Role>{
	
	/**
	 * The constructor of the UserList class. Initializes the user List.
	 */
	public RoleList(){
		directoryObjectList = new ArrayList<Role>();
	}
	
	public RoleList(JSONArray array){
		directoryObjectList = new ArrayList<Role>();
		for(int i = 0; i < array.length(); i++){
			Role role = new Role();
			try {
				JSONHelper.convertJSONObjectToDirectoryObject(array.optJSONObject(i), role);
			} catch (SdkException e) {
				e.printStackTrace();
			}
			directoryObjectList.add(role);
		}
	}
	
	// @return The number of roles in the List.
	 
	public int getRolesSize(){
		return this.directoryObjectList.size();
	}
	
	
	// @param new role
	 
	public void addNewRole(Role role){
		directoryObjectList.add(role);
	}

	
	// @param index The index of the requested role.
	// @return Returns the role at index position in the List.
	 
	public Role getRole(int index){
		return this.directoryObjectList.get(index);
	}
	
	
	// @param index The index of the requested role.
	// @return Returns the DisplayName of the role at index position in the List.
	 
	public String getRoleDisplayName(int index){
		return this.directoryObjectList.get(index).getDisplayName();
	}

	
	// @param index The index of the requested role.
	// @return Returns the ObjectId of the role at index position in the List.
	 	
	public String getRoleObjectId(int index){
		return this.directoryObjectList.get(index).getObjectId();
	}
	
	
	// @param index The index of the requested role.
	// @return Returns the AccountEnabled of the role at index position in the List.
	 
	public String getRoleDisabled(int index){
		return this.directoryObjectList.get(index).getRoleDisabled();
	}
}

