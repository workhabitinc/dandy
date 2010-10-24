package org.workhabit.drupal.api;

import org.json.JSONException;
import org.junit.Test;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializer;
import org.workhabit.drupal.api.site.DrupalFetchException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 2:00:17 PM
 */

public class DrupalJsonObjectSerializerTest {
    @Test
    public void testDrupalNodeSerialization() throws DrupalFetchException, JSONException, IOException {
        DrupalJsonObjectSerializer<DrupalNode> serializer = new DrupalJsonObjectSerializer<DrupalNode>(DrupalNode.class);
        assertNotNull(serializer);

        String json = "{" +
                "\"#error\":false," +
                "\"#data\": {" +
                "   \"nid\":\"1\"," +
                "   \"type\":\"story\"," +
                "   \"language\":\"\"," +
                "   \"uid\":\"1\"," +
                "   \"status\":\"1\"," +
                "   \"created\":\"1285374480\"," +
                "   \"changed\":\"1287462879\"," +
                "   \"comment\":\"2\"," +
                "   \"promote\":\"1\"," +
                "   \"moderate\":\"0\"," +
                "   \"sticky\":\"0\"," +
                "   \"tnid\":\"0\"," +
                "   \"translate\":\"0\"," +
                "   \"vid\":\"1\"," +
                "   \"revision_uid\":\"1\"," +
                "   \"title\":\"Test Title\"," +
                "   \"body\":\"Test body. Adding some more text here.\\r\\n\\r\\nIntrinsicly provide access to process-centric experiences via business benefits. Synergistically network interoperable internal or \\\"organic\\\" sources for standards compliant experiences. Credibly predominate 24\\/365 products for superior initiatives.\\r\\n\\r\\nDynamically optimize 24\\/7 leadership skills rather than distinctive web-readiness. Proactively parallel task intuitive platforms before timely action items. Quickly enhance exceptional schemas before highly efficient sources. \\r\\n\"," +
                "   \"teaser\":\"Test body. Adding some more text here.\\r\\n\\r\\nIntrinsicly provide access to process-centric experiences via business benefits. Synergistically network interoperable internal or \\\"organic\\\" sources for standards compliant experiences. Credibly predominate 24\\/365 products for superior initiatives.\\r\\n\\r\\nDynamically optimize 24\\/7 leadership skills rather than distinctive web-readiness. Proactively parallel task intuitive platforms before timely action items. Quickly enhance exceptional schemas before highly efficient sources. \\r\\n\"," +
                "   \"log\":\"\"," +
                "   \"revision_timestamp\":\"1287462879\"," +
                "   \"format\":\"1\"," +
                "   \"name\":" +
                "   \"admin\"," +
                "   \"picture\":\"\"," +
                "   \"data\":\"a:0:{}\"," +
                "   \"last_comment_timestamp\":\"1285374480\"," +
                "   \"last_comment_name\":null," +
                "   \"comment_count\":\"0\"," +
                "   \"taxonomy\":{" +
                "       \"2\":{" +
                "           \"tid\":\"2\"," +
                "           \"vid\":\"1\"," +
                "           \"name\":\"Category 2\"," +
                "           \"description\":\"\"," +
                "           \"weight\":\"0\"" +
                "       }" +
                "    }" +
                "}}";
        DrupalNode node = serializer.unserialize(json);
        assertNotNull(node);
        assertNotNull(node.getNid());
        assertEquals(1, node.getNid());
        assertNotNull(node.getTitle());
        assertNotNull(node.getCreated());
        assertNotNull(node.getBody());
        assertNotNull(node.getChanged());
        assertNotNull(node.getTeaser());
        Map<Integer, DrupalTaxonomyTerm> terms = node.getTaxonomy();
        assertNotNull(terms);
        assertEquals(1, terms.size());
        DrupalTaxonomyTerm term = terms.get(2);
        assertNotNull(term);
        assertEquals(2, term.getTid());
        assertEquals(1, term.getVid());
        assertEquals("Category 2", term.getName());
        assertNotNull(node.getCommentCount());
        assertEquals(0, node.getCommentCount());
        assertNotNull(node.getData());
        assertNotNull(node.getComment());
        assertEquals(2, node.getComment());
        assertNull(node.getLastCommentName());
        assertNotNull(node.getLastCommentTimestamp());
        assertNotNull(node.getRevisionTimestamp());
    }

    @Test
    public void testDrupalNodeListSerialization() throws IOException, DrupalFetchException, JSONException {
        String json = "{\"#error\":false,\"#data\":[{\"nid\":\"3\",\"node_title\":\"More Content\",\"node_created\":\"1285472651\"},{\"nid\":\"2\",\"node_title\":\"Another display\",\"node_created\":\"1285468757\"},{\"nid\":\"1\",\"node_title\":\"Test Title\",\"node_created\":\"1285374480\"}]}";
        DrupalJsonObjectSerializer<DrupalNode> serializer = new DrupalJsonObjectSerializer<DrupalNode>(DrupalNode.class);
        assertNotNull(serializer);
        Collection<DrupalNode> nodeList = serializer.unserializeList(json);
        assertNotNull(nodeList);
        assertEquals(3, nodeList.size());

    }

    @Test
    public void testGetTaxonomyTerm() throws IOException, DrupalFetchException, JSONException {
        String json = "{\"#error\":false,\"#data\":[{\"tid\":\"1\",\"vid\":\"1\",\"name\":\"Category 1\",\"description\":\"\",\"weight\":\"0\",\"depth\":0,\"parents\":[\"0\"],\"node_count\":\"2\"},{\"tid\":\"2\",\"vid\":\"1\",\"name\":\"Category 2\",\"description\":\"\",\"weight\":\"0\",\"depth\":0,\"parents\":[\"0\"],\"node_count\":\"1\"}]}";
        /* CommonsHttpClientJsonRequestManager manager = new CommonsHttpClientJsonRequestManager();
        KeyRequestSigningInterceptorImpl requestSigningInterceptor = new KeyRequestSigningInterceptorImpl();
        requestSigningInterceptor.setDrupalDomain("workhabit.com");
        requestSigningInterceptor.setPrivateKey("9e47c52fae3c36baff404f7072e46547");
        manager.setRequestSigningInterceptor(requestSigningInterceptor);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("vid", 1);
        String json = manager.post("http://ad.hourglassone.com/services/json", "taxonomy.dictionary", data);
        System.out.println(json);*/
        DrupalJsonObjectSerializer<DrupalTaxonomyTerm> serializer = new DrupalJsonObjectSerializer<DrupalTaxonomyTerm>(DrupalTaxonomyTerm.class);
        List<DrupalTaxonomyTerm> taxonomyTerms = serializer.unserializeList(json);
        assertNotNull(taxonomyTerms);
        assertFalse(0 == taxonomyTerms.size());
        for (DrupalTaxonomyTerm term : taxonomyTerms) {
            assertNotNull(term);
            assertEquals(1, term.getVid());
            assertNotNull(term.getTid());
            assertNotNull(term.getName());
            assertTrue(term.getName().startsWith("Category "));
        }
    }
}

