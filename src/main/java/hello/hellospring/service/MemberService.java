package hello.hellospring.service;

import hello.hellospring.entity.MemberEntity;
import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.mapper.MemberMapper;
import hello.hellospring.mapper.ProjectMapper;
import hello.hellospring.vo.*;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final ProjectMapper projectMapper;

    /**
     * MyBatis 사용
     * */

    /**
     * 사용자 추가
     * */
    public ResponseVo addMember(MemberReqVo memberReqVo) {
        if(ObjectUtils.isEmpty(memberReqVo)) {
            return null;
        }
        try {
            /** 프로젝트 존재 여부 확인 => 동적 where절로 인해 projectId만 셋팅 */
            ProjectReqVo projectReqVo = ProjectReqVo.builder()
                    .projectId(memberReqVo.getProjectId())
                    .build();
            List<ProjectResVo> projectResVo = projectMapper.selectProjects(projectReqVo);
            if(projectResVo.size() == 0) {
                return new ResponseVo(11,"프로젝트가 존재하지 않습니다.");
            }
            /** 추가하려는 사용자 아이디 중복 확인 => 동적 where절로 인해 userId만 있어야 해당 조건만 필터링 가능 */
            MemberReqVo addMemberVo = MemberReqVo.builder()
                    .userId(memberReqVo.getUserId())
                    .build();
            List<MemberResVo> memberResVo = memberMapper.selectMembers(addMemberVo);
            if(memberResVo.size() > 0) {
                return new ResponseVo(12,"이미 사용 중인 아이디입니다.");
            }
            memberMapper.addMember(memberReqVo);
            /** 추가된 사용자 데이터 가져오기 => 동적 where절로 인해 memberId만 셋팅하여 파라미터로 보내기 */
            MemberReqVo getMemberVo = MemberReqVo.builder()
                    .memberId(memberReqVo.getMemberId())
                    .build();
            List<MemberResVo> list = memberMapper.selectMembers(getMemberVo);
            return new ResponseVo<MemberResVo>(200,"SUCCESS", list);
        } catch (Exception e) {
            return new ResponseVo<>(99,"FAIL");
        }
    }

    /**
     * 사용자 조회(조건별 가능)
     * */
    public ResponseVo<MemberResVo> selectMember(MemberReqVo memberReqVo) {
        if(ObjectUtils.isEmpty(memberReqVo)) {
            return null;
        }
        try {
            /** 사용자 아이디로 조회 => 동적 where절로 인해 새 객체 생성하여 userId만 셋팅 */
            MemberReqVo addMemberReqVo = MemberReqVo.builder()
                    .userId(memberReqVo.getUserId())
                    .build();
            List<MemberResVo> memberResVo = memberMapper.selectMembers(addMemberReqVo);
            /** 사용자 아이디 존재 여부 확인 => 해당 사용자 데이터는 1개만 나와야 함 */
            if(memberResVo.size() == 0 || memberResVo.size() > 1) {
                return new ResponseVo<>(11,"사용자 아이디가 존재하지 않습니다.");
            }
            /** 비밀번호까지 일치하는지 확인 */
            if(!memberReqVo.getUserPwd().equals(memberResVo.get(0).getUserPwd())) {
                return new ResponseVo<>(12,"비밀번호가 일치하지 않습니다.");
            }
            return new ResponseVo<MemberResVo>(200,"SUCCESS", memberResVo);
        } catch (Exception e) {
            return new ResponseVo<>(99,"FAIL");
        }
    }

    /**
     * MyBatis - 사용자 변경
     * */
    public ResponseVo updateMember(MemberReqVo memberReqVo) {
        if(ObjectUtils.isEmpty(memberReqVo)) {
            return null;
        }
        try {
            /** 기본키로 사용자 조회 => 동적 where절로 인해 memberId만 셋팅 */
            MemberReqVo updateMemberReqVo = MemberReqVo.builder()
                    .memberId(memberReqVo.getMemberId())
                    .build();
            List<MemberResVo> memberResVo = memberMapper.selectMembers(updateMemberReqVo);
            if(memberReqVo.getMemberId() == 0 || memberResVo.size() == 0) {
                return new ResponseVo(11,"사용자가 존재하지 않습니다.");
            }
            memberMapper.updateMember(memberReqVo);
            return new ResponseVo<MemberResVo>(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo<>(99,"FAIL");
        }
    }
}
