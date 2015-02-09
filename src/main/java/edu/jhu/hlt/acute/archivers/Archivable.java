/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package edu.jhu.hlt.acute.archivers;

/**
 * An interface that represents an archivable type.
 */
public interface Archivable {
  /**
   * @return the name of the object when archived
   */
  public String getFileName();
  /**
   * The extension of the file in the archive.
   * 
   * @return the extension of the file when archived
   */
  public String getExtension();
  /**
   * 
   * @return the serialized form of this object
   */
  public byte[] getBytes();
}
