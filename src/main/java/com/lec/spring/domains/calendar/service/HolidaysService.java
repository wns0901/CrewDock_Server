package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.dto.HolidaysDTO;

import java.util.List;

public interface HolidaysService {
    List<HolidaysDTO> getHolidays(int year);
}
