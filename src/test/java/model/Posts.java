package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model class to hold the Posts json response
 */
@Data
@AllArgsConstructor
public class Posts {
    private int userId;
    private int id;
    private String title;
    private String body;
}
