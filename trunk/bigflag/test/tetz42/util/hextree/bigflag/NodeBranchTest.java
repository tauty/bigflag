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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import tetz42.util.hextree.bigflag.NodeBranch;

public class NodeBranchTest {

    @Test
    public void generation5() throws Exception {

        System.out.println("generation5 Start.");

        NodeBranch model5 = new NodeBranch((byte)5);

        // not full? and empty?
        assertThat(model5.isFull(), is(false));
        assertThat(model5.isEmpty(), is(true));

        int changed = 0;

        // index all on test
        for (int i = 0; i < 4096; i++) {
            assertThat(model5.get(i), is(false));
            changed = model5.on(i);
            assertThat(model5.get(i), is(true));
            assertThat(changed, is(1));
        }

        // full? and not empty?
        assertThat(model5.isFull(), is(true));
        assertThat(model5.isEmpty(), is(false));

        // index all on to on test
        for (int i = 0; i < 4096; i++) {
            assertThat(model5.get(i), is(true));
            changed = model5.on(i);
            assertThat(model5.get(i), is(true));
            assertThat(changed, is(0));
        }

        // full? and not empty?
        assertThat(model5.isFull(), is(true));
        assertThat(model5.isEmpty(), is(false));

        // index all off test
        for (int i = 0; i < 4096; i++) {
            assertThat(model5.get(i), is(true));
            changed = model5.off(i);
            assertThat(model5.get(i), is(false));
            assertThat(changed, is(-1));
        }

        // not full? and empty?
        assertThat(model5.isFull(), is(false));
        assertThat(model5.isEmpty(), is(true));

        // index all off to off test
        for (int i = 0; i < 4096; i++) {
            assertThat(model5.get(i), is(false));
            changed = model5.off(i);
            assertThat(model5.get(i), is(false));
            assertThat(changed, is(0));
        }

        // not full? and empty?
        assertThat(model5.isFull(), is(false));
        assertThat(model5.isEmpty(), is(true));

        System.out.println("generation5 End.");
    }

    @Test
    public void generation4() throws Exception {

        System.out.println("generation4 Start.");

        NodeBranch model4 = new NodeBranch((byte)4);

        // not full? and empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        int changed = 0;

        // index all on test
        for (int i = 0; i < 65536; i++) {
            assertThat(model4.get(i), is(false));
            changed = model4.on(i);
            assertThat(model4.get(i), is(true));
            assertThat(changed, is(1));
        }

        // full? and not empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        // index all on to on test
        for (int i = 0; i < 65536; i++) {
            assertThat(model4.get(i), is(true));
            changed = model4.on(i);
            assertThat(model4.get(i), is(true));
            assertThat(changed, is(0));
        }

        // full? and not empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        // index all off test
        for (int i = 0; i < 65536; i++) {
            assertThat(model4.get(i), is(true));
            changed = model4.off(i);
            assertThat(model4.get(i), is(false));
            assertThat(changed, is(-1));
        }

        // not full? and empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        // index all off test
        for (int i = 0; i < 65536; i++) {
            assertThat(model4.get(i), is(false));
            changed = model4.off(i);
            assertThat(model4.get(i), is(false));
            assertThat(changed, is(0));
        }

        // not full? and empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        System.out.println("generation4 End.");
    }

    @Test
    public void generation4_shuffle() throws Exception {

        System.out.println("generation4_shuffle Start.");

        // shuffle list
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 0x10000; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        NodeBranch model4 = new NodeBranch((byte)4);

        // not full? and empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        int changed = 0;

        // index all on test
        for (int i : list) {
            assertThat(model4.get(i), is(false));
            changed = model4.on(i);
            assertThat(model4.get(i), is(true));
            assertThat(changed, is(1));
        }

        // full? and not empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        Collections.shuffle(list);

        // index all on to on test
        for (int i : list) {
            assertThat(model4.get(i), is(true));
            changed = model4.on(i);
            assertThat(model4.get(i), is(true));
            assertThat(changed, is(0));
        }

        // full? and not empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        Collections.shuffle(list);

        // index all off test
        for (int i : list) {
            assertThat(model4.get(i), is(true));
            changed = model4.off(i);
            assertThat(model4.get(i), is(false));
            assertThat(changed, is(-1));
        }

        // not full? and empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        Collections.shuffle(list);

        // index all off test
        for (int i : list) {
            assertThat(model4.get(i), is(false));
            changed = model4.off(i);
            assertThat(model4.get(i), is(false));
            assertThat(changed, is(0));
        }

        // not full? and empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        System.out.println("generation4_shuffle End.");
    }

    @Test
    public void generation4_full() throws Exception {

        System.out.println("generation4_full Start.");

        NodeBranch model4 = new NodeBranch((byte)4, true);

        // not full? and empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        int changed = 0;

        // index all off test
        for (int i = 0; i < 65536; i++) {
            assertThat(model4.get(i), is(true));
            changed = model4.off(i);
            assertThat(model4.get(i), is(false));
            assertThat(changed, is(-1));
        }

        // full? and not empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        // index all off to off test
        for (int i = 0; i < 65536; i++) {
            assertThat(model4.get(i), is(false));
            changed = model4.off(i);
            assertThat(model4.get(i), is(false));
            assertThat(changed, is(0));
        }

        // full? and not empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        // index all on test
        for (int i = 0; i < 65536; i++) {
            assertThat(model4.get(i), is(false));
            changed = model4.on(i);
            assertThat(model4.get(i), is(true));
            assertThat(changed, is(1));
        }

        // not full? and empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        // index all on to on test
        for (int i = 0; i < 65536; i++) {
            assertThat(model4.get(i), is(true));
            changed = model4.on(i);
            assertThat(model4.get(i), is(true));
            assertThat(changed, is(0));
        }

        // not full? and empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        System.out.println("generation4_full End.");
    }

