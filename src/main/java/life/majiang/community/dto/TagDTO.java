package life.majiang.community.dto;

import lombok.Data;
import org.thymeleaf.expression.Strings;

import java.util.List;

@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
