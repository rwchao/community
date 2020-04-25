package life.majiang.community.mapper;

import life.majiang.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier_id,receiver_id,comment_id,question_id,type,status,gmt_create) values (#{notifierId},#{receiverId},#{commentId},#{questionId},#{type},#{status},#{gmtCreate})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver_id=#{userId} and status=0")
    Integer countUnreadByuserId(@Param("userId") Long userId);

    @Select("select count(1) from notification where receiver_id=#{userId}")
    Integer countAllByuserId(@Param("userId") Long userId);

    @Select("select * from notification where receiver_id=#{userId} order by gmt_create desc limit #{size} offset #{offset}")
    List<Notification> listByuserId(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from notification where id=#{id}")
    Notification findById(@Param("id") Long id);

    @Update("update notification set status=1 where id=#{id}")
    void updateStatus(@Param("id") Long id);
}
