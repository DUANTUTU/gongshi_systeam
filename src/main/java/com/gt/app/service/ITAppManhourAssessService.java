package com.gt.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gt.model.TAppManhourAssess;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：个人工时评估业务接口类
 * @作者： zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
public interface ITAppManhourAssessService extends IBaseService<TAppManhourAssess> {

    /**
     * 获取datagrid数据
     *
     * @return
     */
    public DatagridForLayUI datagrid(TAppManhourAssess inf, String moperNm, String leftDate, String rightDate,String insId,String jobtype) throws Exception;

    /**
     * 新增
     *
     * @param inf
     * @return
     */
    public Json add(TAppManhourAssess inf) throws Exception;

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
    public Json modify(TAppManhourAssess inf);

    /**
     * 信息验证
     *
     * @param inf
     * @return
     */
    public Json verifyInfo(TAppManhourAssess inf);

    /**
     * 获取下拉框数据
     *
     * @return
     */
    public List<TAppManhourAssess> getList();

    /**
     * 根据时间区间获取数据列表
     *
     * @return
     */
    public List<TAppManhourAssess> getListByDate(Date leftDate, Date rightDate);


}
