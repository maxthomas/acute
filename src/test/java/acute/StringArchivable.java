/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package acute;

import edu.jhu.hlt.acute.archivers.Archivable;

/**
 * Sample implementation of {@link Archivable} for testing.
 */
public class StringArchivable implements Archivable {

  private final String fileName;
  private final String extension;
  private final byte[] contentBytes;
  
  /**
   * Wrap a {@link String}.
   */
  public StringArchivable(String in) {
    this.fileName = in;
    this.extension = "string";
    this.contentBytes = in.getBytes();
  }

  /* (non-Javadoc)
   * @see edu.jhu.hlt.acute.archivers.Archivable#getFileName()
   */
  @Override
  public String getFileName() {
    return this.fileName;
  }

  /* (non-Javadoc)
   * @see edu.jhu.hlt.acute.archivers.Archivable#getExtension()
   */
  @Override
  public String getExtension() {
    return this.extension;
  }

  /* (non-Javadoc)
   * @see edu.jhu.hlt.acute.archivers.Archivable#getBytes()
   */
  @Override
  public byte[] getBytes() {
    return this.contentBytes;
  }
}
