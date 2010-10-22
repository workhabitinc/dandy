package com.workhabit.drupal.api.site;

import com.workhabit.drupal.api.entity.DrupalComment;
import com.workhabit.drupal.api.entity.DrupalNode;
import com.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import com.workhabit.drupal.api.entity.DrupalUser;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 11, 2010, 5:10:31 PM
 */
public interface DrupalSiteContext {
    void connect() throws DrupalFetchException, DrupalLogoutException;

    void logout() throws DrupalLogoutException;

    List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException;

    List<DrupalNode> getNodeView(String viewName, String viewArguments) throws DrupalFetchException;

    DrupalNode getNode(int nid) throws DrupalFetchException;

    DrupalComment getComment(int cid) throws DrupalFetchException;

    void saveComment(DrupalComment comment) throws DrupalFetchException;

    DrupalUser login(String username, String password) throws DrupalLoginException, DrupalFetchException;

    List<DrupalTaxonomyTerm> getTermView(String s) throws DrupalFetchException;

    List<DrupalTaxonomyTerm> getCategoryList() throws DrupalFetchException;
}