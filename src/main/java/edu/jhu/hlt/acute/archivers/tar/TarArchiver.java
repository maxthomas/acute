/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package edu.jhu.hlt.acute.archivers.tar;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import edu.jhu.hlt.acute.archivers.Archivable;
import edu.jhu.hlt.acute.archivers.Archiver;

/**
 * A class that provides the ability to write entries to a .tar archive
 * in a forward-only fashion. New additions will overwrite files with the 
 * same file name.
 * <br/>
 * <br/>
 * This implementation appends a <code>.</code> for the extension if the extension does not begin
 * with a <code>.</code>. 
 * <br/>
 * <br/>
 * For example, if the {@link Archivable} implementation's 
 * {@link Archivable#getExtension()} method returns
 * <code>elf</code>, this implementation will create archives with the name <code>fileName.elf</code>.
 */
public class TarArchiver implements Archiver {

  private final TarArchiveOutputStream tos;
  
  /**
   * Wrap an {@link OutputStream}. 
   */
  public TarArchiver(OutputStream os) {
    this.tos = new TarArchiveOutputStream(os);
  }
  
  /*
   * (non-Javadoc)
   * @see edu.jhu.hlt.acute.archivers.Archiver#addEntry(edu.jhu.hlt.acute.archivers.Archivable)
   */
  @Override
  public void addEntry(Archivable arch) throws IOException {
    final String fn = arch.getFileName();
    TarArchiveEntry entry = new TarArchiveEntry(fn);
    byte[] cbytes = arch.getBytes();
    entry.setSize(cbytes.length);
    this.tos.putArchiveEntry(entry);
    try (ByteArrayInputStream bis = new ByteArrayInputStream(cbytes)) {
      IOUtils.copy(bis, tos);
      tos.closeArchiveEntry();
    }
  }

  /* (non-Javadoc)
   * @see java.lang.AutoCloseable#close()
   */
  @Override
  public void close() throws IOException {
    this.tos.close();
  }
}
