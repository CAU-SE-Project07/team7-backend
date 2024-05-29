package hello.hellospring.Vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CommentVo {
    private String content;
    private String date;
    private String userId; // 현재 로그인한 사용자 아이디
    private String issueTitle; // 이슈 제목(고유값)
    private String projectNm; // 프로젝트명(고유값)
}
