package com.Ha.Anh.repository;

import com.Ha.Anh.model.Grade;
import com.Ha.Anh.model.GradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, GradeId> {
    List<Grade> findBySubject_SubjectID(String subjectId); // Tìm danh sách điểm theo môn học
}
