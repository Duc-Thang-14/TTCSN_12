package com.Ha.Anh.repository;

import com.Ha.Anh.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
}
