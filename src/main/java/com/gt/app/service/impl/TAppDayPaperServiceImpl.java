package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppDayPaperService;
import com.gt.model.TAppDayPaper;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppDayPaperDao;

/**
 * @功能说明：日报业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Service("tAppDayPaperService")
public class TAppDayPaperServiceImpl extends BaseServiceImpl<TAppDayPaper> implements ITAppDayPaperService {

    //定义DAO
    @Autowired
    private ITAppDayPaperDao TAppDayPaperDao;

    @Override
    public DatagridForLayUI datagrid(TAppDayPaper tAppDayPaper) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select t.ID as id,t.DOpercd as dopercd,t.DSummanhour as dsummanhour,t.DMaster as dmaster,t.DBranch as dbranch,t.DMasterrate as dmasterrate,t.DBranchrate as dbranchrate,date_format(t.DCreatedate,'%Y-%c-%d %h:%i:%s') as dcreatedate ";
        String sql = " from t_app_day_paper t where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        // ID
        if (!PbUtils.isEmpty(tAppDayPaper.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tAppDayPaper.getId() + "%%");
        }
        // 人员id
        if (!PbUtils.isEmpty(tAppDayPaper.getDopercd())) {
            sql += " and t.DOpercd like:dopercd";
            param.put("dopercd", "%%" + tAppDayPaper.getDopercd() + "%%");
        }
        // 工时总计
        if (!PbUtils.isEmpty(tAppDayPaper.getDsummanhour())) {
            sql += " and t.DSummanhour like:dsummanhour";
            param.put("dsummanhour", "%%" + tAppDayPaper.getDsummanhour() + "%%");
        }
        // 主线任务工时
        if (!PbUtils.isEmpty(tAppDayPaper.getDmaster())) {
            sql += " and t.DMaster like:dmaster";
            param.put("dmaster", "%%" + tAppDayPaper.getDmaster() + "%%");
        }
        // 支线任务工时
        if (!PbUtils.isEmpty(tAppDayPaper.getDbranch())) {
            sql += " and t.DBranch like:dbranch";
            param.put("dbranch", "%%" + tAppDayPaper.getDbranch() + "%%");
        }
        // 主线工时权重
        if (!PbUtils.isEmpty(tAppDayPaper.getDmasterrate())) {
            sql += " and t.DMasterrate like:dmasterrate";
            param.put("dmasterrate", "%%" + tAppDayPaper.getDmasterrate() + "%%");
        }
        // 支线工时权重
        if (!PbUtils.isEmpty(tAppDayPaper.getDbranchrate())) {
            sql += " and t.DBranchrate like:dbranchrate";
            param.put("dbranchrate", "%%" + tAppDayPaper.getDbranchrate() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(tAppDayPaper.getDcreatedate())) {
            sql += " and t.DCreatedate like:dcreatedate";
            param.put("dcreatedate", "%%" + tAppDayPaper.getDcreatedate() + "%%");
        }


        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, tAppDayPaper.getPage(), tAppDayPaper.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Transactional
    @Override
    public Json add(TAppDayPaper inf) {
        Json j = new Json();
        save(inf);
        j.setSuccess(true);
        j.setMsg("新增成功");
        j.setObj(inf);    // 设置返回对象
        return j;

    }

    @Transactional
    @Override
    public void remove(String ids) {
        String[] nids = ids.split(",");
        String sql = "delete from t_app_day_paper  where ID in (";
        for (int i = 0; i < nids.length; i++) {
            if (i > 0) {
                sql += ",";
            }
            sql += "'" + nids[i] + "'";
        }
        sql += ")";
        executeSql(sql);

    }

    @Transactional
    @Override
    public Json modify(TAppDayPaper inf) {
        Json j = new Json();

        TAppDayPaper tInf = getById(TAppDayPaper.class, inf.getId());
        if (tInf == null) {
            j.setSuccess(false);
            j.setMsg("修改失败：找不到要修改的信息");
            return j;
        }

        // 旧对象
        TAppDayPaper oldObject = new TAppDayPaper();
        MyBeanUtils.copyProperties(tInf, oldObject);
        j.setOldObj(oldObject);

        MyBeanUtils.copyProperties(inf, tInf);
        update(tInf);// 更新
        j.setSuccess(true);
        j.setMsg("更新成功");
        j.setObj(tInf);// 设置返回对象
        return j;
    }


    @Override
    public Json verifyInfo(TAppDayPaper inf) {
        Json j = new Json();
        Map<String, Object> params = new HashMap<String, Object>();
        if (!PbUtils.isEmpty(inf.getId())) {
            params.put("id", inf.getId());
        }
        Long total = super.count("Select count(*) from TAppDayPaper t where t.Id =:id ", params);

        if (total > 0) {
            j.setSuccess(false);
            j.setMsg("此信息已经存在");
            return j;
        }

        j.setSuccess(true);
        j.setMsg("成功！");
        j.setObj(inf);
        return j;
    }

    @Override
    public List<TAppDayPaper> getList() {
        List<TAppDayPaper> l = find("from TAppDayPaper t");
        return l;
    }

    @Override
    public List<Map> getCountManhourByDate(String leftDate, String rightDate) {
        String sql = "select t.DOpercd as MOpercd,sum(t.DSummanhour) as MSummanhour,sum(t.DMaster) as MMaster,sum(t.DBranch) as MBranch " +
                " from t_app_day_paper t " +
                " where t.Dcreatedate>:leftDate and t.Dcreatedate<:rightDate " +
                " group by t.DOpercd ";
        Map<String, Object> params = new HashMap<>();
        params.put("leftDate", leftDate);
        params.put("rightDate", rightDate);
        List<Map> l = findBySql(sql, params);
        return l;
    }

    @Override
    public List<Map> getPersonManhourEcharts() {
        String sql = "SELECT " +
                " a.OPER_NM AS opernm, " +
                " sum(b.MManhour) AS dsummanhour " +
//                "-- \tt.DMaster AS dmaster,\n" +
//                "-- \tt.DBranch AS dbranch \n" +
                " FROM " +
                " t_app_manhour t , t_sys_oper_inf a ,t_app_manhour_confirm b " +
                " WHERE " +
                " a.OPER_CD = t.MOpercd  " +
                " and b.ID=t.MManhourconfimid " +
                " AND a.OPER_ST = '0' " +
                " GROUP BY t.MOpercd";
        List<Map> list = findBySql(sql);
        return list;
    }
}
