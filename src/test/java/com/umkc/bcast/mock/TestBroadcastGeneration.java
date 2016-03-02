package com.umkc.bcast.mock;

/**
 * This test class should demonstrate how to construct a broadcast.
 * 
 * <p>In general, broadcast constuction has 6 steps:
 * <ol><li>Generate the data, however that gets done</li>
 *     <li>Break the data into Buckets, based on our bucket factor</li>
 *     <li>Generate the local index for the bucket as rows are being added</li>
 *     <li>Update pointers for each block to point to the beginning of the next block's
 *     global index</li>
 *     <li>Generate the global index as derived from the buckets</li>
 *     <li>Flatten the buckets into a single continuous bcast list</li>
 * </ol>
 * 
 * The process is reasonably similar whether generating a flat or clustered index, though both will
 * be demonstrated in this test.
 * 
 * @author ac010168
 *
 */
public class TestBroadcastGeneration {
  
  
  
  
  

}
