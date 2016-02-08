package com.signaturit.api.java_sdk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class Client {
	
	/**
	 * Signaturit's production API URL
	 */
	public static final String PROD_BASE_URL = "https://api.signaturit.com";
	
	/**
	 * Signaturit's sandbox API URL
	 */
	public static final String SANDBOX_BASE_URL = "https://api.sandbox.signaturit.com";
	
	/**
	 * API version
	 */
	public static final String API_VERSION = "/v3/";
	
	/**
	 * access token that provides access to the API methods
	 */
	private String accessToken;
	
	/**
	 * URL
	 */
	private String url; 

	/**
	 * @param accesToken the token that grant access and identify the user
	 * @param production define if use production or sandbox end-point.
	 */
	public Client(String accesToken, boolean production) 
	{
		this.accessToken = "Bearer "+ accesToken;
		this.url = production ? Client.PROD_BASE_URL : Client.SANDBOX_BASE_URL;
		this.url += Client.API_VERSION;
	}
	
	/**
	 * @param accesToken the token that grant access and identify the user
	 */
	public Client(String accesToken) 
	{
		this.accessToken = "Bearer "+ accesToken;
		this.url = Client.SANDBOX_BASE_URL;
		this.url += Client.API_VERSION;
	}

	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public Response countSignatures() throws IOException 
	{
		String route = "signatures/count.json";
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public Response countSignatures(Map<String, Object> parameters) throws IOException 
	{
		String route = RequestHelper.putGetParamsToUrl("signatures/count.json?", parameters);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param signatureId 
	 * @return 
	 * @throws IOException
	 */
	public Response getSignature(String signatureId) throws IOException 
	{
		String route = (
			String.format("signatures/%s.json", signatureId)
		);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}

	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public Response getSignatures() throws IOException  
	{
		String route = "signatures.json";
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param limit
	 * @return
	 * @throws IOException
	 */
	public Response getSignatures(int limit) throws IOException 
	{
		String route = String.format("signatures.json?limit=%d", limit);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param limit
	 * @param offset
	 * @return
	 * @throws IOException 
	 */
	public Response getSignatures(int limit, int offset) throws IOException 
	{
		String route = String.format("signatures.json?limit=%doffset=%d", limit, offset);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws IOException 
	 */
	public Response getSignatures(Map<String, Object> parameters) throws IOException  
	{
		String route = "signatures.json?";
		
		route = RequestHelper.putGetParamsToUrl(route, parameters);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param limit
	 * @param offset
	 * @param parameters
	 * @return
	 * @throws IOException 
	 */
	public Response getSignatures(int limit, int offset, Map<String, Object> parameters) throws IOException 
	{
		String route = String.format("signatures.json?limit=%doffset=%d", limit, offset);
		
		route = RequestHelper.putGetParamsToUrl(route, parameters);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param signatureId
	 * @param documentId
	 * @return 
	 * @throws IOException 
	 */
	public Response downloadAuditTrail(String signatureId, String documentId) throws IOException 
	{
		String route = String.format(
			"signatures/%s/documents/%s/download/audit_trail", signatureId, documentId
		);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param signatureId
	 * @param documentId
	 * @return 
	 * @throws IOException 
	 */
	public Response downloadSignedDocument(String signatureId, String documentId) throws IOException 
	{
		String route = String.format(
			"signatures/%s/documents/%s/download/signed", signatureId, documentId
		);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param files
	 * @param recipients
	 * @return
	 * @throws IOException 
	 */
	public Response createSignature(
		ArrayList<File> files,
		ArrayList<HashMap<String, Object>> recipients
	) throws IOException
	{
		HashMap<String, Object> parameters= new HashMap<String, Object>();
		
		return this.createSignature(files, recipients, parameters);
	}
	
	/**
	 * 
	 * @param files
	 * @param recipients
	 * @param parameters
	 * @return
	 * @throws IOException 
	 */
	public Response createSignature(
		ArrayList<File> files,
		ArrayList<HashMap<String, Object>> recipients,
		Map<String, Object> parameters
	) throws IOException
	{
		String route = "signatures.json";
		
		parameters.put("recipients", recipients);
		
		return RequestHelper.requestPost(this.url + route, this.accessToken, parameters, files);
	}
	
	/**
	 * 
	 * @param signatureId
	 * @return
	 * @throws IOException 
	 */
	public Response cancelSignature(String signatureId) throws IOException
	{
		String route = String.format(
			"signatures/%s/cancel.json", signatureId
		);
		
		return RequestHelper.requestPatch(this.url + route, this.accessToken, null);
	}
	
	/**
	 * 
	 * @param signatureId
	 * @return
	 * @throws IOException 
	 */
	public Response sendSignatureReminder(String signatureId) throws IOException
	{
		String route = String.format(
			"signatures/%s/reminder.json", signatureId
		);
		return RequestHelper.requestPost(this.url + route, this.accessToken, null, null);
	}
	
	/**
	 * 
	 * @param brandingId
	 * @return
	 * @throws IOException
	 */
	public Response getBranding(String brandingId) throws IOException
	{
		String route = String.format(
			"brandings/%s.json", brandingId
		);
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public Response getBrandings() throws IOException
	{
		String route = "brandings.json";
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws IOException 
	 */
	public Response createBranding(HashMap<String, Object> parameters) throws IOException
	{
		String route = "brandings.json";
		return RequestHelper.requestPost(this.url + route, this.accessToken, parameters, null);
	}
	
	/**
	 * 
	 * @param brandingId
	 * @param parameters
	 * @return
	 * @throws IOException 
	 */
	public Response updateBranding(String brandingId, HashMap<String, Object> parameters) 
		throws IOException
	{
		String route = String.format(
			"brandings/%s.json", brandingId
		);
		return RequestHelper.requestPatch(this.url + route, this.accessToken, parameters);
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public Response getTemplates() throws IOException
	{
		String route = String.format(
			"templates.json"
		);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 *
	 * @return
	 * @throws IOException 
	 */
	public Response getEmails() 
		throws IOException 
	{
		String route = "emails.json";
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param limit
	 * @return
	 * @throws IOException 
	 */
	public Response getEmails(int limit) 
		throws IOException 
	{
		String route = String.format("emails.json?limit=%d", limit);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param limit
	 * @param offset
	 * @return
	 * @throws IOException 
	 */
	public Response getEmails(int limit, int offset) throws IOException 
	{
		String route = String.format("emails.json?limit=%doffset=%d", limit, offset);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws IOException 
	 */
	public Response getEmails(Map<String, Object> parameters) throws IOException 
	{
		String route = "emails.json?";
		
		route = RequestHelper.putGetParamsToUrl(route, parameters);
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param limit
	 * @param offset
	 * @param parameters
	 * @return
	 * @throws IOException 
	 */
	public Response getEmails(int limit, int offset, Map<String, Object> parameters) throws IOException 
	{
		String route = String.format("emails.json?limit=%doffset=%d", limit, offset);
		
		route = RequestHelper.putGetParamsToUrl(route, parameters);
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public Response countEmails() throws IOException 
	{
		String route = "emails/count.json";
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public Response countEmails(Map<String, Object> parameters) throws IOException 
	{
		String route = "emails/count.json?";
		route = RequestHelper.putGetParamsToUrl(route, parameters);
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param emailId
	 * @return
	 * @throws IOException 
	 */
	public Response getEmail(String emailId) throws IOException
	{
		String route = String.format(
			"emails/%s.json", emailId
		);
		
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
	
	/**
	 * 
	 * @param files
	 * @param recipients
	 * @return
	 * @throws IOException 
	 */
	public Response createEmail(
		ArrayList<File> files, 
		ArrayList<HashMap<String, Object>> recipients
	) throws IOException
	{
		String route = "emails.json";
		
		HashMap<String, Object>	parameters = new HashMap<String, Object>();
		
		parameters.put("recipients", recipients);
		
		return RequestHelper.requestPost(this.url + route, this.accessToken, parameters, files);
	}
	
	/**
	 * 
	 * @param files
	 * @param recipients
	 * @param subject
	 * @param body
	 * @return
	 * @throws IOException 
	 */
	public Response createEmail(
		ArrayList<File> files, 
		ArrayList<HashMap<String, Object>> recipients, 
		String subject, String body
	) throws IOException
	{	
		HashMap<String, Object>	parameters = new HashMap<String, Object>();
		
		return this.createEmail(files, recipients, subject, body, parameters);
	}
	
	/**
	 * 
	 * @param files
	 * @param recipients
	 * @param parameters
	 * @return
	 * @throws IOException 
	 */
	public Response createEmail(
		ArrayList<File> files, 
		ArrayList<HashMap<String, Object>> recipients, 
		HashMap<String, Object> parameters
	) throws IOException
	{
		String route = "emails.json";
		
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		
		parameters.put("recipients", recipients);
		
		return RequestHelper.requestPost(this.url + route, this.accessToken,  parameters, files);
	}
	
	/**
	 * 
	 * @param files
	 * @param recipients
	 * @param subject
	 * @param body
	 * @param parameters
	 * @return
	 * @throws IOException 
	 */
	public Response createEmail(
		ArrayList<File> files, 
		ArrayList<HashMap<String, Object>> recipients, 
		String subject, 
		String body, 
		HashMap<String, Object> parameters
	) throws IOException
	{
		String route = "emails.json";
		
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		
		parameters.put("body", body);
		parameters.put("subject", subject);
		parameters.put("recipients", recipients);
		
		return RequestHelper.requestPost(this.url + route, this.accessToken, parameters, files);
	}
	
	/**
	 * 
	 * @param emailId
	 * @param certificateId
	 * @return 
	 * @throws IOException 
	 */
	public Response downloadEmailAuditTrail(String emailId, String certificateId) 
		throws IOException 
	{
		String route = String.format(
			"emails/%s/certificates/%s/download/audit_trail", emailId, certificateId
		);
		return RequestHelper.requestGet(this.url + route, this.accessToken);
	}
}
