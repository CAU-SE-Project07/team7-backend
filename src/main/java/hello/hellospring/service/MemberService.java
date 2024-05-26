package hello.hellospring.service;

import hello.hellospring.entity.MemberEntity;
import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.mapper.MemberMapper;
import hello.hellospring.vo.MemberReqVo;
import hello.hellospring.vo.MemberResVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.IssueRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final MemberMapper memberMapper;

    public ResponseVo insertMember(MemberReqVo memberReqVo) {
        try {
            if(ObjectUtils.isEmpty(memberReqVo)) {
                return null;
            }
            /** 사용자 아이디 중복 체크 */
            MemberEntity isExistedMemberEntity = memberRepository.findByUserId(memberReqVo.getUserId());
            if(isExistedMemberEntity != null) {
                return new ResponseVo(11,"ID is duplicated.");
            }
            /** 등록하려는 프로젝트 엔티티 조회 - Where : projectNm */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(memberReqVo.getProjectNm());
            /** 사용자 엔티티 추가 */
            MemberEntity memberEntity = MemberEntity.builder()
                    .userId(memberReqVo.getUserId())
                    .userNm(memberReqVo.getUserNm())
                    .userPwd(memberReqVo.getUserPwd())
                    .userChkPwd(memberReqVo.getUserChkPwd())
                    .userRoles(memberReqVo.getUserRoles())
                    .nickNm(memberReqVo.getNickNm())
                    .email(memberReqVo.getEmail())
                    .projectId(projectEntity.getProjectId())
                    .build();
            memberRepository.save(memberEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99, "FAIL");
        }
    }

    public ResponseVo updateMember(MemberReqVo memberReqVo) {
        try {
            /** 변경하려는 사용자 아이디를 가져와 데이터 조회 */
            MemberEntity memberEntity = memberRepository.findByUserId(memberReqVo.getUserId());
            /** 받은 파라미터의 사용자 아이디가 존재하지 않을 경우 */
            if(memberEntity == null) {
                return new ResponseVo(11,"아이디가 존재하지 않습니다.");
            }
            /** 현재 속해있는 프로젝트 엔티티 조회 - Where : projectNm => 필수로 넣어줘야 함(외래키 때문) */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(memberReqVo.getProjectNm());
            if(projectEntity == null) {
                return new ResponseVo(12,"프로젝트가 존재하지 않습니다.");
            }
            /** 멤버 데이터 수정 - 수정 가능한 필드 : 사용자명, 비밀번호, 비밀번호 확인, 닉네임, 이메일 만 가능 */
            MemberEntity updateMemberEntity = MemberEntity.builder()
                    .memberId(memberEntity.getMemberId())
                    .userId(memberReqVo.getUserId())
                    .userNm(memberReqVo.getUserNm())
                    .userPwd(memberReqVo.getUserPwd())
                    .userChkPwd(memberReqVo.getUserChkPwd())
                    .userRoles(memberReqVo.getUserRoles())
                    .nickNm(memberReqVo.getNickNm())
                    .email(memberReqVo.getEmail())
                    .projectId(projectEntity.getProjectId())
                    .build();
            memberRepository.save(updateMemberEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }


    /**
     * MyBatis 사용
     * */

    /**
     * 사용자 조회
     * */
    public ResponseVo<MemberResVo> selectUser(MemberReqVo memberReqVo) {
        if(ObjectUtils.isEmpty(memberReqVo)) {
            return null;
        }
        try {
            MemberResVo memberResVo = memberMapper.selectUser(memberReqVo);
            if(memberResVo == null) {
                return new ResponseVo<>(11,"사용자가 존재하지 않습니다.");
            }
            List<MemberResVo> list = new ArrayList<>();
            list.add(memberResVo);
            return new ResponseVo<MemberResVo>(200,"SUCCESS", list);
        } catch (Exception e) {
            return new ResponseVo<>(99,"FAIL");
        }
    }
}
