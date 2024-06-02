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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            if(memberEntity == null) {return new ResponseVo(99,"NOT FOUND MEMBER[update Member]");}
            ProjectEntity projectEntity = projectRepository.findByProjectNm(memberVo.getProjectNm());
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
                    .projectId(projectEntity)
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
            if(memberEntity.getUserRoles().equals("ADMIN")&&memberEntity.getUserPwd().equals("ADMIN"));
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }

    }

    /**
     * 모든 사용자 조회
     * */
    @Override
    public ResponseVo<MemberVo> selectAllUsers() {
         try {
             /** DB에 저장된 모든 사용자 조회 */
             List<MemberEntity> memberList = memberRepository.findAll();
             /** 최종 반환할 사용자 리스트 > Entity => VO 객체로 변환 */
             List<MemberVo> finalList = new ArrayList<>();
             for(MemberEntity memberEntity: memberList) {
                 /** 외래키 > ProjectNm을 받기 위한 작업 => ProjectEntity로 되어 있기 때문 */
                 ProjectEntity projectEntity = memberEntity.getProjectId();
                 /** 프로젝트 아이디로 프로젝트 조회 => 프로젝트 아이디 가져오기 */
                 Optional<ProjectEntity> getProjectEntity = projectRepository.findById(projectEntity.getProjectId());
                 ProjectEntity pEntity = getProjectEntity.get();
                 MemberVo memberVo = MemberVo.builder()
                         .memberId(memberEntity.getMemberId())
                         .userId(memberEntity.getUserId())
                         .userNm(memberEntity.getUserNm())
                         .userPwd(memberEntity.getUserPwd())
                         .userChkPwd(memberEntity.getUserChkPwd())
                         .userRoles(memberEntity.getUserRoles())
                         .nickNm(memberEntity.getNickNm())
                         .email(memberEntity.getEmail())
                         .projectNm(pEntity.getProjectNm())
                         .build();
                 finalList.add(memberVo);
             }
             return new ResponseVo<>(200,"SUCCESS", finalList);
         } catch (Exception e) {
             return new ResponseVo<>(99,"FAIL");
         }
    }
}
