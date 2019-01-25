package org.tepi.filtertable.client.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.tepi.filtertable.FilterTable;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.UIDL;
import com.vaadin.client.ValueMap;
import com.vaadin.shared.ui.Connect;
import com.vaadin.client.ui.table.TableConnector;

/**
 * Class FilterTableConnector.
 *
 * @author Teppo Kurki
 * @since 27.10.2014
 */
@SuppressWarnings("serial")
@Connect(FilterTable.class)
public class FilterTableConnector extends TableConnector {

	public static final String TAG_FILTERS = "filters";
	public static final String TAG_FILTER_COMPONENT = "filtercomponent-";
	public static final String ATTRIBUTE_FILTERS_VISIBLE = "filtersvisible";
	public static final String ATTRIBUTE_FORCE_RENDER = "forceRender";
	public static final String ATTRIBUTE_COLUMN_ID = "columnid";
	public static final String ATTRIBUTE_CACHED = "cached";
	public static final String ATTRIBUTE_COLUMN_HEADER_STYLE_NAMES = "columnheaderstylenames";

	@Override
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		super.updateFromUIDL(uidl, client);
		updateFiltersFromUIDL(uidl.getChildByTagName(FilterTableConnector.TAG_FILTERS), client);
	}

	@Override
	public VFilterTable getWidget() {
		return (VFilterTable) super.getWidget();
	}

	@SuppressWarnings("deprecation")
	private void updateFiltersFromUIDL(UIDL uidl, ApplicationConnection client) {
		VFilterTable filterTable = getWidget();

		boolean filtersVisible = uidl.hasAttribute(ATTRIBUTE_FILTERS_VISIBLE)
				? uidl.getBooleanAttribute(ATTRIBUTE_FILTERS_VISIBLE) : false;
		filterTable.setFiltersVisible(filtersVisible);
		filterTable.updateHeight();

		/* If filters are not set visible, clear and hide filter panel */
		if (filtersVisible == false) {
			filterTable.filters.clear();
		} else {
			if (uidl.hasAttribute(ATTRIBUTE_COLUMN_HEADER_STYLE_NAMES)) {
				getWidget().setColumnHeaderStylenames(uidl.getMapAttribute(ATTRIBUTE_COLUMN_HEADER_STYLE_NAMES));
			}
			/* Prepare and paint filter components */
			Map<String, Widget> newWidgets = new HashMap<String, Widget>();
			boolean allCached = true;
			for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
				final UIDL childUidl = (UIDL) it.next();
				if (childUidl.getTag().startsWith(TAG_FILTER_COMPONENT)) {
					String cid = childUidl.getStringAttribute(ATTRIBUTE_COLUMN_ID);
					UIDL uidld = childUidl.getChildUIDL(0);
					if (uidld == null) {
						newWidgets.put(cid, null);
					} else {
						ComponentConnector connector = client.getPaintable(uidld);
						newWidgets.put(cid, connector.getWidget());
						if (uidld.hasAttribute(ATTRIBUTE_CACHED) == false
								|| uidld.getBooleanAttribute(ATTRIBUTE_CACHED) == false) {
							allCached = false;
						}
					}
				}
			}

			boolean forceRender = uidl.getBooleanAttribute(ATTRIBUTE_FORCE_RENDER);
			if (forceRender || !allCached || filterTable.filters.isEmpty()) {
				filterTable.filters.clear();
				for (String cid : newWidgets.keySet()) {
					filterTable.filters.put(cid, newWidgets.get(cid));
				}
				filterTable.reRenderFilterComponents();
			} else {
				filterTable.resetFilterWidths();
			}
		}
	}
}
