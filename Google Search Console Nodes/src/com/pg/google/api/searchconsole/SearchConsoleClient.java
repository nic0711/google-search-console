package com.pg.google.api.searchconsole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.webmasters.Webmasters;
import com.google.api.services.webmasters.model.SitesListResponse;
import com.google.api.services.webmasters.model.WmxSite;

public class SearchConsoleClient {

	Webmasters api;
	
	public SearchConsoleClient( GoogleCredential credential) {
		api = new Webmasters.Builder(new NetHttpTransport(), new JacksonFactory(), credential ).build();
	
	}
	
	public List<WmxSite> getSiteList() {
		
		try {
			Webmasters.Sites.List listRequest = api.sites().list();
			SitesListResponse siteList = listRequest.execute();
			
			return siteList.getSiteEntry();
			
		} catch (IOException exc ) {
			exc.printStackTrace();
		}
		
		return new ArrayList<WmxSite>();
		
	}
	 
	
}
