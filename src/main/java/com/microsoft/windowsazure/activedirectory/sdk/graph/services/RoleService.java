package com.microsoft.windowsazure.activedirectory.sdk.graph.services;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.microsoft.azure.activedirectory.sampleapp.config.SampleConfig;
import com.microsoft.windowsazure.activedirectory.sdk.graph.http.RestClient;

/**
 * This class provides API for roles related.
 * @author Azure Active Directory Contributor
 *
 */
public class RoleService {
	
	public static RestClient restClient = new RestClient(SampleConfig.PROTOCOL_NAME, 
														 SampleConfig.getRestServiceHost(),
														 SampleConfig.getTenantContextId());
	
	private static Logger logger;
	static{ 
		logger = Logger.getLogger(RoleService.class);
	//	PropertyConfigurator.configure("log4j.properties");
	}

}
