package com.microsoft.windowsazure.activedirectory.sdk.graph.exceptions;

public class SampleAppException extends Exception {
	 
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;

   /**	
	 * Two arguments constructor for the SampleAppException. 
	 * @param code
	 * @param message
	 * @param cause The original exception that caused this exception.
	 */
	public SampleAppException(String code, String message, Throwable cause){
		super(cause);
		this.code = code;
		this.message = message;
	}
	
  /**	
	* @return the code
	*/
	public String getCode() {
		return code;
	}

  /**	
	* @param code the code to set
	*/ 
	public void setCode(String code) {
		this.code = code;
	}

	
  /**	
	* @return the message
	*/ 
	public String getMessage() {
		return message;
	}

	
  /**	
	* @param message the message to set
	*/ 
	public void setMessage(String message) {
		this.message = message;
	}

}
