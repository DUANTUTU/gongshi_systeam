package com.gt.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.TAppDayPaper;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITAppDayPaperService;


/**
 * @功能说明：日报
 * @作者：zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppDayPaper")

public class TAppDayPaperController extends BaseController {
    private Logger logger = Logger.getLogger(TAppDayPaperController.class);
    private ITAppDayPaperService tAppDayPaperService;

    public ITAppDayPaperService getTAppDayPaperService() {
        return tAppDayPaperService;
    }

    @Autowired
    public void setTAppDayPaperService(ITAppDayPaperService tAppDayPaperService) {
        this.tAppDayPaperService = tAppDayPaperService;
    }


    /**
     * 获取datagrid数据
     */
    @RequestMapping("/datagrid")
    @ResponseBody
    public DatagridForLayUI datagrid(TAppDayPaper tAppDayPaper) {
        DatagridForLayUI datagrid = null;
        try {
            datagrid = tAppDayPaperService.datagrid(tAppDayPaper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }

    /**
     * 新增
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(TAppDayPaper tAppDayPaper, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = tAppDayPaperService.add(tAppDayPaper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            e.printStackTrace();
            j.setMsg("添加失败:" + e.getMessage());
        }

        //添加系统日志
        writeSysLog(j, tAppDayPaper, request, session);

        return j;
    }

    /**
     * 删除
     */
    @RequestMapping("/remove")
    @ResponseBody
    public Json remove(@RequestParam(value = "id", required = true) String id, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            tAppDayPaperService.remove(id);
            j.setSuccess(true);
            j.setMsg("删除成功！");
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("删除失败:" + e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        //添加系统日志
        writeSysLog(j, id, request, session);

        return j;
    }

    /**
     * 修改
     */
    @RequestMapping("/modify")
    @ResponseBody
    public Json modify(TAppDayPaper tAppDayPaper, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = tAppDayPaperService.modify(tAppDayPaper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppDayPaper, request, session);
        return j;
    }

    /**
     * 获取下拉框数据
     */
    @RequestMapping("/getList")
    @ResponseBody
    public List<TAppDayPaper> getList() {
        List<TAppDayPaper> list = tAppDayPaperService.getList();
        return list;
    }

    /**
     * 获取统计人员确认工时情况
     */
    @RequestMapping("/getPersonManhourEcharts")
    @ResponseBody
    public Map<String, List<String>> getPersonManhourEcharts() {
        List<Map> list = tAppDayPaperService.getPersonManhourEcharts();
        List<String> personNameList = new ArrayList<>();
        List<String> sumManhourList = new ArrayList<>();
        List<String> masterManhourList = new ArrayList<>();
        List<String> branchManhourList = new ArrayList<>();
        for (Map entityMap : list) {
            personNameList.add(entityMap.get("opernm").toString());
            sumManhourList.add(entityMap.get("dsummanhour").toString());
//            masterManhourList.add(entityMap.get("dmaster").toString());
//            branchManhourList.add(entityMap.get("dbranch").toString());
        }
        Map<String, List<String>> ruseltMap = new HashMap<>();
        ruseltMap.put("personName", personNameList);
        ruseltMap.put("sumManhour", sumManhourList);
//        ruseltMap.put("masterManhour", masterManhourList);
//        ruseltMap.put("branchManhour", branchManhourList);
        return ruseltMap;
    }

}
