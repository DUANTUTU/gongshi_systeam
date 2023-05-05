package com.gt.quartzService;

import com.gt.app.service.ITAppManhourConfirmService;
import com.gt.app.service.ITAppManhourService;
import com.gt.app.service.ITAppMonthPaperService;
import com.gt.model.TAppMonthPaper;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.*;
@Component
public class GenerateMonthPaper {
    private Logger logger = Logger.getLogger(GenerateMonthPaper.class);
    private ITAppManhourService tAppManhourService;

    public ITAppManhourService getTAppManhourService() {
        return tAppManhourService;
    }

    @Autowired
    public void setTAppManhourService(ITAppManhourService tAppManhourService) {
        this.tAppManhourService = tAppManhourService;
    }

    private ITAppManhourConfirmService tAppManhourConfirmService;

    public ITAppManhourConfirmService getTAppManhourConfirmService() {
        return tAppManhourConfirmService;
    }

    @Autowired
    public void setTAppManhourConfirmService(ITAppManhourConfirmService tAppManhourConfirmService) {
        this.tAppManhourConfirmService = tAppManhourConfirmService;
    }
    private ITAppMonthPaperService tAppMonthPaperService;

    public ITAppMonthPaperService getTAppMonthPaperService() {
        return  tAppMonthPaperService;
    }

    @Autowired
    public void setTAppMonthPaperService(ITAppMonthPaperService tAppMonthPaperService) {
        this.tAppMonthPaperService = tAppMonthPaperService;
    }



    // 静态初使化当前类
    @Autowired
    private static GenerateMonthPaper createChildTable;

    // 通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    @PostConstruct
    public void init() {
        createChildTable = this;
        createChildTable.tAppMonthPaperService = this.tAppMonthPaperService;       // 初使化时将已静态化的Service实例化
    }

    public void monthPaper() throws Exception {
        Date date = new Date();
        Calendar calendarWeekOne = Calendar.getInstance();
        calendarWeekOne.setTime(date);
        calendarWeekOne.add(Calendar.MONTH, -1);
        Calendar calendarWeekSeven = Calendar.getInstance();
        calendarWeekSeven.setTime(date);
        calendarWeekSeven.add(Calendar.DAY_OF_MONTH, -1);
        String weekone = PbUtils.DateToStr(calendarWeekOne.getTime());
        String weekseven = PbUtils.DateToStr(calendarWeekSeven.getTime());
        String dayOne = weekone.substring(0, 10);
        String daySeven = weekseven.substring(0, 10);
        String leftDay = dayOne + " 00:00:00";
        String rightDay = daySeven + " 23:59:59";
        List<Map> listmap = createChildTable.tAppManhourService.getPaperByDate(leftDay, rightDay);
        Map<String, TAppMonthPaper> tAppMonthPaperMap = new HashMap<>();

        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        //先遍历查询结果
        for (int i = 0; i < listmap.size(); i++) {
            TAppMonthPaper tAppMonthPaper = new TAppMonthPaper();
            Map entityMap = listmap.get(i);
            if (tAppMonthPaperMap.containsKey(entityMap.get("opercd").toString())) {
                if (Contans.PROJECT_TYPE_ARRY[0].equals(entityMap.get("projecttype").toString())) {
                    tAppMonthPaper = tAppMonthPaperMap.get(entityMap.get("opercd").toString());
                    tAppMonthPaper.setMmaster(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppMonthPaper.setMsummanhour(tAppMonthPaper.getMbranch() + tAppMonthPaper.getMmaster());
                    tAppMonthPaper.setMbranchrate(Float.valueOf(decimalFormat.format((float) tAppMonthPaper.getMbranch() / tAppMonthPaper.getMsummanhour())));
                    tAppMonthPaper.setMmasterrate(Float.valueOf(decimalFormat.format((float) tAppMonthPaper.getMmaster() / tAppMonthPaper.getMsummanhour())));
                    tAppMonthPaperMap.put(entityMap.get("opercd").toString(), tAppMonthPaper);
                }
                if (Contans.PROJECT_TYPE_ARRY[1].equals(entityMap.get("projecttype").toString())) {
                    tAppMonthPaper = tAppMonthPaperMap.get(entityMap.get("opercd").toString());
                    tAppMonthPaper.setMbranch(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppMonthPaper.setMsummanhour(tAppMonthPaper.getMbranch() + tAppMonthPaper.getMmaster());
                    tAppMonthPaper.setMbranchrate(Float.valueOf(decimalFormat.format((float) tAppMonthPaper.getMbranch() / tAppMonthPaper.getMsummanhour())));
                    tAppMonthPaper.setMmasterrate(Float.valueOf(decimalFormat.format((float) tAppMonthPaper.getMmaster() / tAppMonthPaper.getMsummanhour())));
                    tAppMonthPaperMap.put(entityMap.get("opercd").toString(), tAppMonthPaper);
                }
            } else {
                if (Contans.PROJECT_TYPE_ARRY[0].equals(entityMap.get("projecttype").toString())) {
                    tAppMonthPaper.setMmaster(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppMonthPaper.setMbranch(0);
                    tAppMonthPaper.setMsummanhour(tAppMonthPaper.getMmaster());
                    tAppMonthPaper.setMbranchrate(0f);
                    tAppMonthPaper.setMmasterrate(1f);
                    tAppMonthPaper.setMcreatedate(date);
                    tAppMonthPaper.setMopercd(entityMap.get("opercd").toString());
                    tAppMonthPaperMap.put(entityMap.get("opercd").toString(), tAppMonthPaper);

                }
                if (Contans.PROJECT_TYPE_ARRY[1].equals(entityMap.get("projecttype").toString())) {
                    tAppMonthPaper.setMmaster(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppMonthPaper.setMbranch(tAppMonthPaper.getMbranch());
                    tAppMonthPaper.setMsummanhour(tAppMonthPaper.getMbranch());
                    tAppMonthPaper.setMbranchrate(1f);
                    tAppMonthPaper.setMmasterrate(0f);
                    tAppMonthPaper.setMcreatedate(date);
                    tAppMonthPaper.setMopercd(entityMap.get("opercd").toString());
                    tAppMonthPaperMap.put(entityMap.get("opercd").toString(), tAppMonthPaper);
                }
            }
        }
        for (Map.Entry<String, TAppMonthPaper> entry : tAppMonthPaperMap.entrySet()){
            entry.getValue().setId(PbUtils.getUUID());
            createChildTable.tAppMonthPaperService.add(entry.getValue());
        }
    }
}
