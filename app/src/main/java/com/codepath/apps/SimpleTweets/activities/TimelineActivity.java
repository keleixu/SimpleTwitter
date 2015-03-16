package com.codepath.apps.SimpleTweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.SimpleTweets.R;
import com.codepath.apps.SimpleTweets.fragments.HomeTimelineFragment;
import com.codepath.apps.SimpleTweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.SimpleTweets.fragments.TweetsListFragment;

public class TimelineActivity extends ActionBarActivity {
    private final int COMPOSE_ACTIVITY_RESULT = 1;
    private ViewPager vpPager;
    private PagerSlidingTabStrip tabStrip;
    private TweetsPagerAdapter tweetsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        vpPager = (ViewPager) findViewById(R.id.viewpager);
        tweetsPagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(tweetsPagerAdapter);
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.miCompose) {
            showComposeView();
        } else if (id == R.id.miProfile) {
            showProfileView();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showComposeView() {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, COMPOSE_ACTIVITY_RESULT);
    }

    public void showProfileView() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COMPOSE_ACTIVITY_RESULT) {
            HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) tweetsPagerAdapter.getItem(0);
            homeTimelineFragment.resetList();
        }
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final private String tabTitles[] = { "Home", "Mentions" };
        private HomeTimelineFragment homeTimeLineFragment;
        private MentionsTimelineFragment mentionsTimelineFragment;

        public TweetsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
            homeTimeLineFragment = new HomeTimelineFragment();
            mentionsTimelineFragment = new MentionsTimelineFragment();
            homeTimeLineFragment.setListener(new TweetsListFragment.TweetsListFragmentListener() {
                @Override
                public void onProfileClicked(String screenName) {
                    navigateToProfile(screenName);
                }
            });
            mentionsTimelineFragment.setListener(new TweetsListFragment.TweetsListFragmentListener() {
                @Override
                public void onProfileClicked(String screenName) {
                    navigateToProfile(screenName);
                }
            });
        }

        private void navigateToProfile(String screenName) {
            Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
            i.putExtra("screenName", screenName);
            startActivity(i);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            TweetsListFragment tweetsListFragment = null;
            if (position == 0) {
                tweetsListFragment = homeTimeLineFragment;
            } else if (position == 1) {
                tweetsListFragment = mentionsTimelineFragment;
            }
            return tweetsListFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
