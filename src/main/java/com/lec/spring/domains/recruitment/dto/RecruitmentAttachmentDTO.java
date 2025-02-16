package com.lec.spring.domains.recruitment.dto;

import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RecruitmentAttachmentDTO extends RecruitmentAttachment {
    private String fileName;
}
