package com.readalyse.job;

import com.readalyse.util.FileRetrieval;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.logging.Logger;


public class AnalyseAllFiles implements Job {

    Logger logger = Logger.getLogger(AnalyseAllFiles.class.getName());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        FileRetrieval fileRetrieval = new FileRetrieval("C:/Users/Dela/background-workers/all-time-data", "C:/Users/Dela/background-workers/all-time-data/rdf-files.tar.bz2", "https://gutenberg.org/cache/epub/feeds/rdf-files.tar.bz2");
        try {
            logger.info("Starting file retrieval " + ZonedDateTime.now());
            fileRetrieval.getRdfData();
            logger.info("Ending file retrieval " + ZonedDateTime.now());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }
}
