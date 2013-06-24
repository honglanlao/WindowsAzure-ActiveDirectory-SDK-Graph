package com.microsoft.windowsazure.activedirectory.sdk.graph.services;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.microsoft.windowsazure.activedirectory.sdk.graph.config.SdkConfig;
import com.microsoft.windowsazure.activedirectory.sdk.graph.config.TenantConfiguration;
import com.microsoft.windowsazure.activedirectory.sdk.graph.http.RestClient;

/**
 * This class provides API for roles related.
 * @author Azure Active Directory Contributor
 *
 */
public class RoleService {
	
//	private static final TenantConfiguration TENANTCONFIG = TenantConfiguration.getInstance();

	public static RestClient restClient = new RestClient(SdkConfig.PROTOCOL_NAME, 
														 SdkConfig.restServiceHost,
														 TenantConfiguration.getTenantContextId());
	
	private static Logger logger;
	static{ 
		logger = Logger.getLogger(RoleService.class);
	//	PropertyConfigurator.configure("log4j.properties");
	}

}
