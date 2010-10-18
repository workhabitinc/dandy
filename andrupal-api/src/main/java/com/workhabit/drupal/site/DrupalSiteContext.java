package com.workhabit.drupal.site;

import com.workhabit.drupal.entity.DrupalComment;
import com.workhabit.drupal.entity.DrupalNode;
import com.workhabit.drupal.entity.DrupalTaxonomyTerm;
import com.workhabit.drupal.entity.DrupalUser;

import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 11, 2010, 5:10:31 PM
 */
public interface DrupalSiteContext {
    void connect() throws DrupalFetchException;

    void logout();

    List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException;

    DrupalNode getNode(int nid) throws DrupalFetchException;

    DrupalComment getComment(int nid, int cid) throws DrupalFetchException;

    void saveComment(DrupalComment comment) throws DrupalFetchException;

    DrupalUser login(String username, String password) throws DrupalLoginException, DrupalFetchException;

    List<DrupalTaxonomyTerm> getTermView(String s) throws DrupalFetchException;
}
