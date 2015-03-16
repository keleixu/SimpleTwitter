package com.codepath.apps.SimpleTweets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.SimpleTweets.R;
import com.codepath.apps.SimpleTweets.fragments.TweetsListFragment;
import com.codepath.apps.SimpleTweets.models.Tweet;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by kelei on 3/7/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    private TweetsListFragment.TweetsListFragmentListener listener;

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onProfileClicked(tweet.getUser().getScreenName());
                }
            }
        });

        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvCreatedAt.setText(tweet.getCreatedAtRelative());
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        return convertView;
    }

    public void setListener(TweetsListFragment.TweetsListFragmentListener listener) {
        this.listener = listener;
    }
}
