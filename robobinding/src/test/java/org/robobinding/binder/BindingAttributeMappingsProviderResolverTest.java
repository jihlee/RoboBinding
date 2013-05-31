/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.binder;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robobinding.viewattribute.BindingAttributeMappingsProvider;

import android.content.Context;
import android.view.View;

import com.google.common.collect.Maps;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class BindingAttributeMappingsProviderResolverTest {
    private BindingAttributeMappingsProviderResolver bindingAttributeMappingsProviderResolver;

    @Mock
    private BindingAttributeMappingsProvider<ViewWithNoParent> providerForViewWithNoParent;
    @Mock
    private BindingAttributeMappingsProvider<ViewWithParents> providerForViewWithParents;
    @Mock
    private BindingAttributeMappingsProvider<GrandparentView> providerForGrandparentView;

    @Before
    public void setUp() {
	initMocks(this);
	
	Map<Class<?>, BindingAttributeMappingsProvider<? extends View>> bindingAttributeProvidersMap = Maps.newHashMap();
	bindingAttributeProvidersMap.put(ViewWithNoParent.class, providerForViewWithNoParent);
	bindingAttributeProvidersMap.put(ViewWithParents.class, providerForViewWithParents);
	bindingAttributeProvidersMap.put(GrandparentView.class, providerForGrandparentView);
	bindingAttributeMappingsProviderResolver = new BindingAttributeMappingsProviderResolver(bindingAttributeProvidersMap);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenViewWithNoParent_whenGetCandidateProviders_thenOnlyProviderForViewShouldBeReturned() {
	ViewWithNoParent viewWithNoParent = new ViewWithNoParent();

	Iterable<BindingAttributeMappingsProvider<? extends View>> candidateProviders = bindingAttributeMappingsProviderResolver
		.findCandidateProviders(viewWithNoParent);

	assertThat(candidateProviders, equalTo(newProvidersInOrder(providerForViewWithNoParent)));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenViewWithParents_whenGetCandidateProviders_thenProvidersForViewAndItsParentsShouldBeReturned() {
	ViewWithParents viewWithParents = new ViewWithParents();

	Iterable<BindingAttributeMappingsProvider<? extends View>> candidateProviders = bindingAttributeMappingsProviderResolver
		.findCandidateProviders(viewWithParents);

	assertThat(candidateProviders, equalTo(newProvidersInOrder(providerForViewWithParents, providerForGrandparentView)));
    }

    private Iterable<BindingAttributeMappingsProvider<? extends View>> newProvidersInOrder(BindingAttributeMappingsProvider<? extends View>... providers) {
	return newArrayList(providers);
    }

    private static class ViewWithNoParent extends View {
	public ViewWithNoParent() {
	    super(mock(Context.class));
	}
    }

    private static class GrandparentView extends View {
	public GrandparentView() {
	    super(mock(Context.class));
	}
    }

    private static class ParentView extends GrandparentView {

    }

    private static class ViewWithParents extends ParentView {

    }
}