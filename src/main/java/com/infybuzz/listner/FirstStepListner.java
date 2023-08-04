package com.infybuzz.listner;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListner implements StepExecutionListener{

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("Before Job:"+stepExecution.getStepName());
		System.out.println("Job Exec Count:"+stepExecution.getJobExecution().getExecutionContext());
		System.out.println("Step Excec Count:"+stepExecution.getExecutionContext());
		
		stepExecution.getExecutionContext().put("Ram", "shyam");
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("Before Job:"+stepExecution.getStepName());
		System.out.println("Job Exec Count:"+stepExecution.getJobExecution().getExecutionContext());
		System.out.println("Step Excec Count:"+stepExecution.getExecutionContext());
		return null;
	}

}
