package com.pg.google.api.searchconsole.listsites;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "ListSitesNode" Node.
 * 
 *
 * @author P&G, eBusiness
 */
public class ListSitesNodeNodeFactory 
        extends NodeFactory<ListSitesNodeNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ListSitesNodeNodeModel createNodeModel() {
        return new ListSitesNodeNodeModel();
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
    public NodeView<ListSitesNodeNodeModel> createNodeView(final int viewIndex,
            final ListSitesNodeNodeModel nodeModel) {
        return new ListSitesNodeNodeView(nodeModel);
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
        return new ListSitesNodeNodeDialog();
    }

}

