/*
 * Copyright 2010 tetsuo.ohta[at]gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tetz42.util.hextree.bigflag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Bigflag is a data structure which allows you to manage a large amount of
 * boolean flags effectively.
 * 
 * @author tetz
 */
public class Bigflag implements IBigflag, Serializable, Iterable<Integer> {

	/**
     * 
     */
	private static final long serialVersionUID = 8896717892789639858L;

	private final IBigflag root = new NodeBranch();

	private long size;

	public long getSize() {
		return size;
	}

	/**
	 * Returns true if this object has 'false' value only.
	 */
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	/**
	 * Returns true if this object has 'true' value only.
	 */
	public boolean isFull() {
		return this.root.isFull();
	}

	/**
	 * Returns the value which indicates the index.
	 * 
	 * @param index
	 * @return - the value which indicates the index.
	 */
	public boolean get(int index) {
		return this.root.get(index);
	}

	/**
	 * Sets the value in a position indicated by index.
	 * 
	 * @param index
	 * @param value
	 */
	public void set(int index, boolean value) {
		if (value)
			this.on(index);
		else
			this.off(index);
	}

	private int updateSize(int changed) {
		this.size += changed;
		return changed;
	}

	/**
	 * Sets 'false' in a position indicated by index.
	 * 
	 * @param index
	 * @return - the position indicated by index was false -> 0<br>
	 *         the position indicated by index was true -> -1
	 */
	public int off(int index) {
		return this.updateSize(this.root.off(index));
	}

	/**
	 * Sets 'true' in a position indicated by index.
	 * 
	 * @param index
	 * @return - the position indicated by index was false -> 1<br>
	 *         the position indicated by index was true -> 0
	 */
	public int on(int index) {
		return this.updateSize(this.root.on(index));
	}

	/**
	 * Searches true flag from this Bigflag object.
	 * 
	 * @param list
	 *            - to store indexes which indicate true flag
	 * @param start
	 *            - starting position
	 * @param size
	 *            - the number of indexes to be stored in list. If '-1' is
	 *            specified, this method will return whole indexes which
	 *            indicate true flag.
	 */
	public boolean searchOn(List<Integer> list, int start, int size) {
		return this.root.searchOn(list, start, size);
	}

	/**
	 * Searches true flag from this Bigflag object.
	 * 
	 * @param start
	 *            - starting position
	 * @param size
	 *            - the number of indexes to be stored in list. If '-1' is
	 *            specified, this method will return whole indexes which
	 *            indicate true flag.
	 * @return - list of indexes which indicate true flag
	 */
	public List<Integer> searchOn(int start, int size) {
		List<Integer> list = genList(size);
		searchOn(list, start, size);
		return list;
	}

	/**
	 * Searches false flag from this Bigflag object.
	 * 
	 * @param list
	 *            - to store indexes which indicate false flag
	 * @param start
	 *            - starting position
	 * @param size
	 *            - the number of indexes to be stored in list. If '-1' is
	 *            specified, this method will return whole indexes which
	 *            indicate false flag.
	 */
	public boolean searchOff(List<Integer> list, int start, int size) {
		return this.root.searchOff(list, start, size);
	}

	/**
	 * Searches false flag from this Bigflag object.
	 * 
	 * @param start
	 *            - starting position
	 * @param size
	 *            - the number of indexes to be stored in list. If '-1' is
	 *            specified, this method will return whole indexes which
	 *            indicate false flag.
	 * @return - list of indexes which indicate false flag
	 */
	public List<Integer> searchOff(int start, int size) {
		List<Integer> list = genList(size);
		searchOff(list, start, size);
		return list;
	}

	private List<Integer> genList(int size) {
		if (size <= 0) {
			return new ArrayList<Integer>();
		}
		return new ArrayList<Integer>(size);
	}

	@Override
	public Iterator<Integer> iterator() {
		final Bigflag src = this;
		return new Iterator<Integer>() {

			int pos = 0;

			@Override
			public boolean hasNext() {
				List<Integer> list = genList(1);
				if (!src.searchOn(list, pos, 1))
					return false;
				pos = list.get(0);
				return true;
			}

			@Override
			public Integer next() {
				List<Integer> list = genList(1);
				if (!src.searchOn(list, pos, 1))
					throw new IndexOutOfBoundsException("No next.");
				pos = list.get(0);
				return pos++;
			}

			@Override
			public void remove() {
				src.off(pos);
			}
		};
	}
}
