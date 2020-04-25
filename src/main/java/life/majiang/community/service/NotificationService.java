package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Notification;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;


    public PaginationDTO<NotificationDTO> list(Long userId, Integer size, Integer page) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalCount = notificationMapper.countAllByuserId(userId);

        paginationDTO.setPagination(totalCount, page, size);

        //修正page小于1和大于totalPages时候的问题展示列表，关于数据库查找部分
        if (page < 1) {
            page = 1;
        } else if (page > paginationDTO.getTotalPages()) {
            page = paginationDTO.getTotalPages();
        }

        Integer offset = size * (page - 1);
        List<NotificationDTO> notificationDTOList = new ArrayList<NotificationDTO>();
        List<Notification> notificationList = notificationMapper.listByuserId(userId, offset, size);
        for (Notification notification : notificationList) {
//            User user = userMapper.findById(notification.getCreator());
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            User notifier = userMapper.findById(notification.getNotifierId());
            notificationDTO.setNotifierName(notifier.getName());
            Question question = questionMapper.getById(notification.getQuestionId());
            notificationDTO.setQuestionTitle(question.getTitle());
            notificationDTO.setTypeDesc(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }

    public Notification read(Long id, User user) {
        Notification notification = notificationMapper.findById(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiverId().equals(user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        /*读取成功，标记为已读*/
        notificationMapper.updateStatus(id);

        return notification;
    }
}
