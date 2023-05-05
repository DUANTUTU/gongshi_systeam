package com.gt.quartzService;

import com.gt.app.service.ITAppDayPaperService;
import com.gt.app.service.ITAppManhourConfirmService;
import com.gt.app.service.ITAppManhourService;
import com.gt.model.TAppDayPaper;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.*;

@Component
public class GenerateDayPaper {
    private Logger logger = Logger.getLogger(GenerateDayPaper.class);
    private ITAppManhourService tAppManhourService;

    public ITAppManhourService getTAppManhourService() {
        return tAppManhourService;
    }

    @Autowired
    public void setTAppManhourService(ITAppManhourService tAppManhourService) {
        this.tAppManhourService = tAppManhourService;
    }


    private ITAppDayPaperService tAppDayPaperService;

    public ITAppDayPaperService getTAppDayPaperService() {
        return  tAppDayPaperService;
    }

    @Autowired
    public void setTAppDayPaperService(ITAppDayPaperService tAppDayPaperService) {
        this.tAppDayPaperService = tAppDayPaperService;
    }


    // 静态初使化当前类
    @Autowired
    private static GenerateDayPaper createChildTable;

    // 通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    @PostConstruct
    public void init() {
        createChildTable = this;
        createChildTable.tAppDayPaperService = this.tAppDayPaperService;       // 初使化时将已静态化的Service实例化
    }

    public void dayPaper() throws Exception {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday = PbUtils.DateToStr(calendar.getTime());
        String day = yesterday.substring(0, 10);
        String leftDay = day + " 00:00:00";
        String rightDay = day + " 23:59:59";
        List<Map> listmap = createChildTable.tAppManhourService.getPaperByDate(leftDay, rightDay);
        Map<String, TAppDayPaper> tAppDayPaperMap = new HashMap<>();

        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        //先遍历查询结果
        for (int i = 0; i < listmap.size(); i++) {
            TAppDayPaper tAppDayPaper = new TAppDayPaper();
            Map entityMap = listmap.get(i);
            if (tAppDayPaperMap.containsKey(entityMap.get("opercd").toString())) {
                if (Contans.PROJECT_TYPE_ARRY[0].equals(entityMap.get("projecttype").toString())) {
                    tAppDayPaper = tAppDayPaperMap.get(entityMap.get("opercd").toString());
                    tAppDayPaper.setDmaster(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppDayPaper.setDsummanhour(tAppDayPaper.getDbranch() + tAppDayPaper.getDmaster());
                    tAppDayPaper.setDbranchrate(Float.valueOf(decimalFormat.format((float) tAppDayPaper.getDbranch() / tAppDayPaper.getDsummanhour())));
                    tAppDayPaper.setDmasterrate(Float.valueOf(decimalFormat.format((float) tAppDayPaper.getDmaster() / tAppDayPaper.getDsummanhour())));
                    tAppDayPaperMap.put(entityMap.get("opercd").toString(), tAppDayPaper);
                }
                if (Contans.PROJECT_TYPE_ARRY[1].equals(entityMap.get("projecttype").toString())) {
                    tAppDayPaper = tAppDayPaperMap.get(entityMap.get("opercd").toString());
                    tAppDayPaper.setDbranch(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppDayPaper.setDsummanhour(tAppDayPaper.getDbranch() + tAppDayPaper.getDmaster());
                    tAppDayPaper.setDbranchrate(Float.valueOf(decimalFormat.format((float) tAppDayPaper.getDbranch() / tAppDayPaper.getDsummanhour())));
                    tAppDayPaper.setDmasterrate(Float.valueOf(decimalFormat.format((float) tAppDayPaper.getDmaster() / tAppDayPaper.getDsummanhour())));
                    tAppDayPaperMap.put(entityMap.get("opercd").toString(), tAppDayPaper);
                }
            } else {
                if (Contans.PROJECT_TYPE_ARRY[0].equals(entityMap.get("projecttype").toString())) {
                    tAppDayPaper.setDmaster(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppDayPaper.setDbranch(0);
                    tAppDayPaper.setDsummanhour(tAppDayPaper.getDmaster());
                    tAppDayPaper.setDbranchrate(0f);
                    tAppDayPaper.setDmasterrate(1f);
                    tAppDayPaper.setDcreatedate(date);
                    tAppDayPaper.setDopercd(entityMap.get("opercd").toString());
                    tAppDayPaperMap.put(entityMap.get("opercd").toString(), tAppDayPaper);

                }
                if (Contans.PROJECT_TYPE_ARRY[1].equals(entityMap.get("projecttype").toString())) {
                    tAppDayPaper.setDmaster(Integer.valueOf(entityMap.get("manhour").toString()));
                    tAppDayPaper.setDbranch(tAppDayPaper.getDbranch());
                    tAppDayPaper.setDsummanhour(tAppDayPaper.getDbranch());
                    tAppDayPaper.setDbranchrate(1f);
                    tAppDayPaper.setDmasterrate(0f);
                    tAppDayPaper.setDcreatedate(date);
                    tAppDayPaper.setDopercd(entityMap.get("opercd").toString());
                    tAppDayPaperMap.put(entityMap.get("opercd").toString(), tAppDayPaper);
                }
            }
        }
        for (Map.Entry<String, TAppDayPaper> entry : tAppDayPaperMap.entrySet()){
            entry.getValue().setId(PbUtils.getUUID());
            createChildTable.tAppDayPaperService.add(entry.getValue());
        }
    }
}
