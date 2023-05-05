package com.gt.app.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.TAppProjectAssess;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITAppProjectAssessService;



/**
 * @功能说明：项目工时评估
 * @作者：zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppProjectAssess")

public class TAppProjectAssessController extends BaseController {
	private Logger logger = Logger.getLogger(TAppProjectAssessController.class);
	private ITAppProjectAssessService tAppProjectAssessService;

	public ITAppProjectAssessService getTAppProjectAssessService() {
		return  tAppProjectAssessService;
	}

	@Autowired
	public void setTAppProjectAssessService(ITAppProjectAssessService tAppProjectAssessService) {
		this.tAppProjectAssessService = tAppProjectAssessService;
	}


	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(TAppProjectAssess tAppProjectAssess) {
		DatagridForLayUI datagrid=null;
		try {
			datagrid = tAppProjectAssessService.datagrid(tAppProjectAssess);
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
	public Json add(TAppProjectAssess tAppProjectAssess, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = tAppProjectAssessService.add(tAppProjectAssess);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
			j.setMsg("添加失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, tAppProjectAssess, request, session);
		
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
			tAppProjectAssessService.remove(id);
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
	public Json modify(TAppProjectAssess tAppProjectAssess, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = tAppProjectAssessService.modify(tAppProjectAssess);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
			e.printStackTrace();
		}
	
		//添加系统日志
		writeSysLog(j, tAppProjectAssess,request,session );
		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<TAppProjectAssess>  getList() {
		List<TAppProjectAssess> list = tAppProjectAssessService.getList();
		return list;
	}

}
