package com.gt.app.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gt.app.service.*;
import com.gt.model.TAppProjectPerson;
import com.gt.model.TAppProjectPlan;
import com.gt.model.TSysJob;
import com.gt.pageModel.*;
import com.gt.sys.service.IInsInfService;
import com.gt.sys.service.IOperInfService;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.model.TAppProject;
import com.gt.sys.controller.BaseController;


/**
 * @功能说明：项目计划
 * @作者：zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppProject")

public class TAppProjectController extends BaseController {
    private Logger logger = Logger.getLogger(TAppProjectController.class);
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

    private IInsInfService insInfService;

    public IInsInfService getInsInfService() {
        return insInfService;
    }

    @Autowired
    public void setInsInfService(IInsInfService insInfService) {
        this.insInfService = insInfService;
    }

    private IOperInfService operInfService;// 人员信息

    public IOperInfService getOperInfService() {
        return operInfService;
    }

    @Autowired
    public void setOperInfService(IOperInfService operInfService) {
        this.operInfService = operInfService;
    }

    private ITAppProjectPersonService tAppProjectPersonService;

    public ITAppProjectPersonService getTAppProjectPersonService() {
        return tAppProjectPersonService;
    }

    @Autowired
    public void setTAppProjectPersonService(ITAppProjectPersonService tAppProjectPersonService) {
        this.tAppProjectPersonService = tAppProjectPersonService;
    }

    private ITAppManhourService tAppManhourService;

    public ITAppManhourService getTAppManhourService() {
        return tAppManhourService;
    }

    @Autowired
    public void setTAppManhourService(ITAppManhourService tAppManhourService) {
        this.tAppManhourService = tAppManhourService;
    }

    private ITSysJobService tSysJobService;

    public ITSysJobService getTSysJobService() {
        return tSysJobService;
    }

    @Autowired
    public void setTSysJobService(ITSysJobService tSysJobService) {
        this.tSysJobService = tSysJobService;
    }

    /**
     * 获取datagrid数据
     */
    @RequestMapping("/datagrid")
    @ResponseBody
    public DatagridForLayUI datagrid(TAppProject tAppProject, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            //根据职位权限查看其权限对应内容
            String insUuid = null;
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
            if (Contans.JOB_STATUS_ARRY[0].equals(tSysJob.getJtype())) {
                insUuid = sessionInfo.getOperInf().getInsUuid();
            } else {
                tAppProject.setPopercd(sessionInfo.getOperInf().getOperCd());
            }
            datagrid = tAppProjectService.datagrid(tAppProject, insUuid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }

    /**
     * 获取datagrid数据-首页查询项目数量
     */
    @RequestMapping("/datagridWecomePage")
    @ResponseBody
    public DatagridForLayUI datagridWecomePage(TAppProject tAppProject, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            //根据职位权限查看其权限对应内容
            String insUuid = null;
//            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
//            TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
//            if (Contans.JOB_STATUS_ARRY[0].equals(tSysJob.getJtype())) {
//                insUuid = sessionInfo.getOperInf().getInsUuid();
//            } else {
//                tAppProject.setPopercd(sessionInfo.getOperInf().getOperCd());
//            }
            datagrid = tAppProjectService.datagrid(tAppProject, insUuid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }

    /**
     * 获取datagrid数据-项目工时评估
     */
    @RequestMapping("/datagridProjectAssess")
    @ResponseBody
    public DatagridForLayUI datagridProjectAssess(TAppProject tAppProject, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            //根据职位权限查看其权限对应内容
            String insUuid = null;
            //todo zm 查询暂时全部放开
//            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
//            TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
//            if (Contans.JOB_STATUS_ARRY[0].equals(tSysJob.getJtype())) {
//                insUuid = sessionInfo.getOperInf().getInsUuid();
//            } else {
//                tAppProject.setPopercd(sessionInfo.getOperInf().getOperCd());
//            }
            datagrid = tAppProjectService.datagridProjectAssess(tAppProject, insUuid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }

    /**
     * 获取datagrid数据-项目委派
     */
    @RequestMapping("/datagridWP")
    @ResponseBody
    public DatagridForLayUI datagridWP(TAppProject tAppProject, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            //根据职位权限查看其权限对应内容
            String insUuid = null;
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
            if (Contans.JOB_STATUS_ARRY[0].equals(tSysJob.getJtype())) {
                insUuid = sessionInfo.getOperInf().getInsUuid();
            } else {
                tAppProject.setPopercd(sessionInfo.getOperInf().getOperCd());
            }
            datagrid = tAppProjectService.datagrid(tAppProject, insUuid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }

    /**
     * 获取datagrid数据-项目监管
     */
    @RequestMapping("/datagridJG")
    @ResponseBody
    public DatagridForLayUI datagridJG(TAppProject tAppProject, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            //根据职位权限查看其权限对应内容
            String insUuid = null;
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
            if (Contans.JOB_STATUS_ARRY[0].equals(tSysJob.getJtype())) {
                insUuid = sessionInfo.getOperInf().getInsUuid();
            } else {
                tAppProject.setPopercd(sessionInfo.getOperInf().getOperCd());
            }
            datagrid = tAppProjectService.datagrid(tAppProject, insUuid);
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
    public Json add(String tAppProject, String tAppProjectPlans, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        Date date = new Date();
        try {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            //初始化字段
            if ("".equals(tAppProject) || null == tAppProject) {
                j.setSuccess(false);
                j.setMsg("参数错误请刷新页面");
                return j;
            }
            List<TAppProjectPlan> jsonArray = new ArrayList<>();
            TAppProject entity = JSONObject.toJavaObject(JSONObject.parseObject(tAppProject), TAppProject.class);
            if (Contans.PROJECT_TYPE_ARRY[0].equals(entity.getPtype())) {
                if ("".equals(tAppProjectPlans) || null == tAppProjectPlans) {
                    j.setSuccess(false);
                    j.setMsg("支线项目里程碑必填");
                    return j;
                }
                jsonArray = JSONArray.parseArray(tAppProjectPlans, TAppProjectPlan.class);
                if (jsonArray.size() <= 0) {
                    j.setSuccess(false);
                    j.setMsg("支线项目里程碑必填");
                    return j;
                }
            }
            entity.setId(PbUtils.getUUID());
            entity.setPcreatedate(date);
            entity.setPiscomplete(Contans.IS_COMPLETE_ARRY[0]);
            entity.setPisappoint(Contans.IS_APPOINT_ARRY[0]);
            entity.setPinscd(sessionInfo.getOperInf().getInsUuid());
            entity.setPopercd(sessionInfo.getOperInf().getOperCd());
            entity.setPmanhour(0);
            Integer manhourPlanCount = 0;
            List<TAppProjectPlan> listPlan = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                TAppProjectPlan tAppProjectPlan = jsonArray.get(i);
//                TAppProjectPlan tAppProjectPlan = JSONObject.toJavaObject(jsonArray.getJSONObject(i), TAppProjectPlan.class);
                tAppProjectPlan.setId(PbUtils.getUUID());
                tAppProjectPlan.setPprojectid(entity.getId());
                tAppProjectPlan.setPcreatedate(date);
                tAppProjectPlan.setPiscomplete(Contans.IS_COMPLETE_ARRY[0]);
                listPlan.add(tAppProjectPlan);
                manhourPlanCount += tAppProjectPlan.getPplanmanhour();
            }
            if (manhourPlanCount.intValue() != entity.getPmanhourplan().intValue()) {
                j.setSuccess(false);
                j.setMsg("各里程碑计划工时的和不等于项目计划工时");
                return j;
            }
            j = tAppProjectService.add(entity);
            for (TAppProjectPlan tAppProjectPlan : listPlan) {
                tAppProjectPlanService.add(tAppProjectPlan);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            e.printStackTrace();
            j.setMsg("添加失败:" + e.getMessage());
        }

        //添加系统日志
        writeSysLog(j, tAppProject, request, session);

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
            tAppProjectPlanService.delByProjectId(id);
            tAppProjectService.remove(id);
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
    public Json modify(TAppProject tAppProject, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            List<TAppProjectPlan> listPlan = tAppProjectPlanService.getListPlanByProjectid(tAppProject.getId(), null);
            int planHour = 0;
            for (TAppProjectPlan entity : listPlan) {
                planHour = planHour + entity.getPplanmanhour();
            }
            if (planHour != tAppProject.getPmanhourplan().intValue()) {
                j.setSuccess(false);
                j.setMsg("里程碑工时总和不等于项目计划工时,无法修改");
                return j;
            }
            j = tAppProjectService.modify(tAppProject);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppProject, request, session);
        return j;
    }

    /**
     * 获取下拉框数据
     */
    @RequestMapping("/getList")
    @ResponseBody
    public List<TAppProject> getList() {
        List<TAppProject> list = tAppProjectService.getList();
        return list;
    }

    /**
     * 获取机构人员树
     */
    @RequestMapping("/getInsPersonTree")
    @ResponseBody
    public List<TreeForLayUI> getInsPersonTree(String projectId, HttpSession session) throws Exception {
        List<TreeForLayUI> treeForLayUIList = null;
        List<TreeForLayUI> insPersonTree = new ArrayList<>();
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
        if (sessionInfo != null) {
            treeForLayUIList = insInfService.getInsListForLayUINew(sessionInfo.getOperInf().getInsUuid(), null);
        }
        List<String> personStrList = tAppProjectPersonService.getPersonByProjectId(projectId);
        for (TreeForLayUI treeForLayUI : treeForLayUIList) {
            if (treeForLayUI.getChildren().size() > 0) {
                insPersonTree.add(getAllPersonByIns(treeForLayUI, personStrList));
            } else {
                List<Map> datagrid = operInfService.getAllListByInsCd(treeForLayUI.getId());
                boolean isHave = false;
                for (Map operInf : datagrid) {
                    for (String person : personStrList) {
                        //人员是否被委派
                        if (operInf.get("OPER_CD").equals(person)) {
                            isHave = true;
                            break;
                        }
                    }
                    //人员被委派则
                    if (isHave) {
                        isHave = false;
                        continue;
                    }
                    TreeForLayUI entity = new TreeForLayUI();
                    entity.setId(operInf.get("OPER_CD").toString());
                    entity.setName(operInf.get("OPER_NM").toString());
                    entity.setAlias("person");
                    entity.setPid(operInf.get("INS_CD").toString());
                    treeForLayUI.getChildren().add(entity);
                }
                insPersonTree.add(treeForLayUI);
            }
        }
        return insPersonTree;
    }

    public TreeForLayUI getAllPersonByIns(TreeForLayUI treeForLayUI, List<String> personStrList) throws Exception {
        if (treeForLayUI.getChildren() != null && treeForLayUI.getChildren().size() > 0) {
            for (TreeForLayUI tree : treeForLayUI.getChildren()) {
                getAllPersonByIns(tree, personStrList);
            }
        }
        List<Map> datagrid = operInfService.getAllListByInsCd(treeForLayUI.getId());
        boolean isHave = false;
        for (Map operInf : datagrid) {
            for (String person : personStrList) {
                //人员是否被委派
                if (operInf.get("OPER_CD").equals(person)) {
                    isHave = true;
                    break;
                }
            }
            //人员被委派则
            if (isHave) {
                isHave = false;
                continue;
            }
            TreeForLayUI entity = new TreeForLayUI();
            entity.setId(operInf.get("OPER_CD").toString());
            entity.setName(operInf.get("OPER_NM").toString());
            entity.setAlias("person");
            entity.setPid(operInf.get("INS_CD").toString());
            treeForLayUI.getChildren().add(entity);
        }
        return treeForLayUI;
    }


    /**
     * 获取datagrid数据
     */
    @RequestMapping("/datagridByAppointOpercd")
    @ResponseBody
    public DatagridForLayUI datagridByAppointOpercd(TAppProject tAppProject, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            datagrid = tAppProjectService.datagridByOpercd(tAppProject, sessionInfo.getOperInf().getOperCd());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;

    }

    /**
     * 获取所有已委派未完成的项目下拉菜单
     */
    @RequestMapping("/getListByAppointNoComplete")
    @ResponseBody
    public List<Map> getListNoCompleteByOpercd(HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);

        List<Map> list = tAppProjectService.getListNoCompleteByOpercd(sessionInfo.getOperInf().getOperCd());
        return list;
    }

    /**
     * 修改
     */
    @RequestMapping("/updProjectComplete")
    @ResponseBody
    public Json updProjectComplete(TAppProject tAppProject, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            Integer sumAllManhour = 0;
            Long allPlan = tAppProjectPlanService.countByProjectId(tAppProject.getId(), null);
            if (allPlan == null) {
                allPlan = 0L;
            }
            Long completePlan = tAppProjectPlanService.countByProjectId(tAppProject.getId(), Contans.IS_COMPLETE_ARRY[1]);

            if (allPlan > 0) {
                //有子项目
                if (allPlan == completePlan) {
                    //是否全部完成
                    List<TAppProjectPlan> tAppProjectPlans = tAppProjectPlanService.getListPlanByProjectid(tAppProject.getId(), Contans.IS_COMPLETE_ARRY[0]);
                    if (tAppProjectPlans.size() > 0) {
                        for (TAppProjectPlan tAppProjectPlan : tAppProjectPlans) {
                            sumAllManhour = sumAllManhour + tAppProjectPlan.getPsummanhour();
                            tAppProject.setPcompletedate(new Date());
                            tAppProject.setPiscomplete(Contans.IS_COMPLETE_ARRY[1]);
                            tAppProject.setPmanhour(sumAllManhour);
                        }
                    }
                } else {
                    j.setSuccess(false);
                    j.setMsg("里程碑尚未全部完成,项目不可完成");
                    return j;
                }
            } else {
                //没有子项目
                Long sumManhour = tAppManhourService.getSumManhourByProId(tAppProject.getId());
                tAppProject.setPcompletedate(new Date());
                tAppProject.setPiscomplete(Contans.IS_COMPLETE_ARRY[1]);
                tAppProject.setPmanhour(sumManhour.intValue());
            }
            j = tAppProjectService.modify(tAppProject);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppProject, request, session);
        return j;
    }

    /**
     * 部门参与项目情况统计
     */
    @RequestMapping("/getProjectDeptEcharts")
    @ResponseBody
    public Map<String, List<String>> getProjectDeptEcharts() {
        List<Map> list = tAppProjectService.getProjectDeptEcharts();
        List<String> insnmList = new ArrayList<>();
        List<String> allProList = new ArrayList<>();
        List<String> completeProList = new ArrayList<>();
        List<String> nocompleteProList = new ArrayList<>();
        for (Map entityMap : list) {
            insnmList.add(entityMap.get("insnm") == null ? "" : entityMap.get("insnm").toString());
            allProList.add(entityMap.get("allPro") == null ? "" : entityMap.get("allPro").toString());
            completeProList.add(entityMap.get("completePro") == null ? "" : entityMap.get("completePro").toString());
            nocompleteProList.add(entityMap.get("nocompletePro") == null ? "" : entityMap.get("nocompletePro").toString());
        }
        Map<String, List<String>> ruseltMap = new HashMap<>();
        ruseltMap.put("insnm", insnmList);
        ruseltMap.put("allPro", allProList);
        ruseltMap.put("completePro", completeProList);
        ruseltMap.put("nocompletePro", nocompleteProList);
        return ruseltMap;
    }

    /**
     * 部门参与项目情况占比统计
     */
    @RequestMapping("/getProjectDeptProportionEcharts")
    @ResponseBody
    public List<Map> getProjectDeptProportionEcharts() {
        List<Map> list = tAppProjectService.getProjectDeptProportionEcharts();
        return list;
    }

    /**
     * 各项目预估工时占比
     */
    @RequestMapping("/getProjectManhourEcharts")
    @ResponseBody
    public List<Map> getProjectManhourEcharts() {
        List<Map> list = tAppProjectService.getProjectManhourEcharts();
        return list;
    }

    /**
     * 部门参与项目情况占比统计
     */
    @RequestMapping("/getProjectDeptCompleteEcharts")
    @ResponseBody
    public List<Map> getProjectDeptCompleteEcharts() {
        List<Map> list = tAppProjectService.getProjectDeptCompleteEcharts();
        return list;
    }

    /**
     * 部门人员总确认工时
     */
    @RequestMapping("/getDeptPersonManhourEcharts")
    @ResponseBody
    public Map<String, List<String>> getDeptPersonManhourEcharts() {
        List<Map> list = tAppProjectService.getDeptPersonManhourEcharts();
        List<String> insnmList = new ArrayList<>();
        List<String> allManhourList = new ArrayList<>();
        for (Map entityMap : list) {
            insnmList.add(entityMap.get("insnm").toString());
            allManhourList.add(entityMap.get("allManhour").toString());
        }
        Map<String, List<String>> ruseltMap = new HashMap<>();
        ruseltMap.put("insnm", insnmList);
        ruseltMap.put("allManhour", allManhourList);
        return ruseltMap;
    }

    /**
     * 下拉菜单 查询当前用户组织机构下已委派的项目
     */
    @RequestMapping("/getListByInsId")
    @ResponseBody
    public List<Map> getListByInsId(TAppProject tAppProject, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
        tAppProject.setPinscd(sessionInfo.getOperInf().getInsUuid());
        tAppProject.setPisappoint(Contans.IS_APPOINT_ARRY[1]);
        List<Map> list = tAppProjectService.getListByInsId(tAppProject);
        return list;
    }


}
