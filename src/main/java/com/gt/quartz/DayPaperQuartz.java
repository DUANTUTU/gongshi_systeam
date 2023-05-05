package com.gt.quartz;

import com.gt.quartzService.GenerateDayPaper;
import com.sun.tools.javah.Gen;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class DayPaperQuartz extends BaseQuartz implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            GenerateDayPaper generateDayPaper = new GenerateDayPaper();
            generateDayPaper.dayPaper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
