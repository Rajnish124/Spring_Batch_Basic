package com.infybuzz.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.infybuzz.listner.FirstJobListner;
import com.infybuzz.listner.FirstStepListner;
import com.infybuzz.processor.FirstItemProcessor;
import com.infybuzz.reader.FirstItemReader;
import com.infybuzz.service.SecondTasklet;
import com.infybuzz.writer.FirstItemWriter;

@Configuration
public class SampleJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private SecondTasklet secondTasklet;
	
	@Autowired
	private FirstJobListner firstJobListner;
	
	@Autowired
	private FirstStepListner firstStepListner;
	
	@Autowired
	private FirstItemReader firstItemReader;
	
	@Autowired
	private FirstItemProcessor firstItemProcessor;
	
	@Autowired
	private FirstItemWriter firstItemWriter;

	@Bean
	public Job firstJob() {
		return jobBuilderFactory.get("First Job")
				.start(firstStep())
				.incrementer(new RunIdIncrementer())
				.next(secondStep())
				.next(thirdStep())
				.listener(firstJobListner)
				.build();
	}

	private Step firstStep() {
		return stepBuilderFactory.get("First Step")
				.tasklet(firstTask())
				.listener(firstStepListner)
				.build();
	}

	private Tasklet firstTask() {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("This is first tasklet step");
				System.out.println("sec:"+chunkContext.getStepContext().getStepExecutionContext());
				return RepeatStatus.FINISHED;
			}
		};
	}
	
	private Step secondStep() {
		return stepBuilderFactory.get("second Step")
				.tasklet(secondTasklet)
				.listener(firstStepListner)
				.build();
	}
	
	/*
	 * private Tasklet secondTask() { return new Tasklet() {
	 * 
	 * @Override public RepeatStatus execute(StepContribution contribution,
	 * ChunkContext chunkContext) throws Exception {
	 * System.out.println("This is second tasklet step"); return
	 * RepeatStatus.FINISHED; } }; }
	 */
	
	
	private Step thirdStep() {
		return stepBuilderFactory.get("Third Step")
				.tasklet(thirdTask())
				.listener(firstStepListner)
				.build();
	}
	
	private Tasklet thirdTask() {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("This is third tasklet step");
				return RepeatStatus.FINISHED;
			}
		};
	}
	
	@Bean
	public Job secondJob() {
		return jobBuilderFactory.get("Second Job")
				.incrementer(new RunIdIncrementer())
				.start(firstChunkStep())
				.build();
	}

		
	private Step firstChunkStep() {
		return stepBuilderFactory.get("First Chunk Step")
				.<Integer,Long>chunk(3)
				.reader(firstItemReader)
				.processor(firstItemProcessor)
				.writer(firstItemWriter)
				.build();
				
	}

}
