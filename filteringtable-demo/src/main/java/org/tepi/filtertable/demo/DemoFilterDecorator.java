package org.tepi.filtertable.demo;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;

import org.tepi.filtertable.FilterDecorator;
import org.tepi.filtertable.demo.FilterTableDemoUI.State;
import org.tepi.filtertable.numberfilter.NumberFilterPopupConfig;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.datefield.Resolution;

@SuppressWarnings("serial")
class DemoFilterDecorator implements FilterDecorator, Serializable {

	@Override
	public String getEnumFilterDisplayName(Object propertyId, Object value) {
		if ("state".equals(propertyId)) {
			State state = (State) value;
			switch (state) {
			case CREATED:
				return "Order has been created";
			case PROCESSING:
				return "Order is being processed";
			case PROCESSED:
				return "Order has been processed";
			case FINISHED:
				return "Order is delivered";
			}
		}
		// returning null will output default value
		return null;
	}

	@Override
	public Resource getEnumFilterIcon(Object propertyId, Object value) {
		if ("state".equals(propertyId)) {
			State state = (State) value;
			switch (state) {
			case CREATED:
				return FontAwesome.FILE;
			case PROCESSING:
				return FontAwesome.CLOCK_O;
			case PROCESSED:
				return FontAwesome.CHECK;
			case FINISHED:
				return FontAwesome.FLAG_CHECKERED;
			}
		}
		return null;
	}

	@Override
	public String getBooleanFilterDisplayName(Object propertyId, boolean value) {
		return "validated".equals(propertyId) ? (value ? "Validated" : "Not validated") : null;
	}

	@Override
	public Resource getBooleanFilterIcon(Object propertyId, boolean value) {
		return "validated".equals(propertyId) ? (value ? FontAwesome.CHECK : FontAwesome.TIMES) : null;
	}

	@Override
	public String getFromCaption() {
		return "Start date:";
	}

	@Override
	public String getToCaption() {
		return "End date:";
	}

	@Override
	public String getSetCaption() {
		// use default caption
		return null;
	}

	@Override
	public String getClearCaption() {
		// use default caption
		return null;
	}

	@Override
	public boolean isTextFilterImmediate(Object propertyId) {
		// use text change events for all the text fields
		return true;
	}

	@Override
	public int getTextChangeTimeout(Object propertyId) {
		// use the same timeout for all the text fields
		return 500;
	}

	@Override
	public String getAllItemsVisibleString() {
		return "Show all";
	}

	@Override
	public Resolution getDateFieldResolution(Object propertyId) {
		return Resolution.DAY;
	}

	public DateFormat getDateFormat(Object propertyId) {
		return DateFormat.getDateInstance(DateFormat.SHORT, new Locale("fi", "FI"));
	}

	@Override
	public boolean usePopupForNumericProperty(Object propertyId) {
		return true;
	}

	@Override
	public String getDateFormatPattern(Object propertyId) {
		// TODO Will use the default settings
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Will use the default settings
		return null;
	}

	@Override
	public NumberFilterPopupConfig getNumberFilterPopupConfig() {
		// TODO Will use the default settings
		return null;
	}

	@Override
	public String getNumberValidationErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}
