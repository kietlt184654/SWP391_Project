package com.example.swp391.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Design") // Tên bảng trong SQL
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

    private Float WaterCapacity;

    @ManyToOne // Thêm annotation này để xác định mối quan hệ Many-to-One
    @JoinColumn(name = "TypeDesignID", nullable = false) // Liên kết đến cột khóa chính trong bảng TypeDesign
    private TypeDesignEntity typeDesignId; // Liên kết đến TypeDesignEntity

    @Column(name = "Description", length = 255)
    private String description; // Mô tả thiết kế

    @Column(name = "Img", length = 255)
    private String img; // Đường dẫn tới ảnh

    @Column(name = "Size", nullable = false, length = 50)
    @Enumerated(EnumType.STRING) // Đảm bảo chỉ nhận 'Small', 'Medium', 'Large'
    private Size size; // Kích cỡ của thiết kế, sử dụng enum

    // Thêm validation để giá không âm
    @Column(name = "Price", nullable = false)
    @Min(value = 0, message = "Price must be positive")
    private double price;

    private String ShapeOfPond;

    // Thêm validation để estimatedCompletionTime phải lớn hơn 0
    @Column(name = "EstimatedCompletionTime", nullable = false)
    @Min(value = 1, message = "Estimated completion time must be greater than 0")
    private int estimatedCompletionTime; // Số ngày hoàn thành lớn hơn 0

    @Column(name = "Status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING) // Đảm bảo chỉ nhận 'Available', 'Unavailable', 'Pending'
    private Status status; // Trạng thái của thiết kế, sử dụng enum

    // Enum để giới hạn các giá trị cho Size và Status
    public enum Size {
        Small, Medium, Large
    }

    public enum Status {
        Available, Unavailable, Pending
    }
    @ManyToMany
    @JoinTable(
            name = "Designmaterial",
            joinColumns = @JoinColumn(name = "designId"),
            inverseJoinColumns = @JoinColumn(name = "materialId")
    )
    private List<MaterialEntity> materials;

    @ElementCollection
    @CollectionTable(name = "designMaterialQuantity", joinColumns = @JoinColumn(name = "designId"))
    @MapKeyJoinColumn(name = "materialId")
    @Column(name = "quantityNeeded")
    private Map<MaterialEntity, Integer> materialQuantities; // Số lượng nguyên vật liệu cần cho sản phẩm
}
