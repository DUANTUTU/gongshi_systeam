package com.gt.quartz;

import com.gt.quartzService.GenerateManhourAssess;
import com.gt.quartzService.GenerateMeritAssess;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
/**
 * 绩效评估
 * */
@Component
public class MeritAssessQuartz extends BaseQuartz implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            GenerateMeritAssess generateMeritAssess = new GenerateMeritAssess();
            generateMeritAssess.meritAssess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
