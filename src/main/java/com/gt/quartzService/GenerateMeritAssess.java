package com.gt.quartzService;

import com.gt.app.service.ITAppManhourAssessService;
import com.gt.app.service.ITAppMeritAssessService;
import com.gt.app.service.ITAppMeritTemplateService;
import com.gt.model.TAppManhourAssess;
import com.gt.model.TAppMeritAssess;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class GenerateMeritAssess {
    private Logger logger = Logger.getLogger(GenerateMeritAssess.class);
    private ITAppManhourAssessService tAppManhourAssessService;

    public ITAppManhourAssessService getTAppManhourAssessService() {
        return tAppManhourAssessService;
    }

    @Autowired
    public void setTAppManhourAssessService(ITAppManhourAssessService tAppManhourAssessService) {
        this.tAppManhourAssessService = tAppManhourAssessService;
    }

    private ITAppMeritAssessService tAppMeritAssessService;

    public ITAppMeritAssessService getTAppMeritAssessService() {
        return tAppMeritAssessService;
    }

    @Autowired
    public void setTAppMeritAssessService(ITAppMeritAssessService tAppMeritAssessService) {
        this.tAppMeritAssessService = tAppMeritAssessService;
    }

    private ITAppMeritTemplateService tAppMeritTemplateService;

    public ITAppMeritTemplateService getTAppMeritTemplateService() {
        return tAppMeritTemplateService;
    }

    @Autowired
    public void setTAppMeritTemplateService(ITAppMeritTemplateService tAppMeritTemplateService) {
        this.tAppMeritTemplateService = tAppMeritTemplateService;
    }

    // 静态初使化当前类
    @Autowired
    private static GenerateMeritAssess createChildTable;

    // 通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    @PostConstruct
    public void init() {
        createChildTable = this;
        createChildTable.tAppManhourAssessService = this.tAppManhourAssessService;       // 初使化时将已静态化的Service实例化
        createChildTable.tAppMeritAssessService = this.tAppMeritAssessService;       // 初使化时将已静态化的Service实例化
        createChildTable.tAppMeritTemplateService = this.tAppMeritTemplateService;       // 初使化时将已静态化的Service实例化
    }

    public void meritAssess() throws Exception {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Date leftDay = PbUtils.StrToDateTime(year + "-01-01 00:00:00");
        Date rightDay = PbUtils.StrToDateTime(year + "-12-31 23:59:59");
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        List<TAppManhourAssess> tAppManhourAssessList = createChildTable.tAppManhourAssessService.getListByDate(leftDay, rightDay);
        createChildTable.tAppMeritAssessService.remove(year + "-01-01 00:00:00", year + "-12-31 23:59:59");
        for (TAppManhourAssess tAppManhourAssess : tAppManhourAssessList) {
            List<Map> listMap = createChildTable.tAppMeritTemplateService.getEntityByOpercd(tAppManhourAssess.getMopercd());
            Integer meritTemplateHour = 0;
            for (Map entityMap : listMap) {
                meritTemplateHour = Integer.valueOf(entityMap.get("mmanhour").toString());
            }
            TAppMeritAssess tAppMeritAssess = new TAppMeritAssess();
            tAppMeritAssess.setId(PbUtils.getUUID());
            tAppMeritAssess.setMcreatedate(date);
            tAppMeritAssess.setMopercd(tAppManhourAssess.getMopercd());
            tAppMeritAssess.setMsumhourrate(Float.valueOf(decimalFormat.format((float) tAppManhourAssess.getMsummanhour() / meritTemplateHour)));
            tAppMeritAssess.setMmasterrate(Float.valueOf(decimalFormat.format((float) tAppManhourAssess.getMmaster() / meritTemplateHour)));
            tAppMeritAssess.setMbranchrate(Float.valueOf(decimalFormat.format((float) tAppManhourAssess.getMbranch() / meritTemplateHour)));
            createChildTable.tAppMeritAssessService.add(tAppMeritAssess);
        }

    }
}
