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
   * @return the full filename of the object when archived (including extension)
   */
  public String getFileName();

  /**
   * @return the serialized form of this object
   */
  public byte[] getBytes();
}
