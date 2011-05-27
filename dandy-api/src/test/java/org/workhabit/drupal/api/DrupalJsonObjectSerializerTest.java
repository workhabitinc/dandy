package org.workhabit.drupal.api;

import org.json.JSONException;
import org.junit.Test;
import org.workhabit.drupal.api.entity.DrupalBody;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializer;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 2:00:17 PM
 */

public class DrupalJsonObjectSerializerTest
{
    /**
     * Test the json returned by a call to node.getStream with argument nid=1
     *
     * @throws Exception on error
     */
    @Test
    public void testDrupalNodeSerialization() throws Exception
    {
        DrupalJsonObjectSerializer<DrupalNode> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalNode.class);
        assertNotNull(serializer);

        String json = "{\"vid\":\"1\",\"uid\":\"2\",\"title\":\"Test article\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"1\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297197059\",\"changed\":\"1297795608\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297795608\",\"revision_uid\":\"1\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297197059\",\"last_comment_name\":null,\"last_comment_uid\":\"1\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null,\"uri\":\"http:\\/\\/se.local\\/dandy\\/node\\/1\"}";

        DrupalNode node = serializer.unserialize(json);
        assertNotNull(node);
        assertNotNull(node.getNid());
        assertEquals(1, node.getNid());
        assertNotNull(node.getTitle());
        assertNotNull(node.getCreated());
        assertNotNull(node.getBody());
        assertNotNull(node.getChanged());
        assertNotNull(node.getCommentCount());
        assertEquals(0, node.getCommentCount());
        assertNotNull(node.getComment());
        assertEquals(2, node.getComment());
        assertNull(node.getLastCommentName());
        assertNotNull(node.getLastCommentTimestamp());
        assertNotNull(node.getRevisionTimestamp());
    }

    /**
     * Test unserialize of a list of nodes from a call to views.getStream with view_name="andrupal_recent"
     *
     * @throws Exception on error
     */
    @Test
    public void testDrupalNodeListSerialization() throws Exception
    {

        String json = "[{\"vid\":\"1\",\"uid\":\"2\",\"title\":\"Test article\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"1\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297197059\",\"changed\":\"1297795608\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297795608\",\"revision_uid\":\"1\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297197059\",\"last_comment_name\":null,\"last_comment_uid\":\"1\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null,\"uri\":\"http:\\/\\/se.local\\/dandy\\/node\\/1\"}," +
                      "{\"vid\":\"1\",\"uid\":\"2\",\"title\":\"Test article\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"1\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297197059\",\"changed\":\"1297795608\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297795608\",\"revision_uid\":\"1\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297197059\",\"last_comment_name\":null,\"last_comment_uid\":\"1\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null,\"uri\":\"http:\\/\\/se.local\\/dandy\\/node\\/1\"}," +
                      "{\"vid\":\"1\",\"uid\":\"2\",\"title\":\"Test article\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"1\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297197059\",\"changed\":\"1297795608\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297795608\",\"revision_uid\":\"1\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297197059\",\"last_comment_name\":null,\"last_comment_uid\":\"1\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null,\"uri\":\"http:\\/\\/se.local\\/dandy\\/node\\/1\"}]";

        DrupalJsonObjectSerializer<DrupalNode> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalNode.class);
        assertNotNull(serializer);
        List<DrupalNode> nodeList = serializer.unserializeList(json);
        assertNotNull(nodeList);
        assertEquals(3, nodeList.size());
        for (DrupalNode drupalNode : nodeList) {
            assertNotNull(drupalNode);
            assertNotNull(drupalNode.getTitle());
        }

    }

    /**
     * Test serialization
     * <p/>
     * TODO: This could use some cleanup and testing of individual string tokens
     *
     * @throws org.json.JSONException on error
     */
    @Test
    public void testSerializeDrupalNode() throws JSONException
    {
        DrupalNode node = new DrupalNode();
        DrupalBody body = new DrupalBody();
        body.setFormat("filtered_html");
        body.setSummary("");
        body.setSafeSummary("");
        body.setValue("test body");
        body.setSafeValue("<p>test</p>\n");
        Map<String, DrupalBody> bodyMap = new HashMap<String, DrupalBody>();
        bodyMap.put("und", body);
        node.setBody(bodyMap);
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
        node.setStatus(true);
        HashMap<Integer, DrupalTaxonomyTerm> terms = new HashMap<Integer, DrupalTaxonomyTerm>();
        DrupalTaxonomyTerm term = new DrupalTaxonomyTerm();
        term.setVid(1);
        term.setTid(2);
        term.setDepth(1);
        term.setDescription("Test Description");
        term.setNodeCount(1);
        terms.put(term.getTid(), term);
        node.setTaxonomy(terms);
        node.setTitle("Foo");
        DrupalJsonObjectSerializer<DrupalNode> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalNode.class);
        String json = serializer.serialize(node);
        assertNotNull(json);
    }

    /**
     * Test serialization of drupal Taxonomy Term.
     *
     * @throws org.json.JSONException if there's an error
     */
    @Test
    public void testSerializeDrupalTaxonomyTerm() throws JSONException
    {
        DrupalTaxonomyTerm term = new DrupalTaxonomyTerm();
        term.setVid(1);
        term.setTid(1);
        term.setDescription("Test description");
        term.setName("Test Title");
        DrupalJsonObjectSerializer<DrupalTaxonomyTerm> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalTaxonomyTerm.class);
        String json = serializer.serialize(term);
        assertNotNull(json);
    }
}

