package com.apzumi.apzumi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PostWrapper {
    @JsonProperty("body")
    public List<Post> postsList;

}
