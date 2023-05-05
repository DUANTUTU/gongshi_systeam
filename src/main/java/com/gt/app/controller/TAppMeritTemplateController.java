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
import com.gt.model.TAppMeritTemplate;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITAppMeritTemplateService;



/**
 * @功能说明：绩效模板管理
 * @作者：zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/tAppMeritTemplate")

public class TAppMeritTemplateController extends BaseController {
	private Logger logger = Logger.getLogger(TAppMeritTemplateController.class);
	private ITAppMeritTemplateService tAppMeritTemplateService;

	public ITAppMeritTemplateService getTAppMeritTemplateService() {
		return  tAppMeritTemplateService;
	}

	@Autowired
	public void setTAppMeritTemplateService(ITAppMeritTemplateService tAppMeritTemplateService) {
		this.tAppMeritTemplateService = tAppMeritTemplateService;
	}


	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(TAppMeritTemplate tAppMeritTemplate) {
		DatagridForLayUI datagrid=null;
		try {
			datagrid = tAppMeritTemplateService.datagrid(tAppMeritTemplate);
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
	public Json add(TAppMeritTemplate tAppMeritTemplate, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
            Long l = tAppMeritTemplateService.countEntityByMjobid(tAppMeritTemplate);
            if (l>0){
				j.setSuccess(false);
				j.setMsg("该职位存在绩效模板,请修改或删除后重新添加");
				return j;
			}
			Date date = new Date();
			tAppMeritTemplate.setId(PbUtils.getUUID());
			tAppMeritTemplate.setMcreatedate(date);
			tAppMeritTemplate.setMupdatedate(date);

			j = tAppMeritTemplateService.add(tAppMeritTemplate);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
			j.setMsg("添加失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, tAppMeritTemplate, request, session);
		
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
			tAppMeritTemplateService.remove(id);
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
	public Json modify(TAppMeritTemplate tAppMeritTemplate, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			tAppMeritTemplate.setMupdatedate(new Date());
			j = tAppMeritTemplateService.modify(tAppMeritTemplate);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
			e.printStackTrace();
		}
	
		//添加系统日志
		writeSysLog(j, tAppMeritTemplate,request,session );
		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<TAppMeritTemplate>  getList() {
		List<TAppMeritTemplate> list = tAppMeritTemplateService.getList();
		return list;
	}

}
