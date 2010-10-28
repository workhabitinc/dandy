package org.workhabit.andrupal.dao.impl;

import com.j256.ormlite.db.SqliteDatabaseType;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.workhabit.andrupal.dao.GenericDao;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.ReadItLater;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 27, 2010, 1:57:28 PM
 */
public class DrupalNodeDaoTest {
    private GenericDao<DrupalNode> drupalNodeDao;
    private GenericDao<ReadItLater> readItLaterDao;

    @Before
    public void setUp() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.sqlite.SQLite");
        dataSource.setUrl("jdbc:sqlite:test.db:memory:");

        ConnectionSource connectionSource = new DataSourceConnectionSource(dataSource, new SqliteDatabaseType());
        TableUtils.dropTable(connectionSource, DrupalNode.class, true);
        TableUtils.dropTable(connectionSource, ReadItLater.class, true);
        TableUtils.createTable(connectionSource, DrupalNode.class);
        TableUtils.createTable(connectionSource, ReadItLater.class);
        drupalNodeDao = DaoFactory.getInstanceForClass(connectionSource, DrupalNode.class);
        readItLaterDao = DaoFactory.getInstanceForClass(connectionSource, ReadItLater.class);

    }

    @Test
    public void testDrupalNodeSave() throws SQLException {

        DrupalNode node = createTestNode();
        // create the DAO
        assertNotNull(drupalNodeDao);

        drupalNodeDao.save(node);

        DrupalNode savedNode = drupalNodeDao.queryForId(Integer.toString(node.getNid()));
        assertNotNull(savedNode);
        assertEquals(node.getTitle(), savedNode.getTitle());
        assertEquals(node.getNid(), savedNode.getNid());
        assertEquals(node.getName(), savedNode.getName());
        assertEquals(node.getCreated().getTime(), savedNode.getCreated().getTime());
        assertEquals(node.getChanged().getTime(), savedNode.getChanged().getTime());

        // save the node again to test update
        savedNode.setBody(savedNode.getBody() + " foo");
        drupalNodeDao.save(savedNode);

        savedNode = drupalNodeDao.queryForId(node.getId());
        assertNotNull(savedNode);
        assertEquals(node.getNid(), savedNode.getNid());
    }

    @Test
    public void testSaveAllNodes() throws SQLException {
        List<DrupalNode> nodes = new ArrayList<DrupalNode>();
        DrupalNode node1 = createTestNode();
        DrupalNode node2 = createTestNode();
        nodes.add(node1);
        nodes.add(node2);

        drupalNodeDao.saveAll(nodes);

        List<DrupalNode> savedList = drupalNodeDao.getAll();
        Collections.sort(nodes, new Comparator<DrupalNode>() {
            public int compare(DrupalNode o1, DrupalNode o2) {
                if (o1.getNid() < o2.getNid()) {
                    return -1;
                } else if (o1.getNid() > o2.getNid()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        for (int i = 0; i < savedList.size(); i++) {
            assertTrue(nodes.get(i).getNid() == savedList.get(i).getNid());
        }
    }

    @Test
    public void testSaveReadItLaters() throws SQLException {
        List<ReadItLater> readItLaters = new ArrayList<ReadItLater>();
        readItLaters.add(createReadItLater());
        readItLaters.add(createReadItLater());
        readItLaterDao.saveAll(readItLaters);
    }

    @Test
    public void testGetAllOrdered() throws SQLException {
        List<ReadItLater> readItLaters = new ArrayList<ReadItLater>();
        ReadItLater read1 = createReadItLater();
        read1.setWeight(2);
        readItLaters.add(read1);
        ReadItLater read2 = createReadItLater();
        read2.setWeight(1);
        readItLaters.add(read2);
        readItLaterDao.saveAll(readItLaters);

        List<ReadItLater> savedReadItLaters = readItLaterDao.getAll(ReadItLater.WEIGHT_FIELD_NAME, true);
        assertNotNull(savedReadItLaters);
        assertEquals(1, savedReadItLaters.get(0).getWeight());
        assertEquals(2, savedReadItLaters.get(1).getWeight());
    }

    @Test
    public void testGetAllFailsOnMissingAnnotation() throws SQLException {
        Mockery context = new Mockery();
        final ConnectionSource mockConnectionSource = context.mock(ConnectionSource.class);
        final DatabaseConnection mockReadWriteConnection = context.mock(DatabaseConnection.class);
        final CompiledStatement mockCompiledStatement = context.mock(CompiledStatement.class);
        // this is needed to mock the connection to ORMLite
        context.checking(new Expectations() {
            {
                atLeast(1).of(mockConnectionSource).getDatabaseType();
                will(returnValue(new SqliteDatabaseType()));

                atLeast(1).of(mockConnectionSource).getReadWriteConnection();
                will(returnValue(mockReadWriteConnection));

                atLeast(1).of(mockReadWriteConnection).compileStatement(with(aNonNull(String.class)), with(equal(StatementBuilder.StatementType.EXECUTE)));
                will(returnValue(mockCompiledStatement));

                atLeast(1).of(mockCompiledStatement).executeUpdate();
                will(returnValue(1));

                atLeast(1).of(mockCompiledStatement).close();

                atLeast(1).of(mockConnectionSource).releaseConnection(mockReadWriteConnection);
            }
        });
        GenericDao<TestDrupalEntity> entityDao = new GenericDaoImpl<TestDrupalEntity>(mockConnectionSource, TestDrupalEntity.class);
        try {
            entityDao.getAll();
            fail("Should have thrown exception");
        } catch (RuntimeException e) {
            // ok
            assertEquals("Class " + TestDrupalEntity.class.getName() + " is missing @IdFieldName annotation.  Cannot autowire query for ID field.", e.getMessage());
        }
    }

    private ReadItLater createReadItLater() {
        DrupalNode node = createTestNode();
        ReadItLater readItLater = new ReadItLater();
        readItLater.setNode(node);
        readItLater.setId(node.getNid());
        readItLater.setWeight(TestData.getTestInt());
        return readItLater;
    }

    private DrupalNode createTestNode() {
        DrupalNode node = new DrupalNode();
        int nid = TestData.getTestInt();
        Date now = new Date();
        String title = TestData.getTestText();
        String name = TestData.getTestText();
        // set the required fields
        //
        node.setNid(nid);
        node.setTitle(title);
        node.setRevisionTimestamp(now);
        node.setName(name);
        node.setCreated(now);
        node.setChanged(now);
        return node;
    }

}
