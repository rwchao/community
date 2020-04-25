package life.majiang.community.service;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Notification;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            //已经进入了内层，所以通过异常把消息传递到controller层
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_ERROR);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbcomment =  commentMapper.selectById(comment.getParentId());
            if (dbcomment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question dbquestion = questionMapper.getById(dbcomment.getParentId());
            if (dbquestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            commentMapper.incCommentCount(comment.getParentId());
            //创建并增加通知
            createNotification(comment, dbcomment.getCommentator(), NotificationTypeEnum.REPLY_COMMENT, dbquestion.getId());
        } else {
            //回复问题
            Question dbquestion = questionMapper.getById(comment.getParentId());
            if (dbquestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(comment.getParentId());

            //创建并增加通知
            createNotification(comment, dbquestion.getCreator(), NotificationTypeEnum.REPLY_QUESTION, dbquestion.getId());
        }
    }

    private void createNotification(Comment comment, Long receiver, NotificationTypeEnum type, Long question) {
        Notification notification = new Notification();
        notification.setNotifierId(comment.getCommentator());
        notification.setReceiverId(receiver);
        notification.setQuestionId(question);
        notification.setType(type.getType());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setGmtCreate(System.currentTimeMillis());
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            notification.setCommentId(comment.getParentId());
        }else{
            notification.setCommentId(null);
        }
        notificationMapper.insert(notification);

    }

    public List<CommentDTO> listByParentId(Long id, CommentTypeEnum typeEnum) {
        List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
        List<Comment> commentList = commentMapper.listByParentId(id, typeEnum.getType());
        for (Comment comment : commentList) {
            User user = userMapper.findById(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
