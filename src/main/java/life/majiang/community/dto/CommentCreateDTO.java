package life.majiang.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parent_id;
    private String content;
    private Integer type;
}
