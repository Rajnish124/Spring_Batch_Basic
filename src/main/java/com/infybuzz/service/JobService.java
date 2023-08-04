package com.infybuzz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.batch.runtime.JobExecution;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.infybuzz.request.JobParamsRequest;

@Service
public class JobService {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Qualifier("firstJob")
	@Autowired
	private Job firstJob;
	
	@Qualifier("secondJob")
	@Autowired
	private Job secondJob;
	
	@Async
	public void startJob(String jobName,List<JobParamsRequest> jobParamsRequestList){
		
		Map<String,JobParameter> params=new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));
		
		jobParamsRequestList.stream().forEach(paramReq->{
			params.put(paramReq.getParamKey(),new JobParameter(paramReq.getParamValue()));
			
		});
		
		JobParameters jobParameters=new JobParameters(params);
		try {
			
			JobExecution jobExecution=null;
			if(jobName.equals("First Job")) {
				jobExecution=(JobExecution) jobLauncher.run(firstJob, jobParameters);
			}
			else if(jobName.equals("Second Job")) {
				jobExecution=(JobExecution) jobLauncher.run(secondJob, jobParameters);
			}
			
			System.out.println("Job Execution Id= "+jobExecution.getExecutionId());
		}catch(Exception e) {
			System.out.println("Exception while starting job..");
		}
		
		
		
	}

}
