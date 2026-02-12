package com.evergreen.marksheetGenerator.dto;

public class SubjectMark {
    private String name;
    private int marks;

    public SubjectMark(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public int getMarks() {
        return marks;
    }
}

