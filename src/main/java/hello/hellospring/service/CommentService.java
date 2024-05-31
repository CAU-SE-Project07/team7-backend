package hello.hellospring.service;

import hello.hellospring.entity.CommentEntity;
import hello.hellospring.entity.IssueEntity;
import hello.hellospring.entity.MemberEntity;
import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.mapper.CommentMapper;
import hello.hellospring.vo.CommentReqVo;
import hello.hellospring.vo.CommentResVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.IssueRepository;
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
public class CommentService {

    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final CommentMapper commentMapper;


    /**
     * MyBatis - 코멘트 추가
     * */
    public ResponseVo addComment(CommentReqVo commentReqVo) {
        if(ObjectUtils.isEmpty(commentReqVo)) {
            return null;
        }
        try {
            commentMapper.addComment(commentReqVo);
            CommentResVo commentResVo = commentMapper.selectByComId(commentReqVo);
            List<CommentResVo> result = new ArrayList<>();
            result.add(commentResVo);
            return new ResponseVo(200,"SUCCESS", result);
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }

    /**
     * PL1 > 이슈 및 코멘트 수정
     * */
    public ResponseVo updateComment(CommentReqVo commentReqVo) {
        if(ObjectUtils.isEmpty(commentReqVo)) {
            return null;
        }
        try {
            CommentResVo commentResVo = commentMapper.selectByComId(commentReqVo);
            /** PL1 > 코멘트 수정 */
            commentMapper.updateComment(commentReqVo);
            /** PL1 > 이슈 상태 변경 > assigned */

            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }


}
