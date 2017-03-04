package com.kamal.marcus.popularmoviesv2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new MoviesFragment(Urls.SORT_BY_POPULARITY), "Most Popular");
//        adapter.addFragment(new MoviesFragment(Urls.SORT_BY_HIGHEST_RATING), "Highest Rated");

        MoviesFragment mostPopular = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString("sortOrder", Urls.SORT_BY_POPULARITY);
        mostPopular.setArguments(args);
        adapter.addFragment(mostPopular,"Most Popular");

        MoviesFragment highestRated = new MoviesFragment();
        Bundle args2 = new Bundle();
        args2.putString("sortOrder", Urls.SORT_BY_HIGHEST_RATING);
        highestRated.setArguments(args2);
        adapter.addFragment(highestRated,"Highest Rated");

        viewPager.setAdapter(adapter);
    }

}
