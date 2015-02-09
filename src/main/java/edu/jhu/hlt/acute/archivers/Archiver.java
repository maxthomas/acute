/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package edu.jhu.hlt.acute.archivers;

import java.io.IOException;

/**
 * An interface that represents an archive on the file system (for example, .zip or .tar).
 */
public interface Archiver extends AutoCloseable {
  /**
   * Add an entry to the archive. 
   * 
   * @param arch an implementation of the {@link Archivable} interface.
   * @throws IOException
   */
  public void addEntry(Archivable arch) throws IOException;
}
