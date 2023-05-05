package com.gt.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.model.TAppDebugDetail;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppManhourService;
import com.gt.model.TAppManhour;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppManhourDao;

/**
 * @功能说明：生效工时业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Service("tAppManhourService")
public class TAppManhourServiceImpl extends BaseServiceImpl<TAppManhour> implements ITAppManhourService {

    //定义DAO
    @Autowired
    private ITAppManhourDao TAppManhourDao;

    @Override
    public DatagridForLayUI datagrid(TAppManhour tAppManhour, String leftDate, String rightDate,String operNm) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select a.PName as mprojectidNm," +
                " b.PPlanname as mprojectplanidNm," +
                " c.MWorkdetails as mworkdetails,c.MManhour as mmanhour," +
                " d.OPER_NM as mopercdNm, " +
                " t.ID as id,t.MManhourconfimid as mmanhourconfimid,t.MProjectid as mprojectid,t.MProjectplanid as mprojectplanid,t.MOpercd as mopercd,t.MInscd as minscd,date_format(t.MCreatedate,'%Y-%c-%d %h:%i:%s') as mcreatedate ";
        String sql = " from t_app_manhour t " +
                " left join t_app_project a on a.ID=t.MProjectid " +
                " left join t_app_project_plan b on b.ID=t.MProjectplanid " +
                " left join t_app_manhour_confirm c on c.ID=t.MManhourconfimid " +
                " left join t_sys_oper_inf d on d.OPER_CD=t.MOpercd " +
                " left join t_sys_ins_inf e on e.UUID = d.INS_CD " +
                " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        // ID
        if (!PbUtils.isEmpty(tAppManhour.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tAppManhour.getId() + "%%");
        }
        // 填报人姓名
        if (!PbUtils.isEmpty(operNm)) {
            sql += " and d.OPER_NM like :operNm";
            param.put("operNm", "%%" +operNm+ "%%");
        }
        // 确认工时填报id
        if (!PbUtils.isEmpty(tAppManhour.getMmanhourconfimid())) {
            sql += " and t.MManhourconfimid like:mmanhourconfimid";
            param.put("mmanhourconfimid", "%%" + tAppManhour.getMmanhourconfimid() + "%%");
        }
        // 项目id
        if (!PbUtils.isEmpty(tAppManhour.getMprojectid())) {
            sql += " and t.MProjectid like:mprojectid";
            param.put("mprojectid", "%%" + tAppManhour.getMprojectid() + "%%");
        }
        // 项目里程碑ID
        if (!PbUtils.isEmpty(tAppManhour.getMprojectplanid())) {
            sql += " and t.MProjectplanid like:mprojectplanid";
            param.put("mprojectplanid", "%%" + tAppManhour.getMprojectplanid() + "%%");
        }
        // 填报人ID
        if (!PbUtils.isEmpty(tAppManhour.getMopercd())) {
            sql += " and t.MOpercd like:mopercd";
            param.put("mopercd", "%%" + tAppManhour.getMopercd() + "%%");
        }
        // 填报人部门
        if (!PbUtils.isEmpty(tAppManhour.getMinscd())) {
            sql += " and e.INS_DETAIL like:minscd";
            param.put("minscd", "%%" + tAppManhour.getMinscd() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(leftDate)) {
            sql += " and t.MCreatedate >= :leftDate";
            param.put("leftDate", leftDate);
        }
        if (!PbUtils.isEmpty(rightDate)) {
            sql += " and t.MCreatedate <= :rightDate";
            param.put("rightDate", rightDate);
        }

        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, tAppManhour.getPage(), tAppManhour.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Override
    public DatagridForLayUI datagridExcl(TAppManhour tAppManhour, String leftDate, String rightDate,String operNm) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select a.PName as mprojectidNm," +
                " b.PPlanname as mprojectplanidNm," +
                " c.MWorkdetails as mworkdetails,c.MManhour as mmanhour," +
                " d.OPER_NM as mopercdNm, " +
                " t.ID as id,t.MManhourconfimid as mmanhourconfimid,t.MProjectid as mprojectid,t.MProjectplanid as mprojectplanid,t.MOpercd as mopercd,t.MInscd as minscd,date_format(t.MCreatedate,'%Y-%c-%d %h:%i:%s') as mcreatedate ";
        String sql = " from t_app_manhour t " +
                " left join t_app_project a on a.ID=t.MProjectid " +
                " left join t_app_project_plan b on b.ID=t.MProjectplanid " +
                " left join t_app_manhour_confirm c on c.ID=t.MManhourconfimid " +
                " left join t_sys_oper_inf d on d.OPER_CD=t.MOpercd " +
                " left join t_sys_ins_inf e on e.UUID = d.INS_CD " +
                " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();
// 填报人姓名
        if (!PbUtils.isEmpty(operNm)) {
            sql += " and d.OPER_NM like :operNm";
            param.put("operNm", "%%" +operNm+ "%%");
        }
        // ID
        if (!PbUtils.isEmpty(tAppManhour.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tAppManhour.getId() + "%%");
        }
        // 确认工时填报id
        if (!PbUtils.isEmpty(tAppManhour.getMmanhourconfimid())) {
            sql += " and t.MManhourconfimid like:mmanhourconfimid";
            param.put("mmanhourconfimid", "%%" + tAppManhour.getMmanhourconfimid() + "%%");
        }
        // 项目id
        if (!PbUtils.isEmpty(tAppManhour.getMprojectid())) {
            sql += " and t.MProjectid like:mprojectid";
            param.put("mprojectid", "%%" + tAppManhour.getMprojectid() + "%%");
        }
        // 项目里程碑ID
        if (!PbUtils.isEmpty(tAppManhour.getMprojectplanid())) {
            sql += " and t.MProjectplanid like:mprojectplanid";
            param.put("mprojectplanid", "%%" + tAppManhour.getMprojectplanid() + "%%");
        }
        // 填报人ID
        if (!PbUtils.isEmpty(tAppManhour.getMopercd())) {
            sql += " and t.MOpercd like:mopercd";
            param.put("mopercd", "%%" + tAppManhour.getMopercd() + "%%");
        }
        // 填报人部门
        if (!PbUtils.isEmpty(tAppManhour.getMinscd())) {
            sql += " and e.INS_DETAIL like:minscd";
            param.put("minscd", "%%" + tAppManhour.getMinscd() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(leftDate)) {
            sql += " and t.MCreatedate >= :leftDate";
            param.put("leftDate", leftDate);
        }
        if (!PbUtils.isEmpty(rightDate)) {
            sql += " and t.MCreatedate <= :rightDate";
            param.put("rightDate", rightDate);
        }

        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param);
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Override
    public DatagridForLayUI datagridDeBugExcl(TAppDebugDetail inf, String leftDate, String rightDate, String operNm) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select t.ID as id,t.MProjectid as mprojectid,t.MProjectplanid as mprojectplanid,t.MOpercd as mopercd,t.MWorkdetails as mworkdetails,t.MManhour as mmanhour,t.MCheckstatus as mcheckstatus,t.MCreatedate as mcreatedate,t.MCheckdate as mcheckdate,t.debug_finish_date as debugFinishDate,b.PName as mprojectidNm,c.PPlanname as mprojectplanidNm " ;
        String sql = " from t_app_debug_detail t  left join t_app_project b on b.ID=t.MProjectid  left join t_app_project_plan c on c.ID=t.MProjectplanid  where 1=1 " ;

        Map<String, Object> param = new HashMap<String, Object>();
// 填报人姓名
        if (!PbUtils.isEmpty(operNm)) {
            sql += " and d.OPER_NM like :operNm";
            param.put("operNm", "%%" +operNm+ "%%");

        }
        // ID

        // 创建时间
        if (!PbUtils.isEmpty(leftDate)) {
            sql += " and t.MCreatedate >= :leftDate";
            param.put("leftDate", leftDate);
        }
        if (!PbUtils.isEmpty(rightDate)) {
            sql += " and t.MCreatedate <= :rightDate";
            param.put("rightDate", rightDate);
        }

        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param);
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Override
    public DatagridForLayUI getEntitysByOpercdDateToDate(String opercd, String leftDate, String rightDate) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sql = "SELECT b.PName as projectname,c.PPlanname as projectplanname,b.PType as projecttype,d.DICT_NM as projecttypeNm, " +
                "a.MWorkdetails as workdetails,a.MManhour as manhour,date_format(t.MCreatedate, '%Y-%c-%d %h:%i:%s' ) as createdate " +
                " from t_app_manhour t " +
                " LEFT JOIN t_app_manhour_confirm a on a.ID = t.MManhourconfimid " +
                " LEFT JOIN t_app_project b on b.ID=t.MProjectid " +
                " LEFT JOIN t_app_project_plan c on c.ID=t.MProjectplanid " +
                " Left JOIN t_sys_dict_cd d on b.PType=d.dict_cd " +
                " and d.dict_type_cd='project_type' " +
                " where a.MOpercd =:opercd " +
                " and t.MCreatedate >:leftDate " +
                " and t.MCreatedate <:rightDate";
        Map<String, Object> params = new HashMap<>();
        params.put("opercd", opercd);
        params.put("leftDate", leftDate);
        params.put("rightDate", rightDate);
        List<Map> list = findBySql(sql, params);
        grid.setCount(Long.valueOf(list.size()));
        grid.setData(list);
        return grid;
    }

    @Transactional
    @Override
    public Json add(TAppManhour inf) {
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
        String sql = "delete from t_app_manhour  where ID in (";
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
    public Json modify(TAppManhour inf) {
        Json j = new Json();

        TAppManhour tInf = getById(TAppManhour.class, inf.getId());
        if (tInf == null) {
            j.setSuccess(false);
            j.setMsg("修改失败：找不到要修改的信息");
            return j;
        }

        // 旧对象
        TAppManhour oldObject = new TAppManhour();
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
    public Json verifyInfo(TAppManhour inf) {
        Json j = new Json();
        Map<String, Object> params = new HashMap<String, Object>();
        if (!PbUtils.isEmpty(inf.getId())) {
            params.put("id", inf.getId());
        }
        Long total = super.count("Select count(*) from TAppManhour t where t.Id =:id ", params);

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
    public List<TAppManhour> getList() {
        List<TAppManhour> l = find("from TAppManhour t");
        return l;
    }

    @Override
    public DatagridForLayUI getProPlanManhourByProId(String projectid) {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sql = "select t.MProjectid as mprojectid,t.MProjectplanid as mprojectplanid,d.OPER_NM as operNm,b.PPlanname as pplanname,sum(c.MManhour) as mmanhourSum" +
                " from t_app_manhour t " +
                " LEFT JOIN t_app_project a ON a.ID = t.MProjectid " +
                " LEFT JOIN t_app_project_plan b ON b.ID = t.MProjectplanid" +
                " LEFT JOIN t_app_manhour_confirm c ON c.ID = t.MManhourconfimid" +
                " LEFT JOIN t_sys_oper_inf d ON d.OPER_CD = t.MOpercd " +
                " where 1=1 and t.MProjectid=:projectid " +
                " GROUP BY t.MOpercd,t.MProjectplanid " +
                " ORDER BY b.PPlanorder asc ";
        Map<String, Object> params = new HashMap<>();
        params.put("projectid", projectid);
        List<Map> list = findBySql(sql, params);

        grid.setCount(Long.valueOf(list.size()));
        grid.setData(list);

        return grid;
    }

    @Override
    public Long getSumManhourByProPlanId(String projectplanid) {
        String sql = "select sum(c.MManhour) " +
                " from t_app_manhour t " +
                " LEFT JOIN t_app_project a ON a.ID = t.MProjectid " +
                " LEFT JOIN t_app_project_plan b ON b.ID = t.MProjectplanid" +
                " LEFT JOIN t_app_manhour_confirm c ON c.ID = t.MManhourconfimid" +
                " LEFT JOIN t_sys_oper_inf d ON d.OPER_CD = t.MOpercd " +
                " where 1=1 and t.MProjectplanid=:projectplanid ";
        Map<String, Object> params = new HashMap<>();
        params.put("projectplanid", projectplanid);

        return countBySql(sql, params);
    }

    @Override
    public Long getSumManhourByProId(String projectid) {
        String sql = "select sum(c.MManhour) " +
                " from t_app_manhour t " +
                " LEFT JOIN t_app_project a ON a.ID = t.MProjectid " +
                " LEFT JOIN t_app_project_plan b ON b.ID = t.MProjectplanid" +
                " LEFT JOIN t_app_manhour_confirm c ON c.ID = t.MManhourconfimid" +
                " LEFT JOIN t_sys_oper_inf d ON d.OPER_CD = t.MOpercd " +
                " where 1=1 and t.MProjectid=:projectid ";
        Map<String, Object> params = new HashMap<>();
        params.put("projectid", projectid);

        return countBySql(sql, params);
    }

    @Override
    public List<Map> getPaperByDate(String leftDate, String rightDate) {
        String sql = "SELECT sum(a.MManhour) as manhour,b.PType as projecttype,a.MOpercd as opercd " +
                " from t_app_manhour t " +
                " LEFT JOIN t_app_manhour_confirm a on a.ID = t.MManhourconfimid " +
                " LEFT JOIN t_app_project b on b.ID=t.MProjectid " +
                " where t.MCreatedate > :leftDate and t.MCreatedate < :rightDate" +
                " GROUP BY a.MOpercd,b.PType ";
        Map<String, Object> params = new HashMap<>();
        params.put("leftDate", leftDate);
        params.put("rightDate", rightDate);
        List<Map> list = findBySql(sql, params);
        return list;
    }

    @Override
    public DatagridForLayUI getProSumManhourListByOpercdDate(String opercd, String leftDate, String rightDate) {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sql = "SELECT b.PName as projectname,b.PType as projecttype,d.DICT_NM as projecttypeNm," +
                " a.MManhour as manhour,date_format(t.MCreatedate, '%Y-%c-%d %h:%i:%s' ) as createdate " +
                " from t_app_manhour t " +
                " LEFT JOIN t_app_manhour_confirm a on a.ID = t.MManhourconfimid " +
                " LEFT JOIN t_app_project b on b.ID=t.MProjectid " +
                " Left JOIN t_sys_dict_cd d on b.PType=d.dict_cd and d.dict_type_cd='project_type' " +
                " where a.MOpercd =:opercd " +
                " and t.MCreatedate >:leftDate " +
                " and t.MCreatedate <:rightDate" +
                " GROUP BY t.MProjectid ";
        Map<String, Object> params = new HashMap<>();
        params.put("opercd", opercd);
        params.put("leftDate", leftDate);
        params.put("rightDate", rightDate);

        List<Map> list = findBySql(sql, params);
        grid.setCount(Long.valueOf(list.size()));
        grid.setData(list);
        return grid;
    }

    @Override
    public List<Map> getProManhour(String projectId) {
        String sql = "select PPlanname as planName,PPlanmanhour as planHour " +
                "from t_app_project_plan  " +
                "where PProjectid=:projectId";
        Map<String, Object> param = new HashMap<>();
        param.put("projectId", projectId);
        List<Map> list = findBySql(sql, param);
        return list;
    }

    @Override
    public List<Map> getFinshProManhour(String projectId) {
        String sql = "select b.PPlanname as planName,sum(a.MManhour) as finshHour " +
                " from t_app_manhour t  " +
                " left join t_app_manhour_confirm a on a.ID = t.MManhourconfimid " +
                " left join t_app_project_plan b on a.MProjectplanid = b.ID " +
                " where t.MProjectid = :projectId " +
                " group by t.MProjectplanid";

        Map<String, Object> param = new HashMap<>();
        param.put("projectId", projectId);
        List<Map> list = findBySql(sql, param);
        return list;
    }

    @Override
    public List<Map> getProHourAndFinshHour(String projectId) {
        String sql = "select t.PPlanname as planName,t.PPlanmanhour as planHour,sum(b.MManhour) as finshHour " +
                "from t_app_project_plan t  " +
                "left join t_app_manhour a on a.MProjectplanid = t.ID " +
                "left join t_app_manhour_confirm b on a.MManhourconfimid = b.ID " +
                "where t.PProjectid = :projectId " +
                " group by t.ID";

        Map<String, Object> param = new HashMap<>();
        param.put("projectId", projectId);
        List<Map> list = findBySql(sql, param);
        return list;
    }
}
