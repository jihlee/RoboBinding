package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.view.AbstractViewEvent;

import android.widget.AbsListView;

/**
 *
 * @author jihunlee
 *
 */
public class ScrollStateChangedEvent extends AbstractViewEvent {
    private int scrollState;

    ScrollStateChangedEvent(AbsListView view, int scrollState) {
	super(view);
	this.scrollState = scrollState;
    }

    public int getScrollState() {
	return scrollState;
    }

    public AbsListView getListView() {
	return (AbsListView)getView();
    }

}
