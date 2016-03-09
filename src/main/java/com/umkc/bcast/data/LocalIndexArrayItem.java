package com.umkc.bcast.data;

/**
 * A simple row for the local index.  It is a listing of the key values contained within this bucket, 
 * as well as the offset (doze time) for where the requested data is located.
 * 
 * <p>A local index consists of an array of these objects.  This structure can be used, even in a
 * clustered setting, because the global index lookup handles the clustered evaluation.
 * 
 * @author ac010168
 *
 */
public class LocalIndexArrayItem {

  /** The amount of time to wait until we should read again in blocks (blocks are our time measurement) */
  private int waitTimeAsBlocks;
  /** The block key value stored within the indicated block */
  private String blockKeyValue;

  public LocalIndexArrayItem(int waitTimeAsBlocks, String blockKeyValue) {
    this.waitTimeAsBlocks = waitTimeAsBlocks;
    this.blockKeyValue    = blockKeyValue;
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
   * @return the blockKeyValue
   */
  public String getBlockKeyValue() {
    return blockKeyValue;
  }

  /**
   * @param blockKeyValue the blockKeyValue to set
   */
  public void setBlockKeyValue(String blockKeyValue) {
    this.blockKeyValue = blockKeyValue;
  }
}
