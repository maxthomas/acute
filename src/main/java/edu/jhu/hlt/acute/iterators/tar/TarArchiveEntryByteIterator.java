/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package edu.jhu.hlt.acute.iterators.tar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import edu.jhu.hlt.acute.AutoCloseableIterator;

/**
 * A class that provides the ability to iterate over all files in a
 * <code>.tar</code> archive. It skips folders, but will iterate
 * over files inside of them.
 */
public class TarArchiveEntryByteIterator implements AutoCloseableIterator<byte[]> {

  private final TarArchiveInputStream tis;

  /**
   * Wrap an {@link InputStream}.
   * 
   * @throws IOException if there are issues with the underlying archive (it has no
   * files, for example)
   */
  public TarArchiveEntryByteIterator(InputStream is) throws IOException {
    this.tis = new TarArchiveInputStream(is);

    // Prepare next entry.
    this.tis.getNextTarEntry();
  }
  
  /**
   * Create this object based on a {@link Path}.
   * 
   * @throws IOException
   */
  public TarArchiveEntryByteIterator(Path path) throws IOException {
    this(new BufferedInputStream(Files.newInputStream(path)));
  }

  @Override
  public boolean hasNext() {
    // possible states:
    // processed 1 file, and are now on a dir.
    // processed 1 file, and are now on another file.
    // done iterating (nothing left).

    // if any entry is null, done; return false.
    while (this.tis.getCurrentEntry() != null) {
      TarArchiveEntry entry = this.tis.getCurrentEntry();
      if (!entry.isDirectory())
        return true;
      else
        try {
          this.tis.getNextEntry();
        } catch (IOException ioe) {
          throw new RuntimeException(ioe);
        }
    }

    return this.tis.getCurrentEntry() != null;
  }

  @Override
  public byte[] next() {
    try {
      TarArchiveEntry entry = this.tis.getCurrentEntry();
      if (entry.isDirectory()) {
        // Recurse.
        this.tis.getNextTarEntry();
        this.next();
      }

      byte[] bytes = IOUtils.toByteArray(this.tis);
      this.tis.getNextTarEntry();
      return bytes;
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
  public void close() throws Exception {
    this.tis.close();
  }
}
