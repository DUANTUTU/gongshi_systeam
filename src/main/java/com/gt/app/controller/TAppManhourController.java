package com.gt.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gt.app.service.ITAppManhourService;
import com.gt.app.service.ITSysJobService;
import com.gt.model.TAppDebugDetail;
import com.gt.model.TAppManhour;
import com.gt.model.TSysJob;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.SessionInfo;
import com.gt.sys.controller.BaseController;
import com.gt.utils.Contans;
import com.gt.utils.LoadDebugExcelUtil;
import com.gt.utils.LoadExcelUtil;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @功能说明：生效工时
 * @作者：zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppManhour")

public class TAppManhourController extends BaseController {
    private Logger logger = Logger.getLogger(TAppManhourController.class);
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


    private DatagridForLayUI datagridExcl;
    private DatagridForLayUI datagridDebugExcl;
    /**
     * 获取datagrid数据
     */
    @RequestMapping("/datagrid")
    @ResponseBody
    public DatagridForLayUI datagrid(TAppManhour tAppManhour, String leftDate, String rightDate,String operNm, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
            if ("0".equals(tSysJob.getJtype())) {
                tAppManhour.setMinscd(sessionInfo.getOperInf().getInsUuid());
            } else if ("1".equals(tSysJob.getJtype())) {
                tAppManhour.setMopercd(sessionInfo.getOperInf().getOperCd());
            }
            datagrid = tAppManhourService.datagrid(tAppManhour,leftDate,rightDate,operNm);
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
    public Json add(TAppManhour tAppManhour, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = tAppManhourService.add(tAppManhour);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            e.printStackTrace();
            j.setMsg("添加失败:" + e.getMessage());
        }

        //添加系统日志
        writeSysLog(j, tAppManhour, request, session);

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
            tAppManhourService.remove(id);
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
    public Json modify(TAppManhour tAppManhour, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = tAppManhourService.modify(tAppManhour);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
            e.printStackTrace();
        }

        //添加系统日志
        writeSysLog(j, tAppManhour, request, session);
        return j;
    }

    /**
     * 获取下拉框数据
     */
    @RequestMapping("/getList")
    @ResponseBody
    public List<TAppManhour> getList() {
        List<TAppManhour> list = tAppManhourService.getList();
        return list;
    }

    /**
     * 根据项目ID统计各个参与人于各个项目历程呗参与工时统计
     */
    @RequestMapping("/getProPlanManhourByProId")
    @ResponseBody
    public DatagridForLayUI getProPlanManhourByProId(String pprojectid) {
        DatagridForLayUI datagrid = null;
        if ("".equals(pprojectid)) {
            return null;
        }
        datagrid = tAppManhourService.getProPlanManhourByProId(pprojectid);
        return datagrid;
    }

