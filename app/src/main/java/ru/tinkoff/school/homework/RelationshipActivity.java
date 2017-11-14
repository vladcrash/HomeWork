package ru.tinkoff.school.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Влад on 13.11.2017.
 */

public class RelationshipActivity extends AppCompatActivity {

    private static final String POSITION_ITEM = "POSITION_ITEM";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static void start(Context context, long id) {
        Intent intent = new Intent(context, RelationshipActivity.class);
        intent.putExtra(POSITION_ITEM, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relationship);

        Intent intent = getIntent();
        final long id = intent.getLongExtra(POSITION_ITEM, 0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Parents"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Children"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return RelationshipFragment.newInstance(id, true);
                    case 1:
                        return RelationshipFragment.newInstance(id, false);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return mTabLayout.getTabCount();
            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
