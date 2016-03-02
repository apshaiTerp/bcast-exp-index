package com.umkc.bcast.data.impl;

import java.util.ArrayList;

import com.umkc.bcast.data.BlockType;
import com.umkc.bcast.data.GlobalIndexArrayItem;
import com.umkc.bcast.data.IndexBlock;

/**
 * This is an implementation of a Global Index block in a Clustered Broadcast Structure.
 * This object can be used for clustered or skewed index structures, and is a slightly more
 * complex object than the simpler GlobalFlatIndexBlock.
 * 
 * <p>This index block has three main elements:  The current range identifier (with cluster
 * name and first key value for this bucket), the clustered meta-section index list, 
 * and a smaller version of the exponential index, localized to this clustered meta-section.
 * 
 * <p>Assume our broadcast has 4 clusters, ALPHA, BETA, GAMMA, and DELTA.  Each cluster appears
 * only once during the broadcast.  Each cluster contains 20 buckets, each containing their own
 * index blocks and 10 data elements.
 * 
 * <p>The following represents the construction of the first bucket in the GAMMA cluster,
 * with each bucket being made up of words beginning with the same letter of the alphabet.
 * The completed data structure should look something like this:
 * 
 * <ul><li>clusterGroup = 'GAMMA'</li>
 *     <li>firstBucketValue = 'aardvark'</li>
 *     <li>clusterIndex: as [waitTimeInBuckets, waitTimeInBlocks, maxKeyValue]
 *     <ol><li>[20, 239, 'DELTA']</li>
 *         <li>[40, 479, 'ALPHA']</li>
 *         <li>[60, 719, 'BETA']</li>
 *         <li>[80, 959, 'GAMMA']</li></ol>
 *     </li>
 *     <li>exponentialIndex:  as [waitTimeInBuckets, waitTimeInBlocks, maxKeyValue]
 *     <ol><li>[0, 0, 'axe'] (Bucket 0-0)</li>
 *         <li>[1, 11, 'buzz'] (Bucket 1-1)</li>
 *         <li>[2, 23, 'dumb'] (Bucket 2-3)</li>
 *         <li>[4, 47, 'hype'] (Bucket 4-7)</li>
 *         <li>[8, 95, 'pyre'] (Bucket 8-15)</li>
 *         <li>[16, 191, 'tubes'] (Bucket 16-19)</li></ol>
 *     </li>
 * </ul>
 * 
 * We list the GAMMA block again, which, while unlikely to trigger when hitting the very beginning
 * of the cluster, would be important if we needed an element in bucket 5, but begin our query
 * efforts in bucket 7.  If our clusterGroup matches, but our searchKey is less that our
 * firstBucketValue, it means we have to wait for our cluster to come around again.
 * 
 * <p>Another important difference is that our exponential index does not need to loop, and only needs
 * to cover through the end of the cluster section.  While perhaps a bit tedious, here's an example of
 * what the index should look like for bucket 13 of the GAMMA block:
 * 
 * <ul><li>clusterGroup = 'GAMMA'</li>
 *     <li>firstBucketValue = 'madness'</li>
 *     <li>clusterIndex: as [waitTimeInBuckets, waitTimeInBlocks, maxKeyValue]
 *     <ol><li>[20, 239, 'DELTA']</li>
 *         <li>[40, 479, 'ALPHA']</li>
 *         <li>[60, 719, 'BETA']</li>
 *         <li>[80, 959, 'GAMMA']</li></ol>
 *     </li>
 *     <li>exponentialIndex:  as [waitTimeInBuckets, waitTimeInBlocks, maxKeyValue]
 *     <ol><li>[0, 0, 'mynock'] (Bucket 0-0)</li>
 *         <li>[1, 11, 'nublar'] (Bucket 1-1)</li>
 *         <li>[2, 23, 'pyre'] (Bucket 2-3)</li>
 *         <li>[4, 47, 'tubes'] (Bucket 4-7)</li></ol>
 *     </li>
 * </ul>
 *
 * The primary access method here should be the getNextReadOffset() method.  This method
 * should return the waitTimeInBlocks value appropriate based on the value provided.
 * 
 * <p>Using the first sample above, searching for ['GAMMA', 'ant'] should return 0, searching for 
 * ['GAMMA', 'horse'] should return 47, searching for ['GAMMA', 'thing'] should return 191, and
 * searching for ['BETA', 'fish'] should return 719.
 * 
 * @author AC010168
 *
 */
public class GlobalClusterIndexBlock extends IndexBlock {

  /**
   * This value is the identifier for which cluster grouping this index block resides in.  It's
   * used to help improve identification of when we might need to go into doze mode to wait
   * for other clusters to come around.
   */
  private String clusterGroup;
  
  /**
   * The first value in the bucket, useful for when we're trying to compare against values
   * in the current bucket.
   */
  private String firstBucketValue;
  
  /** 
   * The list of index keys for the global index, stored as an array of options.  An initialized
   * list should always have at least one element (pointing to the local storage block within the
   * current bucket).
   **/
  private ArrayList<GlobalIndexArrayItem> clusterIndex;

  /** 
   * The list of index keys for the global index, stored as an array of options.  An initialized
   * list should always have at least one element (pointing to the local storage block within the
   * current bucket).
   **/
  private ArrayList<GlobalIndexArrayItem> exponentialIndex;
  
  /** Simple Constructor */
  public GlobalClusterIndexBlock() {
    blockType        = BlockType.GLOBAL_CLUSTER_INDEX_BLOCK;
    clusterGroup     = null;
    firstBucketValue = null;
    clusterIndex     = new ArrayList<GlobalIndexArrayItem>();
    exponentialIndex = new ArrayList<GlobalIndexArrayItem>();
  }
  
  public GlobalClusterIndexBlock(String clusterGroup, String firstBucketValue) {
    blockType             = BlockType.GLOBAL_CLUSTER_INDEX_BLOCK;
    this.clusterGroup     = clusterGroup;
    this.firstBucketValue = firstBucketValue;
    clusterIndex          = new ArrayList<GlobalIndexArrayItem>();
    exponentialIndex      = new ArrayList<GlobalIndexArrayItem>();
  }
  
  /* (non-Javadoc)
   * @see com.umkc.bcast.data.IndexBlock#getNextReadOffset(String)
   */
  @Override
  public int getNextReadOffset(String searchKey) {
    throw new RuntimeException ("This version of getNextReadOffset is not supported for this Index type");
  }
  
  /* (non-Javadoc)
   * @see com.umkc.bcast.data.IndexBlock#getNextReadOffset(String, String)
   */
  @Override
  public int getNextReadOffset(String searchCluster, String searchKey) {
    //DEBUG
    System.out.println ("Reading " + blockID + ".  Looking for where [" + searchCluster + ", " + searchKey + "] should be found...");
    
    //TODO This is where the real work of parsing the index block takes place.
    
    return -1;
  }

  /** Helper method to add a new index row to the exponentialIndex. */
  public void addClusterIndexRow(GlobalIndexArrayItem indexEntry) {
    clusterIndex.add(indexEntry);
  }

  /** Helper method to add a new index row to the exponentialIndex. */
  public void addExponentialIndexRow(GlobalIndexArrayItem indexEntry) {
    exponentialIndex.add(indexEntry);
  }

  /**
   * @return the clusterGroup
   */
  public String getClusterGroup() {
    return clusterGroup;
  }

  /**
   * @param clusterGroup the clusterGroup to set
   */
  public void setClusterGroup(String clusterGroup) {
    this.clusterGroup = clusterGroup;
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
