package com.gt.quartzService;

import com.gt.app.service.ITAppManhourConfirmService;
import com.gt.app.service.ITAppManhourService;
import com.gt.app.service.ITAppWeekPaperService;
import com.gt.model.TAppWeekPaper;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.*;

@Component
public class GenerateWeekPaper {
    private Logger logger = Logger.getLogger(GenerateWeekPaper.class);
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
    private ITAppWeekPaperService tAppWeekPaperService;

    public ITAppWeekPaperService getTAppWeekPaperService() {
        return  tAppWeekPaperService;
    }

    @Autowired
    public void setTAppWeekPaperService(ITAppWeekPaperService tAppWeekPaperService) {
        this.tAppWeekPaperService = tAppWeekPaperService;
    }



    // 静态初使化当前类
    @Autowired
    private static GenerateWeekPaper createChildTable;

    // 通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    @PostConstruct
    public void init() {
        createChildTable = this;
        createChildTable.tAppWeekPaperService = this.tAppWeekPaperService;       // 初使化时将已静态化的Service实例化
    }

    public void weekPaper() throws Exception {
        Date date = new Date();
        Calendar calendarWeekOne = Calendar.getInstance();
        calendarWeekOne.setTime(date);
        calendarWeekOne.add(Calendar.DAY_OF_MONTH, -7);
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
        Map<String, TAppWeekPaper> tAppWeekPaperMap = new HashMap<>();

        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        //先遍历查询结果
        for (int i = 0; i < listmap.size(); i++) {
            TAppWeekPaper tAppWeekPaper = new TAppWeekPaper();
            Map entityMap = listmap.get(i);
            if (tAppWeekPaperMap.containsKey(entityMap.get("opercd").toString())) {
                if (Contans.PROJECT_TYPE_ARRY[0].equals(entityMap.get("projecttype").toString())) {
                    tAppWeekPaper = tAppWeekPaperMap.get(entityMap.get("opercd").toString());
                    tAppWeekPaper.setWmaster(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppWeekPaper.setWsummanhour(tAppWeekPaper.getWbranch() + tAppWeekPaper.getWmaster());
                    tAppWeekPaper.setWbranchrate(Float.valueOf(decimalFormat.format((float) tAppWeekPaper.getWbranch() / tAppWeekPaper.getWsummanhour())));
                    tAppWeekPaper.setWmasterrate(Float.valueOf(decimalFormat.format((float) tAppWeekPaper.getWmaster() / tAppWeekPaper.getWsummanhour())));
                    tAppWeekPaperMap.put(entityMap.get("opercd").toString(), tAppWeekPaper);
                }
                if (Contans.PROJECT_TYPE_ARRY[1].equals(entityMap.get("projecttype").toString())) {
                    tAppWeekPaper = tAppWeekPaperMap.get(entityMap.get("opercd").toString());
                    tAppWeekPaper.setWbranch(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppWeekPaper.setWsummanhour(tAppWeekPaper.getWbranch() + tAppWeekPaper.getWmaster());
                    tAppWeekPaper.setWbranchrate(Float.valueOf(decimalFormat.format((float) tAppWeekPaper.getWbranch() / tAppWeekPaper.getWsummanhour())));
                    tAppWeekPaper.setWmasterrate(Float.valueOf(decimalFormat.format((float) tAppWeekPaper.getWmaster() / tAppWeekPaper.getWsummanhour())));
                    tAppWeekPaperMap.put(entityMap.get("opercd").toString(), tAppWeekPaper);
                }
            } else {
                if (Contans.PROJECT_TYPE_ARRY[0].equals(entityMap.get("projecttype").toString())) {
                    tAppWeekPaper.setWmaster(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppWeekPaper.setWbranch(0);
                    tAppWeekPaper.setWsummanhour(tAppWeekPaper.getWmaster());
                    tAppWeekPaper.setWbranchrate(0f);
                    tAppWeekPaper.setWmasterrate(1f);
                    tAppWeekPaper.setWcreatedate(date);
                    tAppWeekPaper.setWopercd(entityMap.get("opercd").toString());
                    tAppWeekPaperMap.put(entityMap.get("opercd").toString(), tAppWeekPaper);

                }
                if (Contans.PROJECT_TYPE_ARRY[1].equals(entityMap.get("projecttype").toString())) {
                    tAppWeekPaper.setWmaster(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppWeekPaper.setWbranch(tAppWeekPaper.getWbranch());
                    tAppWeekPaper.setWsummanhour(tAppWeekPaper.getWbranch());
                    tAppWeekPaper.setWbranchrate(1f);
                    tAppWeekPaper.setWmasterrate(0f);
                    tAppWeekPaper.setWcreatedate(date);
                    tAppWeekPaper.setWopercd(entityMap.get("opercd").toString());
                    tAppWeekPaperMap.put(entityMap.get("opercd").toString(), tAppWeekPaper);
                }
            }
        }
        for (Map.Entry<String, TAppWeekPaper> entry : tAppWeekPaperMap.entrySet()){
            entry.getValue().setId(PbUtils.getUUID());
            createChildTable.tAppWeekPaperService.add(entry.getValue());
        }
    }
}
