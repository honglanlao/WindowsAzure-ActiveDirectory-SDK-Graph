package com.microsoft.windowsazure.activedirectory.sdk.graph.helper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.microsoft.azure.activedirectory.sampleapp.config.SampleConfig;
import com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions.SampleAppException;
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
	
		// Load the initial parameters from the xml configuration file to the appropriate fields in the SampleConfig class.

		SampleConfig.setApiVersion(config.getServletContext().getInitParameter("ApiVersion"));
		SampleConfig.setAcsPrincipalId(config.getServletContext().getInitParameter("AcsPrincipalId"));
		SampleConfig.setAppPrincipalId(config.getServletContext().getInitParameter("AppPrincipalId"));
		SampleConfig.setProtectedResourceHostName(config.getServletContext().getInitParameter("ProtectedResourceHostName"));
		SampleConfig.setRestServiceHost(config.getServletContext().getInitParameter("RestServiceHost"));
		SampleConfig.setSymmetricKey(config.getServletContext().getInitParameter("SymmetricKey"));
		SampleConfig.setTenantDomainName(config.getServletContext().getInitParameter("TenantDomainName"));
		SampleConfig.setTenantContextId(config.getServletContext().getInitParameter("TenantContextId"));
		SampleConfig.setStsUrl(config.getServletContext().getInitParameter("StsUrl"));
		SampleConfig.setProtectedResourcePrincipalId(config.getServletContext().getInitParameter("ProtectedResourcePrincipalId"));
		SampleConfig.setPassword(config.getServletContext().getInitParameter("Password"));
		SampleConfig.setAcsUrl(config.getServletContext().getInitParameter("AcsUrl"));

		// If there is no predefined Access Token, generate an access token and set it to the accessToken field in SampleConfig.
		if (SampleConfig.getAccessToken() != null) return;
/*		try {
			SampleConfig.setAccessToken(TokenGenerator.generateToken(
					SampleConfig.getTenantContextId(),
					SampleConfig.getAppPrincipalId(),
					SampleConfig.getStsUrl(),
					SampleConfig.getAcsPrincipalId(),
					SampleConfig.getSymmetricKey(),
					SampleConfig.getProtectedResourcePrincipalId(),
					SampleConfig.getProtectedResourceHostName()));
		} catch (SampleAppException e) {
			e.getCause().printStackTrace();
			 
			System.exit(1);
		}*/
		
		String token = "";
		try {
			token = TokenGenerator.GetTokenFromUrl(
												SampleConfig.getAcsUrl(),
												SampleConfig.getTenantDomainName(),
												SampleConfig.getAppPrincipalId(),
												"https://" + SampleConfig.getProtectedResourceHostName(),
												SampleConfig.getPassword()
												);
		} catch (SampleAppException e) {
			e.getCause().printStackTrace();	 
			System.exit(1);
		}
		SampleConfig.setAccessToken(token);
	
	}

}
