//package com.gt.threads;
//
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * <p>标题: 线程启动Servlet类</p>
// *
// * @author 刘涛
// * @version 1.0
// */
//
//@JsonAutoDetect
//public class AutoJobServlet extends HttpServlet {
//
//
//    public AutoJobServlet() {
//    }
//
//    public void init() throws ServletException {
//
//
//        ThreadPool threadpool = ThreadPool.getInstance();
//
//        SearchBaiDuTask sdudk = new SearchBaiDuTask();
//
//
//        threadpool.addTask(sdudk);//每天同步更新提醒表显示状态位
//
//
//    }
//
//    public void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException {
//    }
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException {
//    }
//
//    public void destroy() {
//    }
//
//}
