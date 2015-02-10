/*
 * (c) Cloud for Beginners.
 * 
 * author: tmusabbir
 */
package com.example.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class ChainOfWordsReducer.
 */
public class ChainOfWordsReducer extends MapReduceBase implements Reducer<Text, Text, NullWritable, Text> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ChainOfWordsReducer.class);


	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapred.Reducer#reduce(java.lang.Object, java.util.Iterator, org.apache.hadoop.mapred.OutputCollector, org.apache.hadoop.mapred.Reporter)
	 */
	public void reduce(Text key, Iterator<Text> values, OutputCollector<NullWritable, Text> output,
			Reporter reporter) throws IOException {
		List<String> words = new ArrayList<String>();
		while (values.hasNext()) {
			words.add(values.next().toString());
		}

		if (words.size() < 2) {
			return;
		} else {
			for (String firstWord : words) {
				for (String secondWord : words) {
					if (!firstWord.equalsIgnoreCase(secondWord)) {
						LOGGER.debug("First Word: " + firstWord + ", Second Word: " + secondWord);
						if (firstWord.charAt(0) == secondWord.charAt(secondWord.length() - 1)) {
							LOGGER.debug(String.format("Matching pair found for: [%s,%s]", secondWord, firstWord));
							output.collect(NullWritable.get(), new Text("Pair: " + secondWord + ", " + firstWord));
						}
					}
				}
			}
		}
	}
}
