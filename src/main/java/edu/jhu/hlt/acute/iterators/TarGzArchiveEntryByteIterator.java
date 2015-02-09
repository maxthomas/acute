/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */
package edu.jhu.hlt.acute.iterators;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * A class that provides the ability to iterate over files in a
 * <code>.tar.gz</code> archive. It skips folders, but will iterate
 * over files inside of them.
 */
public class TarGzArchiveEntryByteIterator extends TarArchiveEntryByteIterator implements Iterator<byte[]> {

  /**
   * @throws IOException
   */
  public TarGzArchiveEntryByteIterator(InputStream is) throws IOException {
    super(new GzipCompressorInputStream(is));
  }
}
