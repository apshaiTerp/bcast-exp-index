package com.umkc.bcast.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.umkc.bcast.BroadcastBuilder;
import com.umkc.bcast.data.Block;
import com.umkc.bcast.data.BlockType;
import com.umkc.bcast.data.Bucket;
import com.umkc.bcast.data.DataBlock;
import com.umkc.bcast.data.GlobalIndexArrayItem;
import com.umkc.bcast.data.impl.GlobalClusterIndexBlock;

/**
 * Management class designed to facilitate the steps of constructing a broadcast given
 * a specfic set of parameters.
 * 
 * <p>This class will generate a skewed clustered bcast, using the provided
 * exponent factor for the index and the bucket size.
 * 
 * <p>To do this, we will need to do more with cloning buckets repeated in the bcast
 * in order to prevent object sharing from ruining our index.
 * 
 * Personal Notes:
 * While the clustered index portion of things will need to change wildly, the exponential part
 * within each cluster should work exactly like in the ClusteredBroadcastBuilder class.
 * It's going to be the figuring out how to track the 'next' occurrence of a cluster and
 * the associated offsets, along with the replication, that are going to suck.
 * 
 * @author ac010168
 *
 */
public class SkewedClusteredBroadcastBuilder extends BroadcastBuilder {

  /**
   * A mapping from cluster group to each cluster's corresponding data buckets.  This builds out
   * over time, and it is possible to incrementally add data blocks to a cluster via multiple submissions.
   */
  private Map<String, List<Bucket>> clusters;
  /** The order in which the final clusters should be organized in the final bcast. */
  private List<String> clusterOrder;
  /** The alternate order we need to use to generate the correct bcast after mixing everything up. */
  private List<String> newClusterOrder;

  /**
   * The basic constructor.  Instantiators need to to specify our two creation variables, exponentialFactor
   * and bucketSize.
   * 
   * @param exponentialFactor The exponential factor to be used when creating the global exponential indexes.
   * @param bucketSize        The number of data blocks that can be contained in a single bucket
   * @param useUniqueIdentifier Flag to indicate whether the uniqueIdentifier or dataKey value should be used as the
   * searchKey value when building the index.  True indicates using the uniqueIdentifier value.
   */
  public SkewedClusteredBroadcastBuilder(int exponentialFactor, int bucketSize, boolean useUniqueIdentifier) {
    super(exponentialFactor, bucketSize, useUniqueIdentifier);

    clusters = new HashMap<String, List<Bucket>>();
  }

  /* (non-Javadoc)
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
    //System.out.println ("Initial Number of Buckets:     " + bucketIndex);
    //System.out.println ("Initial Number of Data Blocks: " + dataBlockIndex);
    //System.out.println ("Number of new Data Blocks:     " + dataBlocks.size());
    //System.out.println ("Bucket Size:                   " + bucketSize);
    
    //First, we need to enforce the single cluster per submission rule
    String clusterGroup = dataBlocks.get(0).getClusterGroup();
    for (DataBlock curBlock : dataBlocks) {
      if (!curBlock.getClusterGroup().equalsIgnoreCase(clusterGroup))
        throw new RuntimeException("Multiple clusters were detected within this set of data blocks.");
    }
    
    //Check to see if we've already begun a set of buckets for this cluster
    List<Bucket> curCluster = null;
    curCluster = clusters.get(clusterGroup);
    if (curCluster == null)
      curCluster = new LinkedList<Bucket>();
    
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
        curCluster.add(curBucket);
      }
    }
    
    clusters.put(clusterGroup, curCluster);
    
    //DEBUG
    //System.out.println ("Current Number of Buckets:     " + bucketIndex);
    //System.out.println ("Current Number of Data Blocks: " + dataBlockIndex);
  }

  /* (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#addClusterKeys(java.util.List)
   */
  @Override
  public void addClusterKeys(List<String> clusterOrder) {
    this.clusterOrder = clusterOrder;
    
    //Perform validation of elements
    for (String curCluster : clusterOrder) {
      List<Bucket> checkBuckets = clusters.get(curCluster);
      if (checkBuckets == null)
        throw new RuntimeException("One of the clusters in the cluster order could not be found in the existing data set.");
    }
    
    //DEBUG
    //System.out.println ("Total Bucket Count: " + totalBucketCount);
    //System.out.println ("BucketIndex Count:  " + bucketIndex);
  }

