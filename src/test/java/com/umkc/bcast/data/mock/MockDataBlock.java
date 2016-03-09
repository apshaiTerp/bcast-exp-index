package com.umkc.bcast.data.mock;

import com.umkc.bcast.data.BlockType;
import com.umkc.bcast.data.DataBlock;

/**
 * A simple data block to be used in testing.  It's a simple unit that consists of a String description
 * and an integer data value.  The contents don't really matter, as long as we have a means to sort the
 * data.
 * 
 * @author ac010168
 */
public class MockDataBlock extends DataBlock {

  private String mockDataDescription;
  private int    mockDataValue;
  
  public MockDataBlock(String mockDataDescription, int mockDataValue, String uniqueIdentifier, String clusterGroup, String dataKey) {
    this.mockDataDescription = mockDataDescription;
    this.mockDataValue       = mockDataValue;
    this.uniqueIdentifier    = uniqueIdentifier;
    this.clusterGroup        = clusterGroup;
    this.dataKey             = dataKey;
    blockType                = BlockType.DATA_BLOCK;
    nextIndexOffset          = -1;
  }
  
  /**
   * Override of the toString method to assist with troubleshooting/debugging.
   */
  @Override
  public String toString() {
    String result = "   + " + blockID + "  [ " + uniqueIdentifier + " | " + clusterGroup + " : " + dataKey + " ]\n" + 
                    "    mockDataDescription: " + mockDataDescription + "\n" +
                    "    mockDataValue:       " + mockDataValue + "\n" + 
                    "   Next Global Index Block: " + nextIndexOffset + "\n";
    return result;
  }

  /**
   * @return the mockDataDescription
   */
  public String getMockDataDescription() {
    return mockDataDescription;
  }

  /**
   * @param mockDataDescription the mockDataDescription to set
   */
  public void setMockDataDescription(String mockDataDescription) {
    this.mockDataDescription = mockDataDescription;
  }

  /**
   * @return the mockDataValue
   */
  public int getMockDataValue() {
    return mockDataValue;
  }

  /**
   * @param mockDataValue the mockDataValue to set
   */
  public void setMockDataValue(int mockDataValue) {
    this.mockDataValue = mockDataValue;
  }
}
