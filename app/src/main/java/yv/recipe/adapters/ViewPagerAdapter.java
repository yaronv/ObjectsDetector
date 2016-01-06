package yv.recipe.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import yv.recipe.R;
import yv.recipe.fragments.TabFavs;
import yv.recipe.fragments.TabLive;
import yv.recipe.fragments.TabResults;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int[] icons;
    int NumbOfTabs;
    private Context context;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int[] icons, int mNumbOfTabsumb, Context context) {
        super(fm);

        this.titles = mTitles;
        this.icons = icons;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.context = context;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) {
            TabLive tab1 = new TabLive();
            return tab1;
        }
        else  if(position == 1) {
            TabResults tab2 = new TabResults();
            return tab2;
        }
        else {
            TabFavs tab3 = new TabFavs();
            return tab3;
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }


    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(titles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.tabIcon);
        img.setImageResource(icons[position]);
        return v;
    }
}
