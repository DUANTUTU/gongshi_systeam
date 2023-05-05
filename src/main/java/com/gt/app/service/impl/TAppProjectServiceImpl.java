package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.utils.Contans;
import com.gt.utils.MyBeanUtils;
import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppProjectService;
import com.gt.model.TAppProject;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppProjectDao;

/**
 * @功能说明：项目计划业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Service("tAppProjectService")
public class TAppProjectServiceImpl extends BaseServiceImpl<TAppProject> implements ITAppProjectService {

    //定义DAO
    @Autowired
    private ITAppProjectDao TAppProjectDao;

    @Override
    public DatagridForLayUI datagrid(TAppProject tAppProject, String insUuid) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select a.dict_nm as ptypeNm,b.dict_nm as pisappointNm,c.dict_nm as piscompleteNm,d.OPER_NM as popercdNm,e.INS_NM as pinscdNm, " +
                " t.ID as id,t.PName as pname,t.PDetails as pdetails,t.PManhourplan as pmanhourplan,t.PType as ptype,t.POpercd as popercd,t.PInscd as pinscd,t.PManhour as pmanhour,t.PIsappoint as pisappoint,t.PIscomplete as piscomplete,date_format(t.PCreatedate,'%Y-%c-%d %h:%i:%s') as pcreatedate,date_format(t.PCompletedate,'%Y-%c-%d %h:%i:%s') as pcompletedate ";
        String sql = " from t_app_project t " +
                " left join t_sys_dict_cd a on t.PType=a.dict_cd " +
                " and a.dict_type_cd='project_type' " +
                " left join t_sys_dict_cd b on t.PIsappoint=b.dict_cd " +
                " and b.dict_type_cd='is_appoint' " +
                " left join t_sys_dict_cd c on t.PIscomplete=c.dict_cd " +
                " and c.dict_type_cd='is_complete' " +
                " left join t_sys_oper_inf d on t.POpercd=d.OPER_CD " +
                " left join t_sys_ins_inf e on t.PInscd=e.UUID" +
                " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        if (!PbUtils.isEmpty(insUuid)) {
            sql += " and e.INS_DETAIL like:insUuid";
            param.put("insUuid", "%%" + insUuid + "%%");
        }
        // ID
        if (!PbUtils.isEmpty(tAppProject.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tAppProject.getId() + "%%");
        }
        // 项目名称
        if (!PbUtils.isEmpty(tAppProject.getPname())) {
            sql += " and t.PName like:pname";
            param.put("pname", "%%" + tAppProject.getPname() + "%%");
        }
        // 项目描述
        if (!PbUtils.isEmpty(tAppProject.getPdetails())) {
            sql += " and t.PDetails like:pdetails";
            param.put("pdetails", "%%" + tAppProject.getPdetails() + "%%");
        }
        // 工时预估
        if (!PbUtils.isEmpty(tAppProject.getPmanhourplan())) {
            sql += " and t.PManhourplan like:pmanhourplan";
            param.put("pmanhourplan", "%%" + tAppProject.getPmanhourplan() + "%%");
        }
        // 项目类型
        if (!PbUtils.isEmpty(tAppProject.getPtype())) {
            sql += " and t.PType like:ptype";
            param.put("ptype", "%%" + tAppProject.getPtype() + "%%");
        }
        // 发起人
        if (!PbUtils.isEmpty(tAppProject.getPopercd())) {
            sql += " and t.POpercd like:popercd";
            param.put("popercd", "%%" + tAppProject.getPopercd() + "%%");
        }
        // 发起人部门
        if (!PbUtils.isEmpty(tAppProject.getPinscd())) {
            sql += " and t.PInscd like:pinscd";
            param.put("pinscd", "%%" + tAppProject.getPinscd() + "%%");
        }
        // 确认工时
        if (!PbUtils.isEmpty(tAppProject.getPmanhour())) {
            sql += " and t.PManhour like:pmanhour";
            param.put("pmanhour", "%%" + tAppProject.getPmanhour() + "%%");
        }
        // 是否委派
        if (!PbUtils.isEmpty(tAppProject.getPisappoint())) {
            sql += " and t.PIsappoint like:pisappoint";
            param.put("pisappoint", "%%" + tAppProject.getPisappoint() + "%%");
        }
        // 是否完成
        if (!PbUtils.isEmpty(tAppProject.getPiscomplete())) {
            sql += " and t.PIscomplete like:piscomplete";
            param.put("piscomplete", "%%" + tAppProject.getPiscomplete() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(tAppProject.getPcreatedate())) {
            sql += " and t.PCreatedate like:pcreatedate";
            param.put("pcreatedate", "%%" + tAppProject.getPcreatedate() + "%%");
        }
        // 完成时间
        if (!PbUtils.isEmpty(tAppProject.getPcompletedate())) {
            sql += " and t.PCompletedate like:pcompletedate";
            param.put("pcompletedate", "%%" + tAppProject.getPcompletedate() + "%%");
        }


        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, tAppProject.getPage(), tAppProject.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Override
    public DatagridForLayUI datagridProjectAssess(TAppProject tAppProject, String insUuid) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select a.dict_nm as ptypeNm,b.dict_nm as pisappointNm,c.dict_nm as piscompleteNm,d.OPER_NM as popercdNm,e.INS_NM as pinscdNm, " +
                " t.ID as id,t.PName as pname,t.PDetails as pdetails,t.PManhourplan as pmanhourplan,t.PManhour as pmanhour,CONCAT(convert(t.PManhour/t.PManhourplan*100,decimal(10,2)),'%') as pmanhourRate,t.PType as ptype,t.POpercd as popercd,t.PInscd as pinscd,t.PIsappoint as pisappoint,t.PIscomplete as piscomplete,date_format(t.PCreatedate,'%Y-%c-%d %h:%i:%s') as pcreatedate,date_format(t.PCompletedate,'%Y-%c-%d %h:%i:%s') as pcompletedate ";
        String sql = " from t_app_project t " +
                " left join t_sys_dict_cd a on t.PType=a.dict_cd " +
                " and a.dict_type_cd='project_type' " +
                " left join t_sys_dict_cd b on t.PIsappoint=b.dict_cd " +
                " and b.dict_type_cd='is_appoint' " +
                " left join t_sys_dict_cd c on t.PIscomplete=c.dict_cd " +
                " and c.dict_type_cd='is_complete' " +
                " left join t_sys_oper_inf d on t.POpercd=d.OPER_CD " +
                " left join t_sys_ins_inf e on t.PInscd=e.UUID" +
                " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        if (!PbUtils.isEmpty(insUuid)) {
            sql += " and e.INS_DETAIL like:insUuid";
            param.put("insUuid", "%%" + insUuid + "%%");
        }
        // ID
        if (!PbUtils.isEmpty(tAppProject.getId())) {
            sql += " and t.ID like:id";
            param.put("id", "%%" + tAppProject.getId() + "%%");
        }
        // 项目名称
        if (!PbUtils.isEmpty(tAppProject.getPname())) {
            sql += " and t.PName like:pname";
            param.put("pname", "%%" + tAppProject.getPname() + "%%");
        }
        // 项目描述
        if (!PbUtils.isEmpty(tAppProject.getPdetails())) {
            sql += " and t.PDetails like:pdetails";
            param.put("pdetails", "%%" + tAppProject.getPdetails() + "%%");
        }
        // 工时预估
        if (!PbUtils.isEmpty(tAppProject.getPmanhourplan())) {
            sql += " and t.PManhourplan like:pmanhourplan";
            param.put("pmanhourplan", "%%" + tAppProject.getPmanhourplan() + "%%");
        }
        // 项目类型
        if (!PbUtils.isEmpty(tAppProject.getPtype())) {
            sql += " and t.PType like:ptype";
            param.put("ptype", "%%" + tAppProject.getPtype() + "%%");
        }
        // 发起人
        if (!PbUtils.isEmpty(tAppProject.getPopercd())) {
            sql += " and t.POpercd like:popercd";
            param.put("popercd", "%%" + tAppProject.getPopercd() + "%%");
        }
        // 发起人部门
        if (!PbUtils.isEmpty(tAppProject.getPinscd())) {
            sql += " and t.PInscd like:pinscd";
            param.put("pinscd", "%%" + tAppProject.getPinscd() + "%%");
        }
        // 确认工时
        if (!PbUtils.isEmpty(tAppProject.getPmanhour())) {
            sql += " and t.PManhour like:pmanhour";
            param.put("pmanhour", "%%" + tAppProject.getPmanhour() + "%%");
        }
        // 是否委派
        if (!PbUtils.isEmpty(tAppProject.getPisappoint())) {
            sql += " and t.PIsappoint like:pisappoint";
            param.put("pisappoint", "%%" + tAppProject.getPisappoint() + "%%");
        }
        // 是否完成
        if (!PbUtils.isEmpty(tAppProject.getPiscomplete())) {
            sql += " and t.PIscomplete like:piscomplete";
            param.put("piscomplete", "%%" + tAppProject.getPiscomplete() + "%%");
        }
        // 创建时间
        if (!PbUtils.isEmpty(tAppProject.getPcreatedate())) {
            sql += " and t.PCreatedate like:pcreatedate";
            param.put("pcreatedate", "%%" + tAppProject.getPcreatedate() + "%%");
        }
        // 完成时间
        if (!PbUtils.isEmpty(tAppProject.getPcompletedate())) {
            sql += " and t.PCompletedate like:pcompletedate";
            param.put("pcompletedate", "%%" + tAppProject.getPcompletedate() + "%%");
        }


        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, tAppProject.getPage(), tAppProject.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Override
    public DatagridForLayUI datagridByOpercd(TAppProject inf, String appointOpercd) throws Exception {
        DatagridForLayUI grid = new DatagridForLayUI();
        String sqlLeft = "select f.ID as projectpersonid, f.PAppointstatus as pappointstatus,g.dict_nm as pappointstatusNm, a.dict_nm as ptypeNm,b.dict_nm as pisappointNm,c.dict_nm as piscompleteNm,d.OPER_NM as popercdNm,e.INS_NM as pinscdNm, " +
                " t.ID as id,t.PName as pname,t.PDetails as pdetails,t.PManhourplan as pmanhourplan,t.PType as ptype,t.POpercd as popercd,t.PInscd as pinscd,t.PManhour as pmanhour,t.PIsappoint as pisappoint,t.PIscomplete as piscomplete,date_format(t.PCreatedate,'%Y-%c-%d %h:%i:%s') as pcreatedate,date_format(t.PCompletedate,'%Y-%c-%d %h:%i:%s') as pcompletedate ";
        String sql = " from t_app_project t " +
                " left join t_sys_dict_cd a on t.PType=a.dict_cd " +
                " and a.dict_type_cd='project_type' " +
                " left join t_sys_dict_cd b on t.PIsappoint=b.dict_cd " +
                " and b.dict_type_cd='is_appoint' " +
                " left join t_sys_dict_cd c on t.PIscomplete=c.dict_cd " +
                " and c.dict_type_cd='is_complete' " +
                " left join t_sys_oper_inf d on t.POpercd=d.OPER_CD " +
                " left join t_sys_ins_inf e on t.PInscd=e.UUID " +
                " left join t_app_project_person f on t.ID=f.PProjectid " +
                " left join t_sys_dict_cd g on f.PAppointstatus=g.dict_cd " +
                " and g.dict_type_cd='appoint_status' " +
                " where 1=1 ";

        Map<String, Object> param = new HashMap<String, Object>();

        if (!PbUtils.isEmpty(appointOpercd)) {
            sql += " and f.POpercd=:appointOpercd ";
            param.put("appointOpercd", appointOpercd);
        }

        String totalsql = "select count(*) " + sql;

        //排序
        sql += " order by  ID desc";

        List<Map> maps = findBySql(sqlLeft + sql, param, inf.getPage(), inf.getLimit());
        Long total = countBySql(totalsql, param);
        grid.setCount(total);
        grid.setData(maps);

        return grid;
    }

    @Transactional
    @Override
    public Json add(TAppProject inf) {
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
        String sql = "delete from t_app_project  where ID in (";
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
    public Json modify(TAppProject inf) {
        Json j = new Json();

        TAppProject tInf = getById(TAppProject.class, inf.getId());
        if (tInf == null) {
            j.setSuccess(false);
            j.setMsg("修改失败：找不到要修改的信息");
            return j;
        }

        // 旧对象
        TAppProject oldObject = new TAppProject();
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
    public Json verifyInfo(TAppProject inf) {
        Json j = new Json();
        Map<String, Object> params = new HashMap<String, Object>();
        if (!PbUtils.isEmpty(inf.getId())) {
            params.put("id", inf.getId());
        }
        Long total = super.count("Select count(*) from TAppProject t where t.Id =:id ", params);

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
    public List<TAppProject> getList() {
        List<TAppProject> l = find("from TAppProject t");
        return l;
    }

    @Override
    public List<TAppProject> getListByAppoint() {
        List<TAppProject> l = find("from TAppProject t where pisappoint=1");
        return l;
    }

    @Override
    public List<Map> getListByInsId(TAppProject tAppProject) {
        String sql = "select t.ID as id,t.PName as pname" +
                " from t_app_project t " +
                " left join t_sys_ins_inf a on a.UUID = t.PInscd " +
                " where 1=1 ";
        Map<String, Object> param = new HashMap<>();
        if (!PbUtils.isEmpty(tAppProject.getPinscd())) {
            sql += " and a.INS_DETAIL like :insId ";
            param.put("insId", "%%" + tAppProject.getPinscd() + "%%");
        }
        if (!PbUtils.isEmpty(tAppProject.getPisappoint())) {
            sql += " and t.PIsappoint = :isAppoint ";
            param.put("isAppoint", tAppProject.getPisappoint());
        }
        List<Map> l = findBySql(sql, param);
        return l;
    }

    @Override
    public List<Map> getListNoCompleteByOpercd(String opercd) {
        String sql = "select a.ID as id,a.PName as pname,a.PType as ptype " +
                " from t_app_project_person t " +
                " left join t_app_project a on t.PProjectid=a.ID " +
                " and t.POpercd=:opercd " +
                " where 1=1 and a.PIscomplete=:iscomplete ";
        Map<String, Object> params = new HashMap<>();
        params.put("opercd", opercd);
        params.put("iscomplete", Contans.IS_COMPLETE_ARRY[0]);
        List<Map> list = findBySql(sql, params);
        return list;
    }

    @Override
    public List<Map> getProjectDeptEcharts() {
        String sql = "select count(1) as allPro,sum(t.PIscomplete='1') as completePro,sum(t.PIscomplete='0') as nocompletePro,a.INS_NM as insnm " +
                "from t_app_project t " +
                "left join t_sys_ins_inf a on a.UUID=t.PInscd " +
                "where t.PIsappoint='1' " +
                "GROUP BY t.PInscd";
        List<Map> list = findBySql(sql);
        return list;
    }

    @Override
    public List<Map> getProjectDeptProportionEcharts() {
        String sql = "select count(1) as value,a.INS_NM as name " +
                "from t_app_project t " +
                "left join t_sys_ins_inf a on a.UUID=t.PInscd " +
                "where t.PIsappoint='1' " +
                " GROUP BY t.PInscd";
        List<Map> list = findBySql(sql);
        return list;
    }

    @Override
    public List<Map> getProjectManhourEcharts() {
        String sql = "select t.PManhourplan as value,t.PName as name " +
                "from t_app_project t where t.PType='0' ";
        List<Map> list = findBySql(sql);
        return list;
    }

    @Override
    public List<Map> getProjectDeptCompleteEcharts() {
        String sql = "select count(1) as value,a.INS_NM as name " +
                "from t_app_project t " +
                "left join t_sys_ins_inf a on a.UUID=t.PInscd " +
                "where t.PIsappoint='1' " +
                "and t.PIscomplete='1' " +
                " GROUP BY t.PInscd";
        List<Map> list = findBySql(sql);
        return list;
    }

    @Override
    public List<Map> getDeptPersonManhourEcharts() {
        String sql = "select sum(b.MManhour) as allManhour,c.INS_NM as insnm " +
                "from t_app_manhour t " +
                "left join t_app_project a on a.ID=t.MProjectid " +
                "left join t_app_manhour_confirm b on b.ID=t.MManhourconfimid " +
                "left join t_sys_ins_inf c on c.UUID=a.PInscd " +
                "GROUP BY a.PInscd";
        List<Map> list = findBySql(sql);
        return list;
    }
}
