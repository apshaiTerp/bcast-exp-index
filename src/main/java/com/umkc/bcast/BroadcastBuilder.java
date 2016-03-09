package com.umkc.bcast;

import java.util.List;

import com.umkc.bcast.data.Block;
import com.umkc.bcast.data.DataBlock;

/**
 * @author ac010168
 *
 */
public abstract class BroadcastBuilder {

  /** The exponent factor used to generate our index */
  protected int exponentialFactor;
  /** The size of the buckets */
  protected int bucketSize;
  /** Flag that indicates whether to use the uniqueIdentifier value or the dataKey value from the underlying data blocks. */
  protected boolean useUniqueIdentifier;
  
  /** Used to help construct meaningful Data Block identifiers */
  protected int dataBlockIndex;
  /** User to help construct meaningful Bucket Level identifiers */
  protected int bucketIndex;
  
  /**
   * The basic constructor.  Instantiators need to to specify our two creation variables, exponentialFactor
   * and bucketSize.
   * 
   * @param exponentialFactor The exponential factor to be used when creating the global exponential indexes.
   * @param bucketSize        The number of data blocks that can be contained in a single bucket
   * @param useUniqueIdentifier Flag to indicate whether the uniqueIdentifier or dataKey value should be used as the
   * searchKey value when building the index.  True indicates using the uniqueIdentifier value.
   */
  public BroadcastBuilder(int exponentialFactor, int bucketSize, boolean useUniqueIdentifier) {
    this.exponentialFactor   = exponentialFactor;
    this.bucketSize          = bucketSize;
    this.useUniqueIdentifier = useUniqueIdentifier;
    
    dataBlockIndex = 0;
    bucketIndex    = 0;
  }
  
  /**
   * Helper method to assign a list of data blocks to the broadcast.  This method makes several
   * assumptions, some of which may be untested by implementing classes, but are important for
   * the simplicity of bcast construction.
   * 
   * <p>First, the list provided should not be empty.  If it is, throw an error.
   * 
   * <p>Second, we assume that the list of data blocks being provided can evenly be distributed
   * into [bucketSize] buckets.  If not, this method should throw an error.
   * 
   * <p>Third, we assume that all elements in this data block belong to the same cluster.  If
   * we are building a flat index, then this is irrelevant, but if we are building a clustered
   * index, this is important.  If this is not the case, an error should be thrown.
   * 
   * <p>Fourth, each addition of data blocks should be unique.  That is to say that if the same
   * set of data blocks is added twice, it will be added as duplicates.  Similarly, if multiple
   * additions are made to the same cluster group, they should be appended.
   * 
   * @param dataBlocks The list of data blocks that should be added to this cluster
   */
  public abstract void assignDataBlocks(List<DataBlock> dataBlocks);
  
  /**
   * This method should only be used by clustered approaches.  This list should define
   * the ordering for both 'flat' clustering and skewed clustering, where buckets may some
   * clusters may occur multiple times during the bcast.
   * 
   * @param clusterOrder The ordering of clusters to be used in this bcast
   */
  public abstract void addClusterKeys(List<String> clusterOrder);
  
  /**
   * Helper method to create the global index entries for each block.  This method should be
   * invoked only after all the data elements have been added, and after the clusterOrder list
   * has been decided.
   * 
   * <p> The implementation of this method will get slightly more complicated in a skewed
   * bcast, as different occurrences of the same data cluster will have different global
   * index blocks (as the timing of when the 'next' occurrence of a cluster block may be
   * in a different order).
   */
  public abstract void constructGlobalIndices();
  
  /**
   * This method will take the finalized buckets into a single bcast.  It should take all the
   * work previously done, flatten it, and generate the final bcast list.
   * 
   * @return The completed bcast as a single List.
   */
  public abstract List<Block> assembleBcast();

}
