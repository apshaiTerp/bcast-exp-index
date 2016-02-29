package com.umkc.bcast.data;

/**
 * A simple row from the exponential global index.
 * 
 * @author AC010168
 */
public class GlobalFlatArrayItem {
  
  /** The lower bounding offset for the current index row */
  private int    lowerBoundOffset;
  /** The upper bounding offset for the current index row */
  private int    upperBoundOffset;
  /** The maximum key value stored within this range of buckets */
  private String maxKeyValue;

  /** Simple Constructor.  Will need to have range values computed externally */
  public GlobalFlatArrayItem(int lowerBoundOffset, int upperBoundOffset, String maxKeyValue) {
    this.lowerBoundOffset = lowerBoundOffset;
    this.upperBoundOffset = upperBoundOffset;
    this.maxKeyValue      = maxKeyValue;
  }
  
  /**
   * This method returns true if the current searching key is less than or equal to the maxKeyValue
   * for this index range
   * 
   * @param searchKey
   * 
   * @return
   */
  public boolean containsKey(String searchKey) {
    if (searchKey.compareTo(maxKeyValue) <= 0)
      return true;
    return false;
  }

  /**
   * @return the lowerBoundOffset
   */
  public int getLowerBoundOffset() {
    return lowerBoundOffset;
  }

  /**
   * @param lowerBoundOffset the lowerBoundOffset to set
   */
  public void setLowerBoundOffset(int lowerBoundOffset) {
    this.lowerBoundOffset = lowerBoundOffset;
  }

  /**
   * @return the upperBoundOffset
   */
  public int getUpperBoundOffset() {
    return upperBoundOffset;
  }

  /**
   * @param upperBoundOffset the upperBoundOffset to set
   */
  public void setUpperBoundOffset(int upperBoundOffset) {
    this.upperBoundOffset = upperBoundOffset;
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
