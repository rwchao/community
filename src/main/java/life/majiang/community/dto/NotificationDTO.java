package life.majiang.community.dto;

import life.majiang.community.model.Comment;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private Long questionId;


    private String notifierName;
    private String questionTitle;
    private String typeDesc;
//    private Question targetQuestion;
//    private Comment targetComment;

}
