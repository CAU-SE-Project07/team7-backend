package hello.hellospring.service;

import hello.hellospring.entity.PriorityEntity;
import hello.hellospring.vo.PriorityVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.repository.PriorityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            PriorityEntity priorityEntity = new PriorityEntity();
            priorityEntity.setPriorityNm(priorityVo.getPriorityNm());
            priorityEntity.setLevel(priorityVo.getLevel());
            priorityRepository.save(priorityEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }

    @Override
    public ResponseVo updatePriority(PriorityVo priorityVo) {
        try {
            /** 받은 파라미터의 레벨로 우선순위 데이터 조회 => 기본키 알기 위한 작업 */
            PriorityEntity priorityEntity = priorityRepository.findByPriorityNm(priorityVo.getPriorityNm());
            /** 위 entity를 통해 업데이트 해주기 */
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
