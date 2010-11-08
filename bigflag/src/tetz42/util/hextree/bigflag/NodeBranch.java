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
import java.util.List;

public class NodeBranch implements IBigflag, Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 5276959353986224346L;

	private static final int masks[] = { 0xf0000000, 0xf000000, 0xf00000,
			0xf0000, 0xf000, 0xf00 };

	private IBigflag[] table;

	private char fullFlags;

	private byte generation;

	public NodeBranch() {
		this((byte) 0);
	}

	public NodeBranch(byte generation) {
		this(generation, false);
	}

	public NodeBranch(byte generation, boolean isFull) {
		// Note: generation must not be grater than 5.
		this.table = new IBigflag[0x10];
		this.generation = generation;
		if (isFull)
			this.fullFlags = 0xFFFF;
		else
			this.fullFlags = 0;
	}

	public boolean isEmpty() {
		if (this.fullFlags != 0)
			return false;
		for (int i = 0; i < this.table.length; i++) {
			if (this.table[i] != null)
				return false;
		}
		return true;
	}

	public boolean isFull() {
		return this.fullFlags == 0xFFFF;
	}

	public int on(int index) {
		int inx = calc(index);
		int b_inx = 0x1 << inx;

		if ((this.fullFlags & b_inx) != 0) {
			// It means all flags are true
			return 0;
		}

		readyMemory(inx, false);
		int changed = this.table[inx].on(index);

		if (changed != 0 && this.table[inx].isFull()) {
			this.fullFlags = (char) (this.fullFlags | b_inx);
			this.table[inx] = null;
		}

		return changed;
	}

	public int off(int index) {
		int inx = calc(index);
		int b_inx = 0x1 << inx;

		if ((this.fullFlags & b_inx) != 0) {
			this.readyMemory(inx, true);
			this.fullFlags = (char) (this.fullFlags ^ b_inx);
		}

		if (this.table[inx] == null) {
			// It means all flags are false
			return 0;
		}

		int changed = this.table[inx].off(index);

		if (changed != 0 && this.table[inx].isEmpty()) {
			this.table[inx] = null;
		}

		return changed;
	}

	public boolean get(int index) {
		int inx = calc(index);
		int b_inx = 0x1 << inx;

		if ((this.fullFlags & b_inx) != 0) {
			return true;
		}

		if (this.table[inx] == null) {
			return false;
		}

		return this.table[inx].get(index);
	}

	int calc(int point) {
		return (point & masks[this.generation]) >>> ((7 - this.generation) * 4);
	}

	private void readyMemory(int inx, boolean isFull) {
		if (this.table[inx] == null) {
			if (this.generation == 5) {
				this.table[inx] = new NodeLeaf(isFull);
			} else {
				this.table[inx] = new NodeBranch((byte) (this.generation + 1),
						isFull);
			}
		}
	}

	public boolean searchOn(List<Integer> list, int start, int size) {
		return search(list, start, size, false);
	}

	public boolean searchOff(List<Integer> list, int start, int size) {
		return search(list, start, size, true);
	}

	private boolean search(List<Integer> list, int start, int size, boolean flag) {
		int my_start = calc(start);
		for (int i = my_start; i <= 0xf; i++) {
			int childStart = conv(start, i);
			if (this.table[i] == null) {
				int b_inx = 0x1 << i;
				if (((this.fullFlags & b_inx) != 0) ^ flag) {
					int childEnd = conv(start, i + 1);
					for (int j = childStart; j < childEnd; j++) {
						list.add(j);
						if (size != -1 && list.size() >= size)
							return true;
					}
				}
				continue;
			}
			if (flag) {
				if (this.table[i].searchOff(list, childStart, size))
					return true;
			} else {
				if (this.table[i].searchOn(list, childStart, size))
					return true;
			}
		}
		return false;
	}

	private int conv(int src, int cur) {
		int masked = src & (0xF0000000 >>> (generation * 4));
		int current = cur << ((7 - generation) * 4);
		int mask = 0xFFFFFFFF << ((8 - generation) * 4);

		if (masked == current)
			return src;
		return (src & mask) | current;
	}
}
