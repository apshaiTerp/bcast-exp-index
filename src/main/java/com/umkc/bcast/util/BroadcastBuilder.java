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
  
  private ArrayList<Bucket> buckets;
  
  /**
   * The basic constructor.  Instantiators need to to specify our two creation variables, exponentialFactor
   * and bucketSize.
   * 
   * @param exponentialFactor
   * @param bucketSize
   */
  public BroadcastBuilder(int exponentialFactor, int bucketSize) {
    this.exponentialFactor = exponentialFactor;
    this.bucketSize        = bucketSize;
    
    buckets = new ArrayList<Bucket>();
  }
  
  public void assignDataBlocks(ArrayList<DataBlock> dataBlocks) {
    if (dataBlocks.size() == 0)
      throw new RuntimeException("The provided set of data blocks was empty!");
    //This rule is a little less practical, but since this is a simulation, it just makes for an easier
    //rule of thumb
    if ((dataBlocks.size() % bucketSize) != 0)
      throw new RuntimeException("WARNING!  The size of this batch does not evenly fit our bucket size!");
    
    //TODO - Do some stuff here
    
    
  }

}
