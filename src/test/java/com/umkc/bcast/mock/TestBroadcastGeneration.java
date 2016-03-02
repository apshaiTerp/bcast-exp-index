package com.umkc.bcast.mock;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

import com.umkc.bcast.data.DataBlock;
import com.umkc.bcast.data.mock.MockDataBlock;
import com.umkc.bcast.util.BroadcastBuilder;

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
  
  @Test
  public void testSimpleFlatBcast() {
    ArrayList<DataBlock> testBlocks = null;
    
    testBlocks = generateAlphaDataBlocks();
    
    BroadcastBuilder builder = new BroadcastBuilder(2, 10, false);
    builder.assignDataBlocks(testBlocks);
    
    assertTrue("The world is ending", true);
  }
  
  private ArrayList<DataBlock> generateAlphaDataBlocks() {
    ArrayList<DataBlock> testBlocks = new ArrayList<DataBlock>();

    Random randomInt = new Random();
    
    //testBlocks.add(new MockDataBlock("mockDataDescription", mockDataValue, "uniqueIdentifier", "clusterGroup", "dataKey"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for abdicate", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "abdicate"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for abort", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "abort"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for advocate", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "advocate"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for affluent", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "affluent"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for altitude", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "altitude"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for amputate", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "amputate"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for analyze", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "analyze"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for arrest", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "arrest"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for assign", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "assign"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for axiom", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "axiom"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for backfire", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "backfire"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for baggage", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "baggage"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for beetle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "beetle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for bicycle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "bicycle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for blade", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "blade"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for blast", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "blast"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for body", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "body"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for book", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "book"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for bronze", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "bronze"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for bypass", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "bypass"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for cabal", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "cabal"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for cabinet", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "cabinet"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for cancel", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "cancel"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for cellular", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "cellular"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for change", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "change"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for check", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "check"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for color", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "color"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for correct", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "correct"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for cover", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "cover"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for cyclone", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "cyclone"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for daddy", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "daddy"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for dance", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "dance"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for danger", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "danger"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for deep", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "deep"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for divide", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "divide"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for door", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "door"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for double", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "double"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for dump", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "dump"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for dupe", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "dupe"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for dynamic", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "dynamic"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for eager", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "eager"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for eagle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "eagle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for edict", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "edict"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for eggs", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "eggs"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for elect", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "elect"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for elude", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "elude"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for ember", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "ember"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for energy", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "energy"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for evil", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "evil"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for eyrie", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "eyrie"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for fable", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "fable"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for faded", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "faded"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for favor", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "favor"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for feast", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "feast"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for final", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "final"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for fire", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "fire"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for flood", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "flood"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for forest", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "forest"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for forget", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "forget"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for fuzzy", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "fuzzy"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for gable", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "gable"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for game", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "game"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for gavel", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "gavel"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for geek", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "geek"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for gift", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "gift"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for gimble", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "gimble"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for goose", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "goose"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for green", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "green"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for groove", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "groove"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for gyrate", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "gyrate"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for hairy", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "hairy"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for happen", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "happen"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for haze", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "haze"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for hearing", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "hearing"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for help", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "help"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for hinder", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "hinder"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for hints", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "hints"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for hope", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "hope"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for house", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "house"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for huge", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "huge"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for iambic", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "iambic"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for icing", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "icing"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for idiom", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "idiom"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for image", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "image"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for imbue", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "imbue"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for inject", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "inject"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for injest", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "injest"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for injure", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "injure"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for iota", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "iota"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for ivory", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "ivory"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for jabber", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "jabber"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for jangle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "jangle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for jasper", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "jasper"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for jeer", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "jeer"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for jelly", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "jelly"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for jiggle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "jiggle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for jingle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "jingle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for join", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "join"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for jolly", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "jolly"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for juniper", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "juniper"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for kabab", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "kabab"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for karma", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "karma"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for kayak", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "kayak"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for keeper", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "keeper"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for keen", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "keen"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for killer", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "killer"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for kinko", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "kinko"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for knows", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "knows"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for kooky", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "kooky"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for kudzu", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "kudzu"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for label", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "label"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for lady", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "lady"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for last", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "last"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for leech", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "leech"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for leper", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "leper"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for lizard", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "lizard"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for loop", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "loop"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for loser", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "loser"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for lungs", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "lungs"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for lyre", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "lyre"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for madam", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "madam"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for master", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "master"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for meat", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "meat"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for meet", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "meet"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for mess", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "mess"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for mission", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "mission"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for mite", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "mite"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for monster", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "monster"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for mouse", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "mouse"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for muster", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "muster"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for nabs", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "nabs"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for nasty", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "nasty"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for near", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "near"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for nest", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "nest"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for nettle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "nettle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for nice", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "nice"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for ninja", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "ninja"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for notable", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "notable"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for noun", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "noun"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for novel", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "novel"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for oasis", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "oasis"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for oats", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "oats"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for ocean", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "ocean"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for offer", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "offer"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for often", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "often"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for olive", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "olive"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for omen", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "omen"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for omit", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "omit"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for ostrich", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "ostrich"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for oven", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "oven"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for panic", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "panic"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for past", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "past"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for pattern", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "pattern"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for peep", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "peep"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for pester", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "pester"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for pink", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "pink"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for pistol", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "pistol"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for point", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "point"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for puddle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "puddle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for pyre", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "pyre"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for quack", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "quack"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for quadrant", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "quadrant"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for quaffed", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "quaffed"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for quaikers", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "quaikers"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for quails", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "quails"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for qualify", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "qualify"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for quality", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "quality"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for queen", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "queen"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for quest", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "quest"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for quote", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "quote"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for rabbit", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "rabbit"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for range", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "range"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for rattle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "rattle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for reap", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "reap"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for rest", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "rest"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for ring", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "ring"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for road", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "road"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for roam", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "roam"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for rope", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "rope"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for rung", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "rung"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for sabre", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "sabre"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for salad", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "salad"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for same", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "same"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for seed", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "seed"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for settler", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "settler"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for simple", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "simple"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for single", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "single"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for soap", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "soap"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for soda", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "soda"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for sour", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "sour"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for tamper", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "tamper"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for taste", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "taste"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for tattoo", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "tattoo"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for teach", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "teach"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for tempo", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "tempo"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for tiger", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "tiger"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for tiny", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "tiny"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for tone", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "tone"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for touch", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "touch"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for tubes", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "tubes"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for udder", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "udder"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for ulcer", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "ulcer"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for ultra", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "ultra"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for umami", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "umami"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for uncle", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "uncle"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for under", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "under"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for undo", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "undo"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for untoward", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "untoward"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for upset", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "upset"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for urban", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "urban"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for vacant", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "vacant"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for vacuum", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "vacuum"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for vary", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "vary"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for veal", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "veal"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for venom", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "venom"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for vital", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "vital"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for vitamin", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "vitamin"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for vote", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "vote"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for vowel", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "vowel"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for vulgar", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "vulgar"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for waste", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "waste"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for watch", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "watch"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for water", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "water"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for weapon", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "weapon"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for weather", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "weather"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for white", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "white"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for wind", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "wind"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for winter", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "winter"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for work", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "work"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for wound", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "wound"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for xanthin", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xanthin"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for xenia", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xenia"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for xenon", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xenon"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for xenophobe", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xenophobe"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for xerox", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xerox"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for xerus", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xerus"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for xi", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xi"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for xylem", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xylem"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for xylong", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xylong"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for xyst", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "xyst"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for yam", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "yam"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for yarn", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "yarn"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for yawn", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "yawn"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for yearn", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "yearn"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for yellow", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "yellow"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for yet", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "yet"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for yip", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "yip"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for yogurt", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "yogurt"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for young", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "young"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for youth", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "youth"));
    
    testBlocks.add(new MockDataBlock("Dictionary Entry for zany", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zany"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for zap", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zap"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for zeal", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zeal"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for zealot", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zealot"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for zest", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zest"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for zing", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zing"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for zipper", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zipper"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for zodiak", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zodiak"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for zombie", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zombie"));
    testBlocks.add(new MockDataBlock("Dictionary Entry for zone", randomInt.nextInt(), UUID.randomUUID().toString(), "ALPHA", "zone"));
    
    return testBlocks;
  }

}
