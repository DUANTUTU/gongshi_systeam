package com.gt.app.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.Test3;
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.ITest3Service;



/**
 * @功能说明：测试
 * @作者：liutaok
 * @创建日期：2018-12-17
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/test3")

public class Test3Controller extends BaseController {
	private Logger logger = Logger.getLogger(Test3Controller.class);
	private ITest3Service test3Service;

	public ITest3Service getTest3Service() {
		return  test3Service;
	}

	@Autowired
	public void setTest3Service(ITest3Service test3Service) {
		this.test3Service = test3Service;
	}


	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(Test3 test3) {
		DatagridForLayUI datagrid=null;
		try {
			datagrid = test3Service.datagrid(test3);
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
	public Json add(Test3 test3, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = test3Service.add(test3);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
			j.setMsg("添加失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, test3, request, session);
		
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
			test3Service.remove(id);
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
	public Json modify(Test3 test3, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = test3Service.modify(test3);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
			e.printStackTrace();
		}
	
		//添加系统日志
		writeSysLog(j, test3,request,session );
		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<Test3>  getList() {
		List<Test3> list = test3Service.getList();
		return list;
	}

}
