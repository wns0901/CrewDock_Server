package com.lec.spring.domains.recruitment.dto;

import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentCommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllRecruitmentCommentDTO {
    private Integer count;
    private List<RecruitmentCommentDTO> comments;
}
