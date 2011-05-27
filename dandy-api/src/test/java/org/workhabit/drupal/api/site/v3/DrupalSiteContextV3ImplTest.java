package org.workhabit.drupal.api.site.v3;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.workhabit.drupal.api.entity.DrupalBody;
import org.workhabit.drupal.api.entity.DrupalComment;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalLoginException;
import org.workhabit.drupal.api.site.impl.v3.DrupalSiteContextV3Impl;
import org.workhabit.drupal.api.site.support.GenericCookie;
import org.workhabit.drupal.api.site.v3.local.TestData;
import org.workhabit.drupal.http.DrupalServicesRequestManager;
import org.workhabit.drupal.http.ServicesResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/21/11, 10:29 AM
 */
public class DrupalSiteContextV3ImplTest
{
    private DrupalSiteContextV3Impl context;
    Mockery mockery;
    private DrupalServicesRequestManager mockRequestManager;

    @Before
    public void setUp()
    {
        context = new DrupalSiteContextV3Impl("http://se.local", "dandy");
        mockery = new Mockery();
        mockRequestManager = mockery.mock(DrupalServicesRequestManager.class);
        context.setRequestManager(this.mockRequestManager);
    }

    @Test
    public void testGetComment() throws DrupalFetchException, IOException
    {
        mockery.checking(new Expectations()
        {
            {
                one(mockRequestManager).getString("http://se.local/dandy/comment/1.json");
                String json = "{\n" +
                              "    \"cid\": \"1\",\n" +
                              "    \"pid\": \"0\",\n" +
                              "    \"nid\": \"2\",\n" +
                              "    \"uid\": \"1\",\n" +
                              "    \"subject\": \"O hai.\",\n" +
                              "    \"hostname\": \"127.0.0.1\",\n" +
                              "    \"created\": \"1297879941\",\n" +
                              "    \"changed\": \"1297879941\",\n" +
                              "    \"status\": \"1\",\n" +
                              "    \"thread\": \"01\\/\",\n" +
                              "    \"name\": \"admin\",\n" +
                              "    \"mail\": \"\",\n" +
                              "    \"homepage\": \"\",\n" +
                              "    \"language\": \"und\",\n" +
                              "    \"node_type\": \"comment_node_article\",\n" +
                              "    \"registered_name\": \"admin\",\n" +
                              "    \"u_uid\": \"1\",\n" +
                              "    \"signature\": \"\",\n" +
                              "    \"signature_format\": null,\n" +
                              "    \"picture\": \"0\",\n" +
                              "    \"new\": 1,\n" +
                              "    \"comment_body\": {\n" +
                              "        \"und\": [\n" +
                              "            {\n" +
                              "                \"value\": \"My comment.  Let me show u it.\",\n" +
                              "                \"format\": \"filtered_html\",\n" +
                              "                \"safe_value\": \"<p>My comment.  Let me show u it.<\\/p>\\n\"\n" +
                              "            }\n" +
                              "        ]\n" +
                              "    },\n" +
                              "    \"rdf_mapping\": {\n" +
                              "        \"rdftype\": [\n" +
                              "            \"sioc:Post\",\n" +
                              "            \"sioct:Comment\"\n" +
                              "        ],\n" +
                              "        \"title\": {\n" +
                              "            \"predicates\": [\n" +
                              "                \"dc:title\"\n" +
                              "            ]\n" +
                              "        },\n" +
                              "        \"created\": {\n" +
                              "            \"predicates\": [\n" +
                              "                \"dc:date\",\n" +
                              "                \"dc:created\"\n" +
                              "            ],\n" +
                              "            \"datatype\": \"xsd:dateTime\",\n" +
                              "            \"callback\": \"date_iso8601\"\n" +
                              "        },\n" +
                              "        \"changed\": {\n" +
                              "            \"predicates\": [\n" +
                              "                \"dc:modified\"\n" +
                              "            ],\n" +
                              "            \"datatype\": \"xsd:dateTime\",\n" +
                              "            \"callback\": \"date_iso8601\"\n" +
                              "        },\n" +
                              "        \"comment_body\": {\n" +
                              "            \"predicates\": [\n" +
                              "                \"content:encoded\"\n" +
                              "            ]\n" +
                              "        },\n" +
                              "        \"pid\": {\n" +
                              "            \"predicates\": [\n" +
                              "                \"sioc:reply_of\"\n" +
                              "            ],\n" +
                              "            \"type\": \"rel\"\n" +
                              "        },\n" +
                              "        \"uid\": {\n" +
                              "            \"predicates\": [\n" +
                              "                \"sioc:has_creator\"\n" +
                              "            ],\n" +
                              "            \"type\": \"rel\"\n" +
                              "        },\n" +
                              "        \"name\": {\n" +
                              "            \"predicates\": [\n" +
                              "                \"foaf:name\"\n" +
                              "            ]\n" +
                              "        }\n" +
                              "    },\n" +
                              "    \"rdf_data\": {\n" +
                              "        \"date\": {\n" +
                              "            \"property\": [\n" +
                              "                \"dc:date\",\n" +
                              "                \"dc:created\"\n" +
                              "            ],\n" +
                              "            \"content\": \"2011-02-16T10:12:21-08:00\",\n" +
                              "            \"datatype\": \"xsd:dateTime\"\n" +
                              "        },\n" +
                              "        \"nid_uri\": \"\\/node\\/2\"\n" +
                              "    }\n" +
                              "}";
                ServicesResponse response = new ServicesResponse();
                //noinspection NullableProblems
                response.setReasonPhrase(null);
                response.setStatusCode(200);
                response.setResponseBody(json);
                will(returnValue(response));
            }
        });
        DrupalComment comment = context.getComment(1);
        assertCommentValid(comment, 1, 2, "O hai.", 1297879941000L, 1297879941000L, true, "My comment.  Let me show u it.", "filtered_html");
    }

