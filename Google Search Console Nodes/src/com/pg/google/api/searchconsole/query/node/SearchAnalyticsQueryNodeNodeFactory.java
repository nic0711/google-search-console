package com.pg.google.api.searchconsole.query.node;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "SearchAnalyticsQueryNode" Node.
 * 
 *
 * @author P&G, eBusiness
 */
public class SearchAnalyticsQueryNodeNodeFactory 
        extends NodeFactory<SearchAnalyticsQueryNodeNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchAnalyticsQueryNodeNodeModel createNodeModel() {
        return new SearchAnalyticsQueryNodeNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<SearchAnalyticsQueryNodeNodeModel> createNodeView(final int viewIndex,
            final SearchAnalyticsQueryNodeNodeModel nodeModel) {
        return new SearchAnalyticsQueryNodeNodeView(nodeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new SearchAnalyticsQueryNodeNodeDialog();
    }

}

