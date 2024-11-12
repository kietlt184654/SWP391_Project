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
    private TypeDesignService typeDesignService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/showAllDesign")
    public String showProducts(Model model) {
        List<DesignEntity> products = designService.getDesignsByTypeId();
        model.addAttribute("design", products);
        return "Availableproject";
    }

    @GetMapping("/{id}")
    public String getDesignDetail(@PathVariable("id") Long id, Model model) {
        Optional<DesignEntity> designOpt = designService.getProductById(id);
        if (designOpt.isPresent()) {
            DesignEntity design = designOpt.get();
            model.addAttribute("design", design);
            return "viewProductDetail";
        } else {
            return "404";
        }
    }

    @GetMapping("/create")
    public String showCreateDesignForm() {
        return "createDesign";
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
        DesignEntity design = new DesignEntity();
        design.setCustomerReference(customerReference);
        design.setDesignName(designName);
        design.setWaterCapacity(waterCapacity);
        design.setDescription(description);
        design.setSize(DesignEntity.Size.valueOf(size));
        design.setPrice(price);
        design.setShapeOfPond(shapeOfPond);
        design.setEstimatedCompletionTime(estimatedCompletionTime);
        design.setStatus(DesignEntity.Status.Pending);

        TypeDesignEntity typeDesign = typeDesignService.findById(2L);
        if (typeDesign == null) {model.addAttribute("errorMessage", "Invalid design type.");
            return "createDesign";
        }

        design.setTypeDesign(typeDesign);
        designService.save(design);

        model.addAttribute("message", "The design has been submitted for review!");
        return "redirect:/HomeConsulting";
    }
}