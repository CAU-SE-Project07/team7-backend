package hello.hellospring.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PL extends Member {

    @ManyToMany(mappedBy = "PLS")
    @JsonBackReference
    private List<Project> projects;
}