    private void assertCommentValid(DrupalComment comment, int cid, int nid, String subject, long createdDate, long changedDate, boolean status, String body, String format) throws DrupalFetchException
    {
        assertNotNull(comment);
        assertEquals(cid, comment.getCid());
        assertEquals(nid, comment.getNid());
        assertEquals(subject, comment.getSubject());
        assertEquals(new Date(createdDate), comment.getCreated());
        assertEquals(new Date(changedDate), comment.getChanged());
        assertEquals(String.valueOf(cid), comment.getId());
        assertEquals(status, comment.isStatus());
        Map<String, List<DrupalBody>> commentBody = comment.getCommentBody();
        assertNotNull(commentBody);
        assertEquals(1, commentBody.size());
    }

    @Test
    public void testGetComments() throws DrupalFetchException, IOException
    {
        mockery.checking(new Expectations()
        {
            {
                String json = "[{\"cid\":\"1\",\"pid\":\"0\",\"nid\":\"2\",\"uid\":\"1\",\"subject\":\"O hai.\",\"hostname\":\"127.0.0.1\",\"created\":\"1297879941\",\"changed\":\"1297879941\",\"status\":\"1\",\"thread\":\"01\\/\",\"name\":\"admin\",\"mail\":\"\",\"homepage\":\"\",\"language\":\"und\",\"node_type\":\"comment_node_article\",\"registered_name\":\"admin\",\"u_uid\":\"1\",\"signature\":\"\",\"signature_format\":null,\"picture\":\"0\",\"new\":1,\"comment_body\":{\"und\":[{\"value\":\"My comment.  Let me show u it.\",\"format\":\"filtered_html\",\"safe_value\":\"<p>My comment.  Let me show u it.<\\/p>\\n\"}]},\"rdf_mapping\":{\"rdftype\":[\"sioc:Post\",\"sioct:Comment\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"comment_body\":{\"predicates\":[\"content:encoded\"]},\"pid\":{\"predicates\":[\"sioc:reply_of\"],\"type\":\"rel\"},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]}},\"rdf_data\":{\"date\":{\"property\":[\"dc:date\",\"dc:created\"],\"content\":\"2011-02-16T10:12:21-08:00\",\"datatype\":\"xsd:dateTime\"},\"nid_uri\":\"\\/node\\/2\"}}]";
                one(mockRequestManager).post(
                        with(
                                equal("http://se.local/dandy/comment/loadNodeComments.json")
                        ),
                        with(
                                hasEntry("nid", (Object)2)
                        )
                );
                ServicesResponse response = new ServicesResponse();
                response.setStatusCode(200);
                response.setResponseBody(json);
                will(returnValue(response));
            }
        });
        List<DrupalComment> comments = context.getComments(2);
        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertCommentValid(comments.get(0), 1, 2, "O hai.", 1297879941000L, 1297879941000L, true, "My comment.  Let me show u it.", "filtered_html");

    }

