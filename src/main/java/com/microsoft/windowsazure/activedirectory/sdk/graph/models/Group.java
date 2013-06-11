package com.microsoft.windowsazure.activedirectory.sdk.graph.models;

import org.json.JSONObject;

import com.microsoft.windowsazure.activedirectory.sdk.graph.metadata.DirectoryObject;


/**
 * This class represents a Group Object of the WAAD top level entity Group. Also this class gives a publicly available
 * access mechanism to access each individual member variables such as Object Id, DisplayName etc.
 * @author Azure Active Directory Contributor
 */

public class Group extends DirectoryObject{
		
	// The following are the private member variables that contains the individual members of this group.
	protected String objectId;
	protected String description;
	protected String dirSyncEnabled;
	protected String displayName;
	protected String lastDirSyncTime;
	protected String mail;
	protected String mailNickname;
	protected String mailEnabled;
	protected String securityEnabled;
	protected String objectType;
	
	/**
	 * @param The ObjectId of this Group.
	 * @param The DisplayName of this Group.
	 * 
	 */
	public Group(String displayName, String objectId){
		setDisplayName(displayName);
		setObjectId(objectId);
	}
	
	public Group(String displayName, String description, String dirSyncEnabled, String mailEnabled, String objectId) {
		setDisplayName(displayName);
		setDescription(description);
		setDirSyncEnabled(dirSyncEnabled);
		setMailEnabled(mailEnabled);
		setObjectId(objectId);	
	}

	public Group() {
		super();
	}

	/**
	 * @return The ObjectId of this Group.
	 */
	public String getObjectId() {
		return this.objectId;
	}
	/**
	 * @param ObjectId The ObjectId to set.
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return The objectType of this Group.
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 * @param objectType The objectType to set to this Group object.
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	/**
	 * @return The dirSyncEnabled attribute of this Group.
	 */
	public String getDirSyncEnabled() {
		return this.dirSyncEnabled;
	}
	/**
	 * @param dirSyncEnabled The dirSyncEnabled to set.
	 */
	public void setDirSyncEnabled(String dirSyncEnabled) {
		this.dirSyncEnabled = dirSyncEnabled;
	}
	/**
	 * @return The description of the Group.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return The displayName of this Group.
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName The displayName to set to this Group.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return The lastDirSyncTime of this Group.
	 */
	public String getLastDirSyncTime() {
		return lastDirSyncTime;
	}
	/**
	 * @param lastDirSyncTime The lastDirSyncTime to set to this Group.
	 */
	public void setLastDirSyncTime(String lastDirSyncTime) {
		this.lastDirSyncTime = lastDirSyncTime;
	}
	/**
	 * @return The mail attribute of this Group.
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail The mail to set to this Group.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return The MailNickname attribute of this Group.
	 */
	public String getMailNickname() {
		return mailNickname;
	}
	/**
	 * @param The MailNickname attribute to set to this Group.
	 */
	public void setMailNickname(String mailNickname) {
		this.mailNickname = mailNickname;
	}
	/**
	 * @return The mailEnabled attribute of this Group.
	 */
	public String getMailEnabled() {
		return mailEnabled;
	}
	/**
	 * @param mailEnabled The mailEnabled to set to this Group.
	 */
	public void setMailEnabled(String mailEnabled) {
		this.mailEnabled = mailEnabled;
	}
	/**
	 * @return The securityEnabled attribute of this Group.
	 */
	public String getSecurityEnabled() {
		return securityEnabled;
	}
	/**
	 * @param securityEnabled The securityEnabled to set to this Group.
	 */
	public void setSecurityEnabled(String securityEnabled) {
		this.securityEnabled = securityEnabled;
	}
	
	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}
}
