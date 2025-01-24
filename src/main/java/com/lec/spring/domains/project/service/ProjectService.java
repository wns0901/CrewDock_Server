package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.Project;

import java.util.Calendar;
import java.util.List;

public interface ProjectService {

    List<Project> findById(Long id);

    Project update(Project project);

    Calendar findCalenderById(Long id);
}
