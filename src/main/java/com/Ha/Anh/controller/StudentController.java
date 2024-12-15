package com.Ha.Anh.controller;

import com.Ha.Anh.model.Grade;
import com.Ha.Anh.model.GradeId;
import com.Ha.Anh.model.Student;
import com.Ha.Anh.model.Subject;
import com.Ha.Anh.repository.GradeRepository;
import com.Ha.Anh.repository.StudentRepository;
import com.Ha.Anh.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

//    @Autowired
//    private GradeRepository gradeRepository;
//
//    @Autowired
//    private SubjectRepository subjectRepository;
//
    @GetMapping
    public String studentPage() {
        return "student"; // Trả về file `student.html`
    }
//
//    // Hiển thị trang quản lý điểm
//    @GetMapping("/manage-grade")
//    public String manageGrade(Model model) {
//        return "manage_grade"; // Trả về trang manage_grade.html
//    }
    //Điểm-------------------
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // Hiển thị trang quản lý điểm
    @GetMapping("/manage-grade")
    public String manageGrade(Model model) {
        // Lấy danh sách tất cả môn học
        List<Subject> subjects = subjectRepository.findAll();
        model.addAttribute("subjects", subjects); // Gửi danh sách môn học qua model
        return "manage_grade"; // Trả về trang manage_grade.html
    }

    // Xử lý tìm kiếm sinh viên theo môn học
    @GetMapping("/manage-grade/search")
    public String searchStudentsBySubject(@RequestParam("subjectId") String subjectId, Model model) {
        // Lấy danh sách sinh viên học môn học được chọn
        List<Grade> grades = gradeRepository.findBySubject_SubjectID(subjectId);

        model.addAttribute("grades", grades); // Gửi danh sách sinh viên và điểm
        model.addAttribute("selectedSubject", subjectId); // Gửi ID môn học đang được chọn
        model.addAttribute("subjects", subjectRepository.findAll()); // Gửi lại danh sách môn học
        return "manage_grade";
    }

    // Xử lý lưu điểm
    @PostMapping("/manage-grade/save")
    public String saveGrade(
            @RequestParam("studentId") String studentId,
            @RequestParam("subjectId") String subjectId,
            @RequestParam("regularGrade1") Double regularGrade1,
            @RequestParam("regularGrade2") Double regularGrade2,
            @RequestParam("finalGrade") Double finalGrade
    ) {
        // Tìm bản ghi điểm theo ID
        GradeId gradeId = new GradeId(studentId, subjectId);
        Grade grade = gradeRepository.findById(gradeId).orElse(new Grade());
        grade.setId(gradeId);
        grade.setRegularGrade1(regularGrade1);
        grade.setRegularGrade2(regularGrade2);
        grade.setFinalGrade(finalGrade);

        // Lưu vào cơ sở dữ liệu
        gradeRepository.save(grade);
        return "redirect:/student/manage-grade"; // Quay lại trang quản lý điểm
    }
}




