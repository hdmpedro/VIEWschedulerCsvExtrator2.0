/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hdmpedro.scheduler.controller;

import io.hdmpedro.scheduler.model.SchedulerModel;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
/**
 *
 * @author DSK-11
 */
public class XmlLoaderController {
    public static SchedulerModel loadConfig(String filePath) throws JAXBException {


        JAXBContext context = JAXBContext.newInstance(SchedulerModel.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        SchedulerModel config = (SchedulerModel) unmarshaller.unmarshal(new File(filePath));

        System.out.println("Databases configurados:");
        config.getDatabases().forEach(db ->
                System.out.printf("DB: %s | Host: %s | Database: %s%n",
                        db.getName(), db.getHost(), db.getDatabaseName()));

        return config;
    }

}