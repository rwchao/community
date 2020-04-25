package life.majiang.community.model;

import lombok.Data;

@Data
public class Notification {
    private Long id;
    private Long notifierId;
    private Long receiverId;
    private Long commentId;
    private Long questionId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
}
