package com.gt.quartzService;

import com.gt.app.service.ITAppDayPaperService;
import com.gt.app.service.ITAppManhourAssessService;
import com.gt.model.TAppManhourAssess;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.*;

@Component
public class GenerateManhourAssess {
    private Logger logger = Logger.getLogger(GenerateDayPaper.class);

    private ITAppDayPaperService tAppDayPaperService;

    public ITAppDayPaperService getTAppDayPaperService() {
        return tAppDayPaperService;
    }

    @Autowired
    public void setTAppDayPaperService(ITAppDayPaperService tAppDayPaperService) {
        this.tAppDayPaperService = tAppDayPaperService;
    }

    private ITAppManhourAssessService tAppManhourAssessService;

    public ITAppManhourAssessService getTAppManhourAssessService() {
        return tAppManhourAssessService;
    }

    @Autowired
    public void setTAppManhourAssessService(ITAppManhourAssessService tAppManhourAssessService) {
        this.tAppManhourAssessService = tAppManhourAssessService;
    }


    // 静态初使化当前类
    @Autowired
    private static GenerateManhourAssess createChildTable;

    // 通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    @PostConstruct
    public void init() {
        createChildTable = this;
        createChildTable.tAppDayPaperService = this.tAppDayPaperService;       // 初使化时将已静态化的Service实例化
        createChildTable.tAppManhourAssessService = this.tAppManhourAssessService;       // 初使化时将已静态化的Service实例化
    }

    public void manhourAssess() throws Exception {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday = PbUtils.DateToStr(calendar.getTime());
        String leftDay = yesterday;
        String rightDay = PbUtils.DateToStr(date);
        List<Map> listMap = createChildTable.tAppDayPaperService.getCountManhourByDate(yesterday, PbUtils.DateToStr(date));
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        List<TAppManhourAssess> tAppManhourAssessList = new ArrayList<>();
        for (int i = 0; i < listMap.size(); i++) {
            Map entitymap = listMap.get(i);
            TAppManhourAssess tAppManhourAssess = new TAppManhourAssess();
            tAppManhourAssess.setId(PbUtils.getUUID());
            tAppManhourAssess.setMopercd(entitymap.get("MOpercd").toString());
            tAppManhourAssess.setMsummanhour(Integer.valueOf(entitymap.get("MSummanhour").toString()));
            tAppManhourAssess.setMmaster(Integer.valueOf(entitymap.get("MMaster").toString()));
            tAppManhourAssess.setMbranch(Integer.valueOf(entitymap.get("MBranch").toString()));
            tAppManhourAssess.setMmasterrate(Float.valueOf(decimalFormat.format((float) tAppManhourAssess.getMmaster() / tAppManhourAssess.getMsummanhour())));
            tAppManhourAssess.setMbranchrate(Float.valueOf(decimalFormat.format((float) tAppManhourAssess.getMbranch() / tAppManhourAssess.getMsummanhour())));
            tAppManhourAssess.setMcreatedate(date);
            tAppManhourAssessList.add(tAppManhourAssess);
        }
        for (TAppManhourAssess tAppManhourAssess : tAppManhourAssessList) {
            createChildTable.tAppManhourAssessService.add(tAppManhourAssess);
        }
    }
}
