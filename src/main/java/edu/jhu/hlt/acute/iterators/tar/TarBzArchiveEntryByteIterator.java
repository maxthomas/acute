/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package edu.jhu.hlt.acute.iterators.tar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

/**
 * A class that provides the ability to iterate over files in a
 * <code>.tar.bz2</code> archive. It skips folders, but will iterate
 * over files inside of them.
 */
public class TarBzArchiveEntryByteIterator extends TarArchiveEntryByteIterator implements Iterator<byte[]> {

  /**
   * @throws IOException
   */
  public TarBzArchiveEntryByteIterator(InputStream is) throws IOException {
    super(new BZip2CompressorInputStream(is));
  }
}
