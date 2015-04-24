/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package edu.jhu.hlt.acute.archivers.zip;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import edu.jhu.hlt.acute.archivers.Archivable;
import edu.jhu.hlt.acute.archivers.Archiver;

/**
 *
 */
public class ZipArchiver implements Archiver {

  private final ZipArchiveOutputStream zout;

  /**
   * Wrap an {@link Path} that represents a
   * <code>.zip</code> file. This constructor be used when writing to files on disk.
   *
   * @param path
   * @throws IOException on IO error
   */
  public ZipArchiver(Path path) throws IOException {
    this.zout = new ZipArchiveOutputStream(path.toFile());
  }

  /**
   * Wrap an {@link OutputStream}.
   * <br>
   * <br>
   * Do not use this constructor if the {@link OutputStream} is writing to a file.
   * Use {@link #ZipArchiver(Path)} instead.
   *
   * @param out an {@link OutputStream}
   * @see #ZipArchiver(Path)
   */
  public ZipArchiver(OutputStream out) {
    this.zout = new ZipArchiveOutputStream(out);
  }

  /*
   * (non-Javadoc)
   * @see edu.jhu.hlt.acute.archivers.Archiver#close()
   */
  @Override
  public void close() throws IOException {
    this.zout.close();
  }

  /* (non-Javadoc)
   * @see edu.jhu.hlt.acute.archivers.Archiver#addEntry(edu.jhu.hlt.acute.archivers.Archivable)
   */
  @Override
  public void addEntry(Archivable arch) throws IOException {
    final String fn = arch.getFileName();
    ZipArchiveEntry entry = new ZipArchiveEntry(fn);
    byte[] cbytes = arch.getBytes();
    entry.setSize(cbytes.length);
    this.zout.putArchiveEntry(entry);
    try (ByteArrayInputStream bis = new ByteArrayInputStream(cbytes)) {
      IOUtils.copy(bis, zout);
      this.zout.closeArchiveEntry();
    }
  }
}
