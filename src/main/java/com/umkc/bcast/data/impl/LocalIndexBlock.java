package com.umkc.bcast.data.impl;

import java.util.ArrayList;

import com.umkc.bcast.data.BlockType;
import com.umkc.bcast.data.IndexBlock;
import com.umkc.bcast.data.LocalIndexArrayItem;

/**
 * @author ac010168
 *
 */
public class LocalIndexBlock extends IndexBlock {

  /**
   * The list of index keys for the local items contained by this block.
   */
  private ArrayList<LocalIndexArrayItem> localIndex;
  
  public LocalIndexBlock() {
    blockType  = BlockType.LOCAL_INDEX_BLOCK;
    localIndex = new ArrayList<LocalIndexArrayItem>();
  }
  
  /* (non-Javadoc)
   * @see com.umkc.bcast.data.IndexBlock#getNextReadOffset(java.lang.String)
   */
  @Override
  public int getNextReadOffset(String searchKey) {
    //DEBUG
    System.out.println ("Reading " + blockID + ".  Looking for where " + searchKey + " should be found...");
    
    //TODO This is where the real work of parsing the index block takes place.
    
    return -1;
  }
  
  /* (non-Javadoc)
   * @see com.umkc.bcast.data.IndexBlock#getNextReadOffset(java.lang.String, java.lang.String)
   */
  @Override
  public int getNextReadOffset(String searchCluster, String searchKey) {
    throw new RuntimeException ("This version of getNextReadOffset is not supported for this Index type");
  }

  /**
   * Method to add new entries to the to the index.
   * 
   * @param indexEntry The next index entry to be added to this local index.
   */
  public void addIndexRow(LocalIndexArrayItem indexEntry) {
    localIndex.add(indexEntry);
  }
  
  /**
   * Override of the toString method to assist with troubleshooting/debugging.
   */
  @Override
  public String toString() {
    String result = " + " + blockID + "  [ Indexed Blocks: " + localIndex.size() + "]\n";
    for (LocalIndexArrayItem indexItem : localIndex)
      result += "    [" + indexItem.getWaitTimeAsBlocks() + " | " + indexItem.getBlockKeyValue() + "]\n";
    result += "   Next Global Index Block: " + nextIndexOffset + "\n";
    
    return result;
  }
}
