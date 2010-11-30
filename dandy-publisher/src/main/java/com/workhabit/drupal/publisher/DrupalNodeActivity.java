package com.workhabit.drupal.publisher;

import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.workhabit.drupal.publisher.support.DrupalDialogHandler;
import com.workhabit.drupal.publisher.support.ReadItLaterDatabaseHelper;
import org.workhabit.dandy.dao.GenericDao;
import org.workhabit.dandy.dao.impl.DaoFactory;
import org.workhabit.drupal.api.entity.DrupalComment;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.ReadItLater;
import org.workhabit.drupal.api.site.DrupalFetchException;
import org.workhabit.drupal.api.site.DrupalSiteContext;

import java.sql.SQLException;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 7:39:37 PM
 */
public class DrupalNodeActivity extends AbstractDandyActivity {
    private GenericDao<ReadItLater> readLaterDao;
    private boolean initialized = false;
    private DrupalNode lastNode;
    private GenericDao<DrupalNode> drupalNodeDao;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nodeview, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                onCreate(null);
                break;
            case R.id.readItLater:
                if (!initialized) {
                    SQLiteDatabase.CursorFactory cursorFactory = new SQLiteDatabase.CursorFactory() {
                        public Cursor newCursor(SQLiteDatabase sqLiteDatabase, SQLiteCursorDriver sqLiteCursorDriver, String s, SQLiteQuery sqLiteQuery) {
                            return new SQLiteCursor(sqLiteDatabase, sqLiteCursorDriver, s, sqLiteQuery);
                        }
                    };
                    SQLiteOpenHelper helper = new ReadItLaterDatabaseHelper(this, "dandy", cursorFactory, 1);
                    ConnectionSource connectionSource = new AndroidConnectionSource(helper);
                    try {
                        readLaterDao = DaoFactory.getInstanceForClass(connectionSource, ReadItLater.class);
                        drupalNodeDao = DaoFactory.getInstanceForClass(connectionSource, DrupalNode.class);
                    } catch (SQLException e) {
                        // TODO: this shouldn't happen, but we should handle it gracefully if it does.
                        Log.e("error", e.getMessage());
                    }
                    initialized = true;
                }
                ReadItLater readItLater = new ReadItLater();
                readItLater.setNode(this.lastNode);
                try {
                    // ormlite won't persist the child object, so we do it ourselves.
                    drupalNodeDao.save(this.lastNode);
                    readLaterDao.save(readItLater);
                    Toast toast = Toast.makeText(getApplicationContext(), "Saved for Later", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (SQLException e) {
                    // TODO: handle this gracefully
                    Log.e("error", e.getMessage());
                }
                break;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrupalSiteContext drupalSiteContext = DandyApplication.getDrupalSiteContext();
        int nid = getIntent().getExtras().getInt("nid");

        setContentView(R.layout.node);
        try {
            DrupalNode node = drupalSiteContext.getNode(nid);
            lastNode = node;
            TextView titleView = (TextView) findViewById(R.id.nodeTitle);
            TextView bodyView = (TextView) findViewById(R.id.nodeBody);
            titleView.setText(node.getTitle());
            List<DrupalComment> comments = drupalSiteContext.getComments(node.getNid());
            String nodeContent = String.format("<p>%s</p>", node.getBody().replaceAll("\r\n", "\n").replaceAll("\n\n", "</p><p>"));
            bodyView.setText(Html.fromHtml(nodeContent));
        } catch (DrupalFetchException e) {
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        }
    }
}
