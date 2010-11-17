package com.workhabit.drupal.publisher.support;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.workhabit.drupal.publisher.R;
import org.workhabit.drupal.api.entity.DrupalNode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 27, 2010, 6:04:08 PM
 */
public class DrupalNodeArrayAdapter extends ArrayAdapter<DrupalNode> {
    private List<DrupalNode> nodes;
    protected LayoutInflater layoutInflater;

    public DrupalNodeArrayAdapter(Context context, int textViewResourceId, List<DrupalNode> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        this.nodes = objects;
    }

    public List<DrupalNode> getNodes() {
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
                if (node.getCreated() != null) {
                    bt.setText(new SimpleDateFormat().format(node.getCreated()));
                }
            }
        }
        return v;
    }


}

