package com.umkc.bcast.util;

import java.util.ArrayList;

import com.umkc.bcast.data.Bucket;
import com.umkc.bcast.data.DataBlock;

/**
 * Management class designed to facilitate the steps of constructing a broadcast given
 * a specific set of parameters.
 * 
 * <p>This class needs to know the exponent factor and bucket size to generate the correct
 * bcast.
 * 
 * @author ac010168
 *
 */
public class BroadcastBuilder {
  
  /** The exponent factor used to generate our index */
  private int exponentialFactor;
  /** The size of the buckets */
  private int bucketSize;
  /** Flag that indicates whether to use the uniqueIdentifier value or the dataKey value from the underlying data blocks. */
  private boolean useUniqueIdentifier;
  
  private ArrayList<Bucket> buckets;
  
  private int dataBlockIndex;
  private int bucketIndex;
  
  /**
   * The basic constructor.  Instantiators need to to specify our two creation variables, exponentialFactor
   * and bucketSize.
   * 
   * @param exponentialFactor
   * @param bucketSize
   * @param useUniqueIdentifier
   */
  public BroadcastBuilder(int exponentialFactor, int bucketSize, boolean useUniqueIdentifier) {
    this.exponentialFactor   = exponentialFactor;
    this.bucketSize          = bucketSize;
    this.useUniqueIdentifier = useUniqueIdentifier;
    
    buckets = new ArrayList<Bucket>();
    
    dataBlockIndex = 0;
    bucketIndex    = 0;
  }
  
  public void assignDataBlocks(ArrayList<DataBlock> dataBlocks) {
    if (dataBlocks.size() == 0)
      throw new RuntimeException("The provided set of data blocks was empty!");
    
    //This rule is a optional, but since this is a simulation, it just makes for an easier
    //rule of thumb
    //if ((dataBlocks.size() % bucketSize) != 0)
    //  throw new RuntimeException("WARNING!  The size of this batch does not evenly fit our bucket size!");
    
    //DEBUG
    System.out.println ("Initial Number of Buckets:     " + buckets.size());
    System.out.println ("Initial Number of Data Blocks: " + dataBlockIndex);
    System.out.println ("Number of new Data Blocks:     " + dataBlocks.size());
    System.out.println ("Bucket Size:                   " + bucketSize);
    
    //Begin breaking the data set down into buckets
    Bucket curBucket = null;
    for (int loopCtr = 0; loopCtr < dataBlocks.size(); loopCtr++) {
      //DEBUG
      System.out.println ("Processing Block " + loopCtr);
      
      //Check to see if we need to start a new bucket
      if ((loopCtr % bucketSize) == 0) {
        bucketIndex++;
        curBucket = new Bucket("" + bucketIndex, useUniqueIdentifier);
      }
      
      DataBlock curBlock = dataBlocks.get(loopCtr);
      dataBlockIndex++;
      
      curBlock.setBlockID("Data Block " + dataBlockIndex);
      curBucket.addDataBlock(curBlock);
      
      //If we've completed a bucket, time to work through the construction steps
      if ((dataBlockIndex == dataBlocks.size()) || ((dataBlockIndex % bucketSize) == 0)) {
        System.out.println ("Finalizing Bucket " + bucketIndex + "...");
        curBucket.constructLocalIndex();
        buckets.add(curBucket);
      }
    }
    
    //DEBUG
    System.out.println ("Current Number of Buckets:     " + buckets.size());
    System.out.println ("Current Number of Data Blocks: " + dataBlockIndex);
  }

}
