package com.gt.app.service;

import java.util.List;
import java.util.Map;

import com.gt.model.TAppProject;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：项目计划业务接口类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
public interface ITAppProjectService extends IBaseService<TAppProject> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppProject inf,String insUuid) throws Exception;
	/**
	 * 获取datagrid数据
	 *
	 * @return
	 */
	public DatagridForLayUI datagridProjectAssess(TAppProject inf,String insUuid) throws Exception;
	/**
	 * 获取登录人委派项目
	 * */
	public DatagridForLayUI datagridByOpercd(TAppProject inf,String appointOpercd) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppProject inf) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void remove(String ids);

	/**
	 * 修改
	 * 
	 * @param menuInf
	 */
	public Json modify(TAppProject inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppProject inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppProject> getList();
	public List<TAppProject> getListByAppoint();
	/**
	 *下拉菜单 查询当前用户组织机构下已委派的项目
	 * */
	public List<Map> getListByInsId(TAppProject tAppProject);

	public List<Map> getListNoCompleteByOpercd(String opercd);

	/**
	 * 部门参与项目情况统计
	 * */
	public List<Map> getProjectDeptEcharts();
	/**
	 * 部门参与项目占比统计
	 * */
	public List<Map> getProjectDeptProportionEcharts();
	/**
	 * 部门参与项目占比统计
	 * */
	public List<Map> getProjectManhourEcharts();
	/**
	 * 部门完成项目占比统计
	 * */
	public List<Map> getProjectDeptCompleteEcharts();
	/**
	 * 部门人员总确认工时
	 * */
	public List<Map> getDeptPersonManhourEcharts();
}
