package org.workhabit.drupal.api.site.v2.site.local;

import org.junit.Before;
import org.junit.Test;
import org.workhabit.drupal.api.CommonsHttpClientDrupalServicesRequestManager;
import org.workhabit.drupal.api.entity.DrupalComment;
import org.workhabit.drupal.api.entity.DrupalField;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalSaveException;
import org.workhabit.drupal.api.site.impl.v2.DrupalSiteContextV2Impl;
import org.workhabit.drupal.api.site.impl.v2.KeyRequestSigningInterceptorImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/12/10, 10:56 AM
 */
public class LocalDrupalSiteContextV2ImplTest {
    private DrupalSiteContextV2Impl drupalSiteContext;

    @Before
    public void setUp() {
        CommonsHttpClientDrupalServicesRequestManager manager = new CommonsHttpClientDrupalServicesRequestManager();
        KeyRequestSigningInterceptorImpl requestSigningInterceptor = new KeyRequestSigningInterceptorImpl();
        requestSigningInterceptor.setDrupalDomain("workhabit.com");
        requestSigningInterceptor.setPrivateKey("9e47c52fae3c36baff404f7072e46547");
        manager.setRequestSigningInterceptor(requestSigningInterceptor);

        drupalSiteContext = new DrupalSiteContextV2Impl("http://ad.hourglassone.com");
        drupalSiteContext.setDrupalServicesRequestManager(manager);
    }

    @Test
    public void testSaveComment() throws DrupalFetchException {
        DrupalComment comment = new DrupalComment();
        comment.setNid(1);
        comment.setComment("test body 1");
        comment.setSubject("Test title");
        comment.setUid(0);
        //comment.setUid(1);
        drupalSiteContext.saveComment(comment);
    }

    @Test
    public void testGetNode() throws DrupalFetchException {
        DrupalNode node = drupalSiteContext.getNode(4);
        assertNotNull(node);
    }

    @Test
    public void testGetFileDirectory() throws DrupalFetchException {
        String fileDirectoryPath = drupalSiteContext.getFileDirectoryPath();
        assertNotNull(fileDirectoryPath);
        assertFalse("{\"#message\":\"Access denied\",\"#error\":true}".equals(fileDirectoryPath));
        assertTrue("sites/default/files".equals(fileDirectoryPath));
    }

    @Test
    public void testSaveNode() throws DrupalSaveException {
        DrupalNode node = new DrupalNode();
        node.setTitle("foo");
        HashMap<Integer, DrupalTaxonomyTerm> taxonomy = new HashMap<Integer, DrupalTaxonomyTerm>();
        DrupalTaxonomyTerm term = new DrupalTaxonomyTerm();
        term.setTid(1);
        term.setName("Term1");
        taxonomy.put(1, term);
        node.setTaxonomy(taxonomy);
        node.setType("page");
        node.setFormat(1);
        node.setBody("foo");
        node.setUid(1);
        int nid = drupalSiteContext.saveNode(node);
        assertNotNull(nid);

    }

    @Test
    public void testSaveNodeWithFile() throws DrupalFetchException, IOException {
        DrupalNode node = new DrupalNode();
        node.setTitle("foo");
        node.setType("page");
        node.setFormat(1);
        node.setBody("foo");
        node.setUid(1);
        ArrayList<DrupalField> fields = new ArrayList<DrupalField>();
        DrupalField field = new DrupalField();
        field.setName("title_image");
        ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testimage.jpg");

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            os.write(data);
        }
        inputStream.close();
        // Ensure all the bytes have been read in

        int fid = drupalSiteContext.saveFile(os.toByteArray(), "testimage.jpg");
        HashMap<String, String> value = new HashMap<String, String>();
        value.put("fid", String.valueOf(fid));
        values.add(value);
        field.setValues(values);
        fields.add(field);
        node.setFields(fields);
        int nid = drupalSiteContext.saveNode(node);
        assertFalse(nid == 0);
    }
    /*@Test
    public void testRegisterNewUser() throws DrupalSaveException {
        int i = drupalSiteContext.registerNewUser("test", "test123", "test@test.com");
        assertFalse(i == 0);

    }*/
}
