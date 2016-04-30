package com.pg.google.api.searchconsole.listsites;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.port.PortObject;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.port.PortType;

import com.google.api.services.webmasters.model.WmxSite;
import com.pg.google.api.connector.data.GoogleApiConnectionPortObject;
import com.pg.google.api.searchconsole.SearchConsoleClient;

/**
 * This is the model implementation of ListSitesNode.
 * 
 *
 * @author P&G, eBusiness
 */
public class ListSitesNodeNodeModel extends NodeModel {
    
    /**
     * Constructor for the node model.
     */
    protected ListSitesNodeNodeModel() {
    
    	super(new PortType[]{GoogleApiConnectionPortObject.TYPE},
                new PortType[]{BufferedDataTable.TYPE});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PortObject[] execute(PortObject[] inObjects, ExecutionContext exec)
    		throws Exception {

    	GoogleApiConnectionPortObject apiConnection = (GoogleApiConnectionPortObject)inObjects[0];
    	
    	SearchConsoleClient client = new SearchConsoleClient(apiConnection.getGoogleApiConnection().getCredential());
    	List<WmxSite> sites = client.getSiteList();
        
    	DataTableSpec outSpec = createSpec();
        BufferedDataContainer outContainer = exec.createDataContainer(outSpec);
        
        int row = 0;
        for ( WmxSite site : sites ) {
        	
        	List<DataCell> cells = new ArrayList<DataCell>(outSpec.getNumColumns());

        	cells.add(new StringCell(site.getSiteUrl()));
        	cells.add(new StringCell(site.getPermissionLevel()));
        	
        	outContainer.addRowToTable(new DefaultRow("Row" + row++, cells));
        }
        
        outContainer.close();
    	
    	return new BufferedDataTable[]{outContainer.getTable()};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void reset() {
        // TODO: generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PortObjectSpec[] configure(final PortObjectSpec[] inSpecs)
            throws InvalidSettingsException {

        return new DataTableSpec[]{createSpec()};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
         // TODO: generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
        // TODO: generated method stub
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
    
    private DataTableSpec createSpec() {
    	
    	List<DataColumnSpec> colSpecs = new ArrayList<DataColumnSpec>();
    	colSpecs.add(new DataColumnSpecCreator("URL", StringCell.TYPE).createSpec());
    	colSpecs.add(new DataColumnSpecCreator("Permission Level", StringCell.TYPE).createSpec());
    	
    	
    	return new DataTableSpec(colSpecs.toArray(new DataColumnSpec[colSpecs.size()]));
    	
    }

}

