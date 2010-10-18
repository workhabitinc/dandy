package com.workhabit.drupal.publisher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.inject.Inject;
import com.workhabit.drupal.R;
import com.workhabit.drupal.entity.DrupalNode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 27, 2010, 6:04:08 PM
 */
public class DrupalNodeArrayAdapter extends ArrayAdapter<DrupalNode> {
    private ArrayList<DrupalNode> nodes;

    @Inject
    protected LayoutInflater layoutInflater;

    public DrupalNodeArrayAdapter(Context context, int textViewResourceId, ArrayList<DrupalNode> objects) {
        super(context, textViewResourceId, objects);
        this.nodes = objects;

    }

    public ArrayList<DrupalNode> getNodes() {
        return nodes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.row, parent, false);
        }
        DrupalNode node = nodes.get(position);
        if (node != null) {
            TextView tt = (TextView) v.findViewById(R.id.toptext);
            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
            if (tt != null) {
                tt.setText(node.getTitle());
            }
            if (bt != null) {
                bt.setText(new SimpleDateFormat().format(node.getCreated()));
            }
        }
        return v;
    }


}

