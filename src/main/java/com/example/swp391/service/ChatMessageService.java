//package com.example.swp391.service;
//
//import com.example.swp391.entity.ChatMessage;
//import com.example.swp391.repository.ChatMessageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ChatMessageService {
//
//    @Autowired
//    private ChatMessageRepository chatMessageRepository;
//
//    // Lưu tin nhắn
//    public ChatMessage save(ChatMessage chatMessage) {
//        return chatMessageRepository.save(chatMessage);
//    }
//
//
//    public List<ChatMessage> findMessagesByRecipientType(String recipientType) {
//        return chatMessageRepository.findByRecipientType(recipientType);
//    }
//
//    // Lấy toàn bộ tin nhắn trong hệ thống (ví dụ cho mục đích quản lý)
//    public List<ChatMessage> findAll() {
//        return chatMessageRepository.findAll();
//    }
//}
