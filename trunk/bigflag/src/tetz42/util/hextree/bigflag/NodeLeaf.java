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
import java.util.Arrays;
import java.util.List;

public class NodeLeaf implements IBigflag, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1425930501952954384L;

    private int[] table;

    public NodeLeaf() {
        this(false);
    }

    public NodeLeaf(boolean isFull) {
        this.table = new int[8];
        if (isFull) {
            Arrays.fill(this.table, -1);
        }
    }

    private int turn(int index, boolean flag) {
        int inx = (index & 0xff) >>> 5;
        int turn_data = 0x1 << (index & 0x1F);
        if (((this.table[inx] & turn_data) == 0) ^ flag) {
            this.table[inx] = this.table[inx] ^ turn_data;
            return 1;
        } else {
            return 0;
        }
    }

    public int on(int index) {
        return this.turn(index, false);
    }

    public int off(int index) {
        return this.turn(index, true) * -1;
    }

    public boolean get(int index) {
        int inx = (index & 0xff) >>> 5;
        int on_data = 0x1 << (index & 0x1F);
        return (this.table[inx] & on_data) != 0;
    }

    public boolean isFull() {
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] != -1) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean search(List<Integer> list, int start, int size,
            boolean flag) {
        int masked = start & 0xFFFFFF00;
        int my_start = start & 0xFF;
        for (int i = my_start; i < 0x100; i++) {
            if (get(i) ^ flag) {
                list.add(masked | i);
                if(size != -1 && list.size() >= size)
                    return true;
            }
        }
        return false;
    }

    public boolean searchOn(List<Integer> list, int start, int size) {
        return search(list, start, size, false);
    }

    public boolean searchOff(List<Integer> list, int start, int size) {
        return search(list, start, size, true);
    }
}
