package com.workhabit.drupal.publisher;

import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.site.DrupalFetchException;

import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 5:01:14 PM
 */
public class DrupalNodeListViewActivity extends AbstractDrupalNodeListActivity {
    protected List<DrupalNode> doGetNodes(String viewArguments, String viewName) throws DrupalFetchException {
        return drupalSiteContext.getNodeView(viewName, viewArguments);
    }
}
