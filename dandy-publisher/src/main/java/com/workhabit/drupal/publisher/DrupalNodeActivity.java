package com.workhabit.drupal.publisher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.workhabit.drupal.publisher.support.DrupalDialogHandler;
import org.workhabit.drupal.api.entity.DrupalComment;
import org.workhabit.drupal.api.entity.DrupalField;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 7:39:37 PM
 */
public class DrupalNodeActivity extends AbstractDandyActivity
{
    private boolean initialized = false;
    private DrupalNode lastNode;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nodeview, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.refresh:
                onCreate(null);
                break;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DrupalSiteContext drupalSiteContext = DandyApplication.getDrupalSiteContext(savedInstanceState);
        int nid = getIntent().getExtras().getInt("nid");

        setContentView(R.layout.node);
        try {
            DrupalNode node = drupalSiteContext.getNode(nid);
            lastNode = node;
            TextView titleView = (TextView)findViewById(R.id.nodeTitle);
            TextView bodyView = (TextView)findViewById(R.id.nodeBody);
            fetchAndDisplayImage(drupalSiteContext, node, titleView);
            titleView.setText(node.getTitle());
            // TODO: Support i18n incoming from Drupal
            String nodeContent = String.format("<p>%s</p>", node.getBody().get("und").get(0).getValue().replaceAll("\r\n", "\n").replaceAll("\n\n", "</p><p>"));
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

    private void fetchAndDisplayImage(final DrupalSiteContext drupalSiteContext, final DrupalNode node, final TextView titleView) throws IOException
    {
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                if (node.getFields() != null) {
                    Map<String, DrupalField> fields = node.getFields();
                    for (Map.Entry<String, DrupalField> entry : fields.entrySet()) {
                        if ("field_title_image".equals(entry.getKey())) {
                            try {
                                WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
                                int displayWidth = wm.getDefaultDisplay().getWidth();

                                HashMap<String, String> imagedata = entry.getValue().getValues().get(0);
                                String fileDirectoryPath = drupalSiteContext.getFileDirectoryPath();
                                String filepath = fileDirectoryPath + "/imagecache/w" + displayWidth + "/" + imagedata.get("filepath");

                                MessageDigest digest = MessageDigest.getInstance("MD5");
                                digest.update(filepath.getBytes());
                                byte messageDigest[] = digest.digest();
                                StringBuffer hash = new StringBuffer();
                                for (byte aMessageDigest : messageDigest) {
                                    hash.append(Integer.toHexString(0xFF & aMessageDigest));
                                }
                                File f = new File(getCacheDir() + File.separator + hash.toString());

                                InputStream fileStream;
                                if (f.exists()) {
                                    fileStream = new FileInputStream(f);
                                }
                                else {
                                    fileStream = drupalSiteContext.getFileStream(filepath);
                                    byte[] buf = new byte[1024];
                                    // persist the file locally
                                    boolean newFile = f.createNewFile();
                                    if (newFile) {
                                        FileOutputStream outStream = new FileOutputStream(f);

                                        int len;
                                        while ((len = fileStream.read(buf)) > 0) {
                                            outStream.write(buf, 0, len);
                                        }
                                        fileStream.close();
                                        outStream.close();
                                        fileStream = new FileInputStream(f);
                                    }
                                }

                                Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(fileStream));
                                if (bitmap != null) {
                                    // getStream ratio of width/height for drawable
                                    float ratio = displayWidth / bitmap.getWidth();
                                    float newHeight = bitmap.getHeight() * ratio;
                                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, displayWidth, (int)newHeight, false);
                                    BitmapDrawable bitmapDrawable = new BitmapDrawable(resizedBitmap);
                                    titleView.setBackgroundDrawable(bitmapDrawable);
                                    titleView.setHeight((int)newHeight);
                                    titleView.invalidate();
                                }
                            } catch (IOException e) {
                                // do nothing
                            } catch (DrupalFetchException e) {
                                DrupalDialogHandler.showMessageDialog(getParent(), e.getMessage());
                            } catch (NoSuchAlgorithmException e) {
                                // don't do anything
                            }

                        }
                    }
                }
            }
        };
        t.start();
    }


    private void fetchAndDisplayComments(DrupalSiteContext drupalSiteContext, DrupalNode node) throws DrupalFetchException
    {
        ListView lv = (ListView)findViewById(R.id.commentList);
        List<DrupalComment> comments = drupalSiteContext.getComments(node.getNid());
        ArrayAdapter<DrupalComment> commentArrayAdapter = new ArrayAdapter<DrupalComment>(getApplicationContext(), R.layout.commentrow, comments)
        {

            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                DrupalComment drupalComment = this.getItem(position);
                View v = convertView;
                if (v == null) {
                    v = getLayoutInflater().inflate(R.layout.commentrow, (ViewGroup)v);
                }
                if (drupalComment != null) {
                    TextView commentSubjectView = (TextView)v.findViewById(R.id.commentSubject);
                    String subject = drupalComment.getSubject();
                    if (commentSubjectView != null && subject != null && !"".equals(subject)) {
                        commentSubjectView.setText(subject);
                    }
                    TextView commentBodyView = (TextView)v.findViewById(R.id.commentBody);
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
    public void onNewCommentButtonClick(View v)
    {
        Intent i = new Intent(getApplicationContext(), NewCommentActivity.class);
        i.putExtra("nid", lastNode.getNid());
        startActivity(i);
    }

    static class FlushedInputStream extends FilterInputStream
    {
        public FlushedInputStream(InputStream inputStream)
        {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException
        {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int ibyte = read();
                    if (ibyte < 0) {
                        break;  // we reached EOF
                    }
                    else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

}
