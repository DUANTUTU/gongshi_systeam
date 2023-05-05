package com.gt.app.service;

import java.util.List;
import com.gt.model.TAppProjectPlan;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：项目计划-里程碑业务接口类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
public interface ITAppProjectPlanService extends IBaseService<TAppProjectPlan> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppProjectPlan inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppProjectPlan inf) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void remove(String ids);
	/**
	 * 删除根据项目id
	 *
	 * @param ids
	 */
	public void delByProjectId(String ids);

	/**
	 * 修改
	 * 
	 * @param menuInf
	 */
	public Json modify(TAppProjectPlan inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppProjectPlan inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppProjectPlan> getList();
	public List<TAppProjectPlan> getListPlanByProjectid(String pprojectid,String iscomplete);

	public Long countByProjectId(String projectid,String iscomplete);
}
