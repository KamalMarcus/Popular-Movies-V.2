package com.kamal.marcus.popularmoviesv2.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kamal.marcus.popularmoviesv2.Controller.Adapters.ViewPagerAdapter;
import com.kamal.marcus.popularmoviesv2.Controller.Urls;
import com.kamal.marcus.popularmoviesv2.R;

/**
 * Created by KiMoo on 02/04/2017.
 */

public class MainFragment extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

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

        MoviesFragment favouriteMovies=new MoviesFragment();
        Bundle args3 = new Bundle();
        args3.putString("sortOrder", Urls.SORT_BY_FAVOURITES);
        favouriteMovies.setArguments(args3);
        adapter.addFragment(favouriteMovies,"Favourites");

        viewPager.setAdapter(adapter);
    }
}
