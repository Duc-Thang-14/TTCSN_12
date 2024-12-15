package com.Ha.Anh.service;

import com.Ha.Anh.model.Schedule;
import com.Ha.Anh.model.Subject;
import com.Ha.Anh.repository.ScheduleRepository;
import com.Ha.Anh.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    // Lấy danh sách tất cả thời khóa biểu
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll(); // JpaRepository cung cấp sẵn phương thức này
    }

    // Lưu một thời khóa biểu mới hoặc cập nhật thời khóa biểu
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule); // JpaRepository sẽ tự động lưu hoặc cập nhật
    }

    public Schedule getScheduleById(String scheduleID) {
        return scheduleRepository.findById(scheduleID).orElse(null);
    }

    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public void updateSchedule(Schedule schedule) {
        scheduleRepository.save(schedule); // Update thông qua save
    }

    public void deleteSchedule(String scheduleID) {
        scheduleRepository.deleteById(scheduleID);
    }


}

