package com.SEProject.SEP.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    //제목, 내용, 작성자, 우선순위(중요, 덜중요,짱중요), 상태(new-> assign-> fixed -> resolved-> closed)
    private String title;
    private String content;
    private String reporter;
    private String priority;
    private String state;
    private String assignee;
}
