package io.hdmpedro.scheduler;

import io.hdmpedro.scheduler.model.SchedulerModel;
import io.hdmpedro.scheduler.controller.XmlLoaderController;
import io.hdmpedro.scheduler.dao.DatabaseManager;
import io.hdmpedro.scheduler.tasks.ExportTask;
import io.hdmpedro.scheduler.agendador.view.MainFrame;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.swing.*;
import java.io.File;

public class SchedulerMain {

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));

        File configFile = new File("config.xml");
        while (!configFile.exists()) {
            System.out.println("Aguardando criação do arquivo config.xml...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Espera interrompida. Encerrando aplicação.");
                Thread.currentThread().interrupt();
                return;
            }
            configFile = new File("config.xml");
        }




        SchedulerModel config = XmlLoaderController.loadConfig("config.xml");
        DatabaseManager.initialize(config.getDatabases());

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("configPath", "config.xml");

        JobDetail job = JobBuilder.newJob(ExportTask.class)
                .withIdentity("exportJob")
                .usingJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(config.getSchedule().getCronExpression()))
                .build();

        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }



}

