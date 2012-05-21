package com.santoris.bsimple.client.widget;

import com.google.gwt.user.client.ui.Widget;

/**
 * Same as the Bootstrap's DropdownButton except that this one support our DropdownHTMLEntry
 *
 */
public class DropdownButton extends com.github.gwtbootstrap.client.ui.DropdownButton {

	@Override
	protected boolean canBeAdded(Widget widget) {
		return ((widget instanceof DropdownHTMLEntry) || super.canBeAdded(widget));
	}
}
