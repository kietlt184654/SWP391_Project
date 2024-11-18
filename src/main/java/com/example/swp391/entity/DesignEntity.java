package com.example.swp391.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.List;

@Entity
@Table(name = "Design")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DesignID")
    private long designId; // IDENTITY trong SQL nên dùng kiểu Long

    @Column(name = "DesignName", nullable = false, length = 100)
    private String designName; // Tên thiết kế

    private Float waterCapacity;

    @ManyToOne // Thêm mối quan hệ với TypeDesignEntity
    @JoinColumn(name = "TypeDesignID", nullable = false) // Đảm bảo cột này liên kết đúng
    private TypeDesignEntity typeDesign; // Loại thiết kế

    @Column(name = "Description", length = 255)
    private String description; // Mô tả thiết kế

    @Column(name = "Img", length = 255)
    private String img; // Đường dẫn tới ảnh



    @Column(name = "Size", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Size size; // Kích cỡ của thiết kế

    @Column(name = "Price", nullable = false)
    @Positive(message = "Price must be a positive value.")
    private double price; // Giá không âm

    private String shapeOfPond;

    @Column(name = "EstimatedCompletionTime", nullable = false)
    @Min(value = 1, message = "Estimated completion time must be greater than 0.")
    private int estimatedCompletionTime; // Số ngày hoàn thành lớn hơn 0

    @Column(name = "Status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Status status; // Trạng thái của thiết kế

    public enum Size {
        Small, Medium, Large
    }

    public enum Status {
        Available, Unavailable, Pending, Rejected, NeedToPayment, InDesign, Designed
    }
    @OneToMany(mappedBy = "design", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<DesignMaterialQuantity> designMaterialQuantities; // Danh sách các vật liệu cần thiết

    public List<DesignMaterialQuantity> getMaterialQuantities() {
        return this.designMaterialQuantities; // Trả về danh sách các vật liệu cần thiết
    }
    @Column(name = "CustomerReference")
    private Long customerReference;






    public String getFormattedPrice() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(this.price).replace(",", ".");
    }


}