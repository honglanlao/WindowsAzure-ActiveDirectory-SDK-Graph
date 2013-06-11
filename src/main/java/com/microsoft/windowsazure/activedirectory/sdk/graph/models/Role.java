package com.microsoft.windowsazure.activedirectory.sdk.graph.models;

import org.json.JSONObject;

import com.microsoft.windowsazure.activedirectory.sdk.graph.metadata.DirectoryObject;

/**
 * 
 * @author Azure Active Directory Contributor
 * This class represents a Role Object of the WAAD top level entity Role. Also, this class gives a publicly available
 * access mechanism to access each individual member variables such as Object Id, DisplayName etc of the particular Role
 * an instance of this class is associated with.
 *
 */

public class Role extends DirectoryObject{
	
	// The private member variables of this Role.
	private String objectId;
	private String description;
	private String displayName;
	private String builtin;
	private String roleDisabled;
	private String objectType;
	
	public Role(){
		super();
	}
	
	
	/**
	 * @return The objectId of this Role.
	 */
	public String getObjectId() {
		return objectId;
	}
	
	/**
	 * @param objectId The objectId to set to this Role.
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return The description of this Role.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description The description to set to this Role.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return The displayName of this Role.
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @param displayName The displayName to set to this Role.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return The builtin attribute of this Role.
	 */
	public String getBuiltin() {
		return this.builtin;
	}
	
	/**
	 * @param bIn The builtin to set to this Role.
	 */
	public void setBuiltin(String bIn) {
		this.builtin = bIn;
	}
	
	/**
	 * @return The roleDisabled attribute of this Role.
	 */
	public String getRoleDisabled() {
		return roleDisabled;
	}
	
	/**
	 * @param roleDisabled The roleDisabled attribute to set to this Role.
	 */
	public void setRoleDisabled(String roleDisabled) {
		this.roleDisabled = roleDisabled;
	}


	/* (non-Javadoc)
	 * @see com.microsoft.windowsazure.activedirectory.sdk.graph.models.Entity#getObjectType()
	 */
	@Override
	public String getObjectType() {
		// TODO Auto-generated method stub
		return this.objectType;
	}


	/* (non-Javadoc)
	 * @see com.microsoft.windowsazure.activedirectory.sdk.graph.models.Entity#setObjectType(java.lang.String)
	 */
	@Override
	public void setObjectType(String objectType) {
		this.objectType = objectType;	
	}
	
	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}
}
