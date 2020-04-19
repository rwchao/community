package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();

        paginationDTO.setPagination(totalCount,page, size);

        //修正page小于1和大于totalPages时候的问题展示列表，关于数据库查找部分
        if(page < 1){
            page = 1;
        }else if(page > paginationDTO.getTotalPages()){
            page = paginationDTO.getTotalPages();
        }

        Integer offset = size * (page - 1);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        List<Question> questionList = questionMapper.list(offset,size);
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionList(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer size, Integer page) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByuserId(userId);

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

        paginationDTO.setQuestionList(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        User user = userMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        //注入user
        questionDTO.setUser(user);
        return questionDTO;
    }
}
