package com.santoris.bsimple.client.transactions;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasRows;

import com.santoris.bsimple.client.transactions.ContactDatabase.Category;

/**
 * A scrolling pager that automatically increases the range every time the
 * scroll bar reaches the bottom.
 * 
 * Code coming from GWT showcase
 */
public class ShowMorePagerPanel extends AbstractPager {

  /**
   * The default increment size.
   */
  private static final int DEFAULT_INCREMENT = 20;

  private Category category;

  /**
   * The increment size.
   */
  private int incrementSize = DEFAULT_INCREMENT;

  /**
   * The last scroll position.
   */
  private int lastScrollPos = 0;

  /**
   * The scrollable panel.
   */
  private final ScrollPanel scrollable = new ScrollPanel();

  /**
   * Construct a new {@link ShowMorePagerPanel}.
   */
  public ShowMorePagerPanel(Category category) {
    this.category = category;
    init();

    // Handle scroll events.
    scrollable.addScrollHandler(new ScrollHandler() {
      public void onScroll(ScrollEvent event) {
        // If scrolling up, ignore the event.
        int oldScrollPos = lastScrollPos;
        lastScrollPos = scrollable.getScrollPosition();
        if (oldScrollPos >= lastScrollPos) {
          return;
        }

        HasRows display = getDisplay();
        if (display == null) {
          return;
        }
        int maxScrollTop = scrollable.getWidget().getOffsetHeight()
            - scrollable.getOffsetHeight();
        if (lastScrollPos >= maxScrollTop) {
          // We are near the end, so increase the page size.
          int newPageSize = Math.min(display.getVisibleRange().getLength()
              + incrementSize, display.getRowCount());
          display.setVisibleRange(0, newPageSize);
        }
      }
    });
  }

  public Category getCategory() {
    return category;
  }

  /**
   * Get the number of rows by which the range is increased when the scrollbar
   * reaches the bottom.
   * 
   * @return the increment size
   */
  public int getIncrementSize() {
    return incrementSize;
  }

  @Override
  public void setDisplay(HasRows display) {
    assert display instanceof Widget : "display must extend Widget";
    scrollable.setWidget((Widget) display);
    super.setDisplay(display);
  }

  /**
   * Set the number of rows by which the range is increased when the scrollbar
   * reaches the bottom.
   * 
   * @param incrementSize
   *          the incremental number of rows
   */
  public void setIncrementSize(int incrementSize) {
    this.incrementSize = incrementSize;
  }

  @Override
  protected void onRangeOrRowCountChanged() {
  }

  private void init() {
    FlowPanel p = new FlowPanel();
    p.add(new Label(category.getDisplayName()));
    p.add(scrollable);
    p.setStyleName("pagerWrapper");
    scrollable.setStyleName("pager");
    initWidget(p);
  }
}

