package com.otel.warehouseassistant;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.otel.warehouseassistant.menu.MenuObject;
import com.otel.warehouseassistant.menu.MenuRecyclerViewAdapter;
import com.otel.warehouseassistant.tool.MenuUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    private List<MenuObject> gaggeredList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getExtrasAndListItemData();
        initRecyclerView();
        initScreen();
    }

    private void getExtrasAndListItemData() {
        gaggeredList = MenuUtils.LoadMainMenu(this);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        MenuRecyclerViewAdapter rcAdapter = new MenuRecyclerViewAdapter(this, gaggeredList);
        recyclerView.setAdapter(rcAdapter);

        rcAdapter.setOnItemClickListener(new MenuRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);

                ArrayList<MenuObject> listViewItems = (ArrayList<MenuObject>) gaggeredList.get(position).getListMenuObject();
                MenuObject i = gaggeredList.get(position);
                if (listViewItems != null && listViewItems.size() > 0) {
                    if (i.getGotoContext() != null) {
                        /*
                        Intent intent = new Intent(this, SubMenuActivity.class);
                        intent.putExtra("mode", i.getName());
                        intent.putParcelableArrayListExtra("submenu", listViewItems);
                        startActivity(intent);
                        */
                    }
                } else {
                    if (i.getGotoContext() != null) {
                        Intent intent = new Intent(MainActivity.this, i.getGotoContext());
                        intent.putExtra("mode", i.getName());
                        intent.putExtra("titleBg", ((ColorDrawable) relativeLayout.getBackground()).getColor());
                        if (i.getEventCode() != null)
                            intent.putExtra("eventCode", i.getEventCode());
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private void initScreen() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar();

        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(NewMenuActivity.this);
        TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText(getString(R.string.hi)); // + " " + globalVariable.getUserID(this));

        TextView place = (TextView) findViewById(R.id.place);
        //place.setText(getString(R.string.place) + ": " + sharedPref.getString(PREF_PLACE, ""));
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);

        mToolbar.setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_options:
                //intent = new Intent(this, SettingsActivity.class);
                //startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
