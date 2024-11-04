//package com.example.swp391.controller;
//
//import com.example.swp391.entity.AccountEntity;
//import com.example.swp391.entity.ChatMessage;
//import com.example.swp391.service.AccountService;
//import com.example.swp391.service.ChatMessageService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.annotation.SendToUser;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//public class ChatController {
//
//    @Autowired
//    private ChatMessageService chatMessageService;
//
//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private HttpSession session;
//
//    @MessageMapping("/sendMessage")
//    @SendToUser("/queue/reply")
//    public ChatMessage sendMessage(ChatMessage chatMessage) {
//        // Lấy thông tin tài khoản hiện tại từ session
//        AccountEntity senderAccount = accountService.getCurrentAccount(session);
//
//        if (senderAccount != null) {
//            String recipientType;
//
//            if ("Customer".equals(senderAccount.getAccountTypeID())) {
//                recipientType = "Consulting Staff";
//            } else if ("Consulting Staff".equals(senderAccount.getAccountTypeID())) {
//                recipientType = "Customer";
//            } else {
//                return null; // Loại tài khoản không được phép gửi tin nhắn
//            }
//
//            chatMessage.setSenderAccount(senderAccount);
//            chatMessage.setRecipientType(recipientType);
//            chatMessage.setTimestamp(new Date());
//            chatMessageService.save(chatMessage);
//            return chatMessage;
//        }
//        return null;
//    }
//
//    @GetMapping("/chat")
//    public String getChatPage(Model model, HttpSession session) {
//        AccountEntity currentAccount = (AccountEntity) session.getAttribute("loggedInUser");
//        if (currentAccount != null) {
//            model.addAttribute("accountType", currentAccount.getAccountTypeID());
//            return "chat"; // Trả về tên của file HTML (không cần đuôi .html)
//        }
//        return "redirect:/login"; // Nếu không có tài khoản trong session, chuyển hướng đến trang đăng nhập
//    }
//
//    @GetMapping("/chat/messages")
//    public String getChatMessages(Model model) {
//        AccountEntity currentAccount = accountService.getCurrentAccount(session);
//        if (currentAccount != null) {
//            List<ChatMessage> messages;
//            if ("Customer".equals(currentAccount.getAccountTypeID())) {
//                messages = chatMessageService.findMessagesByRecipientType("Customer");
//            } else if ("Consulting Staff".equals(currentAccount.getAccountTypeID())) {
//                messages = chatMessageService.findMessagesByRecipientType("Consulting Staff");
//            } else {
//                messages = new ArrayList<>();
//            }
//            model.addAttribute("messages", messages);
//        }
//        return "chat"; // Trả về view (HTML) để hiển thị danh sách tin nhắn
//    }
//}
