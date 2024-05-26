package hello.hellospring.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResVo {
    private String content;
    private String date;
    private String userId; // 현재 로그인한 사용자 아이디
    private String title; // 이슈 제목(고유값)
    private String projectNm; // 프로젝트명(고유값)
}
