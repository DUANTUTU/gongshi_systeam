package com.gt.app.service;

import java.util.List;
import java.util.Map;

import com.gt.model.TAppProjectPerson;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：项目人员表业务接口类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
public interface ITAppProjectPersonService extends IBaseService<TAppProjectPerson> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppProjectPerson inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppProjectPerson inf) throws Exception;

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
	public Json modify(TAppProjectPerson inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppProjectPerson inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppProjectPerson> getList();
	public List<Map> getProjectListByOpercd(TAppProjectPerson inf);
	/**
	 * 人员参数项目数量统计图表
	 * */
	public List<Map> getPersonProjectEcharts();

	public List<String> getPersonByProjectId(String projectid);

}
