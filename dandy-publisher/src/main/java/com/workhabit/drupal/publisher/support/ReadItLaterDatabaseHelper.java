package com.workhabit.drupal.publisher.support;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.ReadItLater;

import java.sql.SQLException;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/22/10, 1:03 PM
 */
public class ReadItLaterDatabaseHelper extends OrmLiteSqliteOpenHelper {
    public ReadItLaterDatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DrupalNode.class);
            TableUtils.createTable(connectionSource, ReadItLater.class);
        } catch (SQLException e) {
            // ok
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // nop
    }

}
