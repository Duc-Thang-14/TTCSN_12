package com.Ha.Anh.repository;

import com.Ha.Anh.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    // Các phương thức tìm kiếm nếu cần
}
