package hello.hellospring.repository;

import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Vo.ProjectVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.service.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insertProject_shouldReturnSuccess_whenValidProjectVo() {
        // Given
        ProjectVo projectVo = new ProjectVo("New Project", "Project Description");
        when(projectRepository.findByProjectNm(projectVo.getProjectNm())).thenReturn(null);
        when(projectRepository.findAll()).thenReturn(List.of());

        // When
        ResponseVo response = projectService.insertProject(projectVo);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("SUCCESS", response.getMsg());
        verify(projectRepository, times(1)).save(any(ProjectEntity.class));
    }

    @Test
    void insertProject_shouldReturnNull_whenProjectVoIsEmpty() {
        // When
        ResponseVo response = projectService.insertProject(null);

        // Then
        assertNull(response);
        verify(projectRepository, never()).save(any(ProjectEntity.class));
    }


}
