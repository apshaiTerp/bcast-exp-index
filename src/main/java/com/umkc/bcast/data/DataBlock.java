package com.umkc.bcast.data;

/**
 * This is the root class for Data Blocks.  There are a few common elements regardless of data types.
 * All the common identification markers for a data element can be contained within this class.  All
 * the specific data points should be referenced within a subclass.
 * 
 * @author AC010168
 *
 */
public abstract class DataBlock extends Block {

  /** 
   * A unique identifier tag that identifies this particular element distinctly, even when using a skewed
   * broadcast where elements get repeated.  For flat broadcasts, we can generate some combination of
   * clusterGroup and dataKey values.  For others, a simple GUID can suffice.
   **/
  protected String uniqueIdentifier;
  /**
   * Only relevant in clustered/skewed broadcasts, it identifies a larger grouping of data, typically
   * implemented by a child class of DataBlock.
   */
  protected String clusterGroup;
  /**
   * A key that needs to only be unique within a clusterGroup.  Examples might be the Stock abbreviation
   * for stock information.
   */
  protected String dataKey;
  
  /**
   * @return the uniqueIdentifier
   */
  public String getUniqueIdentifier() {
    return uniqueIdentifier;
  }
  
  /**
   * @param uniqueIdentifier the uniqueIdentifier to set
   */
  public void setUniqueIdentifier(String uniqueIdentifier) {
    this.uniqueIdentifier = uniqueIdentifier;
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
   * @return the dataKey
   */
  public String getDataKey() {
    return dataKey;
  }
  
  /**
   * @param dataKey the dataKey to set
   */
  public void setDataKey(String dataKey) {
    this.dataKey = dataKey;
  }
}
