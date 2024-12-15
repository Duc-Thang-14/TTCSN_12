package com.Ha.Anh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Schedule")
public class Schedule {

    @Id
    @Column(name = "ScheduleID")
    private String scheduleID; // Changed to String

    @Column(name = "ClassTime", nullable = false)
    private String classTime;

    @Column(name = "RoomLocation", nullable = false)
    private String roomLocation;

    @Column(name = "Campus", nullable = false)
    private String campus;

    @ManyToOne
    @JoinColumn(name = "SubjectID", referencedColumnName = "SubjectID", nullable = false)
    private Subject subject;

    // Getters and Setters
    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
