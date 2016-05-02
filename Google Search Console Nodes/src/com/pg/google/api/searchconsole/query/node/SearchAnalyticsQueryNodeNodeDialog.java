package com.pg.google.api.searchconsole.query.node;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import org.apache.commons.lang3.ArrayUtils;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.port.PortObjectSpec;

import com.pg.knime.node.InvalidArgumentException;
import com.pg.knime.node.StandardNodeDialogPane;

/**
 * <code>NodeDialog</code> for the "SearchAnalyticsQueryNode" Node.
 * 
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author P&G, eBusiness
 */
public class SearchAnalyticsQueryNodeNodeDialog extends StandardNodeDialogPane {

	private static final NodeLogger LOGGER = NodeLogger.getLogger(SearchQueryConfiguration.class);
	private SearchQueryConfiguration configuration = new SearchQueryConfiguration();
	
	private JTextField txtSite = new JTextField();
	
	private JList<String> lstDimensions = new JList<String>();
	private JComboBox<String> cbxSearchType = new JComboBox<String>();
	private JSpinner spnFromDate = new JSpinner(new SpinnerDateModel());
	private JSpinner spnToDate = new JSpinner(new SpinnerDateModel());
	
	private JSpinner spnRowLimit = new JSpinner(new SpinnerNumberModel(1000, 0, 5000, 1));
	
    /**
     * New pane for configuring the SearchAnalyticsQueryNode node.
     */
    protected SearchAnalyticsQueryNodeNodeDialog() {

    	lstDimensions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	
    	spnFromDate.setEditor(new JSpinner.DateEditor(spnFromDate, "yyyy-MM-dd"));
    	spnToDate.setEditor(new JSpinner.DateEditor(spnToDate, "yyyy-MM-dd"));
    	
    	addTab("Settings",
    		buildStandardPanel(
    			new PanelBuilder()
    				.add("Site", 		txtSite )
    				.add("Search Type", cbxSearchType )
    				.add("Dimensions", 	lstDimensions )
    				.add("Start Date", 	spnFromDate )
    				.add("End Date", 	spnToDate)
    				.add("Max Rows", 	spnRowLimit)
    				
    				.build()
    		)
    	);
    	
    }

    @Override
    protected void loadSettingsFrom(NodeSettingsRO settings, PortObjectSpec[] specs) throws NotConfigurableException {
    
    	configuration.load(settings);
    	
    	// Build Dimension List:
    	lstDimensions.setListData(SearchQueryConfiguration.DIMENSIONS);
    	
    	// Select Saved Dimensions:
    	List<String> selectedDimensions = configuration.getDimensions();
    	List<Integer> selectedDimensionIndexes = new ArrayList<Integer>();
    	for ( String dimension : selectedDimensions ) {
    		Integer index = configuration.getDimensionIndex(dimension);
    		if ( index != -1 ) selectedDimensionIndexes.add(index);
    	}
    	lstDimensions.setSelectedIndices(ArrayUtils.toPrimitive(selectedDimensionIndexes.toArray(new Integer[]{})));
    	
    	spnRowLimit.setValue(configuration.getRowLimit());
    	
    	try {
    		spnFromDate.setValue(SearchQueryConfiguration.SDF.parse(configuration.getStartDate()));
    	} catch ( ParseException pexc ) {
    		LOGGER.error(pexc.getMessage());
    	}
    	
    	try {
    		spnToDate.setValue(SearchQueryConfiguration.SDF.parse(configuration.getEndDate()));
    	} catch ( ParseException pexc ) {
    		LOGGER.error(pexc.getMessage());
    	}
    	
    	cbxSearchType.removeAllItems();
    	for ( String type : SearchQueryConfiguration.SEARCH_TYPES ) {
    		cbxSearchType.addItem(type);
    	}
    	cbxSearchType.setSelectedItem(configuration.getSearchType());
    	
    }
    
	@Override
	protected void saveSettingsTo(NodeSettingsWO settings) throws InvalidSettingsException {
		
		configuration.setSite(txtSite.getText());
		
		configuration.setDimensions(lstDimensions.getSelectedValuesList());
		
		configuration.setStartDate(SearchQueryConfiguration.SDF.format(spnFromDate.getValue()));
		configuration.setEndDate(SearchQueryConfiguration.SDF.format(spnToDate.getValue()));
		
		try { configuration.setRowLimit((Integer)spnRowLimit.getValue()); } catch ( InvalidArgumentException exc ) { LOGGER.error(exc.getMessage()); }
		
		configuration.save(settings);
		
	}
}

