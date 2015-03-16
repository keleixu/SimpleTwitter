package com.codepath.apps.SimpleTweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONObject;

/**
 * Created by kelei on 3/7/15.
 */
@Table(name = "Users")
public class User extends Model {
    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid;

    @Column(name = "name")
    private String name;

    @Column(name = "screenName")
    private String screenName;

    @Column(name = "profileImageUrl")
    private String profileImageUrl;

    @Column(name = "tagLine")
    private String tagline;

    @Column(name = "followersCount")
    private int followersCount;

    @Column(name = "followingCount")
    private int followingCount;

    @Column(name = "statusesCount")
    private int statusesCount;

    @Column(name = "profileBannerUrl")
    private String profileBannerUrl;

    public User() {
        super();
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return "@" + screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.tagline = jsonObject.getString("description");
            u.followersCount = jsonObject.getInt("followers_count");
            u.followingCount = jsonObject.getInt("friends_count");
            u.statusesCount = jsonObject.getInt("statuses_count");
            u.profileBannerUrl = jsonObject.getString("profile_banner_url");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }
}
