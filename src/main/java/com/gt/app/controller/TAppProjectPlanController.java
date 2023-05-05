package com.gt.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gt.app.service.ITAppManhourService;
import com.gt.app.service.ITAppProjectService;
import com.gt.model.TAppProject;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.TAppProjectPlan;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITAppProjectPlanService;


/**
 * @功能说明：项目计划-里程碑
 * @作者：zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppProjectPlan")

public class TAppProjectPlanController extends BaseController {
    private Logger logger = Logger.getLogger(TAppProjectPlanController.class);
    private ITAppProjectPlanService tAppProjectPlanService;

    public ITAppProjectPlanService getTAppProjectPlanService() {
        return tAppProjectPlanService;
    }

    @Autowired
    public void setTAppProjectPlanService(ITAppProjectPlanService tAppProjectPlanService) {
        this.tAppProjectPlanService = tAppProjectPlanService;
    }

    private ITAppManhourService tAppManhourService;

    public ITAppManhourService getTAppManhourService() {
        return tAppManhourService;
    }

    @Autowired
    public void setTAppManhourService(ITAppManhourService tAppManhourService) {
        this.tAppManhourService = tAppManhourService;
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
    public DatagridForLayUI datagrid(TAppProjectPlan tAppProjectPlan) {
        DatagridForLayUI datagrid = null;
        try {

            datagrid = tAppProjectPlanService.datagrid(tAppProjectPlan);
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
    public Json add(TAppProjectPlan tAppProjectPlan, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            TAppProject tAppProject = new TAppProject();
            tAppProject.setId(tAppProjectPlan.getPprojectid());
            List<Map> listMap = tAppProjectService.datagrid(tAppProject, null).getData();
            int sumHour = Integer.valueOf(listMap.get(0).get("pmanhourplan").toString());
            List<TAppProjectPlan> list = tAppProjectPlanService.getListPlanByProjectid(tAppProjectPlan.getPprojectid(), null);
            int sumHourPlan = 0;
            for (TAppProjectPlan entity : list) {
                sumHourPlan = sumHourPlan + entity.getPplanmanhour();
            }
            sumHourPlan = sumHourPlan + tAppProjectPlan.getPplanmanhour();
            if (sumHourPlan > sumHour){
                j.setSuccess(false);
                j.setMsg("添加失败:里程碑总工时不能大于项目计划工时");
                return j;
            }


            tAppProjectPlan.setId(PbUtils.getUUID());
            tAppProjectPlan.setPcreatedate(new Date());
            tAppProjectPlan.setPiscomplete(Contans.IS_COMPLETE_ARRY[0]);
            j = tAppProjectPlanService.add(tAppProjectPlan);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            e.printStackTrace();
            j.setMsg("添加失败:" + e.getMessage());
        }

        //添加系统日志
        writeSysLog(j, tAppProjectPlan, request, session);

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
            tAppProjectPlanService.remove(id);
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
    public Json modify(TAppProjectPlan tAppProjectPlan, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = tAppProjectPlanService.modify(tAppProjectPlan);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppProjectPlan, request, session);
        return j;
    }

    /**
     * 获取下拉框数据
     */
    @RequestMapping("/getList")
    @ResponseBody
    public List<TAppProjectPlan> getList() {
        List<TAppProjectPlan> list = tAppProjectPlanService.getList();
        return list;
    }

    /**
     * 根据项目id获取里程碑下拉菜单
     */
    @RequestMapping("/getListPlanByProjectid")
    @ResponseBody
    public List<TAppProjectPlan> getListPlanByProjectid(String pprojectid) {
        List<TAppProjectPlan> list = tAppProjectPlanService.getListPlanByProjectid(pprojectid, Contans.IS_COMPLETE_ARRY[0]);
        return list;
    }

    /**
     * 根据项目里程碑id修改完成状态
     */
    @RequestMapping("/updProjectPlanIsComplete")
    @ResponseBody
    public Json updProjectPlanIsComplete(TAppProjectPlan tAppProjectPlan, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            Date date = new Date();
            if (null == tAppProjectPlan.getId()) {
                j.setSuccess(false);
                j.setMsg("操作失败:该里程碑无确认工时");
                return j;
            }
            Long sum = tAppManhourService.getSumManhourByProPlanId(tAppProjectPlan.getId());
            if (null == sum) {
                sum = 0L;
            }
            tAppProjectPlan.setPiscomplete(Contans.IS_COMPLETE_ARRY[1]);
            tAppProjectPlan.setPsummanhour(sum.intValue());
            tAppProjectPlan.setPcompletedate(new Date());
            j = tAppProjectPlanService.modify(tAppProjectPlan);

            Long planCount = tAppProjectPlanService.countByProjectId(tAppProjectPlan.getPprojectid(), null);
            if (null == planCount) {
                planCount = 0L;
            }
            if (planCount > 0) {
                Long completePlanCount = tAppProjectPlanService.countByProjectId(tAppProjectPlan.getPprojectid(), Contans.IS_COMPLETE_ARRY[1]);
                if (planCount == completePlanCount) {
                    TAppProject tAppProject = new TAppProject();
                    tAppProject.setId(tAppProjectPlan.getPprojectid());
                    tAppProject.setPiscomplete(Contans.IS_COMPLETE_ARRY[1]);
                    tAppProject.setPcompletedate(date);
                    tAppProjectService.modify(tAppProject);
                }
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppProjectPlan, request, session);
        return j;
    }


}
