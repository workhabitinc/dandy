package com.workhabit.drupal.publisher;

import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Bundle;
import android.widget.Button;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.workhabit.drupal.publisher.support.ReadItLaterDatabaseHelper;
import org.workhabit.dandy.dao.GenericDao;
import org.workhabit.dandy.dao.impl.DaoFactory;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.ReadItLater;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/3/10, 8:42 PM
 */
public class DrupalReadLaterActivity extends AbstractDrupalNodeListActivity {
    private GenericDao<ReadItLater> readLaterDao;
    GenericDao<DrupalNode> drupalNodeDao;
    private static boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button b = (Button) findViewById(R.id.button_readlater);
        b.setCompoundDrawablesWithIntrinsicBounds(
                null,
                getResources().getDrawable(R.drawable.button_readitlater_icon_active),
                null,
                null
        );
    }

    @Override
    protected List<DrupalNode> doGetNodes(String viewArguments, String viewName) throws DrupalFetchException {
        try {
            init();
            List<ReadItLater> readItLaterList = readLaterDao.getAll("weight", false);
            List<DrupalNode> nodes = new ArrayList<DrupalNode>();
            for (ReadItLater readItLater : readItLaterList) {
                drupalNodeDao.refresh(readItLater.getNode());
                nodes.add(readItLater.getNode());
            }
            return nodes;
        } catch (SQLException e) {
            throw new DrupalFetchException(e);
        }
    }

    private void init() throws SQLException {
        SQLiteDatabase.CursorFactory cursorFactory = new SQLiteDatabase.CursorFactory() {
            public Cursor newCursor(SQLiteDatabase sqLiteDatabase, SQLiteCursorDriver sqLiteCursorDriver, String s, SQLiteQuery sqLiteQuery) {
                return new SQLiteCursor(sqLiteDatabase, sqLiteCursorDriver, s, sqLiteQuery);
            }
        };
        SQLiteOpenHelper helper = new ReadItLaterDatabaseHelper(this, "dandy", cursorFactory, 1);
        ConnectionSource connectionSource = new AndroidConnectionSource(helper);
        readLaterDao = DaoFactory.getInstanceForClass(connectionSource, ReadItLater.class);
        drupalNodeDao = DaoFactory.getInstanceForClass(connectionSource, DrupalNode.class);
    }
}
