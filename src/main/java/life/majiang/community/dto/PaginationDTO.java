package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showFirstPage;
    private boolean showPrevious;
    private boolean showEndPage;
    private boolean showNext;
    private Integer currentPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPages;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if (totalCount % size == 0) {
            totalPages= totalCount / size;
        } else {
            totalPages= totalCount / size + 1;
        }
        //解决totalpages=0时的显示错误问题
        if(totalPages == 0){
            totalPages = 1;
        }

        //修正page大于totalPages和小于1的情况，关于分页显示部分
        if (page < 1) {
            page = 1;
        }else if (page > totalPages) {
            page = totalPages;
        }

        currentPage = page;

        pages.add(currentPage);
        for (int i = 1; i <= 3; i++) {
            if (currentPage - i > 0) {
                pages.add(0, currentPage - i);
            }

            if (currentPage + i <= totalPages) {
                pages.add(currentPage + i);
            }
        }

        // 是否展示上一页
        if (currentPage == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        // 是否展示下一页
        if (currentPage == totalPages) {
            showNext = false;
        } else {
            showNext = true;
        }

        // 是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        // 是否展示最后一页
        if (pages.contains(totalPages)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }
}
