package com.gt.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gt.pageModel.SessionInfo;
import com.gt.utils.Contans;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.TAppManhourAssess;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITAppManhourAssessService;


/**
 * @功能说明：个人工时评估
 * @作者：zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppManhourAssess")

public class TAppManhourAssessController extends BaseController {
    private Logger logger = Logger.getLogger(TAppManhourAssessController.class);
    private ITAppManhourAssessService tAppManhourAssessService;

    public ITAppManhourAssessService getTAppManhourAssessService() {
        return tAppManhourAssessService;
    }

    @Autowired
    public void setTAppManhourAssessService(ITAppManhourAssessService tAppManhourAssessService) {
        this.tAppManhourAssessService = tAppManhourAssessService;
    }


    /**
     * 获取datagrid数据
     */
    @RequestMapping("/datagrid")
    @ResponseBody
    public DatagridForLayUI datagrid(TAppManhourAssess tAppManhourAssess, String moperNm, String leftDate, String rightDate, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            String insId = sessionInfo.getOperInf().getInsUuid();
            String jobtype = sessionInfo.getOperInf().getSjobType();
            if (!"0".equals(jobtype)) {
                //如果不是部门领导,则只能查自己
                insId = "";
                moperNm = "";
                tAppManhourAssess.setMopercd(sessionInfo.getOperInf().getOperCd());
            }
            datagrid = tAppManhourAssessService.datagrid(tAppManhourAssess, moperNm, leftDate, rightDate, insId, jobtype);
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
    public Json add(TAppManhourAssess tAppManhourAssess, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = tAppManhourAssessService.add(tAppManhourAssess);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            e.printStackTrace();
            j.setMsg("添加失败:" + e.getMessage());
        }

        //添加系统日志
        writeSysLog(j, tAppManhourAssess, request, session);

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
            tAppManhourAssessService.remove(id);
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
    public Json modify(TAppManhourAssess tAppManhourAssess, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = tAppManhourAssessService.modify(tAppManhourAssess);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppManhourAssess, request, session);
        return j;
    }

    /**
     * 获取下拉框数据
     */
    @RequestMapping("/getList")
    @ResponseBody
    public List<TAppManhourAssess> getList() {
        List<TAppManhourAssess> list = tAppManhourAssessService.getList();
        return list;
    }

}
