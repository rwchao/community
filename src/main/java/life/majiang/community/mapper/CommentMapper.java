package life.majiang.community.mapper;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    public void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment selectById(@Param("id") Long id);

    @Select("select * from comment where parent_id=#{parentId} and type=1 order by gmt_create")
    List<Comment> listByQuestionId(@Param("parentId") Long parentId);
}
