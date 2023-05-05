package com.gt.quartz;

import com.gt.quartzService.GenerateDayPaper;
import com.gt.quartzService.GenerateManhourAssess;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 个人工时评估
 */
@Component
public class ManhourAssessQuartz extends BaseQuartz implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            GenerateManhourAssess generateManhourAssess = new GenerateManhourAssess();
            generateManhourAssess.manhourAssess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
