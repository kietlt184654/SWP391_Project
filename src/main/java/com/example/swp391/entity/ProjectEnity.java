package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "Project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;

    @ManyToOne
    @JoinColumn(name = "formRequestId")
    private FormRequestEnity formRequest;

    @ManyToOne
    @JoinColumn(name = "designId")
    private DesignEnity design;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String status;
}
