package hello.hellospring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "MEMBER")
@Data
@Builder
@AllArgsConstructor
public class MemberEntity {
    @Id
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
    @OneToMany(mappedBy = "memberId", cascade = CascadeType.REMOVE)
    private List<IssueEntity> issueEntities = new ArrayList<>();
    @OneToMany(mappedBy = "memberId", cascade = CascadeType.REMOVE)
    private List<CommentEntity> commentEntities = new ArrayList<>();

    public MemberEntity() {};
}
