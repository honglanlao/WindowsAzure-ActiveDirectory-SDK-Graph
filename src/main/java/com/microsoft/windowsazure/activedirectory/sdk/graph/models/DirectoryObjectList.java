/**
 * 
 */
package com.microsoft.windowsazure.activedirectory.sdk.graph.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SdkException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.helper.JSONHelper;
import com.microsoft.windowsazure.activedirectory.sdk.graph.metadata.DirectoryObject;

/**
 * @author Azure Active Directory Contributor
 *
 */
public class DirectoryObjectList<S extends DirectoryObject> {


	// The users hold the information of the users this page carries.
	protected ArrayList<S> directoryObjectList;
	
	// token info for current directoryObjectList and previous directoryObjectList and next directoryObjectList(same size)
	protected String currSkiptoken;
	protected String nextSkiptoken;
	
	protected JSONObject errorObject = null;
	
	/**
	 * The constructor of the UserList class. Initializes the user List.
	 */
	public DirectoryObjectList(){
		directoryObjectList = new ArrayList<S>();
	}
	
	public DirectoryObjectList(JSONArray array){
		directoryObjectList = new ArrayList<S>();
		for(int i = 0; i < array.length(); i++){
			S directoryObject = null;
			try {
				JSONHelper.convertJSONObjectToDirectoryObject(array.optJSONObject(i), directoryObject);
			} catch (SdkException e) {
				e.printStackTrace();
			}
			directoryObjectList.add(directoryObject);
		}
	}
	
	public int getListSize() {
		return this.directoryObjectList.size();
	}

	
	public void add(S o) {
		this.directoryObjectList.add(o);
	}

	
	public S getSingleDirectoryObject(int index) {
		return this.directoryObjectList.get(index);
	}

	
	public String getDirectoryObjectDisplayName(int index) {
		return getSingleDirectoryObject(index).getDisplayName();
	}

	
	public String getDirectoryObjectObjectId(int index) {
		return getSingleDirectoryObject(index).getObjectId();

	}

	
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
	
	public JSONObject getErrorObject() {
		return errorObject;
	}

	
	public void setErrorObject(JSONObject errorObject) {
		this.errorObject = errorObject;
	}
	
	@Override
	public String toString() {
		
		return directoryObjectList.toString();
		
	}

}
