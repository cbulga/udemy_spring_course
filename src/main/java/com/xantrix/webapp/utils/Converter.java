package com.xantrix.webapp.utils;

/**
 * Converter interface that converts the provided &lt;I&gt; in the corresponding &lt;O&gt;.
 * 
 * @param <I> conversion input
 * @param <O> conversion output
 * 
 * @author cristian
 */
public interface Converter<O, I> {

	/**
	 * Returns a {@code O} converting the provided {@code I}.
	 * 
	 * @param model {@code I} to be converted to {@code O}
	 * @return a {@code O} converting the provided {@code I}.
	 */
	public O convert(I model);
}
