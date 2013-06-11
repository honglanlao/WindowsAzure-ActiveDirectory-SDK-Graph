package com.microsoft.windowsazure.activedirectory.sdk.graph.models;

import java.util.ArrayList;

import org.json.JSONArray;

import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SampleAppException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.helper.JSONHelper;

/**
 * The class UserList holds the data of a page of groups. It also contains information whether
 * this page any next page or previous page and also the contains the page number member.
 * @author Azure Active Directory Contributor
 */
public class GroupList extends DirectoryObjectList<Group>{

	/**
	 * The constructor of the GroupList class. Initializes the group List.
	 */
	public GroupList(){
		directoryObjectList = new ArrayList<Group>();
	}

	public GroupList(JSONArray array){
		directoryObjectList = new ArrayList<Group>();
		for(int i = 0; i < array.length(); i++){
			Group group = new Group();
			try {
				JSONHelper.convertJSONObjectToDirectoryObject(array.optJSONObject(i), group);
			} catch (SampleAppException e) {
				e.printStackTrace();
			}
			directoryObjectList.add(group);
		}
	}
	
//	/**
//	 * @return The number of groups in the List.
//	 */
//	public int getGroupsSize(){
//		return this.directoryObjectList.size();
//	}

	public String getNextSkipToken() {
		return nextSkiptoken;
	}


	public void setNextSkiptoken(String nextSkipToken) {
		this.nextSkiptoken = nextSkipToken;
	}

	public String getCurrSkipToken() {
		return currSkiptoken;
	}


	public void setCurrSkiptoken(String currSkipToken) {
		this.currSkiptoken = currSkipToken;
	}
		
	/**
	 * Adds a new group to the list.
	 * @param group The Group object
	 */
	public void add(Group group){
		directoryObjectList.add(group);
	}
	
	/**
	 * @param index The index of the requested user.
	 * @return Returns the user at index position in the List.
	 */
	public Group getGroup(int index){
		return this.directoryObjectList.get(index);
	}
	
	/**
	 * @param index The index of the requested group.
	 * @return Returns the DisplayName of the group at index position in the List.
	 */
	public String getGroupDisplayName(int index){
		return this.directoryObjectList.get(index).getDisplayName();
	}

	/**
	 * @param index The index of the requested group.
	 * @return Returns the Description of the group at index position in the List.
	 */
	public String getGroupDescription(int index){
		return this.directoryObjectList.get(index).getDescription();
	}

	/**
	 * @param index The index of the requested group.
	 * @return Returns the ObjectId of the group at index position in the List.
	 */
	public String getGroupObjectId(int index){
		return this.directoryObjectList.get(index).getObjectId();
	}
	
	/**
	 * @param index The index of the requested group.
	 * @return Returns the MailEnabled of the group at index position in the List.
	 */
	public String getGroupMailEnabled(int index){
		return this.directoryObjectList.get(index).getMailEnabled();
	}
	
	/**
	 * @param index The index of the requested group.
	 * @return Returns the DirSyncEnabled of the group at index position in the List.
	 */
	public String getGroupDirSyncEnabled(int index){
		return this.directoryObjectList.get(index).getDirSyncEnabled();
	}
	
	/**
	 * @param index The index of the requested group.
	 * @return Returns the SecurityEnabled of the group at index position in the List.
	 */
	public String getGroupSecurityEnabled(int index){
		return this.directoryObjectList.get(index).getSecurityEnabled();
	}
	
}
