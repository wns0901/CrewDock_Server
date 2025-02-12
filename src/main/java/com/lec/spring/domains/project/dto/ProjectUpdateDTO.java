package com.lec.spring.domains.project.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectUpdateDTO {
    private Long id;
    private String name;
    private Integer period;
    private LocalDate startDate;
    private String status;
    private String githubUrl1;
    private String githubUrl2;
    private String designUrl;
    private String imgUrl;
    private String introduction;
    private List<Long> stackIds;
    private MultipartFile file;
}
