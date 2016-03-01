package com.umkc.bcast.data;

/**
 * Generic Base Class for any Index Blocks.  The main common feature between the types of index blocks
 * is that an index block should allow a reader to know where to look next for the requested information.
 * 
 * As such, the only common method should be a length of time (in blocks) to wait for the requested data.
 * 
 * @author AC010168
 *
 */
public abstract class IndexBlock extends Block {

  /**
   * Implementing versions of this method should be able to return the next data block that needs to be
   * read in order to satisfy this request.
   * 
   * <p>For example, if this is a global block, and the next place we need to go to look is the subsequent local
   * index block, the return value should be 0.  If this is a local index, and the data block is six elements
   * into the current bucket, this method should return 5.
   * 
   * <p>Think of the result of this method as being the number of block during which we can go into doze mode
   * until we need to read again.
   * 
   * <p>This method should be used for non-clustered searches
   * 
   * @param searchKey The key we are hoping to find a location for in this index
   * 
   * @return The number of blocks we can doze through before getting more information about our requested data.
   */
  public abstract int getNextReadOffset(String searchKey);
  
  /**
   * Implementing versions of this method should be able to return the next data block that needs to be
   * read in order to satisfy this request.
   * 
   * <p>For example, if this is a global block, and the next place we need to go to look is the subsequent local
   * index block, the return value should be 0.  If this is a local index, and the data block is six elements
   * into the current bucket, this method should return 5.
   * 
   * <p>Think of the result of this method as being the number of block during which we can go into doze mode
   * until we need to read again.
   * 
   * <p>This method should be used for clustered searches
   * 
   * @param clusterGroup The cluster grouping we are looking to search for our key in.
   * @param searchKey The key we are hoping to find a location for in this index
   * 
   * @return The number of blocks we can doze through before getting more information about our requested data.
   */
  public abstract int getNextReadOffset(String searchCluster, String searchKey);
  
}
