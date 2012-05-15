/*
 *  Copyright 2012 GWT-Bootstrap
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.santoris.bsimple.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Modal extends Composite {

    private static ModalUiBinder uiBinder = GWT.create(ModalUiBinder.class);

    interface ModalUiBinder extends UiBinder<Widget, Modal> {
    }

    @UiField
    com.github.gwtbootstrap.client.ui.Modal m;
    
    public Modal() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("onShow")
    void onAddClick(ClickEvent event) {
        m.show();
    }
}