    @Test
    public void testGetCommentsWithArgs() throws DrupalFetchException, IOException
    {
        mockery.checking(new Expectations()
        {
            {
                String json = "[{\"cid\":\"1\",\"pid\":\"0\",\"nid\":\"2\",\"uid\":\"1\",\"subject\":\"O hai.\",\"hostname\":\"127.0.0.1\",\"created\":\"1297879941\",\"changed\":\"1297879941\",\"status\":\"1\",\"thread\":\"01\\/\",\"name\":\"admin\",\"mail\":\"\",\"homepage\":\"\",\"language\":\"und\",\"node_type\":\"comment_node_article\",\"registered_name\":\"admin\",\"u_uid\":\"1\",\"signature\":\"\",\"signature_format\":null,\"picture\":\"0\",\"new\":1,\"comment_body\":{\"und\":[{\"value\":\"My comment.  Let me show u it.\",\"format\":\"filtered_html\",\"safe_value\":\"<p>My comment.  Let me show u it.<\\/p>\\n\"}]},\"rdf_mapping\":{\"rdftype\":[\"sioc:Post\",\"sioct:Comment\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"comment_body\":{\"predicates\":[\"content:encoded\"]},\"pid\":{\"predicates\":[\"sioc:reply_of\"],\"type\":\"rel\"},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]}},\"rdf_data\":{\"date\":{\"property\":[\"dc:date\",\"dc:created\"],\"content\":\"2011-02-16T10:12:21-08:00\",\"datatype\":\"xsd:dateTime\"},\"nid_uri\":\"\\/node\\/2\"}}]";
                one(mockRequestManager).post(
                        with(
                                equal("http://se.local/dandy/comment/loadNodeComments.json")
                        ),
                        with(
                                allOf(
                                        hasEntry("nid", (Object)2),
                                        hasEntry("start", (Object)1),
                                        hasEntry("count", (Object)1)
                                )

                        )
                );
                ServicesResponse response = new ServicesResponse();
                response.setStatusCode(200);
                response.setResponseBody(json);
                will(returnValue(response));
            }
        });
        List<DrupalComment> comments = context.getComments(2, 1, 1);
        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertCommentValid(comments.get(0), 1, 2, "O hai.", 1297879941000L, 1297879941000L, true, "My comment.  Let me show u it.", "filtered_html");
    }

    @Test
    public void testGetCurrentUser() throws DrupalFetchException, DrupalLoginException, IOException
    {
        mockery.checking(new Expectations()
        {
            {
                one(mockRequestManager).post(with(equal("http://se.local/dandy/user/login.json")), with(allOf(hasEntry("username", (Object)"testuser"), hasEntry("password", (Object)"testpass"))));
                ServicesResponse response = new ServicesResponse();
                response.setStatusCode(200);
                response.setResponseBody("{\"sessid\":\"W0ufrnm4HHOg0yf9St54rL4P_TSWFFap6rnTVLE0rrU\",\"session_name\":\"SESSce02cc8e89a8ae4b7e8421568fa2d536\",\"user\":{\"uid\":\"2\",\"name\":\"testuser\",\"pass\":\"$S$CZ\\/EdhnoY6hvA3f5NbsIUFDA9UUAsDRUlbMRfZEyWpf7Njsx.TLs\",\"mail\":\"acs@hourglassone.com\",\"theme\":\"\",\"signature\":\"\",\"signature_format\":\"filtered_html\",\"created\":\"1297793491\",\"access\":\"1298313596\",\"login\":\"0\",\"status\":\"1\",\"timezone\":\"America\\/Los_Angeles\",\"language\":\"\",\"picture\":null,\"init\":\"acs@hourglassone.com\",\"data\":false,\"roles\":{\"2\":\"authenticated user\"},\"rdf_mapping\":{\"rdftype\":[\"sioc:UserAccount\"],\"name\":{\"predicates\":[\"foaf:name\"]},\"homepage\":{\"predicates\":[\"foaf:page\"],\"type\":\"rel\"}}}}");
                will(returnValue(response));
            }
        });
        DrupalUser drupalUser = context.login("testuser", "testpass");
        assertNotNull(drupalUser);

        DrupalUser currentUser = context.getCurrentUser();
        assertEquals(drupalUser, currentUser);
    }

