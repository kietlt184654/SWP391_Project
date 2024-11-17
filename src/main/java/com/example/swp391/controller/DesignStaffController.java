package com.example.swp391.controller;

import com.example.swp391.entity.DesignEntity;
import com.example.swp391.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/designStaff")
public class DesignStaffController {

    @Autowired
    private DesignService designService;

    private final String UPLOAD_DIR = "D:\\K5\\SWP391\\Process_Img_Task";

    // Hiển thị danh sách thiết kế cần xử lý
    @GetMapping("/designs/inprogress")
    public String getInProgressDesigns(Model model) {
        List<DesignEntity> inProgressDesigns = designService.findByStatus(DesignEntity.Status.Pending);
        model.addAttribute("inProgressDesigns", inProgressDesigns);
        return "designStaffDesigns"; // Giao diện cho nhân viên thiết kế
    }

    // Hiển thị chi tiết thiết kế để gửi file thiết kế
    @GetMapping("/designs/{designId}/details")
    public String viewDesignDetails(@PathVariable Long designId, Model model) {
        DesignEntity design = designService.findById(designId);
        if (design == null || design.getStatus() != DesignEntity.Status.Pending) {
            model.addAttribute("errorMessage", "The design does not exist or is not in Pending status.");
            return "errorPage";
        }
        model.addAttribute("design", design);
        return "uploadDesign"; // Form upload file thiết kế
    }

    // Xử lý upload file thiết kế và cập nhật trạng thái
    @PostMapping("/designs/{designId}/submit")
    public String submitDesign(@PathVariable Long designId,
                               @RequestParam("designFile") MultipartFile designFile,
                               RedirectAttributes redirectAttributes) {
        try {
            DesignEntity design = designService.findById(designId);
            if (design != null && design.getStatus() == DesignEntity.Status.Pending) {
                // Kiểm tra nếu file không trống
                if (!designFile.isEmpty()) {
                    // Tạo đường dẫn và lưu file vào thư mục UPLOAD_DIR
                    Path path = Paths.get(UPLOAD_DIR, designFile.getOriginalFilename());
                    Files.createDirectories(path.getParent()); // Tạo thư mục nếu chưa tồn tại
                    Files.write(path, designFile.getBytes());

                    // Lưu đường dẫn file vào đối tượng DesignEntity
                    String imagePath = "/uploads/" + designFile.getOriginalFilename(); // Tùy chỉnh đường dẫn cho web
                    design.setImg(imagePath);
                    design.setStatus(DesignEntity.Status.Designed); // Cập nhật trạng thái thành Designed
                    designService.save(design);

                    redirectAttributes.addFlashAttribute("successMessage", "Design has been submitted successfully.");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "No file selected. Please upload a valid design file.");
                }
                return "redirect:/designStaff/designs/inprogress";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Design does not exist or is not in Pending status.");
                return "redirect:/designStaff/designs/inprogress";
            }
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while uploading the design: " + e.getMessage());
            return "redirect:/designStaff/designs/inprogress";
        }
    }
}
