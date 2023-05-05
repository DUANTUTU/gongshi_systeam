package com.gt.utils;

/**
 * @功能说明：常量设置
 * @作者： liutaok
 * @创建日期：2014-2-27
 * @版本号：V1.0
 */
public class Contans {

    public static final String SESSION_BEAN = "sessionBean";

    // 编码格式
    public static final String CHARACTER_GBK = "GBK";

    // 机构级别
    public static final String INS_LVL = "ins_lvl";
    public static final String INS_LVL_TOP = "0";// 最高层机构级别
    public static final String INS_LVL_TWO = "1";// 第二级
    public static final String INS_LVL_THREE = "2";// 第三级
    public static final String INS_LVL_FOUR = "3";// 第四级

    // 系统默认密码
    public static final String DEFAULT_PASSWORD = "888888";

    // 系统用户状态
    public static final String OPER_ST = "oper_st";
    public static final String OPER_ST_NO = "1";// 禁用
    public static final String OPER_ST_CANCEL = "2";// 注销
    // 机构类型
    public static final String INS_TP = "ins_tp";

    // 岗位类型
    public static final String JOB_TP = "job_tp";

    // 入库类型
    public static final String IN_OPERATETP = "in_operateTp";

    // 出库库类型
    public static final String OUT_OPERATETP = "out_operateTp";

    // 超级管理员
    public static final String ADMIN = "admin";

    // 字典类型
    public static final String ADMINOPER = "adminOper";

    // 是否删除
    public static final String[] ISDEL = {"0", "1"};// 是否删除,0存在,1删除

    // 机构类型
    public static final String INSTP0 = "0";// 管理机构

    // 定时器类型
    public static final String TIMER_TYPE = "timer_type";

    // 定时器开关
    public static final String TIMER_STAR_CLOSED = "timer_star_closed";

    // 定时器状态
    public static final String TIMER_STATE = "timer_state";

    // 调度器状态
    public static final String QUARZ_STATE = "quarz_state";
    public static final String QUARZ_STATE_ARRY[] = {"0", "1"};// 0运行中,1暂停

    // 调度器所属组
    public static final String QUARZ_JOB_GROUP = "quarz_job_group";

    // 数据字典-性别类型
    public static final String T_TEST_SEX = "t_test_sex";


    // ////////////////////////////以上为系统设置所需要/////////////////////////////////////////////////////


    //    ------------------------业务数据字典配置----start----------------------------------
    // 委派状态
    public static final String APPOINT_STATUS = "appoint_status";
    public static final String APPOINT_STATUS_ARRY[] = {"0", "1","2"};// 0同意,1决绝,2尚未确认
    //审核状态
    public static final String CHECK_STATUS = "check_status";
    public static final String CHECK_STATUS_ARRY[] = {"0", "1","2","3","4","5","6"};// 0未提交审核,1待审核,2同意,3拒绝，4提交测试
    //是否委派is_appoint
    public static final String IS_APPOINT = "is_appoint";
    public static final String IS_APPOINT_ARRY[] = {"0", "1"};// 0未委派,1已委派
    //是否完成is_complete
    public static final String IS_COMPLETE = "is_complete";
    public static final String IS_COMPLETE_ARRY[] = {"0", "1"};// 0未完成,1已完成
    //是否关联项目	is_link_project
    public static final String IS_LINK_PROJECT = "is_link_project";
    public static final String IS_LINK_PROJECT_ARRY[] = {"0", "1"};// 0关联,1无需关联
    //职位状态job_status
    public static final String JOB_STATUS = "job_status";
    public static final String JOB_STATUS_ARRY[] = {"0", "1"};// 0正常,1停用
    //项目类型project_type
    public static final String PROJECT_TYPE = "project_type";
    public static final String PROJECT_TYPE_ARRY[] = {"0", "1"};// 0主线任务,1临时任务
    // 项目类型project_type
    public static final String JOB_TYPE = "job_type";
    public static final String JOB_TYPE_ARRY[] = {"0", "1"};// 0管理职位,1非管理职位


    //    ------------------------业务数据字典配置----end----------------------------------


}
