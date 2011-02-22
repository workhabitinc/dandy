package org.workhabit.drupal.api.site.v3;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.workhabit.drupal.api.entity.DrupalBody;
import org.workhabit.drupal.api.entity.DrupalComment;
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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

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
        DrupalBody commentBody = comment.getCommentBody();
        assertNotNull(commentBody);
        assertNotNull(commentBody.getUnd());
        assertNotNull(commentBody.getUnd().get(0));
        assertNotNull(commentBody.getUnd().get(0).getValue());
        assertEquals(body, commentBody.getUnd().get(0).getValue());
        assertEquals(format, commentBody.getUnd().get(0).getFormat());
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
    public void testFileUploadToken() throws DrupalFetchException
    {
        mockery.checking(new Expectations()
        {
            {

            }
        });
        context.getFileUploadToken();
    }

    public void testGetNode() {

    }
    /*
    context.getNode();
    context.getNodeView();
    */
}