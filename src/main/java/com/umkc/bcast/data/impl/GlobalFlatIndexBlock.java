package com.umkc.bcast.data.impl;

import java.util.ArrayList;

import com.umkc.bcast.data.BlockType;
import com.umkc.bcast.data.GlobalIndexArrayItem;
import com.umkc.bcast.data.IndexBlock;

/**
 * This is an implementation of a Global Index block in a Flat Broadcast Structure.
 * 
 * <p>Assume our broadcast has a total of 26 buckets, each containing 10 words all starting
 * with a single letter of the alphabet.  Further assume that this bucket represents the
 * first index on 'a'.  The total construction should look something like this:
 * 
 * <ul><li>firstBucketValue = 'aardvark' (the first data element in this bucket of a's)</li>
 * <li>exponentialIndex:  as [waitTimeInBuckets, waitTimeInBlocks, maxKeyValue]
 *    <ol><li>[0, 0, 'axe'] (Bucket 0-0)</li>
 *        <li>[1, 11, 'buzz'] (Bucket 1-1)</li>
 *        <li>[2, 23, 'dumb'] (Bucket 2-3)</li>
 *        <li>[4, 47, 'hype'] (Bucket 4-7)</li>
 *        <li>[8, 95, 'pyre'] (Bucket 8-15)</li>
 *        <li>[16, 191, 'zym'] (Bucket 16-25)</li></ol>
 * </li></ul>
 * 
 * The primary access method here should be the getNextReadOffset() method.  This method
 * should return the waitTimeInBlocks value appropriate based on the value provided.
 * 
 * <p>Using the above sample, searching for 'ant' should return 0, searching for 'horse' should
 * return 47, and searching for 'thing' should return 191.
 * 
 * @author AC010168
 *
 */
public class GlobalFlatIndexBlock extends IndexBlock {

  /**
   * The first value in the bucket, useful for when we're trying to compare against values
   * in the current bucket.
   */
  private String firstBucketValue;
  
  /** 
   * The list of index keys for the global index, stored as an array of options.  An initialized
   * list should always have at least one element (pointing to the local storage block within the
   * current bucket).
   **/
  private ArrayList<GlobalIndexArrayItem> exponentialIndex;
  
  /** Simple Constructor */
  public GlobalFlatIndexBlock() {
    blockType        = BlockType.GLOBAL_FLAT_INDEX_BLOCK;
    firstBucketValue = null;
    exponentialIndex = new ArrayList<GlobalIndexArrayItem>();
  }
  
  /**
   * Basic Constructor with the firstBucketValue provided.
   * 
   * @param firstBucketValue
   */
  public GlobalFlatIndexBlock(String firstBucketValue) {
    blockType             = BlockType.GLOBAL_FLAT_INDEX_BLOCK;
    this.firstBucketValue = firstBucketValue;
    exponentialIndex      = new ArrayList<GlobalIndexArrayItem>();
  }
  
  /* (non-Javadoc)
   * @see com.umkc.bcast.data.IndexBlock#getNextReadOffset(String)
   */
  @Override
  public int getNextReadOffset(String searchKey) {
    //DEBUG
    System.out.println ("Reading " + blockID + ".  Looking for where " + searchKey + " should be found...");
    
    //TODO This is where the real work of parsing the index block takes place.
    
    return -1;
  }
  
  /* (non-Javadoc)
   * @see com.umkc.bcast.data.IndexBlock#getNextReadOffset(String, String)
   */
  @Override
  public int getNextReadOffset(String searchCluster, String searchKey) {
    throw new RuntimeException ("This version of getNextReadOffset is not supported for this Index type");
  }
  
  /** Helper method to add a new index row to the exponentialIndex. */
  public void addIndexRow(GlobalIndexArrayItem indexEntry) {
    exponentialIndex.add(indexEntry);
  }

  /**
   * @return the firstBucketValue
   */
  public String getFirstBucketValue() {
    return firstBucketValue;
  }

  /**
   * @param firstBucketValue the firstBucketValue to set
   */
  public void setFirstBucketValue(String firstBucketValue) {
    this.firstBucketValue = firstBucketValue;
  }
}
