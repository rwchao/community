package life.majiang.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private long accountId;
    private String name;
    private String bio;
    private String avatarUrl;
}
