//package com.example.swp391.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "ChatMessage")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class ChatMessage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "MessageID")
//    private Long messageId;
//
//    @ManyToOne
//    @JoinColumn(name = "SenderAccountID", nullable = false)
//    private AccountEntity senderAccount;
//
//    @Column(name = "Content", nullable = false)
//    private String content;
//
//    @Column(name = "RecipientType", nullable = false)
//    private String recipientType; // Có thể là "Customer" hoặc "Consulting Staff"
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date timestamp;
//}
