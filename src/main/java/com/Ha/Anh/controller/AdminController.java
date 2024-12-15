package com.Ha.Anh.controller;

import com.Ha.Anh.model.Schedule;
import com.Ha.Anh.model.Student;
import com.Ha.Anh.model.Subject;
import com.Ha.Anh.service.ScheduleService;
import com.Ha.Anh.service.StudentService;
import com.Ha.Anh.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String adminPage() {
        return "admin"; // Trang chính dành cho Admin
    }

    @GetMapping("/manage-students")
    public String manageStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        System.out.println("Students: " + students); // Debug
        model.addAttribute("students", students);
        return "manage_students";
    }

    // Thêm Sinh viên
    @PostMapping("/add-student")
    public String addStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        try {
            if (student.getStudentID() == null || student.getStudentID().isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Mã sinh viên không được để trống!");
                return "redirect:/admin/manage-students";
            }

            // Kiểm tra nếu mã sinh viên đã tồn tại
            if (studentService.isStudentIDExists(student.getStudentID())) {
                redirectAttributes.addFlashAttribute("message", "Mã sinh viên đã tồn tại, vui lòng nhập mã khác!");
                return "redirect:/admin/manage-students";
            }

            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("message", "Thêm sinh viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm sinh viên: " + e.getMessage());
        }
        return "redirect:/admin/manage-students";
    }

    // Sửa sinh viên
    @PostMapping("edit-student")
    public String editStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        // Giả sử bạn đã thực hiện các thao tác sửa sinh viên
        studentService.save(student); // Giả sử studentService.save() là phương thức lưu dữ liệu

        // Thêm thông báo thành công vào redirectAttributes
        redirectAttributes.addFlashAttribute("message", "Sửa sinh viên thành công!");

        return "redirect:/admin/manage-students"; // Chuyển hướng về trang quản lý sinh viên
    }

    // Xóa Sinh viên
    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable String id, Model model) {
        studentService.deleteStudent(id); // Xóa sinh viên
        model.addAttribute("message", "Xóa sinh viên thành công!");
        return "redirect:/admin/manage-students"; // Quay lại trang quản lý sinh viên
    }

    // Môn học---------------------------------------------------------------------------------------------------------------------------------------------------
    @Autowired
    private SubjectService subjectService;

    // Quản lý Subject
    @GetMapping("/manage-subjects")
    public String manageSubjects(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "manage_subjects";
    }

    // Thêm Subject
    @PostMapping("/add-subject")
    public String addSubject(@ModelAttribute Subject subject, RedirectAttributes redirectAttributes) {
        try {
            if (subject.getSubjectID() == null || subject.getSubjectID().isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Mã môn học không được để trống!");
                return "redirect:/admin/manage-subjects";
            }

            // Kiểm tra nếu mã môn học đã tồn tại
            if (subjectService.isSubjectIDExists(subject.getSubjectID())) {
                redirectAttributes.addFlashAttribute("message", "Mã môn học đã tồn tại, vui lòng nhập mã khác!");
                return "redirect:/admin/manage-subjects";
            }

            subjectService.saveSubject(subject);
            redirectAttributes.addFlashAttribute("message", "Thêm môn học thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm môn học: " + e.getMessage());
        }
        return "redirect:/admin/manage-subjects";
    }

    // Sửa Subject
    @PostMapping("/edit-subject")
    public String editSubject(@ModelAttribute Subject subject, RedirectAttributes redirectAttributes) {
        subjectService.save(subject); // Lưu thay đổi môn học
        redirectAttributes.addFlashAttribute("message", "Sửa môn học thành công!");
        return "redirect:/admin/manage-subjects";
    }

    // Xóa Subject
    @GetMapping("/delete-subject/{id}")
    public String deleteSubject(@PathVariable String id, Model model) {
            subjectService.deleteSubject(id); // Xóa môn học
            model.addAttribute("message", "Xóa môn học thành công!");
        return "redirect:/admin/manage-subjects";
    }

    // Thời khóa biểu---------------------------------------------------------------------------------------------------------------------------------------------------
    @Autowired
    private ScheduleService scheduleService; // Service để làm việc với bảng Schedule

    // Hiển thị danh sách thời khóa biểu

    @GetMapping("/manage-schedule")
    public String manageSchedule(Model model) {
        // Lấy danh sách thời khóa biểu
        List<Schedule> schedules = scheduleService.getAllSchedules();

        // Lấy danh sách các môn học
        List<Subject> subjects = subjectService.getAllSubjects();

        // Thêm dữ liệu vào model
        model.addAttribute("schedules", schedules);
        model.addAttribute("subjects", subjects);

        return "manage_schedule"; // Chuyển đến trang HTML
    }
