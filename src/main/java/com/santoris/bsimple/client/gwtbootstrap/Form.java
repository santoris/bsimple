package com.santoris.bsimple.client.gwtbootstrap;

import com.github.gwtbootstrap.client.ui.base.ComplexWidget;
import com.github.gwtbootstrap.client.ui.base.StyleHelper;
import com.github.gwtbootstrap.client.ui.constants.FormType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.google.gwt.user.client.ui.impl.FormPanelImplHost;

//@formatter:off
/**
 * Styled HTML form.
 * 
 * <p>
 * <h3>UiBinder Usage:</h3>
 * 
 * <pre>
 * {@code 
 * <b:Form>...</b:Form>}
 * </pre>
 * 
 * </p>
 * 
 * @since 2.0.3.0
 * 
 * @author Dominik Mayer
 * @author ohashi keisuke
 */
// @formatter:on
public class Form extends ComplexWidget implements FormPanelImplHost {

	private static FormPanelImpl impl = GWT.create(FormPanelImpl.class);

	/**
	 * Fired when a form has been submitted successfully.
	 */
	public static class SubmitCompleteEvent extends GwtEvent<SubmitCompleteHandler> {

		/**
		 * The event type.
		 */
		private static Type<SubmitCompleteHandler> TYPE;

		/**
		 * Handler hook.
		 * 
		 * @return the handler hook
		 */
		static Type<SubmitCompleteHandler> getType() {
			if (TYPE == null) {
				TYPE = new Type<SubmitCompleteHandler>();
			}
			return TYPE;
		}

		private String resultHtml;

		/**
		 * Create a submit complete event.
		 * 
		 * @param resultsHtml
		 *            the results from submitting the form
		 */
		protected SubmitCompleteEvent(String resultsHtml) {
			this.resultHtml = resultsHtml;
		}

		@Override
		public final Type<SubmitCompleteHandler> getAssociatedType() {
			return TYPE;
		}

		/**
		 * Gets the result text of the form submission.
		 * 
		 * @return the result html, or <code>null</code> if there was an error
		 *         reading it
		 * @tip The result html can be <code>null</code> as a result of
		 *      submitting a form to a different domain.
		 */
		public String getResults() {
			return resultHtml;
		}

		@Override
		protected void dispatch(SubmitCompleteHandler handler) {
			handler.onSubmitComplete(this);
		}
	}

	/**
	 * Handler for {@link FormPanel.SubmitCompleteEvent} events.
	 */
	public interface SubmitCompleteHandler extends EventHandler {

		/**
		 * Fired when a form has been submitted successfully.
		 * 
		 * @param event
		 *            the event
		 */
		void onSubmitComplete(SubmitCompleteEvent event);
	}

	/**
	 * Fired when the form is submitted.
	 */
	public static class SubmitEvent extends GwtEvent<SubmitHandler> {

		/**
		 * The event type.
		 */
		private static Type<SubmitHandler> TYPE = new Type<SubmitHandler>();

		/**
		 * Handler hook.
		 * 
		 * @return the handler hook
		 */
		static Type<SubmitHandler> getType() {
			if (TYPE == null) {
				TYPE = new Type<SubmitHandler>();
			}
			return TYPE;
		}

		private boolean canceled = false;

		/**
		 * Cancel the form submit. Firing this will prevent a subsequent
		 * {@link FormPanel.SubmitCompleteEvent} from being fired.
		 */
		public void cancel() {
			this.canceled = true;
		}

		@Override
		public final Type<SubmitHandler> getAssociatedType() {
			return TYPE;
		}

		/**
		 * Gets whether this form submit will be canceled.
		 * 
		 * @return <code>true</code> if the form submit will be canceled
		 */
		public boolean isCanceled() {
			return canceled;
		}

		@Override
		protected void dispatch(SubmitHandler handler) {
			handler.onSubmit(this);
		}
	}

