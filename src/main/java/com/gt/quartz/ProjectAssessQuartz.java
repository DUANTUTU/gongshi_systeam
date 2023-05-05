package com.gt.quartz;

import com.gt.quartzService.GenerateMonthPaper;
import com.gt.quartzService.GenerateProjectAssess;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
/**
 * 项目工时评估
 * */
@Component
public class ProjectAssessQuartz extends BaseQuartz implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            GenerateProjectAssess generateProjectAssess = new GenerateProjectAssess();
            generateProjectAssess.projectAssess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
