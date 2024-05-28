package hello.hellospring.service;

import hello.hellospring.Entity.MemberEntity;
import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.IssueRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

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
            /** 사용자 기본키 : memberId => 고유값 처리 */
            int memberId = 1;
            int count = memberRepository.findAll().size();
            if(count > 0) {
                memberId = count + 1;
            }
            /** 등록하려는 프로젝트 엔티티 조회 - Where : projectNm */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(memberVo.getProjectNm());
            /** 사용자 엔티티 추가 */
            MemberEntity memberEntity = MemberEntity.builder()
                    .memberId(memberId)
                    .userId(memberVo.getUserId())
                    .userNm(memberVo.getUserNm())
                    .userPwd(memberVo.getUserPwd())
                    .userChkPwd(memberVo.getUserChkPwd())
                    .userRoles(memberVo.getUserRoles())
                    .nickNm(memberVo.getNickNm())
                    .email(memberVo.getEmail())
                    .projectId(projectEntity)
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
            /** 멤버에 해당하는 */
            MemberEntity updateMemberEntity = MemberEntity.builder()
                    .memberId(memberEntity.getMemberId())
                    .userId(memberVo.getUserId())
                    .userNm(memberVo.getUserNm())
                    .userPwd(memberVo.getUserPwd())
                    .userChkPwd(memberVo.getUserChkPwd())
                    .userRoles(memberVo.getUserRoles())
                    .nickNm(memberVo.getNickNm())
                    .email(memberVo.getEmail())
                    .issueEntities(null)
                    .commentEntities(null)
                    .build();
            memberRepository.save(updateMemberEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }

    @Override
    public ResponseVo login(MemberVo memberVo) {
        if(ObjectUtils.isEmpty(memberVo)) {
            return null;
        }
        try {
            /** 아이디로 먼저 사용자 조회 */
            MemberEntity memberEntity = memberRepository.findByUserId(memberVo.getUserId());
            if(memberEntity == null) {
                return new ResponseVo(11,"사용자가 존재하지 않습니다.");
            }
            if(!memberEntity.getUserId().equals(memberVo.getUserId()) || !memberEntity.getUserPwd().equals(memberVo.getUserPwd())) {
                return new ResponseVo(12,"아이디 및 비밀번호가 틀렸습니다.");
            }
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }

    }
}
