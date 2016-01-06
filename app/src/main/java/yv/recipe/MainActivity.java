package yv.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import yv.recipe.adapters.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

//    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private TabLayout tabs;
    private CharSequence tabsTitles[] = {"", "", ""};
    private int[] tabIcons = {
            R.drawable.ic_videocam_white_24dp,
            R.drawable.ic_list_white_24dp,
            R.drawable.ic_favorite_white_24dp
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        tabsTitles[0] = getResources().getString(R.string.tab1_title);
        tabsTitles[1] = getResources().getString(R.string.tab2_title);
        tabsTitles[2] = getResources().getString(R.string.tab3_title);

//        toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabsTitles, tabIcons, tabsTitles.length, this);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.setupWithViewPager(pager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

//        tabs.getTabAt(0).setIcon(tabIcons[0]);
//        tabs.getTabAt(1).setIcon(tabIcons[1]);
//        tabs.getTabAt(2).setIcon(tabIcons[2]);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // case camera icon clicked
//        if (id == R.id.action_camera) {
//            selectImage();
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