  /* (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#constructGlobalIndices()
   */
  @Override
  public void constructGlobalIndices() {
    //Before this method begins, we need to actually clone out the duplicate clusters
    //And construct a truly flat version of our clusters.  I need to investigate whether there's value in
    //generating the local and exponential portions of the index, then cloning, or whether I need
    //to clone, then generate entirely.
    
    //The trick we're going to use here is as follows:
    //1) For each item in the broadcast, we're going to check if it has duplicates.
    // 1.1) If it does, we're going to create copies of that list with 'altered' names
    //      i.e. "Alpha" becomes "Alpha1", "Alpha2", ...
    //      We then clone the Buckets, add them to our new alternate Cluster
    //2) Then we can fall back on the creation process as last time, except the check for the next
    //   occurrence of a cluster needs to take into account the new naming alternative.
    
    newClusterOrder                 = new ArrayList<String>(clusterOrder.size());
    Map<String, Integer> dupOffsets = new HashMap<String, Integer>();
    Map<String, String> dupKeyMap   = new HashMap<String, String>();
    Map<String, Boolean> uniqueMap  = new HashMap<String, Boolean>();
    
    for (int clusterPos = 0; clusterPos < clusterOrder.size(); clusterPos++) {
      //Create a mapping of unique cluster names in our skewed bcast
      if (uniqueMap.get(clusterOrder.get(clusterPos)) == null) {
        uniqueMap.put(clusterOrder.get(clusterPos), new Boolean(false));
      }
      
      //look to see if this cluster has duplicates.
      int countInOrder = 0;
      for (String subOrder : clusterOrder) {
        if (subOrder.equalsIgnoreCase(clusterOrder.get(clusterPos)))
          countInOrder++;
        if (countInOrder >= 2) break;
      }
      
      if (countInOrder > 1) {
        Integer curOffsetVal = dupOffsets.get(clusterOrder.get(clusterPos));
        if (curOffsetVal == null) {
          curOffsetVal = new Integer(0);
        }
        curOffsetVal++;
        
        String newClusterName = clusterOrder.get(clusterPos) + "##" + curOffsetVal;
        newClusterOrder.add(newClusterName);
        dupKeyMap.put(newClusterName, clusterOrder.get(clusterPos));
        dupOffsets.put(clusterOrder.get(clusterPos), curOffsetVal);
        
        //Now that we've created our new entry, clone that crap
        List<Bucket> originalBuckets = clusters.get(clusterOrder.get(clusterPos));
        List<Bucket> clonedBuckets   = new ArrayList<Bucket>(originalBuckets.size());
        
        for (int bucketPos = 0; bucketPos < originalBuckets.size(); bucketPos++) {
          Bucket curBucket = originalBuckets.get(bucketPos);
          bucketIndex++;
          Bucket cloneBucket = curBucket.clone("" + bucketIndex);
          clonedBuckets.add(cloneBucket);
        }
        clusters.put(newClusterName, clonedBuckets);
        
      } else {
        newClusterOrder.add(clusterOrder.get(clusterPos));
      }
    }
    
    //DEBUG
    //System.out.println ("Number of Old Clusters: " + clusterOrder.size());
    //System.out.println ("Number of New Clusters: " + newClusterOrder.size());
    
    //for (int clusterPos = 0; clusterPos < clusterOrder.size(); clusterPos++) {
    // System.out.println (clusterOrder.get(clusterPos) + " --> " + newClusterOrder.get(clusterPos));
    //}
    
    int linearBucketCount = 0;
    //This is a fair amount of work, so here's the high level of what needs to be done
    //1)  For each cluster, in order of clusterOrder
    //2)    Calculate the offsets to the next clusters, including to loop back around to this same
    //        cluster again.
    //3)  For each bucket contained in the cluster
    //4)    Construct the cluster index, looping through all positions start with current clusterOrder index
    //4.1)  Bypass the last entry if waitTimeInBuckets = bucketIndex (shortcut to cheat if we are in the
    //        first bucket of the cluster)
    
    List<Integer> clusterOffsets = new ArrayList<Integer>(clusterOrder.size());
    
    //We are building a baseline offset between clusters, which we will adjust as we move through the buckets
    clusterOffsets.add(bucketIndex);
    int offsetBase = 0;
    for (int i = 1; i < clusterOrder.size(); i++) {
      offsetBase += clusters.get(clusterOrder.get(i - 1)).size();
      clusterOffsets.add(offsetBase);
    }
    
    //DEBUG
    //System.out.println ("Setting initial cluster offsets:");
    //for (int i = 0; i < clusterOrder.size(); i++) {
    //  System.out.println ("  clusterGroup: " + clusterOrder.get(i) + ":  [buckets: " + 
    //      clusters.get(clusterOrder.get(i)).size() + " | initialOffset: " + clusterOffsets.get(i) + "]");
    //}
    
    //For all clusters
    for (int clusterPos = 0; clusterPos < clusterOrder.size(); clusterPos++) {
      
      //DEBUG
      System.out.println ("Processing Cluster " + newClusterOrder.get(clusterPos) + " [" + clusterOrder.get(clusterPos) + "]");
      
      //Get the list of all buckets under this cluster
      int bucketPos = 0;
      for (Bucket curBucket : clusters.get(newClusterOrder.get(clusterPos))) {
        //DEBUG
        //System.out.println (" > Building Index for Bucket " + (bucketPos + 1));
        
        //Create the Index Block
        linearBucketCount++;
        GlobalClusterIndexBlock indexBlock = new GlobalClusterIndexBlock(clusterOrder.get(clusterPos), curBucket.getFirstBucketKey());
        indexBlock.setBlockID("GlobalIndex " + linearBucketCount);
        
        //DEBUG
        //System.out.println ("   > Cluster Index Block");
        //Build the cluster level global index block
        
        //Note: In this case, we need to not include some rows that are the later entries of the same
        //clusters
        //To do this, we're going to start by initializing a found list
        for (String key : uniqueMap.keySet())
          uniqueMap.put(key, new Boolean(false));
        
        for (int eachClusterPos = 0; eachClusterPos < clusterOrder.size(); eachClusterPos++) {
          int nextClusterPos = (eachClusterPos + clusterPos + 1) % clusterOrder.size();
          if (clusterOffsets.get(nextClusterPos) != bucketIndex) {
            //DEBUG
            //System.out.println ("     + [ " + clusterOrder.get(nextClusterPos) + " - " + newClusterOrder.get(nextClusterPos) + 
            //    " | " + clusterOffsets.get(nextClusterPos) + " ]");
            
            if (uniqueMap.get(clusterOrder.get(nextClusterPos)) == false) {
              //DEBUG
              //System.out.println ("      We are adding this index entry!");
              
              uniqueMap.put(clusterOrder.get(nextClusterPos), new Boolean(true));
              
              GlobalIndexArrayItem indexItem = new GlobalIndexArrayItem(clusterOffsets.get(nextClusterPos), (clusterOffsets.get(nextClusterPos) * (bucketSize + 2)) - 1, 
                  clusterOrder.get(nextClusterPos));
              indexBlock.addClusterIndexRow(indexItem);
            } else {
              //DEBUG
              //System.out.println ("      We are skipping this index entry!");
            }
          }
        }
        
        //Build the exponential index, but only to the end of the bucket
        //We will need to be more explicit in handling the first few rows, since those are handled
        //differently and outside any loop structure.
        int bucketsRemaining = clusters.get(clusterOrder.get(clusterPos)).size() - (bucketPos + 1);
        ArrayList<Integer> expBuckets = new ArrayList<Integer>();
        expBuckets.add(0);
        if (bucketsRemaining >= 1)
          expBuckets.add(1);
        if (bucketsRemaining >= 2) {
          int expBucket = 2;
          int expOffset = exponentialFactor;
          while (expBucket <= bucketsRemaining) {
            expBuckets.add(expBucket);
            expBucket += expOffset;
            expOffset *= exponentialFactor;
          }
        }
        
        /* DEBUG ---------------------------------------
        System.out.println("Printing Index Buckets");
        System.out.println ("Range: [0 - 0]");
        System.out.println ("Range: [1 - 1]");
        
        for (int i = 3; i < expBuckets.size(); i++) {
          System.out.println ("Range: [" + expBuckets.get(i - 1) + " - " + (expBuckets.get(i) - 1) + "]");
        }
        System.out.println ("Range: [" + expBuckets.get(expBuckets.size() - 1) + " - " + bucketsRemaining + "]");
        
        System.out.println ("True Ranges");
        for (int values : expBuckets) {
          System.out.println ("Value: " + values);
        }
        //END DEBUG ---------------------------------------- */
        
        GlobalIndexArrayItem indexItem0 = new GlobalIndexArrayItem(0, 0, curBucket.getLastBucketKey());
        indexBlock.addExponentialIndexRow(indexItem0);
        
        List<Bucket> bucketList = clusters.get(clusterOrder.get(clusterPos));
        
        int endBucketPos = bucketPos + 1;
        if (expBuckets.size() >= 2) {
          GlobalIndexArrayItem indexItem1 = new GlobalIndexArrayItem(1, bucketSize + 1, bucketList.get(bucketPos + 1).getLastBucketKey());
          indexBlock.addExponentialIndexRow(indexItem1);
        }
        
        //All the middle buckets we can handle the same way.
        for (int i = 3; i < expBuckets.size(); i++) {
          //DEBUG
          //System.out.println ("Range: [" + expBuckets.get(i - 1) + " - " + (expBuckets.get(i) - 1) + "]");
          endBucketPos = bucketPos + expBuckets.get(i) - 1;
          GlobalIndexArrayItem indexItem = new GlobalIndexArrayItem(expBuckets.get(i - 1), (expBuckets.get(i - 1) * (bucketSize + 2)) - 1, 
              bucketList.get(endBucketPos).getLastBucketKey());
          indexBlock.addExponentialIndexRow(indexItem);
        }
        
        //Now we need to handle the last block manually
        if (expBuckets.size() >= 3) {
          endBucketPos = bucketList.size() - 1;
          GlobalIndexArrayItem indexItemEnd = new GlobalIndexArrayItem(expBuckets.get(expBuckets.size() - 1), 
              (expBuckets.get(expBuckets.size() - 1) * (bucketSize + 2)) - 1, 
              bucketList.get(endBucketPos).getLastBucketKey());
          indexBlock.addExponentialIndexRow(indexItemEnd);
        }
        
        //We now need to decrement our index offsets
        for (int offsetPos = 0; offsetPos < clusterOffsets.size(); offsetPos++) {
          if (clusterOffsets.get(offsetPos) == 1)
            clusterOffsets.set(offsetPos, bucketIndex);
          else clusterOffsets.set(offsetPos, clusterOffsets.get(offsetPos) - 1);
        }
        
        curBucket.assignGlobalIndex(indexBlock);
        curBucket.updateNextIndexOffsets();
        
        //DEBUG
        //System.out.println (curBucket.toString());
        
        bucketPos++;
      }//end for buckets in this cluster
    }//end for all clusters
    
  }

