package com.umkc.bcast.data;

import java.util.ArrayList;

import com.umkc.bcast.data.impl.LocalIndexBlock;

/**
 * This class is only really used to help organize buckets.  The basic Bucket workflow should be
 * initiated after the data blocks have already been created.  Once that's done, here's the flow
 * of the Bucket manipulation tasks:
 * 
 * <ol><li>Initialize the Bucket.  Specify whether we are using the uniqueIdentifier (for flat
 *         indexes) or the simpler data key (for clustered indexes).</li>
 *     <li>Add data blocks to this Bucket.</li>
 *     <li>Externally construct the Global Index blocks, using the getFirstBucketKey() and
 *         getLastBucketKey() methods of this Bucket</li>
 *     <li>Add the Global Index to this Bucket.</li>
 *     <li>Set the next global index offsets using the updateNextIndexOffsets() method.</li>
 *     <li>Use the flattenIndex() method to flatten out this bucket for the finalized bcast.</li>
 * </ol>
 * 
 * @author ac010168
 *
 */
public class Bucket {
  
  private IndexBlock globalIndex;
  private LocalIndexBlock localIndex;
  
  private ArrayList<DataBlock> dataBlocks;
  
  private boolean useUniqueIdentifier;
  
  /**
   * Basic Constructor.  Used to initialize a Bucket.
   * 
   * @param bucketLabel A Text label to distinguish this bucket from other buckets.
   * @param useUniqueIdentifier Flag that indicates whether to use the uniqueIdentifier value or
   * the dataKey value from the underlying data blocks.
   */
  public Bucket(String bucketLabel, boolean useUniqueIdentifier) {
    localIndex = new LocalIndexBlock();
    localIndex.setBlockID("LocalIndex " + bucketLabel);
  }

  /**
   * Add a new data block to the bucket.
   * 
   * @param dataBlock
   */
  public void addDataBlock(DataBlock dataBlock) {
    dataBlocks.add(dataBlock);
  }
  
  /**
   * Helper method to construct the local index.  This should be invoked once all the required data blocks
   * have been added to the bucket.
   */
  public void constructLocalIndex() {
    int pos = 0;
    for (DataBlock curBlock : dataBlocks) {
      LocalIndexArrayItem arrayItem = new LocalIndexArrayItem(pos, useUniqueIdentifier ? curBlock.getUniqueIdentifier() : curBlock.getDataKey());
      localIndex.addIndexRow(arrayItem);
      
      pos++;
    }
  }
  
  /**
   * Helper method to get the first key value stored in this bucket.
   * 
   * @return The first search key value in this bucket
   */
  public String getFirstBucketKey() {
    if (useUniqueIdentifier) return dataBlocks.get(0).getUniqueIdentifier();
    else                     return dataBlocks.get(0).getDataKey();
  }
  
  /**
   * Helper method to get the last key value stored in this bucket.
   * 
   * @return The last search key value in this bucket
   */
  public String getLastBucketKey() {
    if (useUniqueIdentifier) return dataBlocks.get(dataBlocks.size() - 1).getUniqueIdentifier();
    else                     return dataBlocks.get(dataBlocks.size() - 1).getDataKey();
  }
  
  /**
   * Assign the constructed global index block to this bucket.
   * 
   * @param globalIndex
   */
  public void assignGlobalIndex(IndexBlock globalIndex) {
    this.globalIndex = globalIndex;
  }
  
  /**
   * Helper method to assign the offset to the next global index block to all blocks contained
   * in this bucket.
   */
  public void updateNextIndexOffsets() {
    int offset = dataBlocks.size() + 1;
    globalIndex.setNextIndexOffset(offset);
    offset--;
    localIndex.setNextIndexOffset(offset);
    offset--;
    for (DataBlock curBlock : dataBlocks) {
      curBlock.setNextIndexOffset(offset);
      offset--;
    }
  }
  
  /**
   * Helper method to flatten out the bucket to be added to a finalized bcast list.
   * 
   * @return
   */
  public ArrayList<Block> flattenBucket() {
    ArrayList<Block> blocks = new ArrayList<Block>(dataBlocks.size() + 2);
    blocks.add(globalIndex);
    blocks.add(localIndex);
    blocks.addAll(dataBlocks);
    
    return blocks;
  }
}
