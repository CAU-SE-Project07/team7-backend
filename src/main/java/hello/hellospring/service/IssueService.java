package hello.hellospring.service;

import hello.hellospring.entity.IssueEntity;
import hello.hellospring.entity.MemberEntity;
import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.mapper.IssueMapper;
import hello.hellospring.mapper.MemberMapper;
import hello.hellospring.vo.*;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.IssueRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueService {

    private final IssueMapper issueMapper;
    private final MemberMapper memberMapper;

    /***
     *** MyBatis 사용
     ***/

    /**
     * test1 > 이슈 생성
     * */
    public ResponseVo addIssue(IssueReqVo issueReqVo) {
        if(ObjectUtils.isEmpty(issueReqVo)) {
            return null;
        }
        try {
            /**  */
            issueMapper.addIssue(issueReqVo);
            IssueResVo issueResVo = issueMapper.selectById(issueReqVo);
            List<IssueResVo> result = new ArrayList<>();
            result.add(issueResVo);
            return new ResponseVo(200,"SUCCESS",result);
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }

    /**
     * test1/PL1 > 이슈 변경
     * */
    public ResponseVo updateIssue(IssueReqVo issueReqVo) {
        if(ObjectUtils.isEmpty(issueReqVo)) {
            return null;
        }
        try {
            issueMapper.updateIssue(issueReqVo);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }

    /**
     * 조건 별 이슈 조회
     * */
    public ResponseVo selectIssueList(IssueReqVo issueReqVo) {
        if(ObjectUtils.isEmpty(issueReqVo)) {
            return null;
        }
        try {
            List<IssueResVo> list = issueMapper.selectIssues(issueReqVo);
            return new ResponseVo(200,"SUCCESS", list);
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }


}
