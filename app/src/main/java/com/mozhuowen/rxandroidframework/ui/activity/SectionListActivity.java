package com.mozhuowen.rxandroidframework.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mozhuowen.rxandroidframework.R;
import com.mozhuowen.rxandroidframework.ui.adapter.SimpleSectionListAdapter;
import com.mozhuowen.rxandroidframework.ui.iView.SectionListHeaderCell;
import com.mozhuowen.rxandroidframework.ui.iView.SectionListItemCell;
import com.mozhuowen.widget.views.sectionrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Awen on 16/7/28.
 * Email:mozhuowen@gmail.com
 */
public class SectionListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simplerecyclerview);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        List<String> datalist = new ArrayList<>();
        datalist = Arrays.asList(getResources().getStringArray(R.array.animals));
        SimpleSectionListAdapter adapter = new SimpleSectionListAdapter(this,R.layout.item_section,
                R.layout.item_section_header,
                datalist,
                SectionListItemCell.class,
                SectionListHeaderCell.class);

        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);

    }
}
