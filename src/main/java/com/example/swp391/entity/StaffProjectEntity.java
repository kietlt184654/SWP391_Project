package com.example.swp391.entity;
import jakarta.persistence.*;
import lombok.Data;           // Tự động sinh getter, setter, toString, hashCode, equals
import lombok.NoArgsConstructor; // Tạo constructor không tham số
import lombok.AllArgsConstructor; // Tạo constructor với tất cả tham số
import com.example.swp391.entity.StaffEntity;  // Đảm bảo đường dẫn đúng

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffProjectEntity {


    @EmbeddedId
    private StaffProjectId id;  // Khóa chính tổng hợp

    @ManyToOne
    @MapsId("staffID")  // Ánh xạ khóa tổng hợp StaffID
    @JoinColumn(name = "staffID", referencedColumnName = "staffID")
    private StaffEntity staff;

    @ManyToOne
    @MapsId("projectID")  // Ánh xạ khóa tổng hợp ProjectID
    @JoinColumn(name = "projectID", referencedColumnName = "projectID")
    private ProjectEntity project;

    private Date assignmentDate;
    private String progressImage;
    // Constructor không tham số sẽ tự động có từ @NoArgsConstructor
    // Constructor nhận đầy đủ các tham số (id, assignmentDate, progressImage)
    public StaffProjectEntity(StaffProjectId id, Date assignmentDate, String progressImage) {
        this.id = id;
        this.assignmentDate = assignmentDate;
        this.progressImage = progressImage;
    }
}
