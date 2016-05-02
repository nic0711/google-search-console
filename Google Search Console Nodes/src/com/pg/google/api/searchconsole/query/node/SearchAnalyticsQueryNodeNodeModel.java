package com.pg.google.api.searchconsole.query.node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.port.PortObject;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.port.PortType;

import com.google.api.services.webmasters.model.ApiDataRow;
import com.pg.google.api.connector.data.GoogleApiConnectionPortObject;
import com.pg.google.api.searchconsole.SearchConsoleClient;

/**
 * This is the model implementation of SearchAnalyticsQueryNode.
 * 
 *
 * @author P&G, eBusiness
 */
public class SearchAnalyticsQueryNodeNodeModel extends NodeModel {
    
	private static NodeLogger LOGGER = NodeLogger.getLogger(SearchAnalyticsQueryNodeNodeModel.class);
	
	private SearchQueryConfiguration configuration = new SearchQueryConfiguration();
	
    /**
     * Constructor for the node model.
     */
    protected SearchAnalyticsQueryNodeNodeModel() {
    	super(new PortType[]{GoogleApiConnectionPortObject.TYPE},
                new PortType[]{BufferedDataTable.TYPE});
    }

    @Override
    protected PortObject[] execute(PortObject[] inObjects, ExecutionContext exec) throws Exception {

    	GoogleApiConnectionPortObject apiConnection = (GoogleApiConnectionPortObject)inObjects[0];
    	SearchConsoleClient client = new SearchConsoleClient(apiConnection.getGoogleApiConnection().getCredential());
    	
    	List<ApiDataRow> rows = new ArrayList<ApiDataRow>();
    	try {
	    	rows = client.getSearchAnalytics(
	    			configuration.getSite(), 
	    			configuration.getDimensions(), 
	    			configuration.getStartDate(),
	    			configuration.getEndDate(), 
	    			configuration.getRowLimit()
	    	);
    	} catch (Exception exc) {
    		LOGGER.error(exc.getMessage());
    	}
	    	
    	DataTableSpec outSpec = createSpec(configuration.getDimensions());
        BufferedDataContainer outContainer = exec.createDataContainer(outSpec);
        
        int rowCnt = 0;
        for ( ApiDataRow row : rows ) {
        	
        	List<DataCell> cells = new ArrayList<DataCell>(outSpec.getNumColumns());

        	cells.add(new StringCell(configuration.getSite()));
        	cells.add(new StringCell(configuration.getStartDate()));
        	cells.add(new StringCell(configuration.getEndDate()));
        	
        	for (String key : row.getKeys() ) {
        		cells.add(new StringCell(key));
        	}
        	
        	
        	cells.add(new DoubleCell(row.getClicks()));
        	cells.add(new DoubleCell(row.getCtr()));
        	cells.add(new DoubleCell(row.getImpressions()));
        	cells.add(new DoubleCell(row.getPosition()));
        	
        	
        	outContainer.addRowToTable(new DefaultRow("Row" + rowCnt++, cells));
        }
        
        outContainer.close();

    	
    	
    	
    	return new PortObject[] { outContainer.getTable() };
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void reset() {
        
    }

    @Override
    protected PortObjectSpec[] configure(PortObjectSpec[] inSpecs) throws InvalidSettingsException {
    	return new PortObjectSpec[] { createSpec(configuration.getDimensions()) };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
         
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    	
    	configuration.load(settings);
    	
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
        // TODO: generated method stub
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
        // TODO: generated method stub
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
        // TODO: generated method stub
    }
    
    private DataTableSpec createSpec(List<String> dimensions) {
    	
    	List<DataColumnSpec> colSpecs = new ArrayList<DataColumnSpec>();
    	colSpecs.add(new DataColumnSpecCreator("Site", StringCell.TYPE).createSpec());
    	
    	colSpecs.add(new DataColumnSpecCreator("Start Date", StringCell.TYPE).createSpec());
    	colSpecs.add(new DataColumnSpecCreator("End Date", StringCell.TYPE).createSpec());
    	
    	for ( String dimension : dimensions ) {
    		colSpecs.add(new DataColumnSpecCreator(dimension, StringCell.TYPE).createSpec());
    	}
    	
    	for ( String field : SearchQueryConfiguration.FIELDS ) {
    		if ( "keys".equals(field) ) continue;
    		colSpecs.add(new DataColumnSpecCreator(field, DoubleCell.TYPE).createSpec());
    	}
    	
    	return new DataTableSpec(colSpecs.toArray(new DataColumnSpec[colSpecs.size()]));
    	
    }

}

