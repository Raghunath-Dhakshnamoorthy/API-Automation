package model;

import lombok.Data;

/**
 * Model class to hold the Comments json response
 */
@Data
public class Comments {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;
}
