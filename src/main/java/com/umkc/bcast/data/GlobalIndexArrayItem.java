package com.umkc.bcast.data;

/**
 * A simple row from the exponential global index.  We track three key items per row.
 * The first is the maximum key value contained as the last element in this set of buckets.
 * Then we have the time to wait to find our data in both blocks (our substitute time measurement)
 * and in buckets, which helps primarily for troubleshooting index access.
 * 
 * <p>So, for example, our value is in the next bucket, and the bucket size is 10 data nodes and 
 * terminated by a key value of 'final', our values would look like this:
 * <ul><li>maxKeyValue = "final"</li>
 * <li>waitTimeAsBuckets = 1</li>
 * <li>waitTimeAsBlocks = 11 (1 to skip the local index block, and 10 to skip the rest of the current bucket)</li></ul>
 * 
 * @author AC010168
 */
public class GlobalIndexArrayItem {
  
  /** The amount of time to wait until we should read again in buckets */
  private int waitTimeAsBuckets;
  /** The amount of time to wait until we should read again in blocks (blocks are our time measurement) */
  private int waitTimeAsBlocks;
  /** The maximum key value stored within this range of buckets */
  private String maxKeyValue;

  /** Simple Constructor.  Will need to have range values computed externally 
   * 
   * @param waitTimeAsBuckets The wait time in units of buckets
   * @param waitTimeAsBlocks  The wait time in units of blocks
   * @param maxKeyValue       The max Key value contained within this index range
   */
  public GlobalIndexArrayItem(int waitTimeAsBuckets, int waitTimeAsBlocks, String maxKeyValue) {
    this.waitTimeAsBuckets = waitTimeAsBuckets;
    this.waitTimeAsBlocks  = waitTimeAsBlocks;
    this.maxKeyValue       = maxKeyValue;
  }
  
  /**
   * This method returns true if the current searching key is less than or equal to the maxKeyValue
   * for this index range
   * 
   * @param searchKey The search key we want to find
   * 
   * @return true if the searchKey value is lexically before the end of this index entry, false if not.
   */
  public boolean containsKey(String searchKey) {
    if (searchKey.compareTo(maxKeyValue) <= 0)
      return true;
    return false;
  }

  /**
   * @return the waitTimeAsBuckets
   */
  public int getWaitTimeAsBuckets() {
    return waitTimeAsBuckets;
  }

  /**
   * @param waitTimeAsBuckets the waitTimeAsBuckets to set
   */
  public void setWaitTimeAsBuckets(int waitTimeAsBuckets) {
    this.waitTimeAsBuckets = waitTimeAsBuckets;
  }

  /**
   * @return the waitTimeAsBlocks
   */
  public int getWaitTimeAsBlocks() {
    return waitTimeAsBlocks;
  }

  /**
   * @param waitTimeAsBlocks the waitTimeAsBlocks to set
   */
  public void setWaitTimeAsBlocks(int waitTimeAsBlocks) {
    this.waitTimeAsBlocks = waitTimeAsBlocks;
  }

  /**
   * @return the maxKeyValue
   */
  public String getMaxKeyValue() {
    return maxKeyValue;
  }

  /**
   * @param maxKeyValue the maxKeyValue to set
   */
  public void setMaxKeyValue(String maxKeyValue) {
    this.maxKeyValue = maxKeyValue;
  }
}
