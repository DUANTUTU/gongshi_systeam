package com.gt.app.service.impl;

import com.gt.app.dao.ITAppManhourConfirmDao;
import com.gt.app.service.ITAppManhourConfirmService;
import com.gt.model.TAppManhourConfirm;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.MyBeanUtils;
import com.gt.utils.PbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @功能说明：工时提报业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Service("tAppManhourConfirmService")
public class TAppManhourConfirmServiceImpl extends BaseServiceImpl<TAppManhourConfirm> implements ITAppManhourConfirmService {

    //定义DAO
    @Autowired
    private ITAppManhourConfirmDao TAppManhourConfirmDao;

    @Override
    public DatagridForLayUI datagrid(TAppManhourConfirm tAppManhourConfirm, String createProOpercd, String insId, String jobType) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select a.dict_nm as mcheckstatusNm,b.PName as mprojectidNm,c.PPlanname as mprojectplanidNm,d.OPER_NM as mopercdNm, " +
                " t.ID as id,t.MProjectid as mprojectid,t.MProjectplanid as mprojectplanid,t.MOpercd as mopercd,t.MWorkdetails as mworkdetails,t.MManhour as mmanhour,t.MCheckstatus as mcheckstatus,date_format(t.MCreatedate,'%Y-%c-%d %h:%i:%s') as mcreatedate,date_format(t.MCheckdate,'%Y-%c-%d %h:%i:%s') as mcheckdate,t.MRemark as mremark,t.debug_content as debugContent,t.debug_id as debugId,t.debug_finish_date as debugFinishDate,t.debug_leave as debugLeave ";
        String sql = " from t_app_manhour_confirm t " +
                " left join t_sys_dict_cd a on t.MCheckstatus=a.dict_cd " +
                " and  a.dict_type_cd='check_status' " +
                " left join t_app_project b on b.ID=t.MProjectid " +
                " left join t_app_project_plan c on c.ID=t.MProjectplanid " +
                " left join t_sys_oper_inf d on d.OPER_CD=t.MOpercd " +
                " left join t_sys_ins_inf e on e.UUID = d.INS_CD " +
                " left join t_sys_job f on f.ID = d.sjobid " +
                " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        // ID
        if (!PbUtils.isEmpty(tAppManhourConfirm.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tAppManhourConfirm.getId() + "%%");
        }
        // insId  and t.MOpercd not in ('" + createProOpercd + "')    && "0".equals(jobType)
        if (!PbUtils.isEmpty(insId)) {
            sql += " and e.INS_DETAIL like:insId ";
            param.put("insId", "%%" + insId + "%%");
        }
        //根据项目创建人查询
//        if (!PbUtils.isEmpty(createProOpercd)) {
//            sql += " and b.POpercd =:createProOpercd";
//            param.put("createProOpercd", createProOpercd);
//        }
        // 项目ID
        if (!PbUtils.isEmpty(tAppManhourConfirm.getMprojectid())) {
            sql += " and t.MProjectid like:mprojectid";
            param.put("mprojectid", "%%" + tAppManhourConfirm.getMprojectid() + "%%");
        }
        // 项目里程碑ID
        if (!PbUtils.isEmpty(tAppManhourConfirm.getMprojectplanid())) {
            sql += " and t.MProjectplanid like:mprojectplanid";
            param.put("mprojectplanid", "%%" + tAppManhourConfirm.getMprojectplanid() + "%%");
        }
        // 人员id
        if (!PbUtils.isEmpty(tAppManhourConfirm.getMopercd())) {
            sql += " and t.MOpercd like:mopercd";
            param.put("mopercd", "%%" + tAppManhourConfirm.getMopercd() + "%%");
        }
        // 工作内容
        if (!PbUtils.isEmpty(tAppManhourConfirm.getMworkdetails())) {
            sql += " and t.MWorkdetails like:mworkdetails";
            param.put("mworkdetails", "%%" + tAppManhourConfirm.getMworkdetails() + "%%");
        }
        // 工时
        if (!PbUtils.isEmpty(tAppManhourConfirm.getMmanhour())) {
            sql += " and t.MManhour like:mmanhour";
            param.put("mmanhour", "%%" + tAppManhourConfirm.getMmanhour() + "%%");
        }
        // 审核状态
        if (!PbUtils.isEmpty(tAppManhourConfirm.getMcheckstatus())) {
            sql += " and t.MCheckstatus like:mcheckstatus";
            param.put("mcheckstatus", "%%" + tAppManhourConfirm.getMcheckstatus() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(tAppManhourConfirm.getMcreatedate())) {
            sql += " and t.MCreatedate like:mcreatedate";
            param.put("mcreatedate", "%%" + tAppManhourConfirm.getMcreatedate() + "%%");
        }
        // 审核时间
        if (!PbUtils.isEmpty(tAppManhourConfirm.getMcheckdate())) {
            sql += " and t.MCheckdate like:mcheckdate";
            param.put("mcheckdate", "%%" + tAppManhourConfirm.getMcheckdate() + "%%");
        }
        // 拒绝理由
        if (!PbUtils.isEmpty(tAppManhourConfirm.getMremark())) {
            sql += " and t.MRemark like:mremark";
            param.put("mremark", "%%" + tAppManhourConfirm.getMremark() + "%%");
        }


        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, tAppManhourConfirm.getPage(), tAppManhourConfirm.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Transactional
    @Override
    public Json add(TAppManhourConfirm inf) {
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
        String sql = "delete from t_app_manhour_confirm  where ID in (";
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
    public Json modify(TAppManhourConfirm inf) {
        Json j = new Json();

        TAppManhourConfirm tInf = getById(TAppManhourConfirm.class, inf.getId());
        if (tInf == null) {
            j.setSuccess(false);
            j.setMsg("修改失败：找不到要修改的信息");
            return j;
        }

        // 旧对象
        TAppManhourConfirm oldObject = new TAppManhourConfirm();
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
    public Json verifyInfo(TAppManhourConfirm inf) {
        Json j = new Json();
        Map<String, Object> params = new HashMap<String, Object>();
        if (!PbUtils.isEmpty(inf.getId())) {
            params.put("id", inf.getId());
        }
        Long total = super.count("Select count(*) from TAppManhourConfirm t where t.Id =:id ", params);

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
    public List<TAppManhourConfirm> getList() {
        List<TAppManhourConfirm> l = find("from TAppManhourConfirm t");
        return l;
    }

    @Override
    public Long queryAllManhourConfirm(String insId, String operCd) {
        Map<String, Object> param = new HashMap<>();
        String sql = "select sum(MManhour) as num " +
                "from t_app_manhour_confirm t " +
                "left join t_sys_oper_inf a on a.OPER_CD = t.MOpercd " +
                "left join t_sys_ins_inf b on a.INS_CD = b.UUID " +
                "where 1=1 ";
        // 人员ID
        if (!PbUtils.isEmpty(operCd)) {
            sql += "and t.MOpercd = :operCd ";
            param.put("operCd", operCd);
        }
        if (!PbUtils.isEmpty(insId)) {
            sql += "and b.INS_DETAIL like :insId ";
            param.put("insId", "%%" + insId + "%%");
        }
        List<Map> list = findBySql(sql, param);

        Long num = list.get(0).get("num") == null ? 0L : Long.parseLong(list.get(0).get("num").toString());

        return num;
    }

    @Override
    public List<Map> echartsAccount7DayManhour(TAppManhourConfirm inf, Date leftDate, Date rightDate) {
        String sql = "select sum(t.MManhour) as manhour, a.OPER_NM as operNm,t.MOpercd as operCd " +
                " from t_app_manhour_confirm t " +
                " left join t_sys_oper_inf a on a.OPER_CD = t.MOpercd " +
                " where 1=1 ";
        Map<String, Object> param = new HashMap<>();
        if (!PbUtils.isEmpty(inf.getMcheckstatus())) {
            sql += " and t.MCheckstatus = :checkStatus ";
            param.put("checkStatus", inf.getMcheckstatus());
        }
        if (!PbUtils.isEmpty(leftDate)) {
            sql += " and t.MCreatedate > :leftDate ";
            param.put("leftDate", leftDate);
        }
        if (!PbUtils.isEmpty(rightDate)) {
            sql += " and t.MCreatedate < :rightDate ";
            param.put("rightDate", rightDate);
        }
        sql += " group by t.MOpercd ";
        List<Map> list = findBySql(sql, param);
        return list;
    }
}
