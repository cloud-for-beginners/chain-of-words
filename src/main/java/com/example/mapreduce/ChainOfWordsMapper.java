/*
 * (c) Cloud for Beginners.
 * 
 * author: tmusabbir
 */
package com.example.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


/**
 * The Class ChainOfWordsMapper.
 */
public class ChainOfWordsMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapred.Mapper#map(java.lang.Object, java.lang.Object, org.apache.hadoop.mapred.OutputCollector, org.apache.hadoop.mapred.Reporter)
	 */
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {
		String input = value.toString();
		StringTokenizer tokenizer = new StringTokenizer(input);
		while (tokenizer.hasMoreTokens()) {
			String word = tokenizer.nextToken();
			char firstChar = word.charAt(0);
			char lastChar = word.charAt(word.length() - 1);
			output.collect(new Text(Character.toString(firstChar)), new Text(word));
			output.collect(new Text(Character.toString(lastChar)), new Text(word));
		}
	}
}
