package com.umkc.bcast.util;

import java.util.List;

import com.umkc.bcast.BroadcastBuilder;
import com.umkc.bcast.data.Block;
import com.umkc.bcast.data.DataBlock;

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

  public SkewedClusteredBroadcastBuilder(int exponentialFactor, int bucketSize, boolean useUniqueIdentifier) {
    super(exponentialFactor, bucketSize, useUniqueIdentifier);
    // TODO Auto-generated constructor stub
  }

  /* (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#assignDataBlocks(java.util.List)
   */
  @Override
  public void assignDataBlocks(List<DataBlock> dataBlocks) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#addClusterKeys(java.util.List)
   */
  @Override
  public void addClusterKeys(List<String> clusterOrder) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#constructGlobalIndices()
   */
  @Override
  public void constructGlobalIndices() {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see com.umkc.bcast.BroadcastBuilder#assembleBcast()
   */
  @Override
  public List<Block> assembleBcast() {
    // TODO Auto-generated method stub
    return null;
  }

}
