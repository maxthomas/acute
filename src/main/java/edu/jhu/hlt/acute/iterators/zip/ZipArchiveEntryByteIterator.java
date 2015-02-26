/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package edu.jhu.hlt.acute.iterators.zip;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

import edu.jhu.hlt.utilt.AutoCloseableIterator;

/**
 * A class that provides the ability to iterate over all files in a
 * <code>.zip</code> archive. It skips folders, but will iterate
 * over files inside of them.
 */
public class ZipArchiveEntryByteIterator implements AutoCloseableIterator<byte[]> {

  private final ZipFile zf;
  private final Enumeration<ZipArchiveEntry> iter;

  /**
   * Wrap a {@link Path} object. 
   * 
   * @throws IOException if there are issues with the underlying archive
   */
  public ZipArchiveEntryByteIterator(Path p) throws IOException {
    this.zf = new ZipFile(p.toFile());
    this.iter = this.zf.getEntries();
  }

  @Override
  public boolean hasNext() {
    return this.iter.hasMoreElements();
  }

  @Override
  public byte[] next() {
    try {
      ZipArchiveEntry entry = this.iter.nextElement();
      try (InputStream in = this.zf.getInputStream(entry);) {
        byte[] bytes = IOUtils.toByteArray(in);
        return bytes;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Cannot remove with this iterator.");
  }

  /* (non-Javadoc)
   * @see java.lang.AutoCloseable#close()
   */
  @Override
  public void close() throws IOException {
    this.zf.close();
  }
}
