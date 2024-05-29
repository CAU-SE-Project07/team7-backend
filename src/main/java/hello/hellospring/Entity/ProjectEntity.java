package hello.hellospring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "PROJECT")
@Data
@Builder
@AllArgsConstructor
@ToString (exclude = "memberEntities")
public class ProjectEntity {
    @Id
    private int projectId;
    @Column
    private String projectNm;
    @Column
    private String projectDesc;
    @OneToMany(mappedBy = "projectId", cascade = CascadeType.REMOVE)
    private List<MemberEntity> memberEntities ;
    @OneToMany(mappedBy = "projectId", cascade = CascadeType.REMOVE)
    private List<IssueEntity> issueEntities ;
    @OneToMany(mappedBy = "projectId", cascade = CascadeType.REMOVE)//?
    private List<CommentEntity> commentEntities;
    public ProjectEntity() {};
}
