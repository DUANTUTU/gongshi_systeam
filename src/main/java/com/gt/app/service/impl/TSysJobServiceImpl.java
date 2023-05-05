package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITSysJobService;
import com.gt.model.TSysJob;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITSysJobDao;

/**
 * @功能说明：职位管理业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Service("tSysJobService")
public class TSysJobServiceImpl extends BaseServiceImpl<TSysJob> implements ITSysJobService {

    //定义DAO
    @Autowired
    private ITSysJobDao TSysJobDao;

    @Override
    public DatagridForLayUI datagrid(TSysJob tSysJob) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select b.dict_nm as jstatusNm,c.dict_nm as jtypeNm , " +
                " t.ID as id,t.JName as jname,t.JType as jtype,t.JStatus as jstatus,date_format(t.JCreatedate,'%Y-%c-%d %h:%i:%s') as jcreatedate ";
        String sql = " from t_sys_job t ";
        sql += " left join t_sys_dict_cd  b on t.JStatus=b.dict_cd";
        sql += " and b.dict_type_cd='job_status'";//职位状态-数据字典
        sql += " left join t_sys_dict_cd  c on t.JType=c.dict_cd";
        sql += " and c.dict_type_cd='job_type'";//职位状态-数据字典
        sql += " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        // ID
        if (!PbUtils.isEmpty(tSysJob.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tSysJob.getId() + "%%");
        }
        // 职位名称
        if (!PbUtils.isEmpty(tSysJob.getJname())) {
            sql += " and t.JName like:jname";
            param.put("jname", "%%" + tSysJob.getJname() + "%%");
        }
        // 职位类型
        if (!PbUtils.isEmpty(tSysJob.getJtype())) {
            sql += " and t.JType like:jtype";
            param.put("jtype", "%%" + tSysJob.getJtype() + "%%");
        }
        // 职位状态
        if (!PbUtils.isEmpty(tSysJob.getJstatus())) {
            sql += " and t.JStatus like:jstatus";
            param.put("jstatus", "%%" + tSysJob.getJstatus() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(tSysJob.getJcreatedate())) {
            sql += " and t.JCreatedate like:jcreatedate";
            param.put("jcreatedate", "%%" + tSysJob.getJcreatedate() + "%%");
        }


        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, tSysJob.getPage(), tSysJob.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Transactional
    @Override
    public Json add(TSysJob inf) {
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
        String sql = "delete from t_sys_job  where ID in (";
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
    public Json modify(TSysJob inf) {
        Json j = new Json();

        TSysJob tInf = getById(TSysJob.class, inf.getId());
        if (tInf == null) {
            j.setSuccess(false);
            j.setMsg("修改失败：找不到要修改的信息");
            return j;
        }

        // 旧对象
        TSysJob oldObject = new TSysJob();
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
    public Json verifyInfo(TSysJob inf) {
        Json j = new Json();
        Map<String, Object> params = new HashMap<String, Object>();
        if (!PbUtils.isEmpty(inf.getId())) {
            params.put("id", inf.getId());
        }
        Long total = super.count("Select count(*) from TSysJob t where t.Id =:id ", params);

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
    public List<TSysJob> getList() {
        List<TSysJob> l = find("from TSysJob t");
        return l;
    }

    @Override
    public TSysJob getEntityById(String Id) {
        TSysJob tInf = getById(TSysJob.class, Id);
        return tInf;
    }
}
