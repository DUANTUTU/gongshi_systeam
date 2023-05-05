package com.gt.quartz;

import com.gt.quartzService.GenerateDayPaper;
import com.gt.quartzService.GenerateWeekPaper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class WeekPaperQuartz extends BaseQuartz implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            GenerateWeekPaper generateWeekPaper = new GenerateWeekPaper();
            generateWeekPaper.weekPaper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
