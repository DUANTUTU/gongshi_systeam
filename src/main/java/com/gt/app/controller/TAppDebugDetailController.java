package com.gt.app.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gt.pageModel.SessionInfo;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.TAppDebugDetail;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITAppDebugDetailService;



/**
 * @功能说明：debug记录表
 * @作者：liutaok
 * @创建日期：2023-04-27
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppDebugDetail")

public class TAppDebugDetailController extends BaseController {
	private Logger logger = Logger.getLogger(TAppDebugDetailController.class);
	private ITAppDebugDetailService tAppDebugDetailService;

	public ITAppDebugDetailService getTAppDebugDetailService() {
		return  tAppDebugDetailService;
	}

	@Autowired
	public void setTAppDebugDetailService(ITAppDebugDetailService tAppDebugDetailService) {
		this.tAppDebugDetailService = tAppDebugDetailService;
	}


	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(TAppDebugDetail tAppDebugDetail) {
		DatagridForLayUI datagrid=null;
		try {
			datagrid = tAppDebugDetailService.datagrid(tAppDebugDetail);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return datagrid;
	}
	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagridWithUserId")
	@ResponseBody
	public DatagridForLayUI datagridWithUserId(TAppDebugDetail tAppDebugDetail,HttpSession session) {
		DatagridForLayUI datagrid=null;
		try {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
			String userId=sessionInfo.getOperInf().getOperCd();

			

			datagrid = tAppDebugDetailService.datagridByUserid(tAppDebugDetail,userId);
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
	public Json add(TAppDebugDetail tAppDebugDetail, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			tAppDebugDetail.setMconfirmid(tAppDebugDetail.getId());
			tAppDebugDetail.setId(PbUtils.getUUID());
			tAppDebugDetail.setMcheckdate(PbUtils.getCurrentDate());
			tAppDebugDetail.setMcreatedate(new Date());

			j = tAppDebugDetailService.add(tAppDebugDetail);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
			j.setMsg("添加失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, tAppDebugDetail, request, session);
		
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
			tAppDebugDetailService.remove(id);
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
	public Json modify(TAppDebugDetail tAppDebugDetail, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = tAppDebugDetailService.modify(tAppDebugDetail);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
			e.printStackTrace();
		}
	
		//添加系统日志
		writeSysLog(j, tAppDebugDetail,request,session );
		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<TAppDebugDetail>  getList() {
		List<TAppDebugDetail> list = tAppDebugDetailService.getList();
		return list;
	}

}
