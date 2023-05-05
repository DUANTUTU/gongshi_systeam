package com.gt.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 测试Quartz
 * @author： liutaok
 * @date：2018-12-7
 */
public class TestQuartz extends BaseQuartz implements Job  {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("正在运行......");
		
	}

}
