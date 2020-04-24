package life.majiang.community.model;

import lombok.Data;

@Data
public class Comment {

    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Integer commentCount;
//    parent_id int not null,
//    type int not null,
//    commentator int not null,
//    gmt_create bigint not null,
//    gmt_modified bigint not null,
//    like_count bigint default 0,
//    content text not null,
}
