package com.example.swp391.controller;//package com.example.swp391.controller;
//
//import com.example.swp391.entity.ProjectEntity;
////import com.example.swp391.entity.StaffEntity;
////import com.example.swp391.entity.StaffProjectEntity;
//import com.example.swp391.service.ProjectService;
////import com.example.swp391.service.StaffProjectService;
////import com.example.swp391.service.StaffService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/projects")
//public class ProjectController {
//    @Autowired
//    private ProjectService projectService;
////    @Autowired
////    private StaffService staffService;
//
//
//    @GetMapping("/showProjects")
//    public String showProjects(Model model) {
//        List<ProjectEntity> projects = projectService.findAll();  // Giả sử có phương thức lấy tất cả các project
//        model.addAttribute("projects", projects);
//        return "show_projects";
//    }
//
////    // API lấy danh sách nhân viên có role là 'Staff'
////    @GetMapping("/staff/list")
////    @ResponseBody
////    public List<StaffEntity> getStaffList() {
////        return staffService.getAllStaffWithRole();  // Lấy danh sách nhân viên có vai trò 'Staff'
////    }
////
////    // API thêm staff vào dự án
////    @PostMapping("/staff/addToProject")
////    @ResponseBody
////    public ResponseEntity<String> addStaffToProject(@RequestParam Integer staffID, @RequestParam Integer projectID) {
////        projectService.addStaffToProject(staffID, projectID);  // Thêm nhân viên vào dự án
////        return ResponseEntity.ok("Staff added successfully to the project");
////    }
//
//    @GetMapping("/viewDetailProject/{projectID}")
//    public String showDetailProject(@PathVariable Integer projectID, Model model) {
//        ProjectEntity project = projectService.getProjectById(projectID)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectID));
//        model.addAttribute("project", project);
//        return "viewDetailProject"; // Name of the HTML file for displaying the project details
//    }
//
//
//
//
//
//
//}