	/**
	 * Handler for {@link FormPanel.SubmitEvent} events.
	 */
	public interface SubmitHandler extends EventHandler {

		/**
		 * Fired when the form is submitted.
		 * 
		 * <p>
		 * The FormPanel must <em>not</em> be detached (i.e. removed from its
		 * parent or otherwise disconnected from a {@link RootPanel}) until the
		 * submission is complete. Otherwise, notification of submission will
		 * fail.
		 * </p>
		 * 
		 * @param event
		 *            the event
		 */
		void onSubmit(SubmitEvent event);
	}

	private static int formId = 0;

	private String frameName;

	private Element synthesizedFrame;

	/**
	 * Creates an empty form.
	 */
	public Form() {
		this(true);
	}
	
	public Form(boolean createIFrame) {
		this(Document.get().createFormElement(), createIFrame);
	}

	/**
	 * Adds a {@link SubmitCompleteEvent} handler.
	 * 
	 * @param handler
	 *            the handler
	 * @return the handler registration used to remove the handler
	 */
	public HandlerRegistration addSubmitCompleteHandler(SubmitCompleteHandler handler) {
		return addHandler(handler, SubmitCompleteEvent.getType());
	}

	/**
	 * Adds a {@link SubmitEvent} handler.
	 * 
	 * @param handler
	 *            the handler
	 * @return the handler registration used to remove the handler
	 */
	public HandlerRegistration addSubmitHandler(SubmitHandler handler) {
		return addHandler(handler, SubmitEvent.getType());
	}

	/**
	 * Gets the 'action' associated with this form. This is the URL to which it
	 * will be submitted.
	 * 
	 * @return the form's action
	 */
	public String getAction() {
		return getFormElement().getAction();
	}

	/**
	 * Gets the encoding used for submitting this form. This should be either
	 * {@link #ENCODING_MULTIPART} or {@link #ENCODING_URLENCODED}.
	 * 
	 * @return the form's encoding
	 */
	public String getEncoding() {
		return impl.getEncoding(getElement());
	}

	/**
	 * Gets the HTTP method used for submitting this form. This should be either
	 * {@link #METHOD_GET} or {@link #METHOD_POST}.
	 * 
	 * @return the form's method
	 */
	public String getMethod() {
		return getFormElement().getMethod();
	}

	/**
	 * This constructor may be used by subclasses to explicitly use an existing
	 * element. This element must be a &lt;form&gt; element.
	 * 
	 * <p>
	 * If the createIFrame parameter is set to <code>true</code>, then the
	 * wrapped form's target attribute will be set to a hidden iframe. If not,
	 * the form's target will be left alone, and the FormSubmitComplete event
	 * will not be fired.
	 * </p>
	 * 
	 * @param element
	 *            the element to be used
	 * @param createIFrame
	 *            <code>true</code> to create an &lt;iframe&gt; element that
	 *            will be targeted by this form
	 */
	protected Form(Element element,
		boolean createIFrame) {
		super(element.getTagName());
		FormElement.as(element);

		if (createIFrame) {
			assert ((getTarget() == null) || (getTarget().trim().length() == 0)) : "Cannot create target iframe if the form's target is already set.";

			// We use the module name as part of the unique ID to ensure that
			// ids are
			// unique across modules.
			frameName = "FormPanel_" + GWT.getModuleName() + "_" + (++formId);
			setTarget(frameName);

			sinkEvents(Event.ONLOAD);
		}
	}

	public String getTarget() {
		return getFormElement().getTarget();
	}

	private FormElement getFormElement() {
		return getElement().cast();
	}

	/**
	 * Sets the type of the form.
	 * 
	 * @param type
	 *            the form's type
	 */
	public void setType(FormType type) {
		StyleHelper.changeStyle(this, type, FormType.class);
	}

	/**
	 * Resets the form, clearing all fields.
	 */
	public void reset() {
		impl.reset(getElement());
	}

