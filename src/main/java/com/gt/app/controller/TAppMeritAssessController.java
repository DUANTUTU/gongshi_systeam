package com.gt.app.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.TAppMeritAssess;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITAppMeritAssessService;



/**
 * @功能说明：绩效评估
 * @作者：zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppMeritAssess")

public class TAppMeritAssessController extends BaseController {
	private Logger logger = Logger.getLogger(TAppMeritAssessController.class);
	private ITAppMeritAssessService tAppMeritAssessService;

	public ITAppMeritAssessService getTAppMeritAssessService() {
		return  tAppMeritAssessService;
	}

	@Autowired
	public void setTAppMeritAssessService(ITAppMeritAssessService tAppMeritAssessService) {
		this.tAppMeritAssessService = tAppMeritAssessService;
	}


	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(TAppMeritAssess tAppMeritAssess) {
		DatagridForLayUI datagrid=null;
		try {
			datagrid = tAppMeritAssessService.datagrid(tAppMeritAssess);
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
	public Json add(TAppMeritAssess tAppMeritAssess, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = tAppMeritAssessService.add(tAppMeritAssess);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
			j.setMsg("添加失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, tAppMeritAssess, request, session);
		
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
			tAppMeritAssessService.remove(id);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败:" + e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, id,request, session);
		
		return j;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(TAppMeritAssess tAppMeritAssess, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = tAppMeritAssessService.modify(tAppMeritAssess);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
			e.printStackTrace();
		}
	
		//添加系统日志
		writeSysLog(j, tAppMeritAssess,request,session );
		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<TAppMeritAssess>  getList() {
		List<TAppMeritAssess> list = tAppMeritAssessService.getList();
		return list;
	}


	/**
	 * 绩效情况统计
	 * */
	@RequestMapping("/getMeritAssessEcharts")
	@ResponseBody
	public Map<String, List<String>> getMeritAssessEcharts(String sJobId) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		Date leftDay = PbUtils.StrToDateTime(year + "-01-01 00:00:00");
		Date rightDay = PbUtils.StrToDateTime(year + "-12-31 23:59:59");
		List<Map> list = tAppMeritAssessService.getMeritAssessEcharts(sJobId,year + "-01-01 00:00:00",year + "-12-31 23:59:59");
		List<String> opernmList = new ArrayList<>();
		List<String> msumhourrateList = new ArrayList<>();
		List<String> mmasterrateList = new ArrayList<>();
		List<String> mbranchrateList = new ArrayList<>();
		for (Map entityMap : list) {
			opernmList.add(entityMap.get("opernm")==null?"":entityMap.get("opernm").toString());
			msumhourrateList.add(entityMap.get("msumhourrate").toString());
			mmasterrateList.add(entityMap.get("mmasterrate").toString());
			mbranchrateList.add(entityMap.get("mbranchrate").toString());
		}
		Map<String, List<String>> ruseltMap = new HashMap<>();
		ruseltMap.put("opernm", opernmList);
		ruseltMap.put("msumhourrate", msumhourrateList);
		ruseltMap.put("mmasterrate", mmasterrateList);
		ruseltMap.put("mbranchrate", mbranchrateList);
		return ruseltMap;
	}
}
