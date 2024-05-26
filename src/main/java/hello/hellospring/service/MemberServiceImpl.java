package hello.hellospring.service;

import hello.hellospring.entity.MemberEntity;
import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.vo.MemberVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.IssueRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public MemberServiceImpl(IssueRepository issueRepository, CommentRepository commentRepository, MemberRepository memberRepository, ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public ResponseVo insertMember(MemberVo memberVo) {
        try {
            if(ObjectUtils.isEmpty(memberVo)) {
                return null;
            }
            /** 사용자 아이디 중복 체크 */
            MemberEntity isExistedMemberEntity = memberRepository.findByUserId(memberVo.getUserId());
            if(isExistedMemberEntity != null) {
                return new ResponseVo(11,"ID is duplicated.");
            }
            /** 등록하려는 프로젝트 엔티티 조회 - Where : projectNm */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(memberVo.getProjectNm());
            /** 사용자 엔티티 추가 */
            MemberEntity memberEntity = MemberEntity.builder()
                    .userId(memberVo.getUserId())
                    .userNm(memberVo.getUserNm())
                    .userPwd(memberVo.getUserPwd())
                    .userChkPwd(memberVo.getUserChkPwd())
                    .userRoles(memberVo.getUserRoles())
                    .nickNm(memberVo.getNickNm())
                    .email(memberVo.getEmail())
                    .projectId(projectEntity.getProjectId())
                    .build();
            memberRepository.save(memberEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99, "FAIL");
        }
    }

    @Override
    public ResponseVo updateMember(MemberVo memberVo) {
        try {
            /** 변경하려는 사용자 아이디를 가져와 데이터 조회 */
            MemberEntity memberEntity = memberRepository.findByUserId(memberVo.getUserId());
            /** 받은 파라미터의 사용자 아이디가 존재하지 않을 경우 */
            if(memberEntity == null) {
                return new ResponseVo(11,"아이디가 존재하지 않습니다.");
            }
            /** 현재 속해있는 프로젝트 엔티티 조회 - Where : projectNm => 필수로 넣어줘야 함(외래키 때문) */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(memberVo.getProjectNm());
            if(projectEntity == null) {
                return new ResponseVo(12,"프로젝트가 존재하지 않습니다.");
            }
            /** 멤버 데이터 수정 - 수정 가능한 필드 : 사용자명, 비밀번호, 비밀번호 확인, 닉네임, 이메일 만 가능 */
            MemberEntity updateMemberEntity = MemberEntity.builder()
                    .memberId(memberEntity.getMemberId())
                    .userId(memberVo.getUserId())
                    .userNm(memberVo.getUserNm())
                    .userPwd(memberVo.getUserPwd())
                    .userChkPwd(memberVo.getUserChkPwd())
                    .userRoles(memberVo.getUserRoles())
                    .nickNm(memberVo.getNickNm())
                    .email(memberVo.getEmail())
                    .projectId(projectEntity.getProjectId())
                    .build();
            memberRepository.save(updateMemberEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }
}