  /* (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#assembleBcast()
   */
  @Override
  public List<Block> assembleBcast() {
    //DEBUG
    //System.out.println ("Assembling the bcast");
    
    int expectedBlockCount = 0;
    for (String cluster : clusterOrder)
      expectedBlockCount += (clusters.get(cluster).size() * (bucketSize + 2));
    
    //DEBUG
    //System.out.println ("Expected final bcast size: " + expectedBlockCount);
    
    List<Block> bcast = new ArrayList<Block>(expectedBlockCount);
    
    for (String cluster : newClusterOrder) {
      List<Bucket> bucketList = clusters.get(cluster);
      for (Bucket curBucket : bucketList)
        bcast.addAll(curBucket.flattenBucket());
    }
    
    //One last thing we need to do is rename the blocks to match the order they are now
    //lined up in for the bcast.
    int globalIndexCount = 0;
    int localIndexCount  = 0;
    int dataBlockCount   = 0;
    
    for (Block curBlock : bcast) {
      if (curBlock.getBlockType() == BlockType.GLOBAL_CLUSTER_INDEX_BLOCK) {
        globalIndexCount++;
        curBlock.setBlockID("GlobalIndex " + globalIndexCount);
      }
      if (curBlock.getBlockType() == BlockType.LOCAL_INDEX_BLOCK) {
        localIndexCount++;
        curBlock.setBlockID("LocalIndex " + localIndexCount);
      }
      if (curBlock.getBlockType() == BlockType.DATA_BLOCK) {
        dataBlockCount++;
        curBlock.setBlockID("DataBlock" + dataBlockCount);
      }
    }

    //DEBUG
    //System.out.println ("Actual final bcast size:   " + bcast.size());
    
    return bcast;
  }

}
