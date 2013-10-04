package com.basket.adapters;

import java.util.List;

import com.basket.general.Section;
import com.basket.general.SectionItem;
import com.example.basket.R;
import com.example.basket.R.color;
import com.example.basket.R.id;
import com.example.basket.R.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SectionListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Section> sections;

    public SectionListAdapter(Context context, List<Section> sections) {
        this.sections = sections;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sections.get(groupPosition).getSectionItems().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return sections.get(groupPosition).getSectionItems().get(childPosition).getId();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sections.get(groupPosition).getSectionItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.sections.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.sections.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.slidingmenu_sectionview,
                    parent, false);
        }

        TextView textView = (TextView) convertView
                .findViewById(R.id.slidingmenu_section_title);
        textView.setText(((Section) getGroup(groupPosition)).getTitle());
        if(!((Section)getGroup(groupPosition)).isBold()){
        	textView.setBackgroundColor(parent.getResources().getColor(R.color.black));
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.slidingmenu_sectionitem,
                    parent, false);
        }

        SectionItem oSectionItem = this.sections.get(groupPosition).getSectionItems().get(childPosition);
        
        TextView textView = (TextView) convertView
                .findViewById(R.id.slidingmenu_sectionitem_label);
        textView.setText(oSectionItem.getTitle());

        final ImageView itemIcon = (ImageView) convertView
                .findViewById(R.id.slidingmenu_sectionitem_icon);
        itemIcon.setImageDrawable(getDrawableByName(
                oSectionItem.getIcon(), this.context));

        return convertView;
    }
    
    public static Drawable getDrawableByName( String name, Context context ) {
        int drawableResource = context.getResources().getIdentifier(
                        name,
                        "drawable",
                        context.getPackageName());
        if ( drawableResource == 0 ) {
            throw new RuntimeException("Can't find drawable with name: " + name );
        }
        return context.getResources().getDrawable(drawableResource);
    }
}