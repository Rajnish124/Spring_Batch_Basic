package com.infybuzz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infybuzz.request.JobParamsRequest;
import com.infybuzz.service.JobService;
import com.infybuzz.service.SecondJobScheduler;

@RestController
@RequestMapping("/api/job")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	
	@GetMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName,@RequestBody List<JobParamsRequest> jobParamsRequestList) throws Exception {
		
		jobService.startJob(jobName,jobParamsRequestList);

		
		return "Job Started....";
		
	}
	

}
