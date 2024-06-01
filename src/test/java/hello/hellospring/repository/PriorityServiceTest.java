package hello.hellospring.service;

import hello.hellospring.Entity.PriorityEntity;
import hello.hellospring.Vo.PriorityVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.repository.PriorityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriorityServiceTest {

    @Mock
    private PriorityRepository priorityRepository;

    @InjectMocks
    private PriorityServiceImpl priorityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void insertPriority_shouldReturnSuccess_whenValidPriorityVo() {
        // Given
        PriorityVo priorityVo = PriorityVo.builder()
                .priorityNm("NEW_PRIORITY")
                .level(1)
                .build();
        when(priorityRepository.save(any())).thenReturn(new PriorityEntity());

        // When
        ResponseVo response = priorityService.insertPriority(priorityVo);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("SUCCESS", response.getMsg());
        verify(priorityRepository, times(1)).save(any(PriorityEntity.class));
    }

    @Test
    void updatePriority_shouldReturnSuccess_whenValidPriorityVo() {
        // Given
        PriorityVo priorityVo = PriorityVo.builder()
                .priorityNm("EXISTING_PRIORITY")
                .level(2)
                .build();
        when(priorityRepository.findByPriorityNm(priorityVo.getPriorityNm())).thenReturn(new PriorityEntity());

        // When
        ResponseVo response = priorityService.updatePriority(priorityVo);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("SUCCESS", response.getMsg());
        verify(priorityRepository, times(1)).save(any(PriorityEntity.class));
    }

    @Test
    void updatePriority_shouldReturnFailure_whenPriorityNotFound() {
        // Given
        PriorityVo priorityVo = PriorityVo.builder()
                .priorityNm("NON_EXISTING_PRIORITY")
                .level(2)
                .build();
        when(priorityRepository.findByPriorityNm(priorityVo.getPriorityNm())).thenReturn(null);

        // When
        ResponseVo response = priorityService.updatePriority(priorityVo);

        // Then
        assertNotNull(response);
        assertEquals(99, response.getCode()); // 실패 코드
        assertEquals("FAIL", response.getMsg()); // 실제 반환된 메시지가 "FAIL"임을 확인
        verify(priorityRepository, never()).save(any(PriorityEntity.class)); // 저장이 일어나지 않아야 함
    }


}