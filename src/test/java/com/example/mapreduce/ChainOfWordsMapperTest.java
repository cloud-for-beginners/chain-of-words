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
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Log4jConfigurer;


/**
 * The Class ChainOfWordsMapperTest.
 */
public class ChainOfWordsMapperTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ChainOfWordsMapperTest.class);

	/** The test mapper. */
	private Mapper<LongWritable, Text, Text, Text> testMapper;

	/** The test driver. */
	private MapDriver<LongWritable, Text, Text, Text> testDriver;

	/** The Constant INPUT_STR. */
	public static final String INPUT_STR = "abc cef fabc xya af pqrx sa";


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
		testDriver = MapDriver.newMapDriver(testMapper);
	}


	/**
	 * Test mapper.
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testMapper() throws IOException {
		List<Pair<Text, Text>> results = testDriver.withInput(new LongWritable(1), new Text(INPUT_STR)).run();
		boolean found = false;

		assertTrue(results.size() == 14);
		for (Pair<Text, Text> p : results) {
			if (p.getFirst().toString().equalsIgnoreCase("a") && p.getSecond().toString().equalsIgnoreCase("xya")) {
				found = true;
			}
			LOGGER.debug("Key: " + p.getFirst() + ", value: " + p.getSecond());
		}
		assertTrue(found);
	}
}
