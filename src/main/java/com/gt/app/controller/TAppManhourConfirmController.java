package com.gt.app.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gt.app.service.*;
import com.gt.app.service.impl.TAppDebugDetailServiceImpl;
import com.gt.model.*;
import com.gt.pageModel.SessionInfo;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;


/**
 * @功能说明：工时提报
 * @作者：zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppManhourConfirm")

public class TAppManhourConfirmController extends BaseController {
    private Logger logger = Logger.getLogger(TAppManhourConfirmController.class);
    @Autowired
    private ITAppDebugDetailService tAppDebugDetailService;

    public ITAppDebugDetailService gettAppDebugDetailService() {
        return tAppDebugDetailService;
    }

    public void settAppDebugDetailService(ITAppDebugDetailService tAppDebugDetailService) {
        this.tAppDebugDetailService = tAppDebugDetailService;
    }

    private ITAppManhourConfirmService tAppManhourConfirmService;

    public ITAppManhourConfirmService getTAppManhourConfirmService() {
        return tAppManhourConfirmService;
    }

    @Autowired
    public void setTAppManhourConfirmService(ITAppManhourConfirmService tAppManhourConfirmService) {
        this.tAppManhourConfirmService = tAppManhourConfirmService;
    }

    private ITSysJobService tSysJobService;

    public ITSysJobService getTSysJobService() {
        return tSysJobService;
    }

    @Autowired
    public void setTSysJobService(ITSysJobService tSysJobService) {
        this.tSysJobService = tSysJobService;
    }

    private ITAppManhourService tAppManhourService;

    public ITAppManhourService getTAppManhourService() {
        return tAppManhourService;
    }

    @Autowired
    public void setTAppManhourService(ITAppManhourService tAppManhourService) {
        this.tAppManhourService = tAppManhourService;
    }

    private ITAppMeritTemplateService tAppMeritTemplateService;

    public ITAppMeritTemplateService getTAppMeritTemplateService() {
        return tAppMeritTemplateService;
    }

    @Autowired
    public void setTAppMeritTemplateService(ITAppMeritTemplateService tAppMeritTemplateService) {
        this.tAppMeritTemplateService = tAppMeritTemplateService;
    }

    private ITAppProjectService tAppProjectService;

    public ITAppProjectService getTAppProjectService() {
        return tAppProjectService;
    }

    @Autowired
    public void setTAppProjectService(ITAppProjectService tAppProjectService) {
        this.tAppProjectService = tAppProjectService;
    }

    private ITAppProjectPlanService tAppProjectPlanService;

    public ITAppProjectPlanService getTAppProjectPlanService() {
        return tAppProjectPlanService;
    }

    @Autowired
    public void setTAppProjectPlanService(ITAppProjectPlanService tAppProjectPlanService) {
        this.tAppProjectPlanService = tAppProjectPlanService;
    }

    /**
     * 获取datagrid数据
     */
    @RequestMapping("/datagrid")
    @ResponseBody
    public DatagridForLayUI datagrid(TAppManhourConfirm tAppManhourConfirm, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            tAppManhourConfirm.setMopercd(sessionInfo.getOperInf().getOperCd());
            datagrid = tAppManhourConfirmService.datagrid(tAppManhourConfirm, null, sessionInfo.getOperInf().getInsUuid(), null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }
    /*
     * 返回提报工时，供测试人员核验有效工时
     * */


    /**
     * 新增
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(TAppManhourConfirm tAppManhourConfirm, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            Date date = new Date();
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            //判断当前用户职位类型,如果是
            TAppMeritTemplate tAppMeritTemplate = new TAppMeritTemplate();
            tAppMeritTemplate.setMjobid(sessionInfo.getOperInf().getSjobid());
            DatagridForLayUI datagrid = tAppMeritTemplateService.datagrid(tAppMeritTemplate);
            if (datagrid.getCount() <= 0) {
                j.setSuccess(false);
                j.setMsg("您当前用户职位未关联绩效模板,请先设置职位绩效模板再进行工时提报!");
                return j;
            }
            Map entitymap = (Map) datagrid.getData().get(0);
            if (Contans.IS_LINK_PROJECT_ARRY[0].equals(entitymap.get("mislinkproject").toString())) {
                //如果需要关联项目则不能为空
                if (null == tAppManhourConfirm.getMprojectid() || "".equals(tAppManhourConfirm.getMprojectid())) {
                    j.setSuccess(false);
                    j.setMsg("您的当前职位权限项目为必填项!");
                    return j;
                }
            }
            tAppManhourConfirm.setMopercd(sessionInfo.getOperInf().getOperCd());
            tAppManhourConfirm.setId(PbUtils.getUUID());
            tAppManhourConfirm.setMcreatedate(new Date());
            if (PbUtils.isEmpty(tAppManhourConfirm.getMprojectid())) {
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[2]);
                tAppManhourConfirm.setMcheckdate(date);
            } else {
                // 新增工时需要测试审核
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[0]);
            }
            j = tAppManhourConfirmService.add(tAppManhourConfirm);
            if (Contans.CHECK_STATUS_ARRY[2].equals(tAppManhourConfirm.getMcheckstatus())) {

                //同意
                TAppManhour tAppManhour = new TAppManhour();
                tAppManhour.setId(PbUtils.getUUID());
                tAppManhour.setMcreatedate(date);
                tAppManhour.setMmanhourconfimid(tAppManhourConfirm.getId());
                tAppManhour.setMprojectid(tAppManhourConfirm.getMprojectid());
                tAppManhour.setMprojectplanid(tAppManhourConfirm.getMprojectplanid());
                tAppManhour.setMopercd(tAppManhourConfirm.getMopercd());

                TAppProject tAppProject = new TAppProject();
                tAppProject.setId(tAppManhourConfirm.getMprojectid());
                DatagridForLayUI datagridpro = tAppProjectService.datagrid(tAppProject, null);
                if (datagridpro.getCount() > 0) {
                    List<Map> mapList = datagridpro.getData();
                    String hourStr = mapList.get(0).get("pmanhour") == null ? "" : mapList.get(0).get("pmanhour").toString();
                    int hour = 0;
                    if (null != hourStr && !"".equals(hourStr)) {
                        hour = Integer.parseInt(hourStr);
                    }
                    hour = hour + tAppManhourConfirm.getMmanhour();
                    tAppProject.setPmanhour(hour);
                    tAppProjectService.modify(tAppProject);
                }

                tAppManhourService.add(tAppManhour);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            e.printStackTrace();
            j.setMsg("添加失败:" + e.getMessage());
        }

        //添加系统日志
        writeSysLog(j, tAppManhourConfirm, request, session);

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
            tAppManhourConfirmService.remove(id);
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
    public Json modify(TAppManhourConfirm tAppManhourConfirm, String type, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            Date date = new Date();
            if ("okSubmit".equals(type)) {
                //同意
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[2]);
                tAppManhourConfirm.setMcheckdate(date);
                TAppManhour tAppManhour = new TAppManhour();
                tAppManhour.setId(PbUtils.getUUID());
                tAppManhour.setMcreatedate(date);
                tAppManhour.setMmanhourconfimid(tAppManhourConfirm.getId());
                tAppManhour.setMprojectid(tAppManhourConfirm.getMprojectid());
                tAppManhour.setMprojectplanid(tAppManhourConfirm.getMprojectplanid());
                tAppManhour.setMopercd(tAppManhourConfirm.getMopercd());
                tAppManhourService.add(tAppManhour);

                TAppProject tAppProject = new TAppProject();
                tAppProject.setId(tAppManhourConfirm.getMprojectid());
                DatagridForLayUI datagrid = tAppProjectService.datagrid(tAppProject, null);
                if (datagrid.getCount() > 0) {
                    List<Map> mapList = datagrid.getData();
                    String hourStr = mapList.get(0).get("pmanhour") == null ? "" : mapList.get(0).get("pmanhour").toString();
                    int hour = 0;
                    if (null != hourStr && !"".equals(hourStr)) {
                        hour = Integer.parseInt(hourStr);
                    }
                    hour = hour + tAppManhourConfirm.getMmanhour();
                    tAppProject.setPmanhour(hour);
                    tAppProjectService.modify(tAppProject);
                }
            }
            if ("notokSubmit".equals(type)) {
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[3]);
            }
            if ("edit".equals(type)) {
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[4]);
            }
            j = tAppManhourConfirmService.modify(tAppManhourConfirm);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppManhourConfirm, request, session);
        return j;
    }

    /**
     * 修改  领导审核
     */
    @RequestMapping("/modifyShenHe")
    @ResponseBody
    public Json modifyShenHe(TAppManhourConfirm tAppManhourConfirm, String type, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            Date date = new Date();
            if ("okSubmit".equals(type)) {
                //同意
                //生效工时表插入生效工时
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[2]);
                tAppManhourConfirm.setMcheckdate(date);
                TAppManhour tAppManhour = new TAppManhour();
                tAppManhour.setId(PbUtils.getUUID());
                tAppManhour.setMcreatedate(date);
                tAppManhour.setMmanhourconfimid(tAppManhourConfirm.getId());
                tAppManhour.setMprojectid(tAppManhourConfirm.getMprojectid());
                tAppManhour.setMprojectplanid(tAppManhourConfirm.getMprojectplanid());
                tAppManhour.setMopercd(tAppManhourConfirm.getMopercd());
                tAppManhourService.add(tAppManhour);

                //项目确认工时修改
                TAppProject tAppProject = new TAppProject();
                tAppProject.setId(tAppManhourConfirm.getMprojectid());
                DatagridForLayUI datagrid = tAppProjectService.datagrid(tAppProject, null);
                if (datagrid.getCount() > 0) {
                    List<Map> mapList = datagrid.getData();
                    String hourStr = mapList.get(0).get("pmanhour") == null ? "" : mapList.get(0).get("pmanhour").toString();

                    int hour = 0;
                    if (null != hourStr && !"".equals(hourStr)) {
                        hour = Integer.parseInt(hourStr);
                    }
                    hour = hour + tAppManhourConfirm.getMmanhour();
                      //减去调试工时
                    if (tAppManhourConfirm.getDebugFinishDate() != null) {
                        hour = hour - tAppManhourConfirm.getDebugFinishDate();
                        tAppManhourConfirm.setMmanhour(tAppManhourConfirm.getMmanhour() - tAppManhourConfirm.getDebugFinishDate());
                    }

                    tAppProject.setPmanhour(hour);
                    tAppProjectService.modify(tAppProject);
                }
                //项目里程碑工时修改
                TAppProjectPlan tAppProjectPlan = new TAppProjectPlan();
                tAppProjectPlan.setId(tAppManhourConfirm.getMprojectplanid());
                DatagridForLayUI datagridplan = tAppProjectPlanService.datagrid(tAppProjectPlan);
                if (datagridplan.getCount() > 0) {
                    List<Map> mapList = datagrid.getData();
                    String hourStr = mapList.get(0).get("psummanhour") == null ? "" : mapList.get(0).get("psummanhour").toString();
                    int hour = 0;
                    if (!PbUtils.isEmpty(hourStr)) {
                        hour = Integer.parseInt(hourStr);
                    }
                    hour = hour + tAppManhourConfirm.getMmanhour();
                    tAppProjectPlan.setPsummanhour(hour);
                    tAppProjectPlanService.modify(tAppProjectPlan);
                }

            }
            if ("notokSubmit".equals(type)) {
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[3]);
            }
            if ("edit".equals(type)) {
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[1]);
            }
            j = tAppManhourConfirmService.modify(tAppManhourConfirm);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppManhourConfirm, request, session);
        return j;
    }
