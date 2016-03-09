package com.umkc.bcast.util;

import java.util.ArrayList;
import java.util.List;

import com.umkc.bcast.BroadcastBuilder;
import com.umkc.bcast.data.Block;
import com.umkc.bcast.data.Bucket;
import com.umkc.bcast.data.DataBlock;
import com.umkc.bcast.data.GlobalIndexArrayItem;
import com.umkc.bcast.data.impl.GlobalFlatIndexBlock;

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
public class FlatBroadcastBuilder extends BroadcastBuilder {
  
  private ArrayList<Bucket> buckets;
  
  /**
   * The basic constructor.  Instantiators need to to specify our two creation variables, exponentialFactor
   * and bucketSize.
   * 
   * @param exponentialFactor The exponential factor to be used when creating the global exponential indexes.
   * @param bucketSize        The number of data blocks that can be contained in a single bucket
   * @param useUniqueIdentifier Flag to indicate whether the uniqueIdentifier or dataKey value should be used as the
   * searchKey value when building the index.  True indicates using the uniqueIdentifier value.
   */
  public FlatBroadcastBuilder(int exponentialFactor, int bucketSize, boolean useUniqueIdentifier) {
    super(exponentialFactor, bucketSize, useUniqueIdentifier);
    
    buckets = new ArrayList<Bucket>();
  }
  
  /*
   * (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#assignDataBlocks(java.util.List)
   */
  @Override
  public void assignDataBlocks(List<DataBlock> dataBlocks) {
    if (dataBlocks.size() == 0)
      throw new RuntimeException("The provided set of data blocks was empty!");
    
    //This rule is optional, but since this is a simulation, it just makes for an easier
    //rule of thumb, otherwise it will mess with out algorithm later
    if ((dataBlocks.size() % bucketSize) != 0)
      throw new RuntimeException("WARNING!  The size of this batch does not evenly fit our bucket size!");
    
    //DEBUG
    //System.out.println ("Initial Number of Buckets:     " + buckets.size());
    //System.out.println ("Initial Number of Data Blocks: " + dataBlockIndex);
    //System.out.println ("Number of new Data Blocks:     " + dataBlocks.size());
    //System.out.println ("Bucket Size:                   " + bucketSize);
    
    //Begin breaking the data set down into buckets
    Bucket curBucket = null;
    for (int loopCtr = 0; loopCtr < dataBlocks.size(); loopCtr++) {
      //DEBUG
      //System.out.println ("Processing Block " + loopCtr);
      
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
        //DEBUG
        //System.out.println ("Finalizing Bucket " + bucketIndex + "...");
        curBucket.constructLocalIndex();
        buckets.add(curBucket);
      }
    }
    
    //DEBUG
    //System.out.println ("Current Number of Buckets:     " + buckets.size());
    //System.out.println ("Current Number of Data Blocks: " + dataBlockIndex);
  }
  
  /*
   * (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#addClusterKeys(java.util.List)
   */
  @Override
  public void addClusterKeys(List<String> clusterOrder) {
    throw new RuntimeException ("This method is not supported for Flat Index Broadcasts");
  }
  
  /*
   * (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#constructGlobalIndices()
   */
  @Override
  public void constructGlobalIndices() {
    ArrayList<Integer> expBuckets = new ArrayList<Integer>();
    expBuckets.add(0);
    expBuckets.add(1);

    int expBucket = 2;
    int expOffset = exponentialFactor;
    while (expBucket < buckets.size()) {
      expBuckets.add(expBucket);
      expBucket += expOffset;
      expOffset *= exponentialFactor;
    }
    
    /*DEBUG ---------------------------------------
    System.out.println("Printing Index Buckets");
    System.out.println ("Range: [0 - 0]");
    System.out.println ("Range: [1 - 1]");
    
    for (int i = 3; i < expBuckets.size(); i++) {
      System.out.println ("Range: [" + expBuckets.get(i - 1) + " - " + (expBuckets.get(i) - 1) + "]");
    }
    System.out.println ("Range: [" + expBuckets.get(expBuckets.size() - 1) + " - " + (buckets.size() - 1) + "]");
    
    System.out.println ("True Ranges");
    for (int values : expBuckets) {
      System.out.println ("Value: " + values);
    }
    //END DEBUG ---------------------------------------- */
    
    for (int bucketPos = 0; bucketPos < buckets.size(); bucketPos++) {
      //DEBUG
      //System.out.println ("Tackling Bucket " + (bucketPos + 1) + "...");
      Bucket curBucket = buckets.get(bucketPos);
      
      GlobalFlatIndexBlock indexBlock = new GlobalFlatIndexBlock(curBucket.getFirstBucketKey());
      indexBlock.setBlockID("GlobalIndex " + (bucketPos + 1));
      
      //The first two blocks are a little special.  We'll hardcode these first two entries
      GlobalIndexArrayItem indexItem0 = new GlobalIndexArrayItem(0, 0, curBucket.getLastBucketKey());
      indexBlock.addIndexRow(indexItem0);
      
      int endBucketPos = (bucketPos + 1) % buckets.size();
      GlobalIndexArrayItem indexItem1 = new GlobalIndexArrayItem(1, bucketSize + 1, buckets.get(endBucketPos).getLastBucketKey());
      indexBlock.addIndexRow(indexItem1);
      
      //All the middle buckets we can handle the same way.
      for (int i = 3; i < expBuckets.size(); i++) {
        //DEBUG
        //System.out.println ("Range: [" + expBuckets.get(i - 1) + " - " + (expBuckets.get(i) - 1) + "]");
        endBucketPos = (bucketPos + expBuckets.get(i) - 1) % buckets.size();
        GlobalIndexArrayItem indexItem = new GlobalIndexArrayItem(expBuckets.get(i - 1), (expBuckets.get(i - 1) * (bucketSize + 2)) - 1, 
            buckets.get(endBucketPos).getLastBucketKey());
        indexBlock.addIndexRow(indexItem);
      }
      
      //Now we need to handle the last block manually
      endBucketPos = (bucketPos + buckets.size() - 1) % buckets.size();
      GlobalIndexArrayItem indexItemEnd = new GlobalIndexArrayItem(expBuckets.get(expBuckets.size() - 1), 
          (expBuckets.get(expBuckets.size() - 1) * (bucketSize + 2)) - 1, 
          buckets.get(endBucketPos).getLastBucketKey());
      indexBlock.addIndexRow(indexItemEnd);
      
      curBucket.assignGlobalIndex(indexBlock);
      curBucket.updateNextIndexOffsets();
      
      //DEBUG
      //System.out.println (curBucket.toString());
    }
  }
  
  /*
   * (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#assembleBcast()
   */
  @Override
  public List<Block> assembleBcast() {
    //DEBUG
    //System.out.println ("Assembling the bcast");
    //System.out.println ("Expected final bcast size: " + (buckets.size() * (bucketSize + 2)));
    
    //Initialize the ArrayList to be the length of all the buckets
    List<Block> bcast = new ArrayList<Block>(buckets.size() * (bucketSize + 2));
    for (Bucket curBucket : buckets)
      bcast.addAll(curBucket.flattenBucket());
    
    //DEBUG
    //System.out.println ("Actual final bcast size:   " + bcast.size());
    
    return bcast;
  }
}
