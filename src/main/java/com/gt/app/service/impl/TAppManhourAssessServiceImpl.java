package com.gt.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppManhourAssessService;
import com.gt.model.TAppManhourAssess;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppManhourAssessDao;

/**
 * @功能说明：个人工时评估业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
@Service("tAppManhourAssessService")
public class TAppManhourAssessServiceImpl extends BaseServiceImpl<TAppManhourAssess> implements ITAppManhourAssessService {

    //定义DAO
    @Autowired
    private ITAppManhourAssessDao TAppManhourAssessDao;

    @Override
    public DatagridForLayUI datagrid(TAppManhourAssess tAppManhourAssess, String moperNm, String leftDate, String rightDate, String insId, String jobtype) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select a.OPER_NM as mopercdNm," +
                "t.ID as id,t.MOpercd as mopercd,t.MSummanhour as msummanhour,t.MMaster as mmaster,t.MBranch as mbranch,CONCAT(t.MMasterrate*100,'%') as mmasterrate,CONCAT(t.MBranchrate*100,'%') as mbranchrate,date_format(t.MCreatedate,'%Y-%c-%d %h:%i:%s') as mcreatedate ";
        String sql = " from t_app_manhour_assess t " +
                " left join t_sys_oper_inf a on a.OPER_CD = t.MOpercd " +
                " left join t_sys_ins_inf b on b.UUID = a.INS_CD " +
                " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        if (!PbUtils.isEmpty(jobtype) && "0".equals(jobtype)) {
            sql += " and b.INS_DETAIL like :insId ";
            param.put("insId", "%%" + insId + "%%");
        }
        // ID
        if (!PbUtils.isEmpty(tAppManhourAssess.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tAppManhourAssess.getId() + "%%");
        }
        // OPER_NM姓名
        if (!PbUtils.isEmpty(moperNm)) {
            sql += " and a.OPER_NM like:moperNm";
            param.put("moperNm", "%%" + moperNm + "%%");
        }
        // 人员ID
        if (!PbUtils.isEmpty(tAppManhourAssess.getMopercd())) {
            sql += " and t.MOpercd like:mopercd";
            param.put("mopercd", "%%" + tAppManhourAssess.getMopercd() + "%%");
        }
        // 总工时
        if (!PbUtils.isEmpty(tAppManhourAssess.getMsummanhour())) {
            sql += " and t.MSummanhour like:msummanhour";
            param.put("msummanhour", "%%" + tAppManhourAssess.getMsummanhour() + "%%");
        }
        // 主线工时
        if (!PbUtils.isEmpty(tAppManhourAssess.getMmaster())) {
            sql += " and t.MMaster like:mmaster";
            param.put("mmaster", "%%" + tAppManhourAssess.getMmaster() + "%%");
        }
        // 支线工时
        if (!PbUtils.isEmpty(tAppManhourAssess.getMbranch())) {
            sql += " and t.MBranch like:mbranch";
            param.put("mbranch", "%%" + tAppManhourAssess.getMbranch() + "%%");
        }
        // 主线工时权重
        if (!PbUtils.isEmpty(tAppManhourAssess.getMmasterrate())) {
            sql += " and t.MMasterrate like:mmasterrate";
            param.put("mmasterrate", "%%" + tAppManhourAssess.getMmasterrate() + "%%");
        }
        // 支线任务权重
        if (!PbUtils.isEmpty(tAppManhourAssess.getMbranchrate())) {
            sql += " and t.MBranchrate like:mbranchrate";
            param.put("mbranchrate", "%%" + tAppManhourAssess.getMbranchrate() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(tAppManhourAssess.getMcreatedate())) {
            sql += " and t.MCreatedate like:mcreatedate";
            param.put("mcreatedate", "%%" + tAppManhourAssess.getMcreatedate() + "%%");
        }

        if (!PbUtils.isEmpty(leftDate)) {
            sql += " and t.MCreatedate > :leftDate ";
            param.put("leftDate", leftDate);
        }
        if (!PbUtils.isEmpty(rightDate)) {
            sql += " and t.MCreatedate < :rightDate ";
            param.put("rightDate", rightDate);
        }


        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, tAppManhourAssess.getPage(), tAppManhourAssess.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Transactional
    @Override
    public Json add(TAppManhourAssess inf) {
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
        String sql = "delete from t_app_manhour_assess  where ID in (";
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
    public Json modify(TAppManhourAssess inf) {
        Json j = new Json();

        TAppManhourAssess tInf = getById(TAppManhourAssess.class, inf.getId());
        if (tInf == null) {
            j.setSuccess(false);
            j.setMsg("修改失败：找不到要修改的信息");
            return j;
        }

        // 旧对象
        TAppManhourAssess oldObject = new TAppManhourAssess();
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
    public Json verifyInfo(TAppManhourAssess inf) {
        Json j = new Json();
        Map<String, Object> params = new HashMap<String, Object>();
        if (!PbUtils.isEmpty(inf.getId())) {
            params.put("id", inf.getId());
        }
        Long total = super.count("Select count(*) from TAppManhourAssess t where t.Id =:id ", params);

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
    public List<TAppManhourAssess> getList() {
        List<TAppManhourAssess> l = find("from TAppManhourAssess t");
        return l;
    }

    @Override
    public List<TAppManhourAssess> getListByDate(Date leftDate, Date rightDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("leftDate", leftDate);
        params.put("rightDate", rightDate);
        List<TAppManhourAssess> l = find("from TAppManhourAssess t where t.mcreatedate>:leftDate and t.mcreatedate<:rightDate", params);
        return l;
    }


}
