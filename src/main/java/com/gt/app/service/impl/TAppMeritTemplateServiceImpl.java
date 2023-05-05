package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppMeritTemplateService;
import com.gt.model.TAppMeritTemplate;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppMeritTemplateDao;

/**
 * @功能说明：绩效模板管理业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Service("tAppMeritTemplateService")
public class TAppMeritTemplateServiceImpl extends BaseServiceImpl<TAppMeritTemplate> implements ITAppMeritTemplateService {

    //定义DAO
    @Autowired
    private ITAppMeritTemplateDao TAppMeritTemplateDao;

    @Override
    public DatagridForLayUI datagrid(TAppMeritTemplate tAppMeritTemplate) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select a.dict_nm as mislinkprojectNm,b.JName as mjobidNm, " +
                " t.ID as id,t.MJobid as mjobid,t.MManhour as mmanhour,t.MTarget as mtarget,t.MDetails as mdetails,t.MIslinkproject as mislinkproject,date_format(t.MCreatedate,'%Y-%c-%d %h:%i:%s') as mcreatedate,date_format(t.MUpdatedate,'%Y-%c-%d %h:%i:%s') as mupdatedate ";
        String sql = " from t_app_merit_template t " +
                " left join t_sys_dict_cd  a on t.MIslinkproject=a.dict_cd " +
                " and  a.dict_type_cd='is_link_project' " +
                " left join t_sys_job b on t.MJobid=b.ID " +
                " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        // ID
        if (!PbUtils.isEmpty(tAppMeritTemplate.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tAppMeritTemplate.getId() + "%%");
        }
        // 职位ID
        if (!PbUtils.isEmpty(tAppMeritTemplate.getMjobid())) {
            sql += " and t.MJobid like:mjobid";
            param.put("mjobid", "%%" + tAppMeritTemplate.getMjobid() + "%%");
        }
        // 工时设定
        if (!PbUtils.isEmpty(tAppMeritTemplate.getMmanhour())) {
            sql += " and t.MManhour like:mmanhour";
            param.put("mmanhour", "%%" + tAppMeritTemplate.getMmanhour() + "%%");
        }
        // 任务目标
        if (!PbUtils.isEmpty(tAppMeritTemplate.getMtarget())) {
            sql += " and t.MTarget like:mtarget";
            param.put("mtarget", "%%" + tAppMeritTemplate.getMtarget() + "%%");
        }
        // 任务内容
        if (!PbUtils.isEmpty(tAppMeritTemplate.getMdetails())) {
            sql += " and t.MDetails like:mdetails";
            param.put("mdetails", "%%" + tAppMeritTemplate.getMdetails() + "%%");
        }
        // 是否关联项目
        if (!PbUtils.isEmpty(tAppMeritTemplate.getMislinkproject())) {
            sql += " and t.MIslinkproject like:mislinkproject";
            param.put("mislinkproject", "%%" + tAppMeritTemplate.getMislinkproject() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(tAppMeritTemplate.getMcreatedate())) {
            sql += " and t.MCreatedate like:mcreatedate";
            param.put("mcreatedate", "%%" + tAppMeritTemplate.getMcreatedate() + "%%");
        }
        // 修改时间
        if (!PbUtils.isEmpty(tAppMeritTemplate.getMupdatedate())) {
            sql += " and t.MUpdatedate like:mupdatedate";
            param.put("mupdatedate", "%%" + tAppMeritTemplate.getMupdatedate() + "%%");
        }


        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, tAppMeritTemplate.getPage(), tAppMeritTemplate.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Transactional
    @Override
    public Json add(TAppMeritTemplate inf) {
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
        String sql = "delete from t_app_merit_template  where ID in (";
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
    public Json modify(TAppMeritTemplate inf) {
        Json j = new Json();

        TAppMeritTemplate tInf = getById(TAppMeritTemplate.class, inf.getId());
        if (tInf == null) {
            j.setSuccess(false);
            j.setMsg("修改失败：找不到要修改的信息");
            return j;
        }

        // 旧对象
        TAppMeritTemplate oldObject = new TAppMeritTemplate();
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
    public Json verifyInfo(TAppMeritTemplate inf) {
        Json j = new Json();
        Map<String, Object> params = new HashMap<String, Object>();
        if (!PbUtils.isEmpty(inf.getId())) {
            params.put("id", inf.getId());
        }
        Long total = super.count("Select count(*) from TAppMeritTemplate t where t.Id =:id ", params);

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
    public List<TAppMeritTemplate> getList() {
        List<TAppMeritTemplate> l = find("from TAppMeritTemplate t");
        return l;
    }

    @Override
    public Long countEntityByMjobid(TAppMeritTemplate inf) {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "select count(1) from t_app_merit_template where MJobid=:mjobid";
        if (!PbUtils.isEmpty(inf.getMjobid())) {
            params.put("mjobid", inf.getMjobid());
        }
        Long total = countBySql(sql, params);
        return total;
    }

    @Override
    public List<Map> getEntityByOpercd(String opercd) {
        String sql = "select t.ID as id,t.MJobid as mjobid,t.MManhour as mmanhour,t.MTarget as mtarget,t.MDetails as mdetails,t.MIslinkproject as mislinkproject,date_format(t.MCreatedate,'%Y-%c-%d %h:%i:%s') as mcreatedate,date_format(t.MUpdatedate,'%Y-%c-%d %h:%i:%s') as mupdatedate " +
                " from t_app_merit_template t join t_sys_oper_inf a " +
                " where a.sjobid = t.MJobid " +
                " and a.OPER_CD=:opercd ";
        Map<String, Object> params = new HashMap<>();
        params.put("opercd", opercd);
        List<Map> list = findBySql(sql, params);
        return list;
    }
}
