package hello.hellospring.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResVo {
    private int commentId;
    private String content;
    private String date;
    private int projectId;
    private int memberId;
    private int issueId;
}
