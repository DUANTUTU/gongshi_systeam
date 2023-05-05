package com.gt.app.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.TAppBuginfo;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITAppBuginfoService;



/**
 * @功能说明：缺陷记录表
 * @作者：liutaok
 * @创建日期：2023-04-27
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppBuginfo")

public class TAppBuginfoController extends BaseController {
	private Logger logger = Logger.getLogger(TAppBuginfoController.class);
	private ITAppBuginfoService tAppBuginfoService;

	public ITAppBuginfoService getTAppBuginfoService() {
		return  tAppBuginfoService;
	}

	@Autowired
	public void setTAppBuginfoService(ITAppBuginfoService tAppBuginfoService) {
		this.tAppBuginfoService = tAppBuginfoService;
	}


	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(TAppBuginfo tAppBuginfo) {
		DatagridForLayUI datagrid=null;
		try {
			datagrid = tAppBuginfoService.datagrid(tAppBuginfo);
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
	public Json add(TAppBuginfo tAppBuginfo, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = tAppBuginfoService.add(tAppBuginfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
			j.setMsg("添加失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, tAppBuginfo, request, session);
		
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
			tAppBuginfoService.remove(id);
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
	public Json modify(TAppBuginfo tAppBuginfo, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = tAppBuginfoService.modify(tAppBuginfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
			e.printStackTrace();
		}
	
		//添加系统日志
		writeSysLog(j, tAppBuginfo,request,session );
		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<TAppBuginfo>  getList() {
		List<TAppBuginfo> list = tAppBuginfoService.getList();
		return list;
	}

}
