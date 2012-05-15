package com.santoris.bsimple.client.transactions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import com.santoris.bsimple.client.transactions.ContactDatabase.Category;
import com.santoris.bsimple.client.transactions.ContactDatabase.ContactInfo;

/**
 * A form used for editing contacts. Code coming from gwt showcase
 * http://gwt.google.com/samples/Showcase/Showcase.html#!CwCellList
 */
public class ContactInfoForm extends Composite {

  private static Binder uiBinder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, ContactInfoForm> {
  }

  @UiField
  TextArea addressBox;
  @UiField
  DateBox birthdayBox;
  @UiField
  ListBox categoryBox;
  @UiField
  Button createButton;
  @UiField
  TextBox firstNameBox;
  @UiField
  TextBox lastNameBox;
  @UiField
  Button updateButton;

  private ContactInfo contactInfo;

  public ContactInfoForm() {
    initWidget(uiBinder.createAndBindUi(this));
    DateTimeFormat dateFormat = DateTimeFormat
        .getFormat(PredefinedFormat.DATE_LONG);
    birthdayBox.setFormat(new DateBox.DefaultFormat(dateFormat));

    // Add the categories to the category box.
    final Category[] categories = ContactDatabase.get().queryCategories();
    for (Category category : categories) {
      categoryBox.addItem(category.getDisplayName());
    }

    // Initialize the contact to null.
    setContact(null);

    // Handle events.
    updateButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (contactInfo == null) {
          return;
        }

        // Update the contact.
        contactInfo.setFirstName(firstNameBox.getText());
        contactInfo.setLastName(lastNameBox.getText());
        contactInfo.setAddress(addressBox.getText());
        contactInfo.setBirthday(birthdayBox.getValue());

        int categoryIndex = categoryBox.getSelectedIndex();
        Category newCategory = categories[categoryIndex];
        Category oldCategory = contactInfo.getCategory();
        if (oldCategory != newCategory) {
          contactInfo.setCategory(newCategory);
          ContactDatabase.get().moveContact(contactInfo, oldCategory);

        }

        // Update the views.
        ContactDatabase.get().refreshDisplays(contactInfo.getCategory());
      }
    });
    createButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        int categoryIndex = categoryBox.getSelectedIndex();
        Category category = categories[categoryIndex];
        contactInfo = new ContactInfo(category);
        contactInfo.setFirstName(firstNameBox.getText());
        contactInfo.setLastName(lastNameBox.getText());
        contactInfo.setAddress(addressBox.getText());
        contactInfo.setBirthday(birthdayBox.getValue());
        ContactDatabase.get().addContact(contactInfo);
        setContact(contactInfo);
      }
    });
  }

  public void setContact(ContactInfo contact) {
    this.contactInfo = contact;
    updateButton.setEnabled(contact != null);
    if (contact != null) {
      firstNameBox.setText(contact.getFirstName());
      lastNameBox.setText(contact.getLastName());
      addressBox.setText(contact.getAddress());
      birthdayBox.setValue(contact.getBirthday());
      Category category = contact.getCategory();
      Category[] categories = ContactDatabase.get().queryCategories();
      for (int i = 0; i < categories.length; i++) {
        if (category == categories[i]) {
          categoryBox.setSelectedIndex(i);
          break;
        }
      }
    }
  }
}
