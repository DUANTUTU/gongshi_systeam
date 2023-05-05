package com.gt.quartzService;

import com.gt.app.service.ITAppManhourService;
import com.gt.app.service.ITAppProjectAssessService;
import com.gt.app.service.ITAppProjectPlanService;
import com.gt.app.service.ITAppProjectService;
import com.gt.model.TAppManhour;
import com.gt.model.TAppProject;
import com.gt.model.TAppProjectAssess;
import com.gt.model.TAppProjectPlan;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Component
public class GenerateProjectAssess {
    private Logger logger = Logger.getLogger(GenerateProjectAssess.class);
    private ITAppProjectAssessService tAppProjectAssessService;

    public ITAppProjectAssessService getTAppProjectAssessService() {
        return tAppProjectAssessService;
    }

    @Autowired
    public void setTAppProjectAssessService(ITAppProjectAssessService tAppProjectAssessService) {
        this.tAppProjectAssessService = tAppProjectAssessService;
    }

    private ITAppManhourService tAppManhourService;

    public ITAppManhourService getTAppManhourService() {
        return tAppManhourService;
    }

    @Autowired
    public void setTAppManhourService(ITAppManhourService tAppManhourService) {
        this.tAppManhourService = tAppManhourService;
    }

    private ITAppProjectService tAppProjectService;

    public ITAppProjectService getTAppProjectService() {
        return tAppProjectService;
    }

    @Autowired
    public void setTAppProjectService(ITAppProjectService tAppProjectService) {
        this.tAppProjectService = tAppProjectService;
    }

    private ITAppProjectPlanService tAppProjectPlanService;

    public ITAppProjectPlanService getTAppProjectPlanService() {
        return tAppProjectPlanService;
    }

    @Autowired
    public void setTAppProjectPlanService(ITAppProjectPlanService tAppProjectPlanService) {
        this.tAppProjectPlanService = tAppProjectPlanService;
    }


    // 静态初使化当前类
    @Autowired
    private static GenerateProjectAssess createChildTable;

    // 通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    @PostConstruct
    public void init() {
        createChildTable = this;
        createChildTable.tAppProjectAssessService = this.tAppProjectAssessService;       // 初使化时将已静态化的Service实例化
        createChildTable.tAppManhourService = this.tAppManhourService;       // 初使化时将已静态化的Service实例化
        createChildTable.tAppProjectService = this.tAppProjectService;       // 初使化时将已静态化的Service实例化
        createChildTable.tAppProjectPlanService = this.tAppProjectPlanService;       // 初使化时将已静态化的Service实例化
    }

    public void projectAssess() throws Exception {
        Date date = new Date();
        List<TAppProject> list = createChildTable.tAppProjectService.getListByAppoint();
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        for (TAppProject tAppProject : list) {
            TAppProjectAssess tAppProjectAssess = new TAppProjectAssess();
            tAppProjectAssess.setId(PbUtils.getUUID());
            tAppProjectAssess.setPprojectid(tAppProject.getId());
            List<TAppProjectPlan> listplan = createChildTable.tAppProjectPlanService.getListPlanByProjectid(tAppProject.getId(),null);
            Integer completePlanHourSum = 0;
            Integer notCompletePlanHourSum = 0;
            if (Contans.IS_COMPLETE_ARRY[1].equals(tAppProject.getPiscomplete())){
                tAppProjectAssess.setPunfinishhour(0);
                tAppProjectAssess.setPcompleterate(1f);
            }else{
                for (TAppProjectPlan tAppProjectPlan : listplan) {
                    if (Contans.IS_COMPLETE_ARRY[1].equals(tAppProjectPlan.getPiscomplete())) {
                        completePlanHourSum += tAppProjectPlan.getPplanmanhour();
                    } else {
                        notCompletePlanHourSum += tAppProjectPlan.getPplanmanhour();
                    }
                }
                tAppProjectAssess.setPunfinishhour(notCompletePlanHourSum);
                tAppProjectAssess.setPcompleterate(Float.valueOf(decimalFormat.format((float) completePlanHourSum / tAppProject.getPmanhourplan())));
            }
            Long realCompleteHour = createChildTable.tAppManhourService.getSumManhourByProId(tAppProject.getId());
            tAppProjectAssess.setPfinishhour(realCompleteHour.intValue());
            tAppProjectAssess.setPfinishhourrate(Float.valueOf(decimalFormat.format((float) realCompleteHour / tAppProject.getPmanhourplan())));
            tAppProjectAssess.setPcreatedate(date);
            createChildTable.tAppProjectAssessService.add(tAppProjectAssess);
        }

    }
}
