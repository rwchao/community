package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    /*search支持title和tag的相关性*/
    @Select("select * from question where title regexp #{search} or tag regexp #{search} order by gmt_create desc limit #{size} offset #{offset}")
    List<Question> list(@Param("search") String search,@Param("offset") Integer offset, @Param("size") Integer size);

    /*search支持title和tag的相关性*/
    @Select("select count(1) from question where title regexp #{search} or tag regexp #{search}")
    Integer count(@Param("search") String search);

    @Select("select * from question where creator=#{userId} order by gmt_create desc limit #{size} offset #{offset}")
    List<Question> listByuserId(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator=#{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);

    @Update("update question set view_count = view_count+1 where id = #{id}")
    void updateViewCount(@Param("id") Long id);

    @Update("update question set comment_count = comment_count+1 where id = #{id}")
    void incCommentCount(@Param("id") Long id);

    @Select("select * from question where tag regexp #{regexpTag} and id!=#{id} ")
    List<Question> selectRelated(@Param("id") Long id,@Param("regexpTag") String regexpTag);
}
