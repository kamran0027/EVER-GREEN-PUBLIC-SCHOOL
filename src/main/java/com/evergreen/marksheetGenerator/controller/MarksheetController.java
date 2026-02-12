package com.evergreen.marksheetGenerator.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.evergreen.marksheetGenerator.dto.MarksheetData;
import com.evergreen.marksheetGenerator.dto.SubjectMark;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.List;

@Controller
public class MarksheetController {



    // Folder to store generated PDFs and uploaded images
    private final Path storagePath = Paths.get("storage");

    public MarksheetController() throws IOException {
        Files.createDirectories(storagePath);
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("marksheetData", new MarksheetData());
        return "form";
    }

    @PostMapping("/result")
    public String ShowResult(@ModelAttribute MarksheetData data,
                                @RequestParam("studentPhoto") MultipartFile studentPhoto, Model model) {
        
        try {
            // 1. Calculations
            int totalMarks = data.getHindi() + data.getEnglish() + data.getMath() + 
                             data.getScience() + data.getSocialScience() + data.getGk() + data.getDrawing();
            double percentage = (totalMarks / 700.0) * 100;
            String division = percentage >= 60 ? "First" : (percentage >= 45 ? "Second" : "Third");
            model.addAttribute("totalMarks", totalMarks);
            model.addAttribute("percentage", String.format("%.2f", percentage));
            model.addAttribute("division", division);
            model.addAttribute("data", data);
            List<SubjectMark> subjects = List.of(
                new SubjectMark("HINDI", data.getHindi()),
                new SubjectMark("ENGLISH", data.getEnglish()),
                new SubjectMark("MATH", data.getMath()),
                new SubjectMark("SCIENCE", data.getScience()),
                new SubjectMark("SOCIAL SCIENCE", data.getSocialScience()),
                new SubjectMark("G.K", data.getGk()),
                new SubjectMark("DRAWING", data.getDrawing()));


            model.addAttribute("subjects", subjects);
            String studentPhotoBase64 = convertToBase64(studentPhoto);
            model.addAttribute("studentPhoto", studentPhotoBase64);
            
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to process student photo."+e.getMessage());
            return "error";

        }
        return "marksheet_new";
    }

    // Helper to convert MultipartFile to Base64
    private String convertToBase64(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;
        byte[] bytes = file.getBytes();
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
    }
    
    // Helper to convert local file to Base64
    private String convertPathToBase64(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
    }
    
}
