package com.workhabit.drupal.publisher;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.inject.Inject;
import com.workhabit.drupal.api.entity.DrupalTaxonomyTerm;

import java.util.ArrayList;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 16, 2010, 3:58:44 PM
 */
public class DrupalTaxonomyAdapter extends ArrayAdapter<DrupalTaxonomyTerm> {
    private ArrayList<DrupalTaxonomyTerm> terms;

    @Inject
    protected LayoutInflater layoutInflater;

    public DrupalTaxonomyAdapter(Context context, int textViewResourceId, ArrayList<DrupalTaxonomyTerm> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        this.terms = objects;

    }

    public ArrayList<DrupalTaxonomyTerm> getTerms() {
        return terms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.row, parent, false);
        }
        DrupalTaxonomyTerm term = terms.get(position);
        if (term != null) {
            TextView tt = (TextView) v.findViewById(R.id.toptext);
            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
            if (tt != null) {
                tt.setText(term.getName());
            }
            if (bt != null) {
                bt.setText(term.getDescription());
            }
        }
        return v;
    }
}