	/**
	 * Sets the 'action' associated with this form. This is the URL to which it
	 * will be submitted.
	 * 
	 * @param url
	 *            the form's action
	 */
	public void setAction(String url) {
		getFormElement().setAction(url);
	}

	/**
	 * Sets the 'action' associated with this form. This is the URL to which it
	 * will be submitted.
	 * 
	 * @param url
	 *            the form's action
	 */
	public void setAction(SafeUri url) {
		setAction(url.asString());
	}

	/**
	 * Sets the encoding used for submitting this form. This should be either
	 * {@link #ENCODING_MULTIPART} or {@link #ENCODING_URLENCODED}.
	 * 
	 * @param encodingType
	 *            the form's encoding
	 */
	public void setEncoding(String encodingType) {
		impl.setEncoding(getElement(), encodingType);
	}

	/**
	 * Sets the HTTP method used for submitting this form. This should be either
	 * {@link #METHOD_GET} or {@link #METHOD_POST}.
	 * 
	 * @param method
	 *            the form's method
	 */
	public void setMethod(String method) {
		getFormElement().setMethod(method);
	}

	/**
	 * Submits the form.
	 * 
	 * <p>
	 * The FormPanel must <em>not</em> be detached (i.e. removed from its parent
	 * or otherwise disconnected from a {@link RootPanel}) until the submission
	 * is complete. Otherwise, notification of submission will fail.
	 * </p>
	 */
	public void submit() {
		// Fire the onSubmit event, because javascript's form.submit() does not
		// fire the built-in onsubmit event.
		if (!fireSubmitEvent()) {
			return;
		}

		impl.submit(getElement(), synthesizedFrame);
	}

	@Override
	protected void onAttach() {
		super.onAttach();

		if (frameName != null) {
			// Create and attach a hidden iframe to the body element.
			createFrame();
			Document.get().getBody().appendChild(synthesizedFrame);
		}
		// Hook up the underlying iframe's onLoad event when attached to the
		// DOM.
		// Making this connection only when attached avoids memory-leak issues.
		// The FormPanel cannot use the built-in GWT event-handling mechanism
		// because there is no standard onLoad event on iframes that works
		// across
		// browsers.
		impl.hookEvents(synthesizedFrame, getElement(), this);
	}

	@Override
	protected void onDetach() {
		super.onDetach();

		// Unhook the iframe's onLoad when detached.
		impl.unhookEvents(synthesizedFrame, getElement());

		if (synthesizedFrame != null) {
			// And remove it from the document.
			Document.get().getBody().removeChild(synthesizedFrame);
			synthesizedFrame = null;
		}
	}

	private void createFrame() {
		// Attach a hidden IFrame to the form. This is the target iframe to
		// which
		// the form will be submitted. We have to create the iframe using
		// innerHTML,
		// because setting an iframe's 'name' property dynamically doesn't work
		// on
		// most browsers.
		Element dummy = Document.get().createDivElement();
		dummy.setInnerHTML("<iframe src=\"javascript:''\" name='" + frameName + "' style='position:absolute;width:0;height:0;border:0'>");

		synthesizedFrame = dummy.getFirstChildElement();
	}

	/**
	 * Fire a {@link SubmitEvent}.
	 * 
	 * @return true to continue, false if canceled
	 */
	private boolean fireSubmitEvent() {
		SubmitEvent event = new SubmitEvent();
		fireEvent(event);
		return !event.isCanceled();
	}

	/**
	 * Returns true if the form is submitted, false if canceled.
	 */
	private boolean onFormSubmitImpl() {
		return fireSubmitEvent();
	}

	private void onFrameLoadImpl() {

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				fireEvent(new SubmitCompleteEvent(impl.getContents(synthesizedFrame)));
			}
		});
	}

	public void setTarget(String target) {
		getFormElement().setTarget(target);
	}

	@Override
	public boolean onFormSubmit() {
	    return onFormSubmitImpl();
	}

	@Override
	public void onFrameLoad() {
		onFrameLoadImpl();		
	}

}