    /**
     * 根据人员id和时间区间查询确认工时
     */
    @RequestMapping("/getEntitysByOpercdDateToDate")
    @ResponseBody
    public DatagridForLayUI getEntitysByOpercdDateToDate(String opercd, String createdate, String type) {
        DatagridForLayUI datagrid = null;
        try {
            Date date = PbUtils.StrToDateTime(createdate);
            String leftDate = "";
            String rightDate = "";
            if ("day".equals(type)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                String yesterday = PbUtils.DateToStr(calendar.getTime());
                String day = yesterday.substring(0, 10);
                leftDate = day + " 00:00:00";
                rightDate = day + " 23:59:59";
            }
            if ("week".equals(type)) {
                Calendar calendarWeekOne = Calendar.getInstance();
                calendarWeekOne.setTime(date);
                calendarWeekOne.add(Calendar.DAY_OF_MONTH, -7);
                Calendar calendarWeekSeven = Calendar.getInstance();
                calendarWeekSeven.setTime(date);
                calendarWeekSeven.add(Calendar.DAY_OF_MONTH, -1);
                String weekone = PbUtils.DateToStr(calendarWeekOne.getTime());
                String weekseven = PbUtils.DateToStr(calendarWeekSeven.getTime());
                String dayOne = weekone.substring(0, 10);
                String daySeven = weekseven.substring(0, 10);
                rightDate = dayOne + " 00:00:00";
                rightDate = daySeven + " 23:59:59";
            }
            if ("month".equals(type)) {
                Calendar calendarWeekOne = Calendar.getInstance();
                calendarWeekOne.setTime(date);
                calendarWeekOne.add(Calendar.MONTH, -1);
                Calendar calendarWeekSeven = Calendar.getInstance();
                calendarWeekSeven.setTime(date);
                calendarWeekSeven.add(Calendar.DAY_OF_MONTH, -1);
                String weekone = PbUtils.DateToStr(calendarWeekOne.getTime());
                String weekseven = PbUtils.DateToStr(calendarWeekSeven.getTime());
                String dayOne = weekone.substring(0, 10);
                String daySeven = weekseven.substring(0, 10);
                leftDate = dayOne + " 00:00:00";
                rightDate = daySeven + " 23:59:59";
            }
            datagrid = tAppManhourService.getEntitysByOpercdDateToDate(opercd, leftDate, rightDate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }

    /**
     * 根据人员id和时间区间统计人员参与项目的确认总工时
     */
    @RequestMapping("/getProSumManhourListByOpercdDate")
    @ResponseBody
    public DatagridForLayUI getProSumManhourListByOpercdDate(String opercd, String createdate, String type) {
        DatagridForLayUI datagrid = null;
        try {
            Date date = PbUtils.StrToDateTime(createdate);
            String leftDate = "";
            String rightDate = "";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, -1);
            String yesterday = PbUtils.DateToStr(calendar.getTime());
            leftDate = yesterday;
            rightDate = PbUtils.DateToStr(date);

            datagrid = tAppManhourService.getProSumManhourListByOpercdDate(opercd, leftDate, rightDate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return datagrid;
    }


    /**
     * echarts -项目里程碑预估工时占比
     */
    @RequestMapping("/getProManhour")
    @ResponseBody
    public Map<String, Object> getProManhour(String projectId) {
        if (PbUtils.isEmpty(projectId)) {
            return null;
        }
        Map<String, Object> jsonMap = new HashMap<>();
        List<String> planName = new ArrayList<>();
        List<Map> planHour = new ArrayList<>();
        try {
            List<Map> list = tAppManhourService.getProManhour(projectId);
            for (Map entity : list) {
                planName.add(entity.get("planName") == null ? "" : entity.get("planName").toString());
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("name", entity.get("planName") == null ? "" : entity.get("planName").toString());
                dataMap.put("value", PbUtils.isEmpty(entity.get("planHour")) ? "0" : entity.get("planHour").toString());
                planHour.add(dataMap);
            }
            jsonMap.put("planName", planName);
            jsonMap.put("planHour", planHour);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return jsonMap;
    }


    /**
     * 项目里程碑完成工时占比
     */
    @RequestMapping("/getFinshProManhour")
    @ResponseBody
    public Map<String, Object> getFinshProManhour(String projectId) {
        if (PbUtils.isEmpty(projectId)) {
            return null;
        }
        Map<String, Object> jsonMap = new HashMap<>();
        List<String> planName = new ArrayList<>();
        List<Map> finshHour = new ArrayList<>();
        try {
            List<Map> list = tAppManhourService.getFinshProManhour(projectId);
            for (Map entity : list) {
                planName.add(entity.get("planName") == null ? "" : entity.get("planName").toString());
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("value", PbUtils.isEmpty(entity.get("finshHour")) ? "0" : entity.get("finshHour").toString());
                dataMap.put("name", entity.get("planName") == null ? "" : entity.get("planName").toString());
                finshHour.add(dataMap);
            }
            jsonMap.put("planName", planName);
            jsonMap.put("finshHour", finshHour);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return jsonMap;
    }

    /***/
    @RequestMapping("/getProHourAndFinshHour")
    @ResponseBody
    public Map<String, Object> getProHourAndFinshHour(String projectId) {
        if (PbUtils.isEmpty(projectId)) {
            return null;
        }
        Map<String, Object> jsonMap = new HashMap<>();
        List<String> planName = new ArrayList<>();
        List<String> planHour = new ArrayList<>();
        List<String> finshHour = new ArrayList<>();
        try {
            List<Map> list = tAppManhourService.getProHourAndFinshHour(projectId);
            for (Map entity : list) {
                planName.add(entity.get("planName") == null ? "" : entity.get("planName").toString());
                planHour.add(PbUtils.isEmpty(entity.get("planHour")) ? "0" : entity.get("planHour").toString());
                finshHour.add(PbUtils.isEmpty(entity.get("finshHour")) ? "0" : entity.get("finshHour").toString());
            }
            jsonMap.put("planName", planName);
            jsonMap.put("planHour", planHour);
            jsonMap.put("finshHour", finshHour);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return jsonMap;
    }

    /**
     * 查询生效工时  导出xls
     */
    @RequestMapping("/datagridExcl")
    @ResponseBody
    public Json datagridExcl(TAppManhour tAppManhour, String leftDate, String rightDate,String operNm, HttpSession session) {
        Json j = new Json();
        try {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
            if ("0".equals(tSysJob.getJtype())) {
                tAppManhour.setMinscd(sessionInfo.getOperInf().getInsUuid());
            } else if ("1".equals(tSysJob.getJtype())) {
                tAppManhour.setMopercd(sessionInfo.getOperInf().getOperCd());
            }
            datagridExcl = tAppManhourService.datagridExcl(tAppManhour,leftDate,rightDate,operNm);
            if (datagridExcl.getData().size() == 0) {
                j.setSuccess(false);
                j.setMsg("没有导出数据！请重新确认条件。");
                return j;
            }
            j.setSuccess(true);
            return j;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return j;
    }
    /*导出debug信息*/
    @RequestMapping("/datagridDebugExcl")
    @ResponseBody
    public Json datagridDebugExcl(TAppDebugDetail tAppDebugDetail, String leftDate, String rightDate, String operNm, HttpSession session) {
        Json j = new Json();
        try {

            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
//            if ("0".equals(tSysJob.getJtype())) {
//                tAppManhour.setMinscd(sessionInfo.getOperInf().getInsUuid());
//            } else if ("1".equals(tSysJob.getJtype())) {
//                tAppManhour.setMopercd(sessionInfo.getOperInf().getOperCd());
//            }
            datagridDebugExcl = tAppManhourService.datagridDeBugExcl(tAppDebugDetail, leftDate, rightDate, operNm);

            if (datagridDebugExcl.getData().size() == 0) {
                j.setSuccess(false);
                j.setMsg("没有导出数据！请重新确认条件。");
                return j;
            }
            j.setSuccess(true);
            return j;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 查询设备网络监控情况  导出缺陷xls
     */
    @RequestMapping("/exportManhour")
    public void exportManhour(HttpServletResponse response) throws Exception {
        //后台数据重构成ArrayList<String>  list 转 json 遍历取值
        List list = datagridExcl.getData();
        //导出list
        ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
        //
        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));

        if (jsonArr != null) {
            for (int i = 0; i < jsonArr.size(); i++) {

                ArrayList<String> listdata = new ArrayList<String>();
                listdata.add(jsonArr.getJSONObject(i).get("mprojectidNm") + "");//项目名称
                listdata.add(jsonArr.getJSONObject(i).get("mprojectplanidNm") + "");//项目里程碑名称
                listdata.add(jsonArr.getJSONObject(i).get("mworkdetails") + "");//工作内容
                listdata.add(jsonArr.getJSONObject(i).get("mmanhour") + "");//确认工时
                listdata.add(jsonArr.getJSONObject(i).get("mopercdNm") + "");//填报人
                listdata.add(jsonArr.getJSONObject(i).get("mcreatedate") + "");//创建时间
                listdata.add("");
                fieldData.add(listdata);
            }
        }

        if (datagridExcl.getData() != null && datagridExcl.getData().size() > 0) {//如果存在不规范行，则重新生成表
            Date date = new Date();

            //使用ExcelFileGenerator完成导出
            LoadExcelUtil loadExcelUtil = new LoadExcelUtil(fieldData);
            OutputStream os = response.getOutputStream();
            //导出excel建议加上重置输出流，可以不加该代码，但是如果不加必须要保证输出流中不应该在存在其他数据，否则导出会有问题
            response.reset();
            //配置：//文件名
            String fileName = "项目工时报表(" + new SimpleDateFormat("yyyyMMddHHmmss").format(date) + ").xls";
            //处理乱码
            fileName = new String(fileName.getBytes("gbk"), "iso-8859-1");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setBufferSize(1024);
            //导出excel的操作
            loadExcelUtil.expordExcelManhour(os);
        }
    }
    @RequestMapping("/exportDebugManhour")
    public void exportDebugManhour(HttpServletResponse response) throws Exception {
        //后台数据重构成ArrayList<String>  list 转 json 遍历取值
        List list = datagridDebugExcl.getData();
        //导出list
        ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
        //
        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));

        if (jsonArr != null) {
            for (int i = 0; i < jsonArr.size(); i++) {

                ArrayList<String> listdata = new ArrayList<String>();
                listdata.add(jsonArr.getJSONObject(i).get("mprojectidNm") + "");//项目名称
                listdata.add(jsonArr.getJSONObject(i).get("mprojectplanidNm") + "");//项目里程碑名称
                listdata.add(jsonArr.getJSONObject(i).get("mworkdetails") + "");//工作内容
                listdata.add(jsonArr.getJSONObject(i).get("mmanhour") + "");//确认工时
                listdata.add(jsonArr.getJSONObject(i).get("mopercd") + "");//填报人
                 listdata.add(jsonArr.getJSONObject(i).get("mcheckdate") + "");//创建时间
                listdata.add(jsonArr.getJSONObject(i).get("debugFinishDate") + "");//debug工时
                listdata.add("");
                fieldData.add(listdata);
            }
        }

        if (datagridDebugExcl.getData() != null && datagridDebugExcl.getData().size() > 0) {//如果存在不规范行，则重新生成表
            Date date = new Date();

            //使用ExcelFileGenerator完成导出
            LoadDebugExcelUtil loadExcelUtil = new LoadDebugExcelUtil(fieldData);
            OutputStream os = response.getOutputStream();
            //导出excel建议加上重置输出流，可以不加该代码，但是如果不加必须要保证输出流中不应该在存在其他数据，否则导出会有问题
            response.reset();
            //配置：//文件名
            String fileName = "缺陷管理报表(" + new SimpleDateFormat("yyyyMMddHHmmss").format(date) + ").xls";
            //处理乱码
            fileName = new String(fileName.getBytes("gbk"), "iso-8859-1");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setBufferSize(1024);
            //导出excel的操作
            loadExcelUtil.expordExcelManhour(os);
        }
    }
}
