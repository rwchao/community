package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    /*search支持title和tag的相关性*/
    public PaginationDTO<QuestionDTO> list(String search, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();

        if (search != null) {
            String[] keyWords = StringUtils.split(search, ' ');
            search = Arrays.stream(keyWords).collect(Collectors.joining("|"));
        }else {
            search="";
        }

        Integer totalCount = questionMapper.count(search);

        paginationDTO.setPagination(totalCount,page, size);

        //修正page小于1和大于totalPages时候的问题展示列表，关于数据库查找部分
        if(page < 1){
            page = 1;
        }else if(page > paginationDTO.getTotalPages()){
            page = paginationDTO.getTotalPages();
        }

        Integer offset = size * (page - 1);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        List<Question> questionList = questionMapper.list(search,offset,size);
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO<QuestionDTO> list(Long userId, Integer size, Integer page) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Integer totalCount = questionMapper.countByUserId(userId);

        paginationDTO.setPagination(totalCount,page, size);

        //修正page小于1和大于totalPages时候的问题展示列表，关于数据库查找部分
        if(page < 1){
            page = 1;
        }else if(page > paginationDTO.getTotalPages()){
            page = paginationDTO.getTotalPages();
        }

        Integer offset = size * (page - 1);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        List<Question> questionList = questionMapper.listByuserId(userId,offset,size);
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.getById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        //注入user
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setLikeCount(0);
            question.setViewCount(0);
            question.setCommentCount(0);
            questionMapper.create(question);
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

    public void incView(Long id) {
        questionMapper.updateViewCount(id);
    }

    /**
     * 搜索相关问题展示在右栏
     * @param questionDTO
     * @return
     */
    public List<Question> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(), ',');
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        List<Question> questionList = questionMapper.selectRelated(questionDTO.getId(),regexpTag);
        return questionList;
    }
}
