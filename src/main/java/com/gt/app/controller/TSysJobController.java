package com.gt.app.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.TSysJob;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITSysJobService;



/**
 * @功能说明：职位管理
 * @作者：zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tSysJob")

public class TSysJobController extends BaseController {
	private Logger logger = Logger.getLogger(TSysJobController.class);
	private ITSysJobService tSysJobService;

	public ITSysJobService getTSysJobService() {
		return  tSysJobService;
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
	public DatagridForLayUI datagrid(TSysJob tSysJob) {
		DatagridForLayUI datagrid=null;
		try {
			datagrid = tSysJobService.datagrid(tSysJob);
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
	public Json add(TSysJob tSysJob, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			tSysJob.setId(PbUtils.getUUID());
			tSysJob.setJcreatedate(new Date());
			j = tSysJobService.add(tSysJob);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
			j.setMsg("添加失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, tSysJob, request, session);
		
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
			tSysJobService.remove(id);
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
	public Json modify(TSysJob tSysJob, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = tSysJobService.modify(tSysJob);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
			e.printStackTrace();
		}
	
		//添加系统日志
		writeSysLog(j, tSysJob,request,session );
		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<TSysJob>  getList() {
		List<TSysJob> list = tSysJobService.getList();
		return list;
	}

}