    @Test
    public void testGetCurrentUserCookie()
    {
        mockery.checking(new Expectations()
        {
            {
                one(mockRequestManager).getCookies();
                List<GenericCookie> cookieList = new ArrayList<GenericCookie>();
                GenericCookie cookie = new GenericCookie();
                cookie.setName("name");
                cookie.setValue("value");
                cookie.setVersion(2);
                cookie.setComment("test comment");
                cookie.setExpiryDate(new Date());
                cookie.setPath("/");
                cookie.setDomain("local");
                cookieList.add(cookie);
                will(returnValue(cookieList));
            }
        });
        ArrayList<GenericCookie> cookies = context.getCurrentUserCookie();
        assertNotNull(cookies);
        assertEquals(1, cookies.size());
        GenericCookie genericCookie = cookies.get(0);
        assertEquals("local", genericCookie.getDomain());
        assertEquals("name", genericCookie.getName());
        assertEquals("value", genericCookie.getValue());
    }

    @Test
    public void testGetFileDirectoryPath() throws DrupalFetchException, IOException
    {
        mockery.checking(new Expectations()
        {
            {
                one(mockRequestManager).post("http://se.local/dandy/file/getDirectoryPath.json", "");
                ServicesResponse response = new ServicesResponse();
                response.setResponseBody(TestData.getTestTitle());
                response.setStatusCode(200);
                will(returnValue(response));
            }
        });
        String fileDirectoryPath = context.getFileDirectoryPath();
        assertNotNull(fileDirectoryPath);
    }

    @Test
    public void testGetFileStream() throws IOException
    {
        final InputStream stream = new ByteArrayInputStream("test".getBytes());
        final String filepath = "sites/default/files/testfile.jpg";
        mockery.checking(new Expectations()
        {
            {
                one(mockRequestManager).getStream(filepath);
                will(returnValue(stream));
            }
        });

        InputStream fileStream = context.getFileStream(filepath);
        assertNotNull(fileStream);
        assertSame(stream, fileStream);
    }

    @Test
    public void testFileUploadToken() throws DrupalFetchException, IOException
    {
        final String testTitle = TestData.getTestTitle();
        mockery.checking(new Expectations()
        {
            {
                one(mockRequestManager).getString("http://se.local/dandy/file/fileUploadToken.json");
                ServicesResponse response = new ServicesResponse();
                response.setResponseBody(testTitle);
                response.setStatusCode(200);
                will(returnValue(response));
            }
        });
        String fileUploadToken = context.getFileUploadToken();
        assertNotNull(fileUploadToken);
        assertEquals(testTitle, fileUploadToken);
    }

    @Test
    public void testGetNode() throws DrupalFetchException, IOException
    {
        mockery.checking(new Expectations()
        {
            {
                one(mockRequestManager).getString("http://se.local/dandy/node/2.json");
                ServicesResponse response = new ServicesResponse();
                String json = "{\"vid\":\"1\",\"uid\":\"2\",\"title\":\"JUnitdfd352c6-3a39-11e0-a2a1-ab6d18dbeac4\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"1\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297908106\",\"changed\":\"1297908106\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297908106\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297197059\",\"last_comment_name\":null,\"last_comment_uid\":\"1\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null,\"uri\":\"http:\\/\\/se.local\\/dandy\\/node\\/1\"}";
                response.setResponseBody(json);
                response.setStatusCode(200);
                will(returnValue(response));
            }
        });
        DrupalNode node = context.getNode(2);
        assertNotNull(node);
    }

