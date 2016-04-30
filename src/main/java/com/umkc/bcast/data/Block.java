package com.umkc.bcast.data;

/**
 * The basic model for a single Broadcast Block Element.
 * 
 * @author AC010168
 *
 */
public abstract class Block {

  /** Indicator for what type of bcast block this item is */
  protected BlockType blockType;
  /** Text description for this block.   */
  protected String    blockID;
  /** Indicator for home many bcast blocks away the next index blocks begins */
  protected int       nextIndexOffset;
  
  /**
   * @return the blockType
   */
  public BlockType getBlockType() {
    return blockType;
  }
  
  /**
   * @param blockType the blockType to set
   */
  public void setBlockType(BlockType blockType) {
    this.blockType = blockType;
  }
  
  /**
   * @return the blockID
   */
  public String getBlockID() {
    return blockID;
  }
  
  /**
   * @param blockID the blockID to set
   */
  public void setBlockID(String blockID) {
    this.blockID = blockID;
  }
  
  /**
   * @return the nextIndexOffset
   */
  public int getNextIndexOffset() {
    return nextIndexOffset;
  }
  
  /**
   * @param nextIndexOffset the nextIndexOffset to set
   */
  public void setNextIndexOffset(int nextIndexOffset) {
    this.nextIndexOffset = nextIndexOffset;
  }
  
  /**
   * This method is used to make a clone of the given block.
   * 
   * @return a clone of the current block
   */
  public abstract Block clone();
}
