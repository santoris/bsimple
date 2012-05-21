package com.santoris.bsimple.client.widget;

import com.github.gwtbootstrap.client.ui.base.HtmlWidget;

public class DropdownHTMLEntry extends HtmlWidget {

	public DropdownHTMLEntry(String html) {
		super("li", html);
	}

	public DropdownHTMLEntry() {
		super("li");
	}
}
