package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppMeritAssessService;
import com.gt.model.TAppMeritAssess;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppMeritAssessDao;

/**
 * @功能说明：绩效评估业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
@Service("tAppMeritAssessService")
public class TAppMeritAssessServiceImpl extends BaseServiceImpl<TAppMeritAssess> implements ITAppMeritAssessService {

    //定义DAO
    @Autowired
    private ITAppMeritAssessDao TAppMeritAssessDao;

    @Override
    public DatagridForLayUI datagrid(TAppMeritAssess tAppMeritAssess) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select a.OPER_NM as mopercdNm," +
                "t.ID as id,t.MOpercd as mopercd,CONCAT(t.MSumhourrate*100,'%') as msumhourrate,CONCAT(t.MMasterrate*100,'%') as mmasterrate,CONCAT(t.MBranchrate*100,'%') as mbranchrate,date_format(t.MCreatedate,'%Y-%c-%d %h:%i:%s') as mcreatedate ";
        String sql = " from t_app_merit_assess t " +
                " left join t_sys_oper_inf a on a.OPER_CD=t.MOpercd " +
                " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        // ID
        if (!PbUtils.isEmpty(tAppMeritAssess.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tAppMeritAssess.getId() + "%%");
        }
        // 人员ID
        if (!PbUtils.isEmpty(tAppMeritAssess.getMopercd())) {
            sql += " and t.MOpercd like:mopercd";
            param.put("mopercd", "%%" + tAppMeritAssess.getMopercd() + "%%");
        }
        // 绩效模板工时完成度
        if (!PbUtils.isEmpty(tAppMeritAssess.getMsumhourrate())) {
            sql += " and t.MSumhourrate like:msumhourrate";
            param.put("msumhourrate", "%%" + tAppMeritAssess.getMsumhourrate() + "%%");
        }
        // 主线工时占比绩效模板率
        if (!PbUtils.isEmpty(tAppMeritAssess.getMmasterrate())) {
            sql += " and t.MMasterrate like:mmasterrate";
            param.put("mmasterrate", "%%" + tAppMeritAssess.getMmasterrate() + "%%");
        }
        // 主线工时占比绩效模板率
        if (!PbUtils.isEmpty(tAppMeritAssess.getMbranchrate())) {
            sql += " and t.MBranchrate like:mbranchrate";
            param.put("mbranchrate", "%%" + tAppMeritAssess.getMbranchrate() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(tAppMeritAssess.getMcreatedate())) {
            sql += " and t.MCreatedate like:mcreatedate";
            param.put("mcreatedate", "%%" + tAppMeritAssess.getMcreatedate() + "%%");
        }


        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, tAppMeritAssess.getPage(), tAppMeritAssess.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Transactional
    @Override
    public Json add(TAppMeritAssess inf) {
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
        String sql = "delete from t_app_merit_assess  where ID in (";
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
    public void remove(String leftDay, String rightDay) {
        String sql = "delete from t_app_merit_assess  where MCreatedate >=:leftDay and MCreatedate<=:rightDay ";
        Map<String, Object> param = new HashMap<>();
        param.put("leftDay", leftDay);
        param.put("rightDay", rightDay);
        executeSql(sql, param);
    }

    @Transactional
    @Override
    public Json modify(TAppMeritAssess inf) {
        Json j = new Json();

        TAppMeritAssess tInf = getById(TAppMeritAssess.class, inf.getId());
        if (tInf == null) {
            j.setSuccess(false);
            j.setMsg("修改失败：找不到要修改的信息");
            return j;
        }

        // 旧对象
        TAppMeritAssess oldObject = new TAppMeritAssess();
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
    public Json verifyInfo(TAppMeritAssess inf) {
        Json j = new Json();
        Map<String, Object> params = new HashMap<String, Object>();
        if (!PbUtils.isEmpty(inf.getId())) {
            params.put("id", inf.getId());
        }
        Long total = super.count("Select count(*) from TAppMeritAssess t where t.Id =:id ", params);

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
    public List<TAppMeritAssess> getList() {
        List<TAppMeritAssess> l = find("from TAppMeritAssess t");
        return l;
    }

    @Override
    public List<Map> getMeritAssessEcharts(String sJobId, String leftDate, String rightDate) {
        String sql = "select a.OPER_NM as opernm,t.MSumhourrate as msumhourrate,t.MMasterrate as mmasterrate,t.MBranchrate as mbranchrate " +
                "from t_app_merit_assess t " +
                "join t_sys_oper_inf a " +
                "where a.OPER_CD=t.MOpercd and a.sjobid=:sJobId " +
                " and t.MCreatedate>=:leftDate " +
                " and t.MCreatedate<=:rightDate ";
        Map<String, Object> param = new HashMap<>();
        param.put("sJobId", sJobId);
        param.put("leftDate", leftDate);
        param.put("rightDate", rightDate);

        List<Map> list = findBySql(sql, param);
        return list;
    }
}
