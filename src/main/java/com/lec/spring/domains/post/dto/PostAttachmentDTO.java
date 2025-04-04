package com.lec.spring.domains.post.dto;

import com.lec.spring.domains.post.entity.PostAttachment;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PostAttachmentDTO extends PostAttachment {
    private String fileName;
}
