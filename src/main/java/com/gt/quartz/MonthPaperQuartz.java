package com.gt.quartz;

import com.gt.quartzService.GenerateDayPaper;
import com.gt.quartzService.GenerateMonthPaper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class MonthPaperQuartz extends BaseQuartz implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            GenerateMonthPaper generateMonthPaper = new GenerateMonthPaper();
            generateMonthPaper.monthPaper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
