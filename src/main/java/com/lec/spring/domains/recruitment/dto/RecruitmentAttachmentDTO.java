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
    private Long recruitmentId;

    public static RecruitmentAttachmentDTO of(RecruitmentAttachment attachment, String fileName) {
        RecruitmentAttachmentDTO dto = new RecruitmentAttachmentDTO();
        dto.setId(attachment.getId());
        dto.setFileName(fileName);
        dto.setUrl(attachment.getUrl());
        dto.setRecruitmentId(attachment.getPost().getId());
        return dto;
    }
}
