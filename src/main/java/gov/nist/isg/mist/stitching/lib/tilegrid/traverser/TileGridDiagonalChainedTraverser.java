// ================================================================
//
// Disclaimer: IMPORTANT: This software was developed at the National
// Institute of Standards and Technology by employees of the Federal
// Government in the course of their official duties. Pursuant to
// title 17 Section 105 of the United States Code this software is not
// subject to copyright protection and is in the public domain. This
// is an experimental system. NIST assumes no responsibility
// whatsoever for its use by other parties, and makes no guarantees,
// expressed or implied, about its quality, reliability, or any other
// characteristic. We would appreciate acknowledgement if the software
// is used. This software can be redistributed and/or modified freely
// provided that any derivative works bear some notice that they are
// derived from it, and any modified versions bear some notice that
// they have been modified.
//
// ================================================================

// ================================================================
//
// Author: tjb3
// Date: Aug 1, 2013 3:39:33 PM EST
//
// Time-stamp: <Aug 1, 2013 3:39:33 PM tjb3>
//
//
// ================================================================

package gov.nist.isg.mist.stitching.lib.tilegrid.traverser;

import gov.nist.isg.mist.stitching.lib.imagetile.ImageTile;
import gov.nist.isg.mist.stitching.lib.tilegrid.TileGrid;

import java.util.Iterator;

/**
 * Traversal type for traversing a grid diagonal chained
 * 
 * @author Tim Blattner
 * @version 1.0
 * @param <T>
 */
public class TileGridDiagonalChainedTraverser<T> implements TileGridTraverser<ImageTile<T>>, Iterator<ImageTile<T>> {

  private TileGrid<ImageTile<T>> subGrid;
  private int currentRowPosition;
  private int currentColumnPosition;
  private int linear;

  private int dirRow;
  private int dirCol;
  /**
   * Initializes a diagonal-chained traverser given a subgrid
   * 
   * @param subgrid
   */
  public TileGridDiagonalChainedTraverser(TileGrid<ImageTile<T>> subgrid) {
    this.subGrid = subgrid;
    this.currentRowPosition = 0;
    this.currentColumnPosition = 0;
    this.linear = 0;
    this.dirRow = -1;
    this.dirCol = 1;
  }

  @Override
  public Iterator<ImageTile<T>> iterator() {
    return this;
  }

  /**
   * Gets the current row of the traverser
   */
  @Override
  public int getCurrentRow() {
    return this.currentRowPosition;
  }

  /**
   * Gets the current column of the traverser
   */
  @Override
  public int getCurrentColumn() {
    return this.currentColumnPosition;
  }

  @Override
  public String toString() {
    return "Traversing by diagonal chained: " + this.subGrid;
  }
  
  @Override
  public boolean hasNext() {
    return this.linear < this.subGrid.getSubGridSize();
  }

  @Override
  public ImageTile<T> next() {
    int tempRow = this.currentRowPosition;
    int tempCol = this.currentColumnPosition;

    ImageTile<T> actualTile =
        this.subGrid.getTile(this.currentRowPosition + this.subGrid.getStartRow(), this.currentColumnPosition
            + this.subGrid.getStartCol());

    this.linear++;

    this.currentRowPosition += this.dirRow;
    this.currentColumnPosition += this.dirCol;

    if (hasNext()) {
      if (this.currentColumnPosition < 0 || this.currentColumnPosition >= this.subGrid.getExtentWidth()) {
        this.currentRowPosition = tempRow + 1;
        this.currentColumnPosition = tempCol;

        if (this.currentRowPosition >= this.subGrid.getExtentHeight()) {
          this.currentRowPosition = tempRow;
          this.currentColumnPosition = tempCol + 1;
        }

        this.dirRow = -this.dirRow;
        this.dirCol = -this.dirCol;
      } else if (this.currentRowPosition < 0 || this.currentRowPosition >= this.subGrid.getExtentHeight()) {
        this.currentRowPosition = tempRow;
        this.currentColumnPosition = tempCol + 1;
        this.dirRow = -this.dirRow;
        this.dirCol = -this.dirCol;
      }
    }

    return actualTile;
  }

  @Override
  public void remove() {
    // Not implemented/not needed
  }

}