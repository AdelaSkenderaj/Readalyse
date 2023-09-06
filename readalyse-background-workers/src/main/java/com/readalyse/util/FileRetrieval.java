package com.readalyse.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

public class FileRetrieval {

    private String BASE_PATH;

    private String RDF_FILES;

    private String RDF_URL;

    public FileRetrieval(String basePath, String rdfFiles, String rdfUrl) {
        this.BASE_PATH = basePath;
        this.RDF_FILES = rdfFiles;
        this.RDF_URL = rdfUrl;
    }


    public void getRdfData() throws IOException, ParserConfigurationException, SAXException {
        URL url = new URL(RDF_URL);
        url.openConnection();
        Files.copy(url.openStream(), Paths.get(RDF_FILES));
        extractFiles();
        deleteZipFile();
    }

    private void extractFiles() {
        try {
            File inputFile = new File(RDF_FILES);
            String outputFile = getFileName(inputFile, BASE_PATH);
            File tarFile = new File(outputFile);
            tarFile = deCompressGZipFile(inputFile, tarFile);
            File destFile = new File(BASE_PATH);
            if (!destFile.exists()) {
                destFile.mkdir();
            }
            unTarFile(tarFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(File inputFile, String outputFolder) {
        return outputFolder
                + File.separator
                + inputFile.getName().substring(0, inputFile.getName().lastIndexOf('.'));
    }

    private File deCompressGZipFile(File gZippedFile, File tarFile) throws IOException {
        try (FileInputStream fin = new FileInputStream(gZippedFile);
             BufferedInputStream bis = new BufferedInputStream(fin);
             CompressorInputStream input =
                     new CompressorStreamFactory().createCompressorInputStream(bis);
             FileOutputStream fileOutputStream = new FileOutputStream(tarFile); ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException | CompressorException e) {
            e.printStackTrace();
        }
        return tarFile;
    }

    private void unTarFile(File tarFile, File destFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(tarFile);
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(fileInputStream);
        TarArchiveEntry tarArchiveEntry = null;

        while ((tarArchiveEntry = tarArchiveInputStream.getNextTarEntry()) != null) {
            File outputFile =
                    new File(
                            destFile
                                    + File.separator
                                    + tarArchiveEntry
                                    .getName()
                                    .substring(tarArchiveEntry.getName().lastIndexOf('/') + 1));
            if (tarArchiveEntry.isDirectory()) {
            } else {
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                IOUtils.copy(tarArchiveInputStream, fileOutputStream);
                fileOutputStream.close();
            }
        }
        tarArchiveInputStream.close();
    }

    private void deleteZipFile() {
        File zipFile = new File(RDF_FILES);
        File tarFile = new File(RDF_FILES.substring(0, RDF_FILES.lastIndexOf('.')));
        if (zipFile.exists()) {
            zipFile.delete();
        }
        if (tarFile.exists()) {
            tarFile.delete();
        }
    }
}
