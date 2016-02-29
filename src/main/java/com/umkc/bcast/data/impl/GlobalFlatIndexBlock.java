package com.umkc.bcast.data.impl;

import com.umkc.bcast.data.IndexBlock;

/**
 * This is an implementation of a Global Index block in a Flat Broadcast Structure.
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
  
  
  /* (non-Javadoc)
   * @see com.umkc.bcast.data.IndexBlock#getNextReadOffset()
   */
  @Override
  public int getNextReadOffset() {
    // TODO Auto-generated method stub
    
    
    return -1;
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
