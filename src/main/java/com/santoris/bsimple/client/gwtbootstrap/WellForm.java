package com.santoris.bsimple.client.gwtbootstrap;

import com.github.gwtbootstrap.client.ui.Well;
import com.github.gwtbootstrap.client.ui.constants.Constants;

//@formatter:off
/**
* {@link Form} inside a {@link Well}.
* 
* @since 2.0.3.0
* 
* @author Dominik Mayer
*/
//@formatter:on
public class WellForm extends Form {

	/**
	 * Creates an empty widget.
	 */
	public WellForm() {
		addStyleName(Constants.WELL);
	}
}
