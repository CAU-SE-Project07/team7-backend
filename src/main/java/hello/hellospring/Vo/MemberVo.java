package hello.hellospring.Vo;

import lombok.Builder;
import lombok.Data;

/**
 * Java 객체 클래스
 * */
@Data
@Builder
public class MemberVo {
    private int memberId;
    private String userId; // 아이디
    private String userNm; // 사용자명
    private String userPwd; // 비밀번호
    private String userChkPwd; // 비밀번호 확인
    private String userRoles; // 사요자 권한(역할)
    private String nickNm; // 닉네임
    private String email; // 이메일
    private String projectNm; // 프로젝트명(고유값)
}
