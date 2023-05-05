package com.gt.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gt.model.TAppDebugDetail;
import com.gt.model.TAppManhour;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：生效工时业务接口类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
public interface ITAppManhourService extends IBaseService<TAppManhour> {

    /**
     * 获取datagrid数据
     *
     * @return
     */
    public DatagridForLayUI datagrid(TAppManhour inf, String leftDate, String rightDate,String operNm) throws Exception;
    public DatagridForLayUI datagridExcl(TAppManhour inf, String leftDate, String rightDate,String operNm) throws Exception;
    public DatagridForLayUI datagridDeBugExcl(TAppDebugDetail inf, String leftDate, String rightDate, String operNm) throws Exception;
    /**
     * 根据人员和时间区间获取确认工时
     *
     * @return
     */
    public DatagridForLayUI getEntitysByOpercdDateToDate(String opercd,String leftDate,String rightDate) throws Exception;

    /**
     * 新增
     *
     * @param inf
     * @return
     */
    public Json add(TAppManhour inf) throws Exception;

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
    public Json modify(TAppManhour inf);

    /**
     * 信息验证
     *
     * @param inf
     * @return
     */
    public Json verifyInfo(TAppManhour inf);

    /**
     * 获取下拉框数据
     *
     * @return
     */
    public List<TAppManhour> getList();

    /**
     * 根据项目ID统计各个参与人于各个项目历程呗参与工时统计
     */
    public DatagridForLayUI getProPlanManhourByProId(String projectid);

/**
 * 项目里程碑实际工时使用
 */
    public Long getSumManhourByProPlanId(String projectplanid);
    /**
     * 项目实际工时使用
     */
    public Long getSumManhourByProId(String projectplanid);

    /**
     * 日周月报查询根据时间区间
     * */
    public List<Map> getPaperByDate(String leftDate, String rightDate);
    /**
     * 查询人员参与项目工时总计
     * */
    public DatagridForLayUI getProSumManhourListByOpercdDate(String opercd,String leftDate, String rightDate);

    /**
     * 项目里程碑预估工时占比
     * */
    public List<Map> getProManhour(String projectId);

     /**
     * 项目里程碑完成工时占比
     * */
     public List<Map> getFinshProManhour(String projectId);

     /**
      * 完成工时占里程碑计划工时
      * */
     public List<Map> getProHourAndFinshHour(String projectId);
}
