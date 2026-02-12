package com.evergreen.marksheetGenerator.dto;



import lombok.Data;

@Data
public class MarksheetData {
    private String studentName;
    private String fatherName;
    private String rollNo;
    private String motherName;
    private String dateOfBirth;
    private String studentClass;
    
    // Marks
    private int hindi;
    private int english;
    private int math;
    private int science;
    private int socialScience;
    private int gk;
    private int drawing;

}