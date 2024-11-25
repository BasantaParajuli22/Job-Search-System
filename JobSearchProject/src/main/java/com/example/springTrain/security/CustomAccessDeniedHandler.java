//package com.example.springTrain.security;
//
//import java.io.IOException;
//
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
////If a user is authenticated but tries to access a page that requires a different role, 
////they’ll be redirected by the CustomAccessDeniedHandler
//
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//
//	@Override
//	public void handle(HttpServletRequest request,
//			HttpServletResponse response,
//			AccessDeniedException accessDeniedException)
//			throws IOException, ServletException {
//
//		response.sendRedirect(request.getContextPath() + "/access-denied");
//	}
//}
