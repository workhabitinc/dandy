package com.workhabit.drupal.publisher;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.workhabit.drupal.publisher.support.DrupalDialogHandler;
import com.workhabit.drupal.publisher.support.ReadItLaterDatabaseHelper;
import org.workhabit.dandy.dao.GenericDao;
import org.workhabit.dandy.dao.impl.DaoFactory;
import org.workhabit.drupal.api.entity.DrupalComment;
import org.workhabit.drupal.api.entity.DrupalField;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.ReadItLater;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
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
            fetchAndDisplayImage(drupalSiteContext, node, titleView);
            titleView.setText(node.getTitle());
            String nodeContent = String.format("<p>%s</p>", node.getBody().replaceAll("\r\n", "\n").replaceAll("\n\n", "</p><p>"));
            bodyView.setText(Html.fromHtml(nodeContent));

            if (node.getComment() != 0) {
                // only show if comments are enabled for this node.
                //
                fetchAndDisplayComments(drupalSiteContext, node);
            }


        } catch (DrupalFetchException e) {
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        } catch (IOException e) {
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        }
    }

    private void fetchAndDisplayImage(final DrupalSiteContext drupalSiteContext, final DrupalNode node, final TextView titleView) throws IOException {
        Thread t = new Thread() {
            @Override
            public void run() {
                if (node.getFields() != null) {
                    List<DrupalField> fields = node.getFields();
                    for (DrupalField drupalField : fields) {
                        if ("field_title_image".equals(drupalField.getName())) {
                            try {
                                HashMap<String, String> imagedata = drupalField.getValues().get(0);
                                WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
                                int displayWidth = wm.getDefaultDisplay().getWidth();
                                String fileDirectoryPath = drupalSiteContext.getFileDirectoryPath();
                                String filepath = fileDirectoryPath + "/imagecache/w" + displayWidth + "/" + imagedata.get("filepath");
                                InputStream fileStream = drupalSiteContext.getFileStream(filepath);


                                Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(fileStream));
                                if (bitmap != null) {
                                    // get ratio of width/height for drawable
                                    float ratio = displayWidth / bitmap.getWidth();
                                    float newHeight = bitmap.getHeight() * ratio;
                                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) displayWidth, (int) newHeight, false);
                                    BitmapDrawable bitmapDrawable = new BitmapDrawable(resizedBitmap);
                                    titleView.setBackgroundDrawable(bitmapDrawable);
                                    titleView.setHeight((int) newHeight);
                                }
                            } catch (IOException e) {
                                // do nothing
                            } catch (DrupalFetchException e) {
                                DrupalDialogHandler.showMessageDialog(getParent(), e.getMessage());
                            }

                        }
                    }
                }
            }
        };
        t.start();
    }


    private void fetchAndDisplayComments(DrupalSiteContext drupalSiteContext, DrupalNode node) throws DrupalFetchException {
        ListView lv = (ListView) findViewById(R.id.commentList);
        List<DrupalComment> comments = drupalSiteContext.getComments(node.getNid());
        ArrayAdapter<DrupalComment> commentArrayAdapter = new ArrayAdapter<DrupalComment>(getApplicationContext(), R.layout.commentrow, comments) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                DrupalComment drupalComment = this.getItem(position);
                View v = convertView;
                if (v == null) {
                    v = getLayoutInflater().inflate(R.layout.commentrow, (ViewGroup) v);
                }
                if (drupalComment != null) {
                    TextView commentSubjectView = (TextView) v.findViewById(R.id.commentSubject);
                    String subject = drupalComment.getSubject();
                    if (commentSubjectView != null && subject != null && !"".equals(subject)) {
                        commentSubjectView.setText(subject);
                    }
                    TextView commentBodyView = (TextView) v.findViewById(R.id.commentBody);
                    String commentBody = drupalComment.getComment();
                    if (commentBodyView != null && commentBody != null && !"".equals(drupalComment.getComment())) {
                        commentBodyView.setText(commentBody);
                    }
                }
                return v;
            }
        };
        lv.setAdapter(commentArrayAdapter);
        commentArrayAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void onNewCommentButtonClick(View v) {
        Intent i = new Intent(getApplicationContext(), NewCommentActivity.class);
        i.putExtra("nid", lastNode.getNid());
        startActivity(i);
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int ibyte = read();
                    if (ibyte < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

}
