package com.example.swp391.controller;


import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.entity.DesignEntity;
import com.example.swp391.entity.TypeDesignEntity;
import com.example.swp391.service.CustomerService;
import com.example.swp391.service.DesignService;
import com.example.swp391.service.TypeDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/design")
public class DesignController {

    @Autowired
    private DesignService designService;
    @Autowired
    private TypeDesignService typeDesignService; // Thêm dịch vụ để làm việc với TypeDesign
    @Autowired
    private CustomerService customerService;

    //    private ImageRepository imgRepo;
//show thiet ke
    @GetMapping("/showAllDesign")
    public String showProducts(Model model) {
        // Lấy danh sách các thiết kế có typeDesignId = 1 từ service
        List<DesignEntity> products = designService.getDesignsByTypeId();
        model.addAttribute("design", products);
        return "Availableproject"; // Trả về trang "Availableproject.html"
    }
    // Hiển thị chi tiết sản phẩm
    @GetMapping("/{id}")
    public String getDesignDetail(@PathVariable("id") Long id, Model model) {
        Optional<DesignEntity> designOpt = designService.getProductById(id);
        if (designOpt.isPresent()) {
            DesignEntity design = designOpt.get();
//            List<DesignImgEntity> images = imgRepo.findByDesignId(id);
            model.addAttribute("design", design);
//            model.addAttribute("images", images);
            return "viewProductDetail"; // Tên của template Thymeleaf
        } else {
            return "404"; // Trang lỗi 404 nếu không tìm thấy
        }
    }

    @GetMapping("/create")
    public String showCreateDesignForm() {
        return "createDesign"; // Trả về trang HTML để tạo thiết kế mới
    }
    @PostMapping("/create")
    public String createDesign(@RequestParam("customerReference") Long customerReference,
                               @RequestParam("designName") String designName,
                               @RequestParam("waterCapacity") Float waterCapacity,
                               @RequestParam("description") String description,
                               @RequestParam("size") String size,
                               @RequestParam("price") double price,
                               @RequestParam("shapeOfPond") String shapeOfPond,
                               @RequestParam("estimatedCompletionTime") int estimatedCompletionTime,
                               Model model) {
        // Tạo đối tượng DesignEntity mới và thiết lập các thuộc tính
        DesignEntity design = new DesignEntity();
        design.setCustomerReference(customerReference);  // Lưu thông tin tham chiếu của khách hàng (có thể là tên, email, hoặc mã tham chiếu)
        design.setDesignName(designName);
        design.setWaterCapacity(waterCapacity);
        design.setDescription(description);
        design.setSize(DesignEntity.Size.valueOf(size));
        design.setPrice(price);
        design.setShapeOfPond(shapeOfPond);
        design.setEstimatedCompletionTime(estimatedCompletionTime);
        design.setStatus(DesignEntity.Status.Pending);

        // Lấy TypeDesignEntity với typeDesignId = 2
        TypeDesignEntity typeDesign = typeDesignService.findById(2L);
        if (typeDesign == null) {
            model.addAttribute("errorMessage", "Loại thiết kế không hợp lệ.");
            return "createDesign";
        }

        design.setTypeDesign(typeDesign);

        // Lưu thiết kế vào cơ sở dữ liệu
        designService.save(design);

        model.addAttribute("message", "Thiết kế đã được gửi đi để duyệt!");
        return "redirect:/HomeConsulting";
    }


}