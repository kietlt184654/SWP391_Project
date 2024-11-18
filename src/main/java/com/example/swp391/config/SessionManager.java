//package com.example.swp391.config;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class SessionManager {
//    private final Map<String, HttpSession> userSessions = new ConcurrentHashMap<>();
//
//    public synchronized void registerSession(String username, HttpSession newSession) {
//        HttpSession oldSession = userSessions.get(username);
//        if (oldSession != null) {
//            oldSession.invalidate(); // Hủy session cũ nếu tồn tại
//        }
//        userSessions.put(username, newSession);
//    }
//
//    public synchronized void removeSession(String username) {
//        userSessions.remove(username);
//    }
//}
