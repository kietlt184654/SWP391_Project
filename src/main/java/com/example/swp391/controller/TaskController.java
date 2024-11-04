package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.entity.StaffProjectEntity;
import com.example.swp391.service.AccountService;
import com.example.swp391.service.ProjectService;
import com.example.swp391.service.StaffProjectService;
import com.example.swp391.service.StaffService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;


@Controller


public class TaskController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffProjectService projectStaffService;
    @Autowired
    private StaffProjectService staffProjectService;

    @GetMapping("/staff/list")
    public String showStaffList(@RequestParam("projectId") Integer projectId, Model model) {
        // Lấy danh sách staff từ service
        List<StaffEntity> staffList = staffService.getAllStaff();

        // Thêm projectId và staffList vào model
        model.addAttribute("projectId", projectId);
        model.addAttribute("staffList", staffList);

        // Trả về tên view
        return "staffList"; // Tên file staffList.html
    }

    @GetMapping("/staff/add")
    public String addStaff(Model model) {
        model.addAttribute("staff", new StaffEntity());
        return "staff-add";
    }

    @PostMapping("/staff/save")
    public String saveStaff(@ModelAttribute StaffEntity staff) {
        staffService.save(staff);
        return "redirect:/staff/list";
    }

    @GetMapping("/staff/edit/{staffId}")
    public String editStaff(@PathVariable Integer staffId, Model model) {
        StaffEntity staff = staffService.getStaffById(staffId);
        model.addAttribute("staff", staff);
        return "staff-edit";
    }

    @GetMapping("/staff/delete/{staffId}")
    public String deleteStaff(@PathVariable Integer staffId) {
        staffService.deleteStaff(staffId);
        return "redirect:/staff/list";
    }
    @PostMapping("/tasks/project/assign")
    public String assignStaffToProject(
            @RequestParam("projectId") Long projectId,
            @RequestParam("staffId") Integer staffId,
            @RequestParam("taskDescription") String taskDescription,
            @RequestParam("deadline") String deadline,
            @RequestParam("status") String status,
            RedirectAttributes redirectAttributes) {

        // Log the received status
        System.out.println("Received status: " + status);

        // Continue with processing
        projectStaffService.assignStaffToProject(projectId, staffId, taskDescription, deadline, status);
        redirectAttributes.addFlashAttribute("successMessage", "Assign staff to project successfully");
        return "redirect:/staff/list?projectId=" + projectId;
    }
    @GetMapping("/filterTasks")
    public String filterTasks(@RequestParam(required = false, defaultValue = "All") String status, Model model) {
        List<StaffProjectEntity> tasks;
        if ("All".equals(status)) {
            tasks = staffProjectService.getAllTasks();
        } else {
            tasks = staffProjectService.getTasksByStatus(status);
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentStatus", status);
        return "viewDetailProject";
    }
    @GetMapping("/dashboard")
    public String staffDashboard(Model model, HttpSession session) {
        // Lấy thông tin người dùng hiện tại từ session
        AccountEntity currentUser = (AccountEntity) session.getAttribute("loggedInUser");

        // Nếu chưa đăng nhập hoặc không phải Staff thì chuyển về trang đăng nhập
        if (currentUser == null || !"Construction Staff".equals(currentUser.getAccountTypeID())) {
            return "redirect:/login";
        }

        int staffId = currentUser.getStaff().getStaffID(); // Lấy StaffID từ đối tượng Account
        List<StaffProjectEntity> staffProjects = staffProjectService.getAssignedProjects(staffId);
        model.addAttribute("staffProjects", staffProjects);

        return "StaffTask"; // Trả về view 'StaffTask'
    }
    @PostMapping("/accept")
    public String acceptProject(@RequestParam("staffProjectID") Integer staffProjectId,
                                @RequestParam("currentStatus") String currentStatus,
                                RedirectAttributes redirectAttributes) {
        String newStatus;
        if ("To Do".equals(currentStatus)) {
            newStatus = "In-Progress";
        } else if ("In-Progress".equals(currentStatus)) {
            newStatus = "Done";
        } else {
            newStatus = currentStatus; // Nếu trạng thái không nằm trong To Do hoặc In-Progress thì giữ nguyên
        }

        boolean success = staffProjectService.setStatusForStaffProject(staffProjectId, newStatus);

        String message;
        if (success) {
            message = "Project status updated successfully.";
        } else {
            message = "Failed to update project status.";
        }

        // Thêm message vào RedirectAttributes để truyền thông báo cho trang /dashboard
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/dashboard"; // Quay trở lại trang bảng danh sách sau khi cập nhật
    }
    @GetMapping("/project/images/{staffProjectID}")
    public String viewProjectImages(@PathVariable Integer staffProjectID, Model model) {
        StaffProjectEntity staffProject = staffProjectService.findById(staffProjectID);

        if (staffProject == null || staffProject.getProgressImage() == null || staffProject.getProgressImage().isEmpty()) {
            model.addAttribute("noImagesMessage", "Chưa cập nhật hình ảnh");
        } else {
            model.addAttribute("progressImages", staffProject.getProgressImage());
        }
        return "viewProjectImages"; // Trang HTML để hiển thị hình ảnh
    }
    private final String UPLOAD_DIR = "D:\\K5\\SWP391\\Process_Img_Task"; // Đường dẫn lưu trữ hình ảnh trên server

    @PostMapping("/uploadProgressImage")
    public String uploadProgressImage(@RequestParam("file") MultipartFile file,
                                      @RequestParam("url") String url,
                                      @RequestParam("staffProjectID") Integer staffProjectID,
                                      RedirectAttributes redirectAttributes) {

        String imagePath = null;

        try {
            if (!file.isEmpty()) {
                Path path = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
                Files.write(path, file.getBytes());
                imagePath = "/uploads/" + file.getOriginalFilename();
            } else if (url != null && !url.isEmpty()) {
                imagePath = url;
            }

            if (imagePath != null) {
                staffProjectService.updateProgressImage(staffProjectID, imagePath);
                redirectAttributes.addFlashAttribute("message", "Cập nhật hình ảnh thành công!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Vui lòng tải lên một tệp hoặc nhập URL.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Lỗi khi tải lên hình ảnh.");
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/deleteService/{id}")
    public String deleteStaffProject(@PathVariable("id") int staffProjectID,
                                     @RequestParam("projectID") int projectID,
                                     RedirectAttributes redirectAttributes) {
        staffProjectService.deleteStaffProjectById(staffProjectID);
        redirectAttributes.addFlashAttribute("successMessageDeleteTask", "Task deleted successfully.");
        return "redirect:/projects/viewDetailProject/" + projectID;
    }
}