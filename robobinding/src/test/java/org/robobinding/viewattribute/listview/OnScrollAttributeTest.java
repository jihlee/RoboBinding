package org.robobinding.viewattribute.listview;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.viewattribute.view.AbstractCommandViewAttributeWithViewListenersAwareTest;

import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnScrollAttributeTest extends AbstractCommandViewAttributeWithViewListenersAwareTest<ListView, OnScrollAttribute, MockListViewListeners> {
    @Test@Ignore
    public void givenBoundAttribute_whenScrollView_thenEventReceived() {
	bindAttribute();

	scrollView();

	assertEventReceived();
    }

    private void scrollView() {
        view.smoothScrollToPositionFromTop(1, 0, 500);
    }

    private void assertEventReceived() {
        assertEventReceived(ScrollEvent.class);
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnScrollListenerInvoked);
    }

}