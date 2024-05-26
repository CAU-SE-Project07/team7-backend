package hello.hellospring.service;

import hello.hellospring.entity.PriorityEntity;
import hello.hellospring.vo.PriorityVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.repository.PriorityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
public class PriorityServiceImpl implements PriorityService {

    private final PriorityRepository priorityRepository;
    @Autowired
    public PriorityServiceImpl(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public ResponseVo insertPriority(PriorityVo priorityVo) {
        try {
            if(ObjectUtils.isEmpty(priorityVo)) {
                return null;
            }
            PriorityEntity chkPriorityEntity = priorityRepository.findByPriorityNm(priorityVo.getPriorityNm());
            /** 우선순위 중복체크 */
            if(chkPriorityEntity != null) {
                return new ResponseVo(11,"이미 존재하는 우선순위입니다.1");
            }
            /** 우선순위 추가 */
            PriorityEntity priorityEntity = PriorityEntity.builder()
                    .priorityNm(priorityVo.getPriorityNm())
                    .level(priorityVo.getLevel())
                    .build();
            priorityRepository.save(priorityEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }

    @Override
    public ResponseVo updatePriority(PriorityVo priorityVo) {
        try {
            if(ObjectUtils.isEmpty(priorityVo)) {
                return null;
            }
            /** 받은 파라미터의 레벨로 우선순위 데이터 조회 => 기본키 알기 위한 작업 */
            PriorityEntity priorityEntity = priorityRepository.findByPriorityNm(priorityVo.getPriorityNm());
            if(priorityEntity == null) {
                return new ResponseVo(11, "우선순위가 존재하지 않습니다.");
            }
            /** 위 우선순위 entity를 통해 업데이트 해주기 */
            PriorityEntity updatePriorityEntity = PriorityEntity.builder()
                    .priorityId(priorityEntity.getPriorityId())
                    .priorityNm(priorityVo.getPriorityNm())
                    .level(priorityVo.getLevel())
                    .build();
            priorityRepository.save(updatePriorityEntity);

            return new ResponseVo(200, "SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99, "FAIL");
        }
    }
}
