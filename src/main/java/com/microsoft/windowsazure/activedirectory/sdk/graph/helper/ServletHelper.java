package com.microsoft.windowsazure.activedirectory.sdk.graph.helper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.microsoft.windowsazure.activedirectory.sdk.graph.config.SdkConfig;
import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SdkException;
import com.microsoft.windowsazure.activedirectory.sdk.graph.token.TokenGenerator;

 /**
  * This is Helper class for all servlet.
  * @author Azure Active Directory Contributor
  *
  */
public class ServletHelper {

	public ServletHelper() {
		super();
	}

	public static void loadConfig(ServletConfig config) throws ServletException {
	
		// Load the initial parameters from the xml configuration file to the appropriate fields in the SdkConfig class.
//
//		SdkConfig.setApiVersion(config.getServletContext().getInitParameter("ApiVersion"));
//		SdkConfig.setAcsPrincipalId(config.getServletContext().getInitParameter("AcsPrincipalId"));
//		SdkConfig.setAppPrincipalId(config.getServletContext().getInitParameter("AppPrincipalId"));
//		SdkConfig.setProtectedResourceHostName(config.getServletContext().getInitParameter("ProtectedResourceHostName"));
//		SdkConfig.setRestServiceHost(config.getServletContext().getInitParameter("RestServiceHost"));
//		SdkConfig.setSymmetricKey(config.getServletContext().getInitParameter("SymmetricKey"));
//		SdkConfig.setTenantDomainName(config.getServletContext().getInitParameter("TenantDomainName"));
//		SdkConfig.setTenantContextId(config.getServletContext().getInitParameter("TenantContextId"));
//		SdkConfig.setStsUrl(config.getServletContext().getInitParameter("StsUrl"));
//		SdkConfig.setProtectedResourcePrincipalId(config.getServletContext().getInitParameter("ProtectedResourcePrincipalId"));
//		SdkConfig.setPassword(config.getServletContext().getInitParameter("Password"));
//		SdkConfig.setAcsUrl(config.getServletContext().getInitParameter("AcsUrl"));

		// If there is no predefined Access Token, generate an access token and set it to the accessToken field in SdkConfig.
//		if (SdkConfig.getAccessToken() != null) return;
///*		try {
//			SdkConfig.setAccessToken(TokenGenerator.generateToken(
//					SdkConfig.getTenantContextId(),
//					SdkConfig.getAppPrincipalId(),
//					SdkConfig.getStsUrl(),
//					SdkConfig.getAcsPrincipalId(),
//					SdkConfig.getSymmetricKey(),
//					SdkConfig.getProtectedResourcePrincipalId(),
//					SdkConfig.getProtectedResourceHostName()));
//		} catch (SdkException e) {
//			e.getCause().printStackTrace();
//			 
//			System.exit(1);
//		}*/
//		
//		String token = "";
//		try {
//			token = TokenGenerator.GetTokenFromUrl(
//												SdkConfig.getAcsUrl(),
//												SdkConfig.getTenantDomainName(),
//												SdkConfig.getAppPrincipalId(),
//												"https://" + SdkConfig.getProtectedResourceHostName(),
//												SdkConfig.getPassword()
//												);
//		} catch (SdkException e) {
//			e.getCause().printStackTrace();	 
//			System.exit(1);
//		}
//		SdkConfig.setAccessToken(token);
	
	}

}
