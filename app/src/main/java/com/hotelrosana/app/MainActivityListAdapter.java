package com.hotelrosana.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] itemsHeadings;
    private String[] itemsTexts;
    private Integer[] itemsImgs;

    public MainActivityListAdapter(Activity context, String[] itemsHeadings, String[] itemsTexts, Integer[] itemsImgs) {
        super(context, R.layout.list_item_activity_main_block, itemsTexts);

        this.context = context;
        this.itemsHeadings = itemsHeadings;
        this.itemsTexts = itemsTexts;
        this.itemsImgs = itemsImgs;
    }

    @Override
    public View getView(int position, View view, ViewGroup root) {
        View rowView = null;
        LayoutInflater inflater = context.getLayoutInflater();

        if (position == 0 || position == 2) {
            rowView = inflater.inflate(R.layout.list_item_activity_main_block, null, true);
            ((TextView) rowView.findViewById(R.id.listItemText)).setText(itemsTexts[position]);
            ((ImageView) rowView.findViewById(R.id.listItemImg)).setImageResource(itemsImgs[position]);

        } else if (position == 1 || position == 3) {
            rowView = inflater.inflate(R.layout.title_box, null, true);
            ((TextView) rowView.findViewById(R.id.titleText)).setText(itemsTexts[position]);

        } else {
            rowView = inflater.inflate(R.layout.list_item_activity_main_panel, null, true);
            ((TextView) rowView.findViewById(R.id.listItemHeading)).setText(itemsHeadings[position]);
            ((TextView) rowView.findViewById(R.id.listItemText)).setText(itemsTexts[position]);
            ((ImageView) rowView.findViewById(R.id.listItemImg)).setImageResource(itemsImgs[position]);
        }

        return rowView;
    }
}
