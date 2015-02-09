package acute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import edu.jhu.hlt.acute.archivers.tar.TarArchiver;
import edu.jhu.hlt.acute.iterators.TarArchiveEntryByteIterator;

public class TarAPITest {
  
  String sOne = "foo";
  String sTwo = "qux";
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
    try (OutputStream os = Files.newOutputStream(outPath);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        TarArchiver archiver = new TarArchiver(bos);) {
      archiver.addEntry(saOne);
      archiver.addEntry(saTwo);
    }
    
    try (InputStream is = Files.newInputStream(outPath);
        BufferedInputStream bis = new BufferedInputStream(is);) {
      TarArchiveEntryByteIterator iter = new TarArchiveEntryByteIterator(bis);
      assertTrue(iter.hasNext());
      byte[] baOne = iter.next();
      assertEquals(sOne, new String(baOne));
      byte[] baTwo = iter.next();
      assertEquals(sTwo, new String(baTwo));
    }
  }
}