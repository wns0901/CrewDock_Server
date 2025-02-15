package com.lec.spring.domains.recruitment.entity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecruitmentCommentDTO {
    private Long id;
    private String content;
    private Long userId;  // userId 추가
    private String userName; // 사용자 이름
    private Long parentCommentId; // 부모 댓글 ID
    private Boolean isDeleted; // 삭제 여부
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt; // 생성일

    public static RecruitmentCommentDTO fromEntity(RecruitmentComment comment) {
        RecruitmentCommentDTO dto = new RecruitmentCommentDTO();
        dto.setId(comment.getId());
        dto.setUserId(comment.getUser() != null ? comment.getUser().getId() : null);
        dto.setContent(comment.getContent());
        dto.setUserName(comment.getUser() != null ? comment.getUser().getNickname() : "알 수 없음");
        dto.setIsDeleted(comment.getDeleted());
        dto.setCreatedAt(comment.getCreatedAt());
        Long parentId = comment.getComment() != null ? comment.getComment().getId() : null;
        dto.setParentCommentId(parentId);

        System.out.println("DTO 변환: 댓글 ID = " + dto.getId() + ", 부모 댓글 ID = " + parentId);

        return dto;
    }



}
