package hello.hellospring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "PRIORITY")
@Data
@Builder
@AllArgsConstructor
public class PriorityEntity {
    @Id
    private int priorityId;
    private String priorityNm; // 우선순위 명
    private int level; // 레벨
    public PriorityEntity() {};
}
