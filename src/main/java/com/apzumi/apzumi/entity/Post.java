package com.apzumi.apzumi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Post {
    @Id
    private long id;
    private long userId;
    private String title;
    private String body;
    private boolean wasEditedByUser;

    public Post() {
    }

    public Post(long id, long userId, String title, String body, boolean wasEditedByUser) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.wasEditedByUser = wasEditedByUser;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean isWasEditedByUser() {
        return wasEditedByUser;
    }
}
