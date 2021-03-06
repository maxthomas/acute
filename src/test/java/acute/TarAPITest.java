/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package acute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.jhu.hlt.acute.archivers.tar.TarArchiver;
import edu.jhu.hlt.acute.iterators.tar.TarArchiveEntryByteIterator;
import edu.jhu.hlt.acute.iterators.tar.TarGzArchiveEntryByteIterator;

public class TarAPITest {

  private static final Logger LOGGER = LoggerFactory.getLogger(TarAPITest.class);

  String sOne = "foo.string";
  String sTwo = "Internal_Macedonian_Revolutionary_Organization_–_Democratic_Party_for_Macedonian_National_Unity.comm";
  StringArchivable saOne = new StringArchivable(sOne);
  StringArchivable saTwo = new StringArchivable(sTwo);

  Path outPath;

  @Rule
  public TemporaryFolder tf = new TemporaryFolder();

  @Before
  public void setUp() throws Exception {
    outPath = tf.getRoot().toPath().resolve("test.tar");
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void tarAPI () throws Exception {
    LOGGER.info("Using charset: {}", StandardCharsets.UTF_8.name());
    try (OutputStream os = Files.newOutputStream(outPath);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        TarArchiver archiver = new TarArchiver(bos);) {
      archiver.addEntry(saOne);
      archiver.addEntry(saTwo);
    }

    try (InputStream is = Files.newInputStream(outPath);
        BufferedInputStream bis = new BufferedInputStream(is);
        TarArchiveEntryByteIterator iter = new TarArchiveEntryByteIterator(bis);) {
      assertTrue(iter.hasNext());
      assertTrue(iter.hasNext());
      byte[] baOne = iter.next();
      assertEquals(sOne, new String(baOne));
      assertTrue(iter.hasNext());
      assertTrue(iter.hasNext());
      byte[] baTwo = iter.next();
      assertEquals(sTwo, new String(baTwo));
      assertFalse(iter.hasNext());
    }
  }

  @Test
  public void gzAPI () throws Exception {
    try (OutputStream os = Files.newOutputStream(outPath);
        GzipCompressorOutputStream gos = new GzipCompressorOutputStream(os);
        TarArchiver archiver = new TarArchiver(gos);) {
      archiver.addEntry(saOne);
      archiver.addEntry(saTwo);
    }

    try (InputStream is = Files.newInputStream(outPath);
        TarGzArchiveEntryByteIterator iter = new TarGzArchiveEntryByteIterator(is);) {
      assertTrue(iter.hasNext());
      assertTrue(iter.hasNext());
      byte[] baOne = iter.next();
      assertEquals(sOne, new String(baOne));
      assertTrue(iter.hasNext());
      assertTrue(iter.hasNext());
      byte[] baTwo = iter.next();
      assertEquals(sTwo, new String(baTwo));
      assertFalse(iter.hasNext());
    }
  }

  @Test
  public void noBuffer () throws Exception {
    try (OutputStream os = Files.newOutputStream(outPath);
        TarArchiver archiver = new TarArchiver(os);) {
      archiver.addEntry(saOne);
      archiver.addEntry(saTwo);
    }

    try (InputStream is = Files.newInputStream(outPath);
        TarArchiveEntryByteIterator iter = new TarArchiveEntryByteIterator(is);) {
      assertTrue(iter.hasNext());
      assertTrue(iter.hasNext());
      byte[] baOne = iter.next();
      assertEquals(sOne, new String(baOne));
      assertTrue(iter.hasNext());
      assertTrue(iter.hasNext());
      byte[] baTwo = iter.next();
      assertEquals(sTwo, new String(baTwo));
      assertFalse(iter.hasNext());
    }
  }
}
