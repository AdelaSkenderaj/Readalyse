package com.readalyse.utility;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.ParserConfigurationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
@RequiredArgsConstructor
public class FileRetrieval {

  private static String basePath = "C:/Users/Dela/tmp";
  private static String RDF_FILES = basePath + "/rdf-files.tar.bz2";
  private static String RDF_URL = "https://gutenberg.org/cache/epub/feeds/rdf-files.tar.bz2";

  public void getRdfData() throws IOException, ParserConfigurationException, SAXException {

    // GET RDF DATA FROM PROJECT GUTENBERG IF THE FILES DO NOT EXIST
    // TODO DELETE THE FOLDER AFTER USE
    if (!Files.exists(Paths.get(RDF_FILES))) {
      URL url = new URL(RDF_URL);
      url.openConnection();
      Files.copy(url.openStream(), Paths.get(RDF_FILES));
      extractFiles();
    }
  }

  private void extractFiles() {
    try {
      File inputFile = new File(RDF_FILES);
      String outputFile = getFileName(inputFile, basePath);
      File tarFile = new File(outputFile);
      tarFile = deCompressGZipFile(inputFile, tarFile);
      File destFile = new File(basePath);
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
      File outputFile = new File(destFile + File.separator + tarArchiveEntry.getName());
      if (tarArchiveEntry.isDirectory()) {
        if (!outputFile.exists()) {
          outputFile.mkdirs();
        }
      } else {
        outputFile.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        IOUtils.copy(tarArchiveInputStream, fileOutputStream);
        fileOutputStream.close();
      }
    }
    tarArchiveInputStream.close();
  }
}