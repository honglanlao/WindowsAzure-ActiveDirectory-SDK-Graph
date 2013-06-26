//-----------------------------------------------------------------------
// <copyright file="TenantConfiguration.java" company="Microsoft">
//     Copyright (c) Microsoft Corporation.  All rights reserved.
//
// 
//    Copyright 2012 Microsoft Corporation
//    All rights reserved.
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//      http://www.apache.org/licenses/LICENSE-2.0
//
// THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
// EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION ANY IMPLIED WARRANTIES OR 
// CONDITIONS OF TITLE, FITNESS FOR A PARTICULAR PURPOSE, MERCHANTABLITY OR NON-INFRINGEMENT.
//
// See the Apache Version 2.0 License for specific language governing 
// permissions and limitations under the License.
// </copyright>
//
// <summary>
//     
//
// </summary>
//----------------------------------------------------------------------------------------------

package com.microsoft.windowsazure.activedirectory.sdk.graph.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SdkException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.token.TokenGenerator;
import com.microsoft.windowsazure.activedirectory.sdk.graph.config.SdkConfig;

public class TenantConfiguration {
	private static TenantConfiguration instance = null;
	private static Properties properties;
	private static String accessToken = null;

	public static TenantConfiguration getInstance(String configFile) {
		if (instance == null) {
			synchronized (TenantConfiguration.class) {
				instance = load(configFile);
			}
		}
	
		return instance;
	}

	private TenantConfiguration (){
		getAccessToken();
	}
	private static TenantConfiguration load(String configFile) {
		Properties props = new Properties();

		try {
			InputStream is = TenantConfiguration.class.getResourceAsStream(configFile);
			System.out.println("is ->" + is);
			props.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Configuration could not be loaded", e);
		} catch (Exception e2){
			e2.printStackTrace();
		}

		return new TenantConfiguration(props);
	}

	public String getAccessToken() {
		if (accessToken == null) {

			String token = "";
			try {
				token = TokenGenerator.GetTokenFromUrl(SdkConfig.acsUrl,
						this.getTenantContextId(),
						this.getAppPrincipalId(), 
						SdkConfig.PROTOCOL_NAME + "://" + SdkConfig.protectedResourceHostName,
						this.getPassword());
			} catch (SdkException e) {
				e.getCause().printStackTrace();
				System.exit(1);
			}
			this.setAccessToken(token);
		}
		return accessToken;
	}

	public void setAccessToken(String token) {
		TenantConfiguration.accessToken = token;
	}

	private TenantConfiguration(Properties properties) {
		TenantConfiguration.properties = properties;
	}

	public String getTenantContextId() {
		return TenantConfiguration.properties.getProperty("tenant.TenantContextId");
	}

	public String getTenantDomainName() {
		return TenantConfiguration.properties.getProperty("tenant.TenantDomainName");
	}

	public String getSymmetricKey() {
		return TenantConfiguration.properties.getProperty("tenant.SymmetricKey");
	}

	public String getPassword() {
		return TenantConfiguration.properties.getProperty("tenant.Password");
	}

	public String getAcsPrincipalId() {
		return TenantConfiguration.properties.getProperty("tenant.AcsPrincipalId");
	}

	public String getAppPrincipalId() {
		return TenantConfiguration.properties.getProperty("tenant.AppPrincipalId");
	}

	public String getProtectedResourcePrincipalId() {
		return TenantConfiguration.properties
				.getProperty("tenant.ProtectedResourcePrincipalId");
	}
	
	public static String getStsFriendlyName() {
		return TenantConfiguration.properties
				.getProperty("tenant.friendlyname");
	}
	// public String getReply() {
	// return this.properties.getProperty("federation.reply");
	// }
	//
	// public String[] getTrustedIssuers() {
	// String trustedIssuers =
	// this.properties.getProperty("federation.trustedissuers.subjectname");
	//
	// if (trustedIssuers != null)
	// return trustedIssuers.split("\\|");
	// else
	// return null;
	// }
	//
	// public String[] getAudienceUris() {
	// return
	// this.properties.getProperty("federation.audienceuris").split("\\|");
	// }

}
