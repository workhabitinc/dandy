package org.workhabit.drupal.api.site;

import org.workhabit.drupal.api.entity.*;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalLoginException;
import org.workhabit.drupal.api.site.exceptions.DrupalLogoutException;
import org.workhabit.drupal.api.site.exceptions.DrupalSaveException;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceState;
import org.workhabit.drupal.api.site.support.GenericCookie;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 11, 2010, 5:10:31 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public interface DrupalSiteContext
{
    void connect() throws DrupalFetchException;

    /**
     * logs out the currently logged in user (via user.logout)
     *
     * @throws DrupalLogoutException if there was an error logging out, or if there's no currently logged in user.
     */
    void logout() throws DrupalLogoutException;

    /**
     * returns a list of nodes provided by a node view (via views.get)
     *
     * @param viewName the name of the drupal view to call
     * @return a list of nodes.
     * @throws DrupalFetchException if there's an error during the request.
     */
    List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException;

    /**
     * overload of getNodeView() that passes a list of arguments to the specified view. Useful for views that take an
     * optional argument.
     *
     * @param viewName      the name of the drupal view to call
     * @param viewArguments a "/" separated list of arguments.
     * @return a list of nodes
     * @throws DrupalFetchException if there was an error during the request.
     */
    List<DrupalNode> getNodeView(String viewName, String viewArguments) throws DrupalFetchException;

    /**
     * overload of getNodeView() that accepts a limit and offset for paging.  This is the preferred form.
     *
     * @param viewName      the name of the view
     * @param viewArguments a list of arguments to pass to the view.  If there are no arguments, pass null.
     * @param offset        an integer specifying the offset (from zero) to return.
     * @param limit         the number of results to return.
     * @return a list of nodes
     * @throws DrupalFetchException if there was an error during the request.
     */
    List<DrupalNode> getNodeView(String viewName, String viewArguments, int offset, int limit) throws DrupalFetchException;

    /**
     * returns the node for the give nid (via node.get)
     *
     * @param nid the ID of the node to return
     * @return a populated DrupalNode object
     * @throws DrupalFetchException if there's an error during the request.
     */
    DrupalNode getNode(int nid) throws DrupalFetchException;

    /**
     * fetches a Comment by CID (via comment.load)
     *
     * @param cid the comment ID
     * @return a populated DrupalComment object
     * @throws DrupalFetchException if there's an error during the request.
     */
    DrupalComment getComment(int cid) throws DrupalFetchException;

    /**
     * Save a drupal comment (via comment.save)
     *
     * @param comment the comment object to save.  Note that this MUST have a nid associated with it.  If cid is 0, it
     *                will create a new comment. If cid is a positive integer, then it will update an existing comment (provided
     *                the currently logged in user has permission to do so).
     * @throws DrupalFetchException if there's an error saving the comment.
     * @return the cid of the saved comment, or 0 if there was a problem saving.
     */
    int saveComment(final DrupalComment comment) throws DrupalFetchException;


    /**
     * Logs in a user by username and password (invokes the drupal Service user.login)
     *
     * @param username the user's username
     * @param password the user's plaintext password
     * @return a DrupalUser object if the request was successful.
     * @throws DrupalLoginException if there's an error logging in the user (e.g. username/password mismatch)
     * @throws DrupalFetchException if there's an error during the request.
     */
    DrupalUser login(String username, String password) throws DrupalLoginException, DrupalFetchException;

    /**
     * Returns a list of TaxonomyTerms by invoking a Drupal Term view (via views.get)
     *
     * @param viewName the name of the drupal view to invoke
     * @return a list of DrupalTaxonomyTerms
     * @throws DrupalFetchException if there's an error during the request.
     */
    List<DrupalTaxonomyTerm> getTermView(String viewName) throws DrupalFetchException;

    /**
     * Similar to {@link #getTermView(String)} but returns a node count for each term returned. (uses service endpoint
     * taxonomy.dictionary)
     *
     * @return a list of DrupalTaxonomyTerms
     * @throws DrupalFetchException if there's an error during the request.
     */
    List<DrupalTaxonomyTerm> getCategoryList() throws DrupalFetchException;

    /**
     * creates a new user account with the specified username, password, and email address
     *
     * @param username the new username
     * @param password the new password
     * @param email    the new email address
     * @return the uid of the user.
     * @throws DrupalSaveException if there's an error saving the user (e.g. username or email already exists)
     */
    int registerNewUser(String username, String password, String email) throws DrupalSaveException;

    /**
     * Return a list of comments for the give node ID
     *
     * @param nid the ID of the node to return.
     * @return a list of comments
     * @throws DrupalFetchException if there's an error processing the request.
     */
    List<DrupalComment> getComments(int nid) throws DrupalFetchException;

    /**
     * Return a list of comments for the given node ID.  Start and count can be specified to page the results.
     *
     * @param nid   the ID of the node comments to return.
     * @param start the start offset
     * @param count number of results to return
     * @return a list of comments
     * @throws DrupalFetchException if there's an error processing the request.
     */
    List<DrupalComment> getComments(int nid, int start, int count) throws DrupalFetchException;

    /**
     * Returns an inputstream to the specified file
     *
     * @param filepath the path of the file on the Drupal website
     * @return an inputstream
     * @throws IOException if there's a problem with the request.
     */
    InputStream getFileStream(String filepath) throws IOException;

    /**
     * Returns the drupal Files directory path. Essentially a wrapper for the file_directory_path() api call
     *
     * @return a string with the path to the files directory.
     * @throws DrupalFetchException if there's a problem with the request.
     */
    String getFileDirectoryPath() throws DrupalFetchException;

    /**
     * Save the drupal node. If nid is empty, saves a new node, otherwise updates an existing one.
     *
     * @param node the Drupal node to save
     * @return the nid of the saved node
     * @throws DrupalSaveException if there's an error processing the save, for example if the user doesn't
     *                             have permission.
     */
    int saveNode(final DrupalNode node) throws DrupalSaveException;

    /**
     * Fetch a user object by uid
     *
     * @param uid the UID of the user to fetch
     * @return a populated DrupalUser node
     * @throws DrupalFetchException if there's a problem with the request.
     */
    DrupalUser getUser(int uid) throws DrupalFetchException;

    ArrayList<GenericCookie> getCurrentUserCookie();

    String getFileUploadToken() throws DrupalFetchException;

    DrupalFile saveFileStream(InputStream inputStream, String fileName, String token) throws DrupalSaveException;

    void initializeSavedState(DrupalSiteContextInstanceState state);

    DrupalSiteContextInstanceState getSavedState();
}
