package com.simback.dbbackup;

import java.io.File;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class DumpJob implements Job {
	private static Logger logger = DumpLogger.getLogger();
	
	public static void dumpDB(String database, String username, String password, String localDir, String remoteDir) {
		File file = MySQLDumpMaker.generateDump(database, username, password, localDir);
		if(null != file) {
			DropBoxUploader.upload(file.getAbsolutePath(), file.getName(), remoteDir);
			logger.info("MySQL Dump file generated is located at : " + file.getAbsolutePath());			
			System.out.println("MySQL Dump file generated is located at : " + file.getAbsolutePath());			
		}
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		
		/** Récupération des paramètres d'exécution du Job **/
		String database = (String) jobDataMap.get("database");
		String username = (String) jobDataMap.get("username");
		String password = ((String) jobDataMap.get("password"));
		password = password == null ? "" : password;
		String localDir = (String) jobDataMap.get("local.dir");
		String remoteDir = (String) jobDataMap.get("remote.dir");
		//TODO : Create a logger and use it to follow execution
		logger.debug( "Target database : " + database);
		System.out.println("Target database : " + database);
		logger.debug("Used username : " + username);
		System.out.println("Used username : " + username);
		logger.debug("Using password : " + !password.isEmpty());
		System.out.println("Used password : " + password);
		System.out.println("Target local dir : " + localDir);
		System.out.println("Target remote dir : " + remoteDir);
		
		dumpDB(database, username, password, localDir, remoteDir);
	}
	
	public static void main(String[] args) {
		try {
			JobDetail job = JobBuilder.newJob(DumpJob.class).build();
			Trigger trigger = TriggerBuilder.newTrigger()
					.withSchedule(
							CronScheduleBuilder.cronSchedule("0/0 55 10 * * ?")
							).build();
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
