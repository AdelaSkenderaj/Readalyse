package com.readalyse.dataImport;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZipProcessingService {

  @Value("${projectGutenberg.url}")
  private String URL;

  @Value("${projectGutenberg.temporaryDirectory}")
  private String TEMPORARY_DIRECTORY;

  @Value("${projectGutenberg.temporaryFile}")
  private String TEMPORARY_FILE;

  private final ExtractFileInformationService extractFileInformationService;

  private static final Logger logger = Logger.getLogger(ZipProcessingService.class.getName());

  public void fetchAndProcessZip() {
    logger.info("Zip Processing Started");

    // Ensure the temporary directory exists, and create it if not
    File file = new File(TEMPORARY_DIRECTORY);
    if (!file.exists() && !file.mkdirs()) {
      logger.severe("Error creating temporary directory for downloaded file");
      return;
    }

    // Check if file is already downloaded, if not, download and process
    Optional<File[]> fileList = Optional.ofNullable(file.listFiles());
    if (fileList.isPresent() && fileList.get().length == 0) {
      try {
        downloadZipFile();
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Error downloading the zip file", e);
        return;
      }
    }

    processBz2TarFile(new File(TEMPORARY_FILE));
    deleteZipFile();
    logger.info("Zip Processing Ended");
  }

  /**
   * Downloads the zip file from the specified URL if the file doesn't already exist.
   *
   * @throws IOException If there is an error downloading the file.
   */
  private void downloadZipFile() throws IOException {
    URL url = new URL(URL);
    url.openConnection();
    Files.copy(url.openStream(), Paths.get(TEMPORARY_FILE));
  }

  /**
   * Decompresses a `.bz2` file containing a `.tar` archive and processes each file entry within the
   * `.tar` archive.
   *
   * <p>The method performs the following steps:
   *
   * <ol>
   *   <li>Decompresses the input `.bz2` file to access the `.tar` archive inside it.
   *   <li>Iterates through each entry in the `.tar` archive.
   *   <li>Reads the content of each file in memory without saving it to disk.
   * </ol>
   *
   * @param bz2File The input `.bz2` file that contains the `.tar` archive. The `.tar` archive
   *     within the `.bz2` file is expected to contain RDF files that will be processed by Jena.
   */
  public void processBz2TarFile(File bz2File) {
    try (FileInputStream bz2InputStream = new FileInputStream(bz2File);
        BufferedInputStream bufferedBz2InputStream = new BufferedInputStream(bz2InputStream);
        BZip2CompressorInputStream bz2CompressorInputStream =
            new BZip2CompressorInputStream(bufferedBz2InputStream);
        TarArchiveInputStream tarInputStream =
            new TarArchiveInputStream(bz2CompressorInputStream)) {
      TarArchiveEntry tarEntry;

      while ((tarEntry = tarInputStream.getNextTarEntry()) != null) {
        if (!tarEntry.isDirectory()) {
          processTarEntry(tarInputStream);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading the downloaded file.");
    }
  }

  /**
   * Processes a file entry from the tar archive.
   *
   * @param tarInputStream The input stream of the tar archive.
   * @throws IOException If there is an error processing the file.
   */
  private void processTarEntry(TarArchiveInputStream tarInputStream) throws IOException {

    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      IOUtils.copy(tarInputStream, byteArrayOutputStream);
      byte[] fileContent = byteArrayOutputStream.toByteArray();
      Model fileModel = loadRDFToModel(fileContent);
      extractFileInformationService.extractFileInformation(fileModel);
      fileModel.close();
    }
  }

  private Model loadRDFToModel(byte[] fileContent) {
    Model model = ModelFactory.createDefaultModel();

    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileContent);

    try {
      model.read(byteArrayInputStream, null);
    } catch (Exception e) {
      logger.severe("Error parsing RDF data: " + e.getMessage());
    }

    return model;
  }

  private void deleteZipFile() {
    File file = new File(TEMPORARY_FILE);
    if (file.exists() && !file.delete()) {
      logger.warning("Failed to delete the temporary zip file");
    }
  }
}
