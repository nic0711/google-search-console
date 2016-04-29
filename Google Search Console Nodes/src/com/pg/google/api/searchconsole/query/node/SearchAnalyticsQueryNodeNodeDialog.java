package com.pg.google.api.searchconsole.query.node;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;

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

    /**
     * New pane for configuring the SearchAnalyticsQueryNode node.
     */
    protected SearchAnalyticsQueryNodeNodeDialog() {

    }

	@Override
	protected void saveSettingsTo(NodeSettingsWO settings) throws InvalidSettingsException {
		
		
	}
}

