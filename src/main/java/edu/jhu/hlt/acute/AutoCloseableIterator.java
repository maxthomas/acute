/*
 * Copyright 2012-2015 Johns Hopkins University HLTCOE. All rights reserved.
 * See LICENSE in the project root directory.
 */

package edu.jhu.hlt.acute;

import java.util.Iterator;

/**
 * An an {@link Iterator} that implements {@link AutoCloseable}.
 */
public interface AutoCloseableIterator<E> extends AutoCloseable, Iterator<E> {

}
