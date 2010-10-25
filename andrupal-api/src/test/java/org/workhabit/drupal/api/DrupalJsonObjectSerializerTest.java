package org.workhabit.drupal.api;

import org.json.JSONException;
import org.junit.Test;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializer;
import org.workhabit.drupal.api.site.DrupalFetchException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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
        /*CommonsHttpClientJsonRequestManager manager = new CommonsHttpClientJsonRequestManager();
        KeyRequestSigningInterceptorImpl requestSigningInterceptor = new KeyRequestSigningInterceptorImpl();
        requestSigningInterceptor.setDrupalDomain("workhabit.com");
        requestSigningInterceptor.setPrivateKey("9e47c52fae3c36baff404f7072e46547");
        manager.setRequestSigningInterceptor(requestSigningInterceptor);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("view_name", "andrupal_recent");
        String json = manager.post("http://ad.hourglassone.com/services/json", "views.get", data);
        System.out.println(json);
        */
        String json = "{\"#error\":false,\"#data\":[{\"nid\":\"3\",\"type\":\"page\",\"language\":\"\",\"uid\":\"1\",\"status\":\"1\",\"created\":\"1285472651\",\"changed\":\"1287462859\",\"comment\":\"0\",\"promote\":\"0\",\"moderate\":\"0\",\"sticky\":\"0\",\"tnid\":\"0\",\"translate\":\"0\",\"vid\":\"3\",\"revision_uid\":\"1\",\"title\":\"More Content\",\"body\":\"\\u003ca href=\\\"http:\\/\\/google.com\\/\\\"\\u003eVisit Google\\u003c\\/a\\u003e\\r\\n\\r\\nAssertively empower robust resources with multimedia based ROI. Efficiently initiate mission-critical testing procedures without cross-platform e-tailers. Energistically engineer emerging \\\"outside the box\\\" thinking before fully tested process improvements. \\r\\n\\r\\nUniquely provide access to timely collaboration and idea-sharing without backward-compatible schemas. Dynamically integrate compelling bandwidth vis-a-vis compelling results. Competently generate one-to-one ROI after global products.\\r\\nAssertively empower robust resources with multimedia based ROI. Efficiently initiate mission-critical testing procedures without cross-platform e-tailers. Energistically engineer emerging \\\"outside the box\\\" thinking before fully tested process improvements. \\r\\n\\r\\nUniquely provide access to timely collaboration and idea-sharing without backward-compatible schemas. Dynamically integrate compelling bandwidth vis-a-vis compelling results. Competently generate one-to-one ROI after global products.\\r\\nAssertively empower robust resources with multimedia based ROI. Efficiently initiate mission-critical testing procedures without cross-platform e-tailers. Energistically engineer emerging \\\"outside the box\\\" thinking before fully tested process improvements. \\r\\n\\r\\nUniquely provide access to timely collaboration and idea-sharing without backward-compatible schemas. Dynamically integrate compelling bandwidth vis-a-vis compelling results. Competently generate one-to-one ROI after global products.Assertively empower robust resources with multimedia based ROI. Efficiently initiate mission-critical testing procedures without cross-platform e-tailers. Energistically engineer emerging \\\"outside the box\\\" thinking before fully tested process improvements. \\r\\n\\r\\nUniquely provide access to timely collaboration and idea-sharing without backward-compatible schemas. Dynamically integrate compelling bandwidth vis-a-vis compelling results. Competently generate one-to-one ROI after global products.\",\"teaser\":\"\\u003ca href=\\\"http:\\/\\/google.com\\/\\\"\\u003eVisit Google\\u003c\\/a\\u003e\\r\\n\\r\\nAssertively empower robust resources with multimedia based ROI. Efficiently initiate mission-critical testing procedures without cross-platform e-tailers. Energistically engineer emerging \\\"outside the box\\\" thinking before fully tested process improvements. \\r\\n\\r\\nUniquely provide access to timely collaboration and idea-sharing without backward-compatible schemas. Dynamically integrate compelling bandwidth vis-a-vis compelling results. Competently generate one-to-one ROI after global products.\\r\",\"log\":\"\",\"revision_timestamp\":\"1287462859\",\"format\":\"1\",\"name\":\"admin\",\"picture\":\"\",\"data\":\"a:0:{}\",\"last_comment_timestamp\":\"1285472651\",\"last_comment_name\":null,\"comment_count\":\"0\",\"taxonomy\":{\"1\":{\"tid\":\"1\",\"vid\":\"1\",\"name\":\"Category 1\",\"description\":\"\",\"weight\":\"0\"}}},{\"nid\":\"2\",\"type\":\"page\",\"language\":\"\",\"uid\":\"1\",\"status\":\"1\",\"created\":\"1285468757\",\"changed\":\"1287462868\",\"comment\":\"0\",\"promote\":\"0\",\"moderate\":\"0\",\"sticky\":\"0\",\"tnid\":\"0\",\"translate\":\"0\",\"vid\":\"2\",\"revision_uid\":\"1\",\"title\":\"Another display\",\"body\":\"Giving this a try\",\"teaser\":\"Giving this a try\",\"log\":\"\",\"revision_timestamp\":\"1287462868\",\"format\":\"1\",\"name\":\"admin\",\"picture\":\"\",\"data\":\"a:0:{}\",\"last_comment_timestamp\":\"1285468757\",\"last_comment_name\":null,\"comment_count\":\"0\",\"taxonomy\":{\"1\":{\"tid\":\"1\",\"vid\":\"1\",\"name\":\"Category 1\",\"description\":\"\",\"weight\":\"0\"}}},{\"nid\":\"1\",\"type\":\"story\",\"language\":\"\",\"uid\":\"1\",\"status\":\"1\",\"created\":\"1285374480\",\"changed\":\"1287462879\",\"comment\":\"2\",\"promote\":\"1\",\"moderate\":\"0\",\"sticky\":\"0\",\"tnid\":\"0\",\"translate\":\"0\",\"vid\":\"1\",\"revision_uid\":\"1\",\"title\":\"Test Title\",\"body\":\"Test body. Adding some more text here.\\r\\n\\r\\nIntrinsicly provide access to process-centric experiences via business benefits. Synergistically network interoperable internal or \\\"organic\\\" sources for standards compliant experiences. Credibly predominate 24\\/365 products for superior initiatives.\\r\\n\\r\\nDynamically optimize 24\\/7 leadership skills rather than distinctive web-readiness. Proactively parallel task intuitive platforms before timely action items. Quickly enhance exceptional schemas before highly efficient sources. \\r\\n\",\"teaser\":\"Test body. Adding some more text here.\\r\\n\\r\\nIntrinsicly provide access to process-centric experiences via business benefits. Synergistically network interoperable internal or \\\"organic\\\" sources for standards compliant experiences. Credibly predominate 24\\/365 products for superior initiatives.\\r\\n\\r\\nDynamically optimize 24\\/7 leadership skills rather than distinctive web-readiness. Proactively parallel task intuitive platforms before timely action items. Quickly enhance exceptional schemas before highly efficient sources. \\r\\n\",\"log\":\"\",\"revision_timestamp\":\"1287462879\",\"format\":\"1\",\"name\":\"admin\",\"picture\":\"\",\"data\":\"a:0:{}\",\"last_comment_timestamp\":\"1285374480\",\"last_comment_name\":null,\"comment_count\":\"0\",\"taxonomy\":{\"2\":{\"tid\":\"2\",\"vid\":\"1\",\"name\":\"Category 2\",\"description\":\"\",\"weight\":\"0\"}}}]}";

        DrupalJsonObjectSerializer<DrupalNode> serializer = new DrupalJsonObjectSerializer<DrupalNode>(DrupalNode.class);
        assertNotNull(serializer);
        List<DrupalNode> nodeList = serializer.unserializeList(json);
        assertNotNull(nodeList);
        assertEquals(3, nodeList.size());
        for (DrupalNode drupalNode : nodeList) {
            assertNotNull(drupalNode);
            assertNotNull(drupalNode.getTitle());
        }

    }

    @Test
    public void testGetTaxonomyTerm() throws IOException, DrupalFetchException, JSONException {
        String json = "{\"#error\":false,\"#data\":[{\"tid\":\"1\",\"vid\":\"1\",\"name\":\"Category 1\",\"description\":\"\",\"weight\":\"0\",\"depth\":0,\"parents\":[\"0\"],\"node_count\":\"2\"},{\"tid\":\"2\",\"vid\":\"1\",\"name\":\"Category 2\",\"description\":\"\",\"weight\":\"0\",\"depth\":0,\"parents\":[\"0\"],\"node_count\":\"1\"}]}";

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

    @Test
    public void testSerializeDrupalNode() {
        DrupalNode node = new DrupalNode();
        node.setBody("Test Body");
        node.setChanged(new Date());
        node.setComment(2);
        node.setCommentCount(0);
        node.setCreated(new Date());
        node.setData("a:0:{}");
        node.setFormat(1);
        node.setLastCommentName(null);
        node.setLastCommentTimestamp(new Date());
        node.setLog("");
        node.setModerate(false);
        node.setName("admin");
        node.setNid(1);
        node.setPicture("");
        node.setPromote(true);
        node.setRevisionTimestamp(new Date());
        node.setStatus(true);
        node.setSticky(true);
        Map<Integer, DrupalTaxonomyTerm> termArrayList = new HashMap<Integer, DrupalTaxonomyTerm>();
        DrupalTaxonomyTerm term = new DrupalTaxonomyTerm();
        term.setVid(1);
        term.setTid(2);
        term.setDepth(1);
        term.setDescription("Test Description");
        term.setNodeCount(1);
        termArrayList.put(2, term);
        node.setTaxonomy(termArrayList);
        node.setTitle("Foo");
        DrupalJsonObjectSerializer<DrupalNode> serializer = new DrupalJsonObjectSerializer<DrupalNode>(DrupalNode.class);
        String json = serializer.serialize(node);
        assertNotNull(json);
    }

    @Test
    public void testSerializeDrupalTaxonomyTerm() {
        DrupalTaxonomyTerm term = new DrupalTaxonomyTerm();
        term.setVid(1);
        term.setTid(1);
        term.setDescription("Test description");
        term.setName("Test Title");
        DrupalJsonObjectSerializer<DrupalTaxonomyTerm> serializer = new DrupalJsonObjectSerializer<DrupalTaxonomyTerm>(DrupalTaxonomyTerm.class);
        String json = serializer.serialize(term);
        assertNotNull(json);

    }
}

