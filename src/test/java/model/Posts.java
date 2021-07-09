package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Posts {
    private int userId;
    private int id;
    private String title;
    private String body;
}
