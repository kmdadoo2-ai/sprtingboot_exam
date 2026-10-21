package com.study.springboot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MyController 
{
    @GetMapping("/")
    @ResponseBody
    public String root() 
    {
        return "FileUpload";
    }

    @GetMapping("/uploadForm")
    public String uploadForm() 
    {
        return "/fileForm";
    }

    @PostMapping("/uploadOk")
    @ResponseBody
    public Map<String, Object> uploadOk(
            @RequestParam("filename") MultipartFile file) 
    {
        Map<String, Object> result = new HashMap<>();

        try {
            if (file.isEmpty()) {
                result.put("success", "0");
                result.put("desc", "파일을 선택하세요.");
                return result;
            }
            
            long fileSize = file.getSize();
            String contentType = file.getContentType();

            Path uploadPath = Paths.get("upload").toAbsolutePath().normalize();

            Files.createDirectories(uploadPath);

            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null) {
                int index = originalFilename.lastIndexOf('.');

                if (index >= 0) {
                    extension = originalFilename.substring(index);
                }
            }

            String savedFilename = UUID.randomUUID() + extension;
            
            Path destination = uploadPath.resolve(savedFilename);
            file.transferTo(destination);
            
            System.out.println("Original Filename : " + originalFilename);
            System.out.println("Saved Filename : " + savedFilename);
            System.out.println("File Size (bytes) : " + fileSize);
            System.out.println("Content Type : " + contentType);
            System.out.println("Saved Path : " + destination);
            
            result.put("success", "1");
            result.put("desc", "업로드 성공");
            result.put("originalFilename", originalFilename);
            result.put("savedFilename", savedFilename);
            result.put("fileSize", fileSize);
            result.put("contentType", contentType);
        } catch (IOException e) {
            result.put("success", "0");
            result.put("desc", "업로드 실패");
        }

        return result;
    }
    
    @PostMapping("/uploadMultiple")
    @ResponseBody
    public Map<String, Object> uploadMultiple(
            @RequestParam(value = "filename", required = false) MultipartFile[] files) 
    {
        Map<String, Object> response = new HashMap<>();

        if (files == null || files.length == 0 || (files.length == 1 && files[0].isEmpty())) {
            response.put("success", "0");
            response.put("desc", "업로드할 파일을 선택하세요.");
            return response;
        }

        List<Map<String, Object>> fileList = new ArrayList<>();

        try {
            Path uploadPath = Paths.get("upload").toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }

                Map<String, Object> fileInfo = new HashMap<>();
                long fileSize = file.getSize();
                String contentType = file.getContentType();
                String originalFilename = file.getOriginalFilename();
                String extension = "";

                if (originalFilename != null) {
                    int index = originalFilename.lastIndexOf('.');
                    if (index >= 0) {
                        extension = originalFilename.substring(index);
                    }
                }

                String savedFilename = UUID.randomUUID() + extension;
                Path destination = uploadPath.resolve(savedFilename);
                file.transferTo(destination);

                fileInfo.put("originalFilename", originalFilename);
                fileInfo.put("savedFilename", savedFilename);
                fileInfo.put("fileSize", fileSize);
                fileInfo.put("contentType", contentType);

                fileList.add(fileInfo);
            }

            response.put("success", "1");
            response.put("desc", "다중 파일 업로드 성공");
            response.put("files", fileList);

        } catch (IOException e) {
            response.put("success", "0");
            response.put("desc", "업로드 실패");
        }

        return response;
    }
}
