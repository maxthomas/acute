/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package acute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import edu.jhu.hlt.acute.archivers.zip.ZipArchiver;
import edu.jhu.hlt.acute.iterators.zip.ZipArchiveEntryByteIterator;
import edu.jhu.hlt.utilt.AutoCloseableIterator;

public class ZipAPITest {
  
  String sOne = "foo.string";
  String sTwo = "qux.string";
  StringArchivable saOne = new StringArchivable(sOne);
  StringArchivable saTwo = new StringArchivable(sTwo);
  
  Path outPath;
  
  @Rule
  public TemporaryFolder tf = new TemporaryFolder();
  
  @Before
  public void setUp() throws Exception {
    // outPath = tf.getRoot().toPath().resolve("test.zip");
    outPath = Paths.get("target").resolve("test.zip");
  }

  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void tarAPI () throws Exception {
    try (OutputStream os = Files.newOutputStream(outPath);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        ZipArchiver archiver = new ZipArchiver(bos);) {
      archiver.addEntry(saOne);
      archiver.addEntry(saTwo);
    }
    
    try (AutoCloseableIterator<byte[]> iter = new ZipArchiveEntryByteIterator(this.outPath)) {
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
