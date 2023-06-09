package com.gt.sys.controller;

import com.gt.app.service.ITSysJobService;
import com.gt.model.*;
import com.gt.pageModel.*;
import com.gt.servlet.*;
import com.gt.sys.service.*;
import com.gt.utils.*;
import org.slf4j.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

/**
 * @功能说明：系统用户
 * @作者： liutaok
 * @创建日期：2018-09-24
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/operInf")
public class OperInfController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(OperInfController.class);

    private IRoleOperService roleOperService;// 角色人员信息
    public IRoleOperService getRoleOperService() {
        return roleOperService;
    }

    @Autowired
    public void setRoleOperService(IRoleOperService roleOperService) {
        this.roleOperService = roleOperService;
    }

    private IOperInfService operInfService;// 人员信息

    public IOperInfService getOperInfService() {
        return operInfService;
    }

    @Autowired
    public void setOperInfService(IOperInfService operInfService) {
        this.operInfService = operInfService;
    }
    private ITSysJobService tSysJobService;

    public ITSysJobService getTSysJobService() {
        return  tSysJobService;
    }

    @Autowired
    public void setTSysJobService(ITSysJobService tSysJobService) {
        this.tSysJobService = tSysJobService;
    }


    /**
     * 获取datagrid数据
     */
    @RequestMapping("/datagrid")
    @ResponseBody
    public DatagridForLayUI datagrid(OperInf operInf, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            // 系统登录用户
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
            String insCd = sessionInfo.getOperInf().getInsUuid();
            boolean v = false;
            // 判断是否为超级管理员
            if (Contans.ADMIN.equals(sessionInfo.getOperInf().getOperCd())) {
                v = true;
            }
            datagrid = operInfService.datagrid(operInf, insCd, v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datagrid;
    }

    /**
     * 新增
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(OperInf operInf, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            operInf.setRecCrtTs(PbUtils.getCurrentTime());// 记录创建时间
            MD5 md5 = new MD5();
            operInf.setOperPwd(md5.getMD5Str(operInf.getOperCd() + Contans.DEFAULT_PASSWORD));// 系统默认密码,
            // 用户名+密码
            j = operInfService.verifyInfo(operInf);
            if (j.isSuccess()) {
                OperInf inf = operInfService.add(operInf);
                j.setMsg("新增成功");
                j.setObj(inf);
                j.setSuccess(true);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("添加失败:" + e.getMessage());
        }
        // 添加系统日志
        writeSysLog(j, operInf, request, session);

        return j;
    }

    /**
     * 删除
     */
    @RequestMapping("/remove")
    @ResponseBody
    public Json remove(OperInf operInf, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            operInfService.remove(operInf.getOperCd());
            j.setSuccess(true);
            j.setMsg("删除成功！");
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("删除失败:" + e.getMessage());
        }

        // 添加系统日志
        writeSysLog(j, operInf, request, session);

        return j;
    }

    /**
     * 修改
     */
    @RequestMapping("/modify")
    @ResponseBody
    public Json modify(OperInf operInf, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            operInf.setRecUpdTs(PbUtils.getCurrentTime());// 记录修改时间
            j = operInfService.modify(operInf);
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("修改失败:" + e.getMessage());
        }

        // 添加系统日志
        writeSysLog(j, operInf, request, session);

        return j;
    }

    /**
     * 密码重置
     */
    @RequestMapping("/redoPwd")
    @ResponseBody
    public Json redoPwd(OperInf operInf, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        try {
            j = operInfService.redoPwd(operInf.getOperCd());
        } catch (Exception e) {
            logger.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("密码重置失败:" + e.getMessage());
        }
        // 添加系统日志
        writeSysLog(j, operInf, request, session);

        return j;
    }

    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping("/execute")
    @ResponseBody
    public Json execute(OperInf operInf, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Json j = new Json();
        String codeSession = null;

        // 获取验证码
        if (session.getAttribute(RandomValidateCode.RANDOMCODEKEY) != null) {
            codeSession = session.getAttribute(RandomValidateCode.RANDOMCODEKEY).toString();
        }

        String code = operInf.getCode() == null ? "" : operInf.getCode();
        codeSession = codeSession == null ? "" : codeSession;

        // 判断验证码是否正确
        if (code.equals(codeSession)) {
            j = operInfService.userLogin(operInf);
            if (j.isSuccess()) {// ////////去掉
                try {
                    TSysOperInf tInf = operInfService.getById(TSysOperInf.class, operInf.getOperCd());
                    if (tInf != null) {
                        OperInf operInfObj = new OperInf();
                        BeanUtils.copyProperties(tInf, operInfObj);

                        // 设置用户所属机构
                        if (tInf.getTSysInsInf() != null) {
                            if (!PbUtils.isEmpty(tInf.getTSysInsInf().getUuid())) {
                                operInfObj.setInsUuid(tInf.getTSysInsInf().getUuid());// 设置用户所属机构UUID
                            }
                            if (!PbUtils.isEmpty(tInf.getTSysInsInf().getInsCd())) {
                                operInfObj.setInsCd(tInf.getTSysInsInf().getInsCd());// 设置用户所属机构编号
                            }
                            if (!PbUtils.isEmpty(tInf.getTSysInsInf().getInsNm())) {
                                operInfObj.setInsCdNm(tInf.getTSysInsInf().getInsNm());// 设置用户所属机构名称
                            }
                        }

                        // 添加角色信息
                        List<RoleInf> listRoleInf = new ArrayList<RoleInf>();// 角色信息
                        List<TSysRoleOper> roleOpers = roleOperService.getList(operInfObj.getOperCd());

                        for (TSysRoleOper tSysRoleOper : roleOpers) {
                            RoleInf roleInf = new RoleInf();
                            roleInf.setRoleCd(tSysRoleOper.getTSysRoleInf().getRoleCd());
                            roleInf.setRoleNm(tSysRoleOper.getTSysRoleInf().getRoleNm());
                            //roleInf.setJumpUrl(tSysRoleOper.getTSysRoleInf().getJumpUrl());
                            //roleInf.setUrlNm(tSysRoleOper.getTSysRoleInf().getUrlNm());
                            listRoleInf.add(roleInf);
                        }
                        operInfObj.setRoleInfs(listRoleInf);

                        // 添加用户信息到session
                        SessionInfo sessionInfo = new SessionInfo();
                        sessionInfo.setOperInf(operInfObj);

                        MD5 md5 = new MD5();
                        String password = md5.getMD5Str(sessionInfo.getOperInf().getOperCd() + Contans.DEFAULT_PASSWORD);
                        if (password.equals(operInfObj.getOperPwd())) {

                            sessionInfo.setDefaultPwd(true);// 用户使用系统默认密码，登录时建议修改
                        }
                        TSysJob tSysJob = tSysJobService.getEntityById(sessionInfo.getOperInf().getSjobid());
                        sessionInfo.getOperInf().setSjobType(tSysJob.getJtype());

                        // 设置session
                        session.setAttribute(Contans.SESSION_BEAN, sessionInfo);
                        //j.setOldObj(getListRoleInfs(session));
                    } else {
                        session.setAttribute(Contans.SESSION_BEAN, new SessionInfo());
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        } else {
            j.setSuccess(false);
            j.setMsg("验证码不正确");
        }

        // 新增系统日志
        if (j.isSuccess()) {
            j.setMsg("登录成功");
            writeSysLog(j, operInf, "用户登录", "系统登录", request, session);
        }

        return j;
    }

    /**
     * 页面跳转
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "Login";
    }

    /**
     * 页面跳转
     *
     * @return
     */
    @RequestMapping("/goPage")
    public String goPage(String url) {
        return url;
    }

    /**
     * 密码修改
     */
    @RequestMapping("/changepwd")
    @ResponseBody
    public Json changepwd(OperInf operInf, HttpServletRequest request, HttpSession session) {
        Json j = new Json();
        OperInf oper = getUser(request);
        TSysOperInf sysOperInf = operInfService.getById(TSysOperInf.class, oper.getOperCd());
        MD5 md5 = new MD5();
        String oldPwd = "";// 原始密码

        if (sysOperInf != null) {
            if (!PbUtils.isEmpty(sysOperInf.getOperCd()) && !PbUtils.isEmpty(operInf.getOperPwd())) {
                oldPwd = md5.getMD5Str(sysOperInf.getOperCd() + operInf.getOldOperPwd());
            }

            if (!sysOperInf.getOperPwd().equals(oldPwd)) {
                j.setSuccess(false);
                j.setMsg("原密码不正确!");
            } else {
                operInf.setOperCd(sysOperInf.getOperCd());
                j = operInfService.changepwd(operInf);
            }
        } else {
            j.setSuccess(false);
            j.setMsg("密码修改失败：无法获取登录人信息");
        }

        // 添加系统日志
        writeSysLog(j, operInf, request, session);
        return j;
    }

    /**
     * 用户注销
     *
     * @return
     */
    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (session != null) {
            session.removeAttribute(Contans.SESSION_BEAN);
            session.invalidate();
        }
        request.getRequestDispatcher("/Login.jsp").forward(request, response);
//        request.getRequestDispatcher("/Login.jsp").forward(request, response);
//        return "Login";
    }

    /**
     * 用户注销
     *
     * @return
     */
    @RequestMapping("/getRoles")
    @ResponseBody
    public List<RoleInf> getRoles(HttpSession session) {
        List<RoleInf> roleInfs = getListRoleInfs(session);
        return roleInfs;
    }

    private List<RoleInf> getListRoleInfs(HttpSession session) {
        Map<String, RoleInf> maps = new HashMap<String, RoleInf>();
        List<RoleInf> roleInfs = new ArrayList<RoleInf>();
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
        if (session.getAttribute(Contans.SESSION_BEAN) != null) {
            if (sessionInfo != null && sessionInfo.getOperInf() != null && sessionInfo.getOperInf().getRoleInfs() != null) {
                List<RoleInf> list = sessionInfo.getOperInf().getRoleInfs();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUrlNm() != null && list.get(i).getUrlNm().length() > 0) {
                        maps.put(list.get(i).getUrlNm(), list.get(i));
                    }
                }
            }

            if (maps != null && !maps.isEmpty()) {
                for (String key : maps.keySet()) {
                    RoleInf roleInf = new RoleInf();
                    roleInf = maps.get(key);
                    roleInfs.add(roleInf);
                }
            }
        }

        return roleInfs;
    }
    /**
     * 首页-人员总数
     * */
    @RequestMapping("/queryAllOperNum")
    @ResponseBody
    public DatagridForLayUI queryAllOperNum(OperInf operInf, HttpSession session) {
        DatagridForLayUI datagrid = null;
        try {
            datagrid = operInfService.datagrid(operInf, "1", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datagrid;
    }

}
