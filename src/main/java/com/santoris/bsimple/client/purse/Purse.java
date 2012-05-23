package com.santoris.bsimple.client.purse;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.gwt.query.client.GQuery.$;

import java.math.BigDecimal;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.santoris.bsimple.model.Transaction;

public class Purse extends Composite {
	
	private BigDecimal amount = BigDecimal.ZERO;

	private HTMLPanel amountLabel;
	
	private Set<String> transactionIds = newHashSet();
	
	private final String categoryName;

	private PursesPanel pursesPanel;

	protected interface CategoryLabelTemplates extends SafeHtmlTemplates {
		@Template("<b>{0}</b>")
		SafeHtml label(String categoryName);
	}

	private static final CategoryLabelTemplates CATEGORY_LABEL_TEMPLATES = GWT
			.create(CategoryLabelTemplates.class);
	
	
	protected interface CategoryTagTemplates extends SafeHtmlTemplates {
		@Template("<div class=\"label label-info\" style=\"float: left; margin-top:1px; margin-right:2px\">{0}</div>")
		SafeHtml categoryTag(String categoryName);
	}
	
	private static final CategoryTagTemplates CATEGORY_TAG_TEMPLATE = GWT
			.create(CategoryTagTemplates.class);
	
	public Purse(String categoryName, PursesPanel pursesPanel) {
		this.categoryName = categoryName;
		this.pursesPanel = pursesPanel;
		init();
	}

	private FlowPanel createPurseWrapper() {

		final FlowPanel purseWrapper = new FlowPanel();
		purseWrapper.setStyleName("purseWrapper");
		
		final FlowPanel outerPurse = new FlowPanel();
		outerPurse.setStyleName("purseOuter");
		purseWrapper.add(outerPurse);
		
		final FlowPanel purse = new FlowPanel();
		purse.setStyleName("purse");
		purseWrapper.add(purse);

		HTMLPanel purseLabel = new HTMLPanel("");
		purseLabel.setStyleName("purseLabel");
		purseLabel.getElement().setInnerHTML(CATEGORY_LABEL_TEMPLATES.label(categoryName).asString());
		purse.add(purseLabel);
		
		amountLabel = new HTMLPanel("");
		amountLabel.setStyleName("purseAmountLabel");
		amountLabel.addStyleName("badge-important");
		amountLabel.getElement().setInnerHTML("0");
		purse.add(amountLabel);

		$(outerPurse).fadeOut(500);
		
		purse.addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				//outerPurse.addStyleName("hover");
				$(outerPurse).fadeIn(150);
			}
		}, MouseOverEvent.getType());

		purse.addDomHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				//outerPurse.removeStyleName("hover");
				$(outerPurse).fadeOut(150);
			}
		}, MouseOutEvent.getType());

		return purseWrapper;
	}

	private void init() {
		FlowPanel purseWrapper = createPurseWrapper();
		initWidget(purseWrapper);
	}

	public void addTransaction(Transaction transaction, Element transactionEntryElement) {
		if (!transactionIds.contains(transaction.getId())) {
			transactionIds.add(transaction.getId());
			this.amount = this.amount.add(transaction.getAmount());
			amountLabel.getElement().setInnerText(this.amount.setScale(2).toString());
			amountLabel.removeStyleName("badge-important");
			amountLabel.removeStyleName("badge-success");
			amountLabel.addStyleName(amount.signum() < 0 ? "badge-important" : "badge-success");
			
			$("#categories", transactionEntryElement).append(CATEGORY_TAG_TEMPLATE.categoryTag(this.categoryName).asString());
			
			pursesPanel.updatePursesByTransactionId(this, transaction);
		}
	}

	public String getCategoryName() {
		return categoryName;
	}
}
