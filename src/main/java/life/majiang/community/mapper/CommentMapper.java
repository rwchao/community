package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    public void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment selectById(@Param("id") Long id);

    @Select("select * from comment where parent_id=#{parentId} and type=#{type} order by gmt_create")
    List<Comment> listByParentId(@Param("parentId") Long parentId,@Param("type") Integer type);

    @Update("update comment set comment_count = comment_count+1 where id = #{id}")
    void incCommentCount(@Param("id") Long id);
}
