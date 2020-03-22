package com.example.invoiceamigobusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.invoiceamigobusiness.background.BackgroundUtil;
import com.example.invoiceamigobusiness.ui.dashboard.DashboardFragment;
import com.example.invoiceamigobusiness.ui.invoices.InvoiceFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //For testing: Run the scheduler on create for instant results
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BackgroundUtil.scheduleJob(getApplicationContext());
        }
        setContentView(R.layout.home_activity);

        //Add view pager - allows you to swipe between fragments
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        //Attach tabs to ViewPager
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        //Set titles in the tabs for the fragment
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                String tabTitle;
                switch (position){
                    case 1:tabTitle="Invoices";
                    break;
                    default:tabTitle="Dashboard";
                }
            tab.setText(tabTitle);
        }).attach();
    }

    /**
     * Overwrite default backpress soft key - navigates backwards to
     */
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
            //Close activity - don't go back to login activity
            finishAffinity();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    /**
     * Create an option menu in the action bar
     * @param menu - the menu to inflate
     * @return true to acknowledge completion
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }


    /**
     * Menu handler - currently refreshes the activity
     * @param item - the menu item
     * @return true - acknowledge completion
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
                //Reload activity - reruns api calls
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * A simple pager adapter that shows 2 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            switch (position){
                case 1:
                    fragment = new InvoiceFragment();
                    break;
                default:
                    fragment = new DashboardFragment();
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
