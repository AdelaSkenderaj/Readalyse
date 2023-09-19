package com.readalyse.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Configuration
public class LoggerConfig {

    @Bean
    @Qualifier("InformationExtractionLogger")
    public Logger InformationExtractionLogger() throws IOException {
        Logger logger = Logger.getLogger("InformationExtractionLogger");
        FileHandler fileHandler = new FileHandler("C:/Users/Dela/log/InformationExtractionLogger.log");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        return logger;
    }

    @Bean
    @Qualifier("ReadabilityScoresAnalysisLogger")
    public Logger ReadabilityScoresAnalysisLogger() throws IOException {
        Logger logger = Logger.getLogger("ReadabilityScoresAnalysisLogger");
        FileHandler fileHandler = new FileHandler("C:/Users/Dela/log/ReadabilityScoresAnalysisLogger.log");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        return logger;
    }
}
