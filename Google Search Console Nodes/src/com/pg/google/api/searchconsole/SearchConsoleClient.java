package com.pg.google.api.searchconsole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.webmasters.Webmasters;
import com.google.api.services.webmasters.Webmasters.Searchanalytics.Query;
import com.google.api.services.webmasters.model.ApiDataRow;
import com.google.api.services.webmasters.model.SearchAnalyticsQueryRequest;
import com.google.api.services.webmasters.model.SearchAnalyticsQueryResponse;
import com.google.api.services.webmasters.model.SitesListResponse;
import com.google.api.services.webmasters.model.WmxSite;

public class SearchConsoleClient {

	Webmasters api;
	
	public SearchConsoleClient( GoogleCredential credential) {
		api = new Webmasters.Builder(new NetHttpTransport(), new JacksonFactory(), credential ).setApplicationName("KNIME Search Console").build();
	
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
	
	public List<ApiDataRow> getSearchAnalytics( String site, List<String> dimensions, String startDate, String endDate, Integer rowLimit) throws IOException {
		
		SearchAnalyticsQueryRequest request = new SearchAnalyticsQueryRequest();
		request.setDimensions(dimensions);
		request.setRowLimit(rowLimit);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		
		Query query = api.searchanalytics().query(site, request);
		SearchAnalyticsQueryResponse response = query.execute();
		return response.getRows();	
		
	}
	 
	
}
