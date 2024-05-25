package hello.hellospring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "PROJECT")
@Data
@Builder
@AllArgsConstructor
public class ProjectEntity {
    @Id
    private int projectId;
    @Column
    private String projectNm;
    @Column
    private String projectDesc;
    public ProjectEntity() {};
}
