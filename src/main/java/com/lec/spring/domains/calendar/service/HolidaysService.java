package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.entity.Calendar;

import java.util.List;

public interface HolidaysService {
    List<HolidaysDTO> getHolidaysForCurrentMonth();
}