//    @PostMapping("/save-schedule")
//    public String saveSchedule(@ModelAttribute Schedule schedule, @RequestParam("subjectID") String subjectID) {
//        // Tìm môn học dựa trên subjectID
//        Subject subject = subjectService.findById(subjectID);
//        schedule.setSubject(subject);
//
//        // Lưu thời khóa biểu vào cơ sở dữ liệu
//        scheduleService.save(schedule);
//
//        // Redirect về trang quản lý thời khóa biểu
//        return "redirect:/admin/manage-schedule";
//    }
    @PostMapping("/save-schedule")
    public String saveSchedule(
            @ModelAttribute Schedule schedule,
            @RequestParam("subjectID") String subjectID,
            RedirectAttributes redirectAttributes) {
        Subject subject = subjectService.findById(subjectID);
        schedule.setSubject(subject);

        scheduleService.save(schedule);

        // Thêm thông báo vào RedirectAttributes
        redirectAttributes.addFlashAttribute("message", "Thêm thời khóa biểu thành công!");

        return "redirect:/admin/manage-schedule";
    }

    // Hiển thị form sửa thời khóa biểu
    @GetMapping("/edit-schedule/{scheduleID}")
    public String editSchedule(@PathVariable("scheduleID") String scheduleID, Model model) {
        Schedule schedule = scheduleService.getScheduleById(scheduleID);
        model.addAttribute("schedule", schedule);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "edit_schedule"; // Trang sửa thời khóa biểu
    }

    // Cập nhật thông tin thời khóa biểu
//    @PostMapping("/update-schedule")
//    public String updateSchedule(@ModelAttribute Schedule schedule, @RequestParam("subjectID") String subjectID) {
//        // Lấy đối tượng Subject từ database dựa trên subjectID
//        Subject subject = subjectService.getSubjectById(subjectID);
//
//        // Gán Subject vào Schedule
//        schedule.setSubject(subject);
//
//        // Cập nhật thông tin thời khóa biểu
//        scheduleService.updateSchedule(schedule);
//
//        return "redirect:/admin/manage-schedule"; // Quay lại trang quản lý thời khóa biểu
//    }
    @PostMapping("/update-schedule")
    public String updateSchedule(
            @ModelAttribute Schedule schedule,
            @RequestParam("subjectID") String subjectID,
            RedirectAttributes redirectAttributes) {
        Subject subject = subjectService.getSubjectById(subjectID);
        schedule.setSubject(subject);

        scheduleService.updateSchedule(schedule);

        // Thêm thông báo vào RedirectAttributes
        redirectAttributes.addFlashAttribute("message", "Cập nhật thời khóa biểu thành công!");

        return "redirect:/admin/manage-schedule";
    }

    // Xóa thời khóa biểu
    @PostMapping("/delete-schedule")
    public String deleteSchedule(@RequestParam("scheduleID") String scheduleID) {
        scheduleService.deleteSchedule(scheduleID);
        return "redirect:/admin/manage-schedule"; // Quay lại trang quản lý thời khóa biểu
    }





}
