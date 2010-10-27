package org.workhabit.andrupal.dao.impl;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.SqliteDatabaseType;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.workhabit.drupal.api.entity.DrupalNode;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 27, 2010, 1:57:28 PM
 */
public class DrupalNodeDaoTest {
    @Test
    public void testDrupalNodeSave() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.sqlite.SQLite");
        dataSource.setUrl("jdbc:sqlite:test.db:memory:");
        ConnectionSource connectionSource = new DataSourceConnectionSource(dataSource, new SqliteDatabaseType());
        DrupalNode node = new DrupalNode();
        int nid = TestData.getTestId();
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
        // create the DAO
        TableUtils.dropTable(connectionSource, node.getClass(), true);
        GenericDaoImpl<DrupalNode> drupalNodeDao = (GenericDaoImpl<DrupalNode>) DaoFactory.getInstanceForClass(connectionSource, node.getClass());
        assertNotNull(drupalNodeDao);
        
        drupalNodeDao.create(node);

        DrupalNode savedNode = drupalNodeDao.queryForId(Integer.toString(nid));
        assertNotNull(savedNode);
        assertEquals(title, savedNode.getTitle());
        assertEquals(nid, savedNode.getNid());
        assertEquals(name, savedNode.getName());
        assertEquals(now.getTime(), savedNode.getCreated().getTime());
        assertEquals(now.getTime(), savedNode.getChanged().getTime());
    }
}
