package hello.hellospring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "MEMBER")
@Data
@Builder
@AllArgsConstructor
@ToString(exclude = "projectId")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;
    @Column
    private String userId; // 아이디
    @Column
    private String userNm; // 사용자명
    @Column
    private String userPwd; // 비밀번호
    @Column
    private String userChkPwd; // 비밀번호 확인
    @Column
    private String userRoles; // 사요자 권한(역할)
    @Column
    private String nickNm; // 닉네임
    @Column
    private String email; // 이메일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private ProjectEntity projectId;
    @OneToMany(mappedBy = "memberId")
    private List<IssueEntity> issueEntities ;
    @OneToMany(mappedBy = "memberId")
    private List<CommentEntity> commentEntities ;

    public MemberEntity() {};
}
