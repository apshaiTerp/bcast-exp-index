package com.umkc.bcast.data.impl;

import java.util.ArrayList;

import com.umkc.bcast.data.Block;
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
   * @param firstBucketValue The first key value contained in the local range of this bucket.
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
    //System.out.println ("Reading " + blockID + ".  Looking for where " + searchKey + " should be found...");
    
    if (searchKey.compareTo(firstBucketValue) < 0) {
      //DEBUG
      //System.out.println ("The Search Key is before values in this index bucket");
      
      //If the term we want comes 'before' this bucket, we need to skip through the index until we've wrapped
      //Since we're guaranteed the last index item will reference the max key in the bucket immediately
      //preceeding this one.
      for (GlobalIndexArrayItem indexItem : exponentialIndex) {
        //If the indexEntry preceeds this bucket, and our search key is less than that max, we found what we wanted
        if ((indexItem.getMaxKeyValue().compareTo(firstBucketValue) < 0) && (searchKey.compareTo(indexItem.getMaxKeyValue()) <= 0)) {
          //DEBUG
          //System.out.println ("Found my hit in [" + indexItem.getWaitTimeAsBuckets() + " | " +  + indexItem.getWaitTimeAsBlocks() + " | " + indexItem.getMaxKeyValue() + "]");
          return indexItem.getWaitTimeAsBlocks();
        }
      }
      
      //If we somehow didn't find our value, then we've got a big problem, throw an error
      throw new RuntimeException("Malformed Index Block.  Search Key could not be found correctly");
    } else {
      //DEBUG
      //System.out.println ("The Search Key is equal to or after values in this index bucket");
      
      //In this case, there are two possibilities.  One, the search term we want is easily found under an index
      //block.  Two, the term we want is in a range that 'wraps', so the bucket isn't bounded by the last lexical
      //search term.
      for (GlobalIndexArrayItem indexItem : exponentialIndex) {
        if (searchKey.compareTo(indexItem.getMaxKeyValue()) <= 0) {
          //DEBUG
          //System.out.println ("Found my hit in [" + indexItem.getWaitTimeAsBuckets() + " | " +  + indexItem.getWaitTimeAsBlocks() + " | " + indexItem.getMaxKeyValue() + "]");
          return indexItem.getWaitTimeAsBlocks();
        } else if ((searchKey.compareTo(indexItem.getMaxKeyValue()) > 0) && (firstBucketValue.compareTo(indexItem.getMaxKeyValue()) > 0)) {
          //DEBUG
          //System.out.println ("Found my hit in [" + indexItem.getWaitTimeAsBuckets() + " | " +  + indexItem.getWaitTimeAsBlocks() + " | " + indexItem.getMaxKeyValue() + "]");
          return indexItem.getWaitTimeAsBlocks();
        }
        
      }

      //If we somehow didn't find our value, then we've got a big problem, throw an error
      throw new RuntimeException("Malformed Index Block.  Search Key could not be found correctly");
    }
  }
  
  /* (non-Javadoc)
   * @see com.umkc.bcast.data.IndexBlock#getNextReadOffset(String, String)
   */
  @Override
  public int getNextReadOffset(String searchCluster, String searchKey) {
    throw new RuntimeException ("This version of getNextReadOffset is not supported for this Index type");
  }
  
  /** Helper method to add a new index row to the exponentialIndex.
   * 
   * @param indexEntry The next index entry to be added to this index list.
   */
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

  /**
   * Override of the toString method to assist with troubleshooting/debugging.
   */
  @Override
  public String toString() {
    String result = " + " + blockID + "  [ Indexed Blocks: " + exponentialIndex.size() + "]\n";
    result += "   FirstBucketValue: " + firstBucketValue + "\n";
    for (GlobalIndexArrayItem indexItem : exponentialIndex)
      result += "    [" + indexItem.getWaitTimeAsBuckets() + " | " +  + indexItem.getWaitTimeAsBlocks() + " | " + indexItem.getMaxKeyValue() + "]\n";
    result += "   Next Global Index Block: " + nextIndexOffset + "\n";
    
    return result;
  }
  
  /*
   * (non-Javadoc)
   * @see com.umkc.bcast.data.Block#clone()
   */
  @Override
  public Block clone() {
    return null;
  }
}