    @Test
    public void testGetNodeView() throws DrupalFetchException, IOException
    {
        mockery.checking(new Expectations()
        {
            {
                one(mockRequestManager).getString("http://se.local/dandy/views/dandy_recent.json?offset=0&limit=0");
                ServicesResponse response = new ServicesResponse();
                response.setResponseBody("[{\"vid\":\"13\",\"uid\":\"2\",\"title\":\"JUnitdfd352c6-3a39-11e0-a2a1-ab6d18dbeac4\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"13\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297908369\",\"changed\":\"1297908369\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297908369\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297908369\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"12\",\"uid\":\"2\",\"title\":\"JUnite3de53b7-3a39-11e0-a2a1-ab6d18dbeac4\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"12\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297908113\",\"changed\":\"1297908113\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297908113\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297908113\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"1\",\"uid\":\"2\",\"title\":\"JUnitdfd352c6-3a39-11e0-a2a1-ab6d18dbeac4\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"1\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297908106\",\"changed\":\"1297908106\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297908106\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297197059\",\"last_comment_name\":null,\"last_comment_uid\":\"1\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"11\",\"uid\":\"2\",\"title\":\"JUnitb3e8c200-3a38-11e0-ae56-5d66fc9905bb\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"11\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297908104\",\"changed\":\"1297908104\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297908104\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297908104\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"10\",\"uid\":\"2\",\"title\":\"JUnitb7c86d31-3a38-11e0-ae56-5d66fc9905bb\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"10\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297907609\",\"changed\":\"1297907609\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297907609\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297907609\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"9\",\"uid\":\"2\",\"title\":\"JUnit032b9e6b-3a36-11e0-ab03-8127e3213445\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"9\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297907601\",\"changed\":\"1297907601\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297907601\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297907601\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"8\",\"uid\":\"2\",\"title\":\"JUnit070b97bc-3a36-11e0-ab03-8127e3213445\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"8\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297906454\",\"changed\":\"1297906454\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297906454\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297906454\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"7\",\"uid\":\"2\",\"title\":\"JUnitd1dc91ef-3a21-11e0-ba0b-21a13414a5b4\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"7\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297906445\",\"changed\":\"1297906445\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297906445\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297906445\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"6\",\"uid\":\"2\",\"title\":\"JUnitede5e8bc-3a35-11e0-93dd-b9f2b843d6d8\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"6\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297906412\",\"changed\":\"1297906412\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297906412\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297906412\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"5\",\"uid\":\"2\",\"title\":\"Test article\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"5\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297885120\",\"changed\":\"1297885120\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297885120\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297885120\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"4\",\"uid\":\"2\",\"title\":\"Test article\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"4\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297798668\",\"changed\":\"1297798668\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297798668\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297798668\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"3\",\"uid\":\"2\",\"title\":\"Test article\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"3\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297797307\",\"changed\":\"1297797307\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297797307\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"0\",\"last_comment_timestamp\":\"1297797307\",\"last_comment_name\":null,\"last_comment_uid\":\"2\",\"comment_count\":\"0\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null},{\"vid\":\"2\",\"uid\":\"2\",\"title\":\"Test article\",\"log\":\"\",\"status\":\"1\",\"comment\":\"2\",\"promote\":\"1\",\"sticky\":\"0\",\"nid\":\"2\",\"type\":\"article\",\"language\":\"und\",\"created\":\"1297797129\",\"changed\":\"1297797129\",\"tnid\":\"0\",\"translate\":\"0\",\"revision_timestamp\":\"1297797129\",\"revision_uid\":\"2\",\"body\":{\"und\":[{\"value\":\"test\",\"summary\":\"\",\"format\":\"filtered_html\",\"safe_value\":\"<p>test<\\/p>\\n\",\"safe_summary\":\"\"}]},\"field_tags\":[],\"field_image\":[],\"rdf_mapping\":{\"field_image\":{\"predicates\":[\"og:image\",\"rdfs:seeAlso\"],\"type\":\"rel\"},\"field_tags\":{\"predicates\":[\"dc:subject\"],\"type\":\"rel\"},\"rdftype\":[\"sioc:Item\",\"foaf:Document\"],\"title\":{\"predicates\":[\"dc:title\"]},\"created\":{\"predicates\":[\"dc:date\",\"dc:created\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"changed\":{\"predicates\":[\"dc:modified\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"},\"body\":{\"predicates\":[\"content:encoded\"]},\"uid\":{\"predicates\":[\"sioc:has_creator\"],\"type\":\"rel\"},\"name\":{\"predicates\":[\"foaf:name\"]},\"comment_count\":{\"predicates\":[\"sioc:num_replies\"],\"datatype\":\"xsd:integer\"},\"last_activity\":{\"predicates\":[\"sioc:last_activity_date\"],\"datatype\":\"xsd:dateTime\",\"callback\":\"date_iso8601\"}},\"cid\":\"1\",\"last_comment_timestamp\":\"1297879941\",\"last_comment_name\":\"\",\"last_comment_uid\":\"1\",\"comment_count\":\"1\",\"name\":\"testuser\",\"picture\":\"0\",\"data\":null}]");
                response.setStatusCode(200);
                will(returnValue(response));
            }
        });
        List<DrupalNode> nodeList = context.getNodeView("dandy_recent");
        assertNotNull(nodeList);
        assertEquals(13, nodeList.size());
    }

    @After
    public void tearDown()
    {
        mockery.assertIsSatisfied();
    }
}