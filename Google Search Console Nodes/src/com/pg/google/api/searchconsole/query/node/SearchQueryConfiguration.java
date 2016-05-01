package com.pg.google.api.searchconsole.query.node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

import com.pg.knime.node.InvalidArgumentException;

public class SearchQueryConfiguration {

	private String site;
	private List<String> dimensions = new ArrayList<String>();
	private String startDate;
	private String endDate;
	private Integer rowLimit = 1000;
	private String searchType;
	private List<String> fields = new ArrayList<String>();
	
	private static Integer MAX_ROW_LIMIT = 5000;
	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	// TODO: Add support for dimensional filtering
	
	private static String CFG_DIMENSIONS = "cfg.search.dimensions";
	private static String CFG_STARTDATE = "cfg.search.start-date";
	private static String CFG_ENDDATE = "cfg.search.end-date";
	private static String CFG_ROWLIMIT = "cfg.search.row-limit";
	private static String CFG_SEARCHTYPE = "cfg.search.search-type";
	private static String CFG_FIELDS = "cfg.search.fields";
	private static String CFG_SITE = "cfg.search.site";
	
	public static String[] DIMENSIONS = {"query", "date", "page", "country", "device", "searchAppearance" };
	public static String[] FIELDS = /*rows*/ {"clicks", "ctr", "impressions", "keys", "position"};
	public static String[] AGGREGATION_TYPES = {"auto", "byPage", "byProperty"};
	public static String[] SEARCH_TYPES = {"web", "image", "video" };
	
	
	
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public Integer getFieldIndex ( String field ) {
		return GET_INDEX ( field, FIELDS );
	}
	public List<String> getDimensions() {
		return dimensions;
	}
	public void setDimensions(List<String> dimensions) {
		this.dimensions = dimensions;
	}
	
	public Integer getDimensionIndex ( String dimension ) {
		return GET_INDEX ( dimension, DIMENSIONS );
	}
	
	public Integer GET_INDEX ( String item, String[] items ) {		
		
		if ( item == null || items == null || items.length == 0 ) return -1;
		
		int i = 0;
		for ( String it : items ) {
			if ( it.equals( items ) ) return i++;
		}
		
		return -1;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getRowLimit() {
		return Math.min(rowLimit, MAX_ROW_LIMIT);
	}
	public void setRowLimit(Integer rowLimit) throws InvalidArgumentException {
		if ( rowLimit > MAX_ROW_LIMIT ) {
			throw new InvalidArgumentException("Max row limit is " + MAX_ROW_LIMIT);
		}
		this.rowLimit = rowLimit;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	
	
	public void save ( NodeSettingsWO settings ) {
		
		if ( settings == null ) return;
		
		settings.addStringArray(CFG_DIMENSIONS, getDimensions().toArray(new String[]{}));
		settings.addString(CFG_STARTDATE, getStartDate());
		settings.addString(CFG_ENDDATE, getEndDate());
		settings.addInt(CFG_ROWLIMIT, getRowLimit());
		settings.addString(CFG_SEARCHTYPE, getSearchType());
		settings.addStringArray(CFG_FIELDS, getFields().toArray(new String[] {}));
		settings.addString(CFG_SITE, getSite());
		
	}
	
	public void load ( NodeSettingsRO settings ) {
		
		if ( settings == null ) return;
		
		setFields(Arrays.asList(settings.getStringArray(CFG_FIELDS, new String[]{FIELDS[0]})));
		setDimensions(Arrays.asList(settings.getStringArray(CFG_DIMENSIONS, new String[]{DIMENSIONS[0]})));
		setStartDate(settings.getString(CFG_STARTDATE, SDF.format(new Date() ) ) );
		setEndDate(settings.getString(CFG_ENDDATE, SDF.format(new Date())));
		try {
			setRowLimit(MAX_ROW_LIMIT); // Set to a value in-case settings throws exception in next line
			setRowLimit(settings.getInt(CFG_ROWLIMIT, 1000)); }
		catch ( InvalidArgumentException exc ) {
			exc.printStackTrace();
		}
		setSearchType(settings.getString(CFG_SEARCHTYPE, SEARCH_TYPES[0]));
		
		setSite(settings.getString(CFG_SITE, ""));
	}
	
	
}
