/*
 * (c) Cloud for Beginners.
 * 
 * author: tmusabbir
 */
package com.example.mapreduce;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mrunit.MapReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Log4jConfigurer;


/**
 * The Class ChainOfWordsReducerTest.
 */
public class ChainOfWordsReducerTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ChainOfWordsReducerTest.class);

	/** The test mapper. */
	private Mapper<LongWritable, Text, Text, Text> testMapper;

	/** The test reducer. */
	private Reducer<Text, Text, NullWritable, Text> testReducer;

	/** The test driver. */
	private MapReduceDriver<LongWritable, Text, Text, Text, NullWritable, Text> testDriver;


	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		try {
			Log4jConfigurer.initLogging("classpath:META-INF/log4j.properties");
		} catch (FileNotFoundException e) {
			LOGGER.error("Unable to read log4j.properties file.");
		}
		testMapper = new ChainOfWordsMapper();
		testReducer = new ChainOfWordsReducer();
		testDriver = MapReduceDriver.newMapReduceDriver(testMapper, testReducer);
	}


	/**
	 * Test reducer.
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testReducer() throws IOException {

		Pair<LongWritable, Text> pair = new Pair<LongWritable, Text>(new LongWritable(1), new Text(
			ChainOfWordsMapperTest.INPUT_STR));

		List<Pair<NullWritable, Text>> results = testDriver.withInput(pair).run();

		assertTrue(results.size() == 11);
		boolean found = false;
		for (Pair<NullWritable, Text> p : results) {
			if (p.getSecond().toString().equalsIgnoreCase("Pair: cef, fabc")) {
				found = true;
			}
			LOGGER.debug(p.getSecond().toString());
		}
		assertTrue(found);
	}
}