/**
 * 修改  测试审核
 */
@RequestMapping("/modifyCeShiShenHe")
@ResponseBody
public Json modifyCeShiShenHe(TAppManhourConfirm tAppManhourConfirm, String type, HttpServletRequest request, HttpSession session) {
    Json j = new Json();
    try {
        Date date = new Date();
        if ("okSubmit".equals(type)) {
            //同意
            //生效工时表插入生效工时
            tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[1]);
//            tAppManhourConfirm.setMcheckdate(date);
//
//            TAppManhour tAppManhour = new TAppManhour();
//            tAppManhour.setId(PbUtils.getUUID());
//            tAppManhour.setMcreatedate(date);
//            tAppManhour.setMmanhourconfimid(tAppManhourConfirm.getId());
//            tAppManhour.setMprojectid(tAppManhourConfirm.getMprojectid());
//            tAppManhour.setMprojectplanid(tAppManhourConfirm.getMprojectplanid());
//            tAppManhour.setMopercd(tAppManhourConfirm.getMopercd());
//            tAppManhourService.add(tAppManhour);
//
//            //项目确认工时修改
//            TAppProject tAppProject = new TAppProject();
//            tAppProject.setId(tAppManhourConfirm.getMprojectid());
//            DatagridForLayUI datagrid = tAppProjectService.datagrid(tAppProject, null);
//            if (datagrid.getCount() > 0) {
//                List<Map> mapList = datagrid.getData();
//                String hourStr = mapList.get(0).get("pmanhour") == null ? "" : mapList.get(0).get("pmanhour").toString();
//                int hour = 0;
//                if (null != hourStr && !"".equals(hourStr)) {
//                    hour = Integer.parseInt(hourStr);
//                }
//                hour = hour + tAppManhourConfirm.getMmanhour();
//                tAppProject.setPmanhour(hour);
//                tAppProjectService.modify(tAppProject);
//            }
//            //项目里程碑工时修改
//            TAppProjectPlan tAppProjectPlan = new TAppProjectPlan();
//            tAppProjectPlan.setId(tAppManhourConfirm.getMprojectplanid());
//            DatagridForLayUI datagridplan = tAppProjectPlanService.datagrid(tAppProjectPlan);
//            if (datagridplan.getCount() > 0) {
//                List<Map> mapList = datagrid.getData();
//                String hourStr = mapList.get(0).get("psummanhour") == null ? "" : mapList.get(0).get("psummanhour").toString();
//                int hour = 0;
//                if (!PbUtils.isEmpty(hourStr)) {
//                    hour = Integer.parseInt(hourStr);
//                }
//                hour = hour + tAppManhourConfirm.getMmanhour();
//                tAppProjectPlan.setPsummanhour(hour);
//                tAppProjectPlanService.modify(tAppProjectPlan);
//            }

        }
        if ("notokSubmit".equals(type)) {
            tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[3]);
        }
        if ("edit".equals(type)) {
            tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[1]);
        }
        j = tAppManhourConfirmService.modify(tAppManhourConfirm);
    } catch (Exception e) {
        logger.error(e.getMessage());
        j.setSuccess(false);
        j.setMsg("修改失败:" + e.getMessage());
        e.printStackTrace();
    }

    //添加系统日志
    writeSysLog(j, tAppManhourConfirm, request, session);
    return j;
}
/*
* 测试详情审核
* */
    @RequestMapping("/debugDetailShenHe")
    @ResponseBody
    public Json debugDetailShenHe(TAppManhourConfirm tAppManhourConfirm)
    {
        Json j = new Json();
        try {
            TAppDebugDetail tAppDebugDetail = new TAppDebugDetail();
            tAppDebugDetail.setMprojectid(tAppManhourConfirm.getMprojectid());
            tAppDebugDetail.setMprojectid(tAppManhourConfirm.getMprojectid());
            tAppDebugDetail.setMopercd(tAppManhourConfirm.getMopercd());
            tAppDebugDetail.setMmanhour(tAppManhourConfirm.getMmanhour());

            tAppDebugDetail.setDebugID(tAppManhourConfirm.getDebugID());
            tAppDebugDetail.setDebugFinishDate(tAppManhourConfirm.getDebugFinishDate());
            tAppDebugDetail.setDebugLeave(tAppManhourConfirm.getDebugLeave());

            tAppDebugDetailService.add(tAppDebugDetail);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志

        return j;
    }
@RequestMapping("/modifyCeShiShenHeDetail")
@ResponseBody
    public Json modifyCeShiShenHeDetail(TAppManhourConfirm tAppManhourConfirm, String type, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            Date date = new Date();
            if ("dahuitijiao".equals(type)) {
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[6]);
            }
            if ("SubmitList".equals(type)) {
            TAppDebugDetail tAppDebugDetail = new TAppDebugDetail();
            tAppDebugDetail.setMprojectid(tAppManhourConfirm.getMprojectid());
            tAppDebugDetail.setMprojectid(tAppManhourConfirm.getMprojectid());
            tAppDebugDetail.setMopercd(tAppManhourConfirm.getMopercd());
            tAppDebugDetail.setMmanhour(tAppManhourConfirm.getMmanhour());
            tAppDebugDetail.setMcreatedate(date);
            tAppDebugDetail.setDebugID(tAppManhourConfirm.getDebugID());
            tAppDebugDetail.setDebugFinishDate(tAppManhourConfirm.getDebugFinishDate());
            tAppDebugDetail.setDebugLeave(tAppManhourConfirm.getDebugLeave());

           tAppDebugDetailService.add(tAppDebugDetail);
                //同意
                //生效工时表插入生效工时
//                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[6]);
//                tAppManhourConfirm.setMcheckdate(date);
//
//                TAppManhour tAppManhour = new TAppManhour();
//                tAppManhour.setId(PbUtils.getUUID());
//                tAppManhour.setMcreatedate(date);
//                tAppManhour.setMmanhourconfimid(tAppManhourConfirm.getId());
//                tAppManhour.setMprojectid(tAppManhourConfirm.getMprojectid());
//                tAppManhour.setMprojectplanid(tAppManhourConfirm.getMprojectplanid());
//                tAppManhour.setMopercd(tAppManhourConfirm.getMopercd());
//                tAppManhourService.add(tAppManhour);
//
//                //项目确认工时修改
//                TAppProject tAppProject = new TAppProject();
//                tAppProject.setId(tAppManhourConfirm.getMprojectid());
//                DatagridForLayUI datagrid = tAppProjectService.datagrid(tAppProject, null);
//                if (datagrid.getCount() > 0) {
//                    List<Map> mapList = datagrid.getData();
//                    String hourStr = mapList.get(0).get("pmanhour") == null ? "" : mapList.get(0).get("pmanhour").toString();
//                    int hour = 0;
//                    if (null != hourStr && !"".equals(hourStr)) {
//                        hour = Integer.parseInt(hourStr);
//                    }
//                    hour = hour + tAppManhourConfirm.getMmanhour();
//                    tAppProject.setPmanhour(hour);
//                    tAppProjectService.modify(tAppProject);
//                }
//                //项目里程碑工时修改
//                TAppProjectPlan tAppProjectPlan = new TAppProjectPlan();
//                tAppProjectPlan.setId(tAppManhourConfirm.getMprojectplanid());
//                DatagridForLayUI datagridplan = tAppProjectPlanService.datagrid(tAppProjectPlan);
//                if (datagridplan.getCount() > 0) {
//                    List<Map> mapList = datagrid.getData();
//                    String hourStr = mapList.get(0).get("psummanhour") == null ? "" : mapList.get(0).get("psummanhour").toString();
//                    int hour = 0;
//                    if (!PbUtils.isEmpty(hourStr)) {
//                        hour = Integer.parseInt(hourStr);
//                    }
//                    hour = hour + tAppManhourConfirm.getMmanhour();
//                    tAppProjectPlan.setPsummanhour(hour);
//                    tAppProjectPlanService.modify(tAppProjectPlan);
//                }

            }
            if ("okSubmit".equals(type)) {
                //同意
                //生效工时表插入生效工时
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[6]);
                tAppManhourConfirm.setMcheckdate(date);

                TAppManhour tAppManhour = new TAppManhour();
                tAppManhour.setId(PbUtils.getUUID());
                tAppManhour.setMcreatedate(date);
                tAppManhour.setMmanhourconfimid(tAppManhourConfirm.getId());
                tAppManhour.setMprojectid(tAppManhourConfirm.getMprojectid());
                tAppManhour.setMprojectplanid(tAppManhourConfirm.getMprojectplanid());
                tAppManhour.setMopercd(tAppManhourConfirm.getMopercd());
                tAppManhourService.add(tAppManhour);

                //项目确认工时修改
                TAppProject tAppProject = new TAppProject();
                tAppProject.setId(tAppManhourConfirm.getMprojectid());
                DatagridForLayUI datagrid = tAppProjectService.datagrid(tAppProject, null);
                if (datagrid.getCount() > 0) {
                    List<Map> mapList = datagrid.getData();
                    String hourStr = mapList.get(0).get("pmanhour") == null ? "" : mapList.get(0).get("pmanhour").toString();
                    int hour = 0;
                    if (null != hourStr && !"".equals(hourStr)) {
                        hour = Integer.parseInt(hourStr);
                    }
                    hour = hour + tAppManhourConfirm.getMmanhour();
                    tAppProject.setPmanhour(hour);
                    tAppProjectService.modify(tAppProject);
                }
                //项目里程碑工时修改
                TAppProjectPlan tAppProjectPlan = new TAppProjectPlan();
                tAppProjectPlan.setId(tAppManhourConfirm.getMprojectplanid());
                DatagridForLayUI datagridplan = tAppProjectPlanService.datagrid(tAppProjectPlan);
                if (datagridplan.getCount() > 0) {
                    List<Map> mapList = datagrid.getData();
                    String hourStr = mapList.get(0).get("psummanhour") == null ? "" : mapList.get(0).get("psummanhour").toString();
                    int hour = 0;
                    if (!PbUtils.isEmpty(hourStr)) {
                        hour = Integer.parseInt(hourStr);
                    }
                    hour = hour + tAppManhourConfirm.getMmanhour();
                    tAppProjectPlan.setPsummanhour(hour);
                    tAppProjectPlanService.modify(tAppProjectPlan);
                }

            }
            if ("notokSubmit".equals(type)) {
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[3]);
            }
            if ("edit".equals(type)) {
                tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[1]);
            }
            j = tAppManhourConfirmService.modify(tAppManhourConfirm);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppManhourConfirm, request, session);
        return j;
    }

    /**
     * 获取下拉框数据
     */
    @RequestMapping("/getList")
    @ResponseBody
    public List<TAppManhourConfirm> getList() {
        List<TAppManhourConfirm> list = tAppManhourConfirmService.getList();
        return list;
    }


    /**
     * 获取datagrid数据
     */
    @RequestMapping("/datagridByCreateProOpercd")
    @ResponseBody
    public DatagridForLayUI datagridByCreateProOpercd(TAppManhourConfirm tAppManhourConfirm, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[1]);
            datagrid = tAppManhourConfirmService.datagrid(tAppManhourConfirm, sessionInfo.getOperInf().getOperCd(), sessionInfo.getOperInf().getInsUuid(), sessionInfo.getOperInf().getSjobType());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }
    /**
     * 获取测试datagrid数据
     */
    @RequestMapping("/CeshidatagridByCreateProOpercd")
    @ResponseBody
    public DatagridForLayUI CeshidatagridByCreateProOpercd(TAppManhourConfirm tAppManhourConfirm, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[4]);
            datagrid = tAppManhourConfirmService.datagrid(tAppManhourConfirm, sessionInfo.getOperInf().getOperCd(), sessionInfo.getOperInf().getInsUuid(), sessionInfo.getOperInf().getSjobType());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }

    /**
     * 首页-获取总提报工时
     */
    @RequestMapping("/queryAllManhourConfirm")
    @ResponseBody
    public Long queryAllManhourConfirm(TAppManhourConfirm tAppManhourConfirm, HttpSession session) {
        //登录人部门
        //是不是部门管理人
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
        sessionInfo.getOperInf().getSjobid();
        TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
        Long allManhourConfirmNum = 0L;
        allManhourConfirmNum = tAppManhourConfirmService.queryAllManhourConfirm(sessionInfo.getOperInf().getInsUuid(), null);
//        if (Contans.JOB_TYPE_ARRY[0].equals(tSysJob.getJtype())) {
//            allManhourConfirmNum = tAppManhourConfirmService.queryAllManhourConfirm(sessionInfo.getOperInf().getInsUuid(), null);
//        } else {
//            allManhourConfirmNum = tAppManhourConfirmService.queryAllManhourConfirm(null, sessionInfo.getOperInf().getOperCd());
//        }
        return allManhourConfirmNum;
    }

    /**
     * 人员7日工时统计
     */
    @RequestMapping("/echartsAccount7DayManhour")
    @ResponseBody
    public Map<String, Object> echartsAccount7DayManhour(TAppManhourConfirm tAppManhourConfirm, HttpSession session) {
        Map<String, Object> jsonMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //过去七天
        Date rightDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(rightDate);
        c.add(Calendar.DATE, -7);
        Date leftDate = c.getTime();
//        String day = sdf.format(d);

        List<Map> allAccountHours = tAppManhourConfirmService.echartsAccount7DayManhour(tAppManhourConfirm, leftDate, rightDate);
        tAppManhourConfirm.setMcheckstatus(Contans.CHECK_STATUS_ARRY[2]);
        List<Map> allAccountCheckHours = tAppManhourConfirmService.echartsAccount7DayManhour(tAppManhourConfirm, leftDate, rightDate);

        List<String> accountList = new ArrayList<>();
        List<String> manhourList = new ArrayList<>();
        List<String> checkManhourList = new ArrayList<>();

        Map<String, String> accountHourMap = new HashMap<>();
        Map<String, String> accountCheckHourMap = new HashMap<>();
        for (Map entityMap : allAccountHours) {
            if (!accountHourMap.containsKey(entityMap.get("operCd"))) {
                accountHourMap.put(entityMap.get("operNm").toString(), entityMap.get("manhour").toString());
            }
        }
        for (Map entityMap : allAccountCheckHours) {
            if (!accountCheckHourMap.containsKey(entityMap.get("operCd"))) {
                accountCheckHourMap.put(entityMap.get("operNm").toString(), entityMap.get("manhour").toString());
            }
        }

        for (Map.Entry<String, String> entry : accountHourMap.entrySet()) {
            if (null == accountCheckHourMap.get(entry.getKey())) {
                checkManhourList.add("0");
            } else {
                checkManhourList.add(accountCheckHourMap.get(entry.getKey()));
            }
            accountList.add(entry.getKey());
            manhourList.add(entry.getValue());
        }
        jsonMap.put("accountList", accountList);
        jsonMap.put("manhourList", manhourList);
        jsonMap.put("checkManhourList", checkManhourList);


        return jsonMap;
    }
    /*
    * debug记录表
    * **/

}