    @Test
    public void generation4_full_shuffle() throws Exception {

        System.out.println("generation4_full_shuffle Start.");

        // shuffle list
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 0x10000; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        NodeBranch model4 = new NodeBranch((byte)4, true);

        // not full? and empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        int changed = 0;

        // index all off test
        Collections.shuffle(list);
        for (int i : list) {
            assertThat(model4.get(i), is(true));
            changed = model4.off(i);
            assertThat(model4.get(i), is(false));
            assertThat(changed, is(-1));
        }

        // full? and not empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        // index all off to off test
        Collections.shuffle(list);
        for (int i : list) {
            assertThat(model4.get(i), is(false));
            changed = model4.off(i);
            assertThat(model4.get(i), is(false));
            assertThat(changed, is(0));
        }

        // full? and not empty?
        assertThat(model4.isFull(), is(false));
        assertThat(model4.isEmpty(), is(true));

        // index all on test
        Collections.shuffle(list);
        for (int i : list) {
            assertThat(model4.get(i), is(false));
            changed = model4.on(i);
            assertThat(model4.get(i), is(true));
            assertThat(changed, is(1));
        }

        // not full? and empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        // index all on to on test
        Collections.shuffle(list);
        for (int i : list) {
            assertThat(model4.get(i), is(true));
            changed = model4.on(i);
            assertThat(model4.get(i), is(true));
            assertThat(changed, is(0));
        }

        // not full? and empty?
        assertThat(model4.isFull(), is(true));
        assertThat(model4.isEmpty(), is(false));

        System.out.println("generation4_full_shuffle End.");
    }


    @Test
    public void search5() throws Exception {
        NodeBranch branch = new NodeBranch((byte)5);
        List<Integer> result = new ArrayList<Integer>();

        // まずはoff調べてみる。
        assertThat(branch.searchOff(result, 200, 400), is(true));
        assertThat(result.size(), is(400));
        int res = 200;
        for (int i : result) {
            assertThat(i, is(res++));
        }
        result.clear();

        // onはない。
        assertThat(branch.searchOn(result, 200, 400), is(false));
        assertThat(result.size(), is(0));

        // 適当にonしてsearchOn
        for(int i = 200; i<600; i++)
        	branch.on(i);
        branch.on(609);
        branch.on(2009);
        branch.on(2010);

        assertThat(branch.searchOn(result, 100, 402), is(true));
        assertThat(result.size(), is(402));
        res = 200;
        for (int i = 0; i < 300; i++) {
            assertThat(result.get(i), is(res++));
        }
        assertThat(result.get(400), is(609));
        assertThat(result.get(401), is(2009));
        result.clear();

        assertThat(branch.searchOn(result, 0, -1), is(false));
        assertThat(result.size(), is(403));
        res = 200;
        for (int i = 0; i < 400; i++) {
            assertThat(result.get(i), is(res++));
        }
        assertThat(result.get(400), is(609));
        assertThat(result.get(401), is(2009));
        assertThat(result.get(402), is(2010));
        result.clear();

    }
    
    @Test
    public void searchRoot() throws Exception {
        NodeBranch root = new NodeBranch();
        List<Integer> result = new ArrayList<Integer>();

        // まずはoff調べてみる。
        assertThat(root.searchOff(result, 1000000, 400), is(true));
        assertThat(result.size(), is(400));
        int res = 1000000;
        for (int i : result) {
            assertThat(i, is(res++));
        }
        result.clear();

        // onはない。
        assertThat(root.searchOn(result, 0, -1), is(false));
        assertThat(result.size(), is(0));

        // 適当にonしてsearchOn
        for(int i = 0; i<1000000; i++)
        	root.on(i);
        root.on(1000001);
        root.on(1008911);
        root.on(1390129);
        root.on(9162932);

        assertThat(root.searchOn(result, 200, 300), is(true));
        assertThat(result.size(), is(300));
        res = 200;
        for (int i = 0; i < 300; i++) {
            assertThat(result.get(i), is(res++));
        }
        result.clear();

        assertThat(root.searchOn(result, 1000000, -1), is(false));
        assertThat(result.size(), is(4));
        assertThat(result.get(0), is(1000001));
        assertThat(result.get(1), is(1008911));
        assertThat(result.get(2), is(1390129));
        assertThat(result.get(3), is(9162932));
        result.clear();
        
        // 適当にoffしてsearchOff
        root.off(255);
        root.off(256);
        root.off(2923);
        root.off(11263);
        root.off(913345);

        assertThat(root.searchOff(result, 0, 5), is(true));
        assertThat(result.size(), is(5));
        assertThat(result.get(0), is(255));
        assertThat(result.get(1), is(256));
        assertThat(result.get(2), is(2923));
        assertThat(result.get(3), is(11263));
        assertThat(result.get(4), is(913345));
        result.clear();

        assertThat(root.searchOff(result, 0, 10), is(true));
        assertThat(result.size(), is(10));
        assertThat(result.get(0), is(255));
        assertThat(result.get(1), is(256));
        assertThat(result.get(2), is(2923));
        assertThat(result.get(3), is(11263));
        assertThat(result.get(4), is(913345));
        assertThat(result.get(5), is(1000000));
        assertThat(result.get(6), is(1000002));
        assertThat(result.get(7), is(1000003));
        assertThat(result.get(8), is(1000004));
        assertThat(result.get(9), is(1000005));
        result.clear();
    }
}
