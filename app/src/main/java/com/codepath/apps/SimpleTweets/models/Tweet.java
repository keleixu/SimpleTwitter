package com.codepath.apps.SimpleTweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.codepath.apps.SimpleTweets.utils.DateHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelei on 3/7/15.
 */
@Table(name = "Tweets")
public class Tweet extends Model {
    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid;

    @Column(name = "body")
    private String body;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "user", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtRelative() {
        return DateHelper.getRelativeTimeAgo(createdAt);
    }

    public Tweet() {
        super();
    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public User getUser() {
        return user;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                tweets.add(fromJSON(jsonArray.getJSONObject(i)));
            } catch (Exception e) {

            }
        }
        return tweets;
    }

    public static ArrayList<Tweet> getAll() {
        List<Tweet> tweets = new Select()
            .from(Tweet.class)
            .orderBy("uid DESC")
            .limit("1000")
            .execute();
        if (tweets == null) {
            return new ArrayList<Tweet>();
        } else {
            return new ArrayList<Tweet>(tweets);
        }
    }

}
