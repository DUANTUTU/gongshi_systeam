package com.gt.app.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gt.app.service.ITAppProjectService;
import com.gt.model.TAppProject;
import com.gt.pageModel.SessionInfo;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.TAppProjectPerson;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITAppProjectPersonService;


/**
 * @功能说明：项目人员表
 * @作者：zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppProjectPerson")

public class TAppProjectPersonController extends BaseController {
    private Logger logger = Logger.getLogger(TAppProjectPersonController.class);
    private ITAppProjectPersonService tAppProjectPersonService;

    public ITAppProjectPersonService getTAppProjectPersonService() {
        return tAppProjectPersonService;
    }

    @Autowired
    public void setTAppProjectPersonService(ITAppProjectPersonService tAppProjectPersonService) {
        this.tAppProjectPersonService = tAppProjectPersonService;
    }

    private ITAppProjectService tAppProjectService;

    public ITAppProjectService getTAppProjectService() {
        return tAppProjectService;
    }

    @Autowired
    public void setTAppProjectService(ITAppProjectService tAppProjectService) {
        this.tAppProjectService = tAppProjectService;
    }

    /**
     * 获取datagrid数据
     */
    @RequestMapping("/datagrid")
    @ResponseBody
    public DatagridForLayUI datagrid(TAppProjectPerson tAppProjectPerson) {
        DatagridForLayUI datagrid = null;
        try {
            datagrid = tAppProjectPersonService.datagrid(tAppProjectPerson);
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
    public Json add(TAppProjectPerson tAppProjectPerson, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = tAppProjectPersonService.add(tAppProjectPerson);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            e.printStackTrace();
            j.setMsg("添加失败:" + e.getMessage());
        }

        //添加系统日志
        writeSysLog(j, tAppProjectPerson, request, session);

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
            tAppProjectPersonService.remove(id);
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
    public Json modify(TAppProjectPerson tAppProjectPerson, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = tAppProjectPersonService.modify(tAppProjectPerson);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppProjectPerson, request, session);
        return j;
    }

    /**
     * 获取下拉框数据
     */
    @RequestMapping("/getList")
    @ResponseBody
    public List<TAppProjectPerson> getList() {
        List<TAppProjectPerson> list = tAppProjectPersonService.getList();
        return list;
    }

    /**
     * 根据项目ID获取参与人员
     */
    @RequestMapping("/getPersonByProjectId")
    @ResponseBody
    public Map<String, Object> getPersonByProjectId(String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rows", tAppProjectPersonService.getPersonByProjectId(projectId));
        return map;
    }

    /**
     * 项目委派
     */
    @RequestMapping("/addPersonByProject")
    @ResponseBody
    public Json addPersonByProject(String projectId, String operCds, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            Date date = new Date();
            if ("".equals(operCds) || null == operCds) {
                j.setSuccess(false);
                j.setMsg("未选择人员");
            }
            String[] operCdsAry = operCds.split(",");
            for (String opercd : operCdsAry) {
                TAppProjectPerson tAppProjectPerson = new TAppProjectPerson();
                tAppProjectPerson.setId(PbUtils.getUUID());
                tAppProjectPerson.setPopercd(opercd.split("\\.")[0]);
                tAppProjectPerson.setPinscd(opercd.split("\\.")[1]);
                tAppProjectPerson.setPprojectid(projectId);
                tAppProjectPerson.setPcreatedate(date);
                tAppProjectPerson.setPappointstatus(Contans.APPOINT_STATUS_ARRY[2]);
                j = tAppProjectPersonService.add(tAppProjectPerson);
            }
            TAppProject tAppProject = new TAppProject();
            tAppProject.setId(projectId);
            tAppProject.setPisappoint(Contans.IS_APPOINT_ARRY[1]);
            tAppProjectService.modify(tAppProject);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            e.printStackTrace();
            j.setMsg("添加失败:" + e.getMessage());
        }

        //添加系统日志
        writeSysLog(j, projectId, request, session);

        return j;
    }

    /**
     * 获取个人参与项目下拉菜单
     * */

    @RequestMapping("/getProjectListByOpercd")
    @ResponseBody
    public List<Map> getProjectListByOpercd(HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
        TAppProjectPerson tAppProjectPerson = new TAppProjectPerson();
        tAppProjectPerson.setPopercd(sessionInfo.getOperInf().getOperCd());
        tAppProjectPerson.setPappointstatus(Contans.APPOINT_STATUS_ARRY[0]);
        List<Map> list = tAppProjectPersonService.getProjectListByOpercd(tAppProjectPerson);
        return list;
    }

    /**
     * 获取统计人员确认工时情况
     */
    @RequestMapping("/getPersonProjectEcharts")
    @ResponseBody
    public Map<String, List<String>> getPersonProjectEcharts() {
        List<Map> list = tAppProjectPersonService.getPersonProjectEcharts();
        List<String> personNameList = new ArrayList<>();
        List<String> proAllNumList = new ArrayList<>();
        List<String> proMasterNumList = new ArrayList<>();
        List<String> proBranchNumList = new ArrayList<>();
        for (Map entityMap : list) {
            personNameList.add(entityMap.get("opernm").toString());
            proAllNumList.add(entityMap.get("proAllNum").toString());
            proMasterNumList.add(entityMap.get("proMasterNum").toString());
            proBranchNumList.add(entityMap.get("proBranchNum").toString());
        }
        Map<String, List<String>> ruseltMap = new HashMap<>();
        ruseltMap.put("personName", personNameList);
        ruseltMap.put("proAllNum", proAllNumList);
        ruseltMap.put("proMasterNum", proMasterNumList);
        ruseltMap.put("proBranchNum", proBranchNumList);
        return ruseltMap;
    }

}
