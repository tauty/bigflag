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
import java.util.List;

import org.junit.Test;

import tetz42.util.hextree.bigflag.NodeLeaf;

public class NodeLeafTest {

	@Test
	public void test() throws Exception {
		NodeLeaf model = new NodeLeaf();

		assertThat(model, is(notNullValue()));

		// empty?
		assertThat(model.isEmpty(), is(true));

		// index 0 on Test
		assertThat(model.get(0), is(false));
		model.on(0);
		assertThat(model.get(0), is(true));

		// not empty?
		assertThat(model.isEmpty(), is(false));

		// index 255 on test
		assertThat(model.get(255), is(false));
		model.on(255);
		assertThat(model.get(255), is(true));

		// index 7 on test
		assertThat(model.get(7), is(false));
		model.on(7);
		assertThat(model.get(7), is(true));

		// index 7 off test
		assertThat(model.get(7), is(true));
		model.off(7);
		assertThat(model.get(7), is(false));

		// index 255 off test
		assertThat(model.get(255), is(true));
		model.off(255);
		assertThat(model.get(255), is(false));

		// index 0 off test
		assertThat(model.get(0), is(true));
		model.off(0);
		assertThat(model.get(0), is(false));

		// not full? and empty?
		assertThat(model.isFull(), is(false));
		assertThat(model.isEmpty(), is(true));

		int changed = 0;

		// index all on test
		for (int i = 0; i < 256; i++) {
			assertThat(model.get(i), is(false));
			changed = model.on(i);
			assertThat(model.get(i), is(true));
			assertThat(changed, is(1));
		}

		// max?, full? and not empty?
		assertThat(model.isFull(), is(true));
		assertThat(model.isEmpty(), is(false));

		// index all on to on test
		for (int i = 0; i < 256; i++) {
			assertThat(model.get(i), is(true));
			changed = model.on(i);
			assertThat(model.get(i), is(true));
			assertThat(changed, is(0));
		}

		// max?, full? and not empty?
		assertThat(model.isFull(), is(true));
		assertThat(model.isEmpty(), is(false));

		// index all off test
		for (int i = 0; i < 256; i++) {
			assertThat(model.get(i), is(true));
			changed = model.off(i);
			assertThat(model.get(i), is(false));
			assertThat(changed, is(-1));
		}

		// not full? and empty?
		assertThat(model.isFull(), is(false));
		assertThat(model.isEmpty(), is(true));

		// index all off to off test
		for (int i = 0; i < 256; i++) {
			assertThat(model.get(i), is(false));
			changed = model.off(i);
			assertThat(model.get(i), is(false));
			assertThat(changed, is(0));
		}

		// not full? and empty?
		assertThat(model.isFull(), is(false));
		assertThat(model.isEmpty(), is(true));
	}

	@Test
	public void fullTable() throws Exception {

		NodeLeaf modelf = new NodeLeaf(true);

		// full? and not empty?
		assertThat(modelf.isFull(), is(true));
		assertThat(modelf.isEmpty(), is(false));

		int changed = 0;

		// index all off test
		for (int i = 0; i < 256; i++) {
			assertThat(modelf.get(i), is(true));
			changed = modelf.off(i);
			assertThat(modelf.get(i), is(false));
			assertThat(changed, is(-1));
		}

		// not full? and empty?
		assertThat(modelf.isFull(), is(false));
		assertThat(modelf.isEmpty(), is(true));

		// index all on test
		for (int i = 0; i < 256; i++) {
			assertThat(modelf.get(i), is(false));
			changed = modelf.on(i);
			assertThat(modelf.get(i), is(true));
			assertThat(changed, is(1));
		}

		// max?, full? and not empty?
		assertThat(modelf.isFull(), is(true));
		assertThat(modelf.isEmpty(), is(false));
	}

	@Test
	public void x2y() throws Exception {
		NodeLeaf leaf = new NodeLeaf();

		for (int i = 3; i < 10; i++)
			leaf.on(i);
		assertThat(leaf.get(0), is(false));
		assertThat(leaf.get(1), is(false));
		assertThat(leaf.get(2), is(false));
		assertThat(leaf.get(3), is(true));
		assertThat(leaf.get(4), is(true));
		assertThat(leaf.get(5), is(true));
		assertThat(leaf.get(6), is(true));
		assertThat(leaf.get(7), is(true));
		assertThat(leaf.get(8), is(true));
		assertThat(leaf.get(9), is(true));
		assertThat(leaf.get(10), is(false));
		assertThat(leaf.get(11), is(false));
		assertThat(leaf.get(12), is(false));
		assertThat(leaf.get(13), is(false));
		assertThat(leaf.isEmpty(), is(false));
		assertThat(leaf.isFull(), is(false));

		for (int i = 0; i < 0x100; i++)
			leaf.on(i);
		for (int i = 0; i < 0x100; i++) {
			assertThat(leaf.get(i), is(true));
		}
		assertThat(leaf.isEmpty(), is(false));
		assertThat(leaf.isFull(), is(true));

		for (int i = 100; i < 0x100; i++)
			leaf.off(i);
		for (int i = 0; i < 100; i++) {
			assertThat(leaf.get(i), is(true));
		}
		for (int i = 100; i < 0x100; i++) {
			assertThat(leaf.get(i), is(false));
		}
		assertThat(leaf.isEmpty(), is(false));
		assertThat(leaf.isFull(), is(false));

		for (int i = 0; i < 0x100; i++)
			leaf.off(i);
		for (int i = 0; i < 0x100; i++) {
			assertThat(leaf.get(i), is(false));
		}
		assertThat(leaf.isEmpty(), is(true));
		assertThat(leaf.isFull(), is(false));
	}

	@Test
	public void search() {
		NodeLeaf leaf = new NodeLeaf();
		List<Integer> result = new ArrayList<Integer>();

		leaf.on(10);
		leaf.on(31);
		leaf.on(32);
		leaf.on(100);
		leaf.on(200);
		leaf.on(255);

		assertThat(leaf.searchOn(result, 0, 3), is(true));
		assertThat(result.size(), is(3));
		assertThat(result.get(0), is(10));
		assertThat(result.get(1), is(31));
		assertThat(result.get(2), is(32));
		result.clear();

		assertThat(leaf.searchOn(result, 0, 7), is(false));
		assertThat(result.size(), is(6));
		assertThat(result.get(0), is(10));
		assertThat(result.get(1), is(31));
		assertThat(result.get(2), is(32));
		assertThat(result.get(3), is(100));
		assertThat(result.get(4), is(200));
		assertThat(result.get(5), is(255));
		result.clear();

		assertThat(leaf.searchOn(result, 60, 3), is(true));
		assertThat(result.size(), is(3));
		assertThat(result.get(0), is(100));
		assertThat(result.get(1), is(200));
		assertThat(result.get(2), is(255));
		result.clear();

		assertThat(leaf.searchOn(result, 8, -1), is(false));
		assertThat(result.size(), is(6));
		assertThat(result.get(0), is(10));
		assertThat(result.get(1), is(31));
		assertThat(result.get(2), is(32));
		assertThat(result.get(3), is(100));
		assertThat(result.get(4), is(200));
		assertThat(result.get(5), is(255));
		result.clear();

		leaf = new NodeLeaf(true);

		leaf.off(10);
		leaf.off(31);
		leaf.off(32);
		leaf.off(100);
		leaf.off(200);
		leaf.off(255);

		assertThat(leaf.searchOff(result, 0, 3), is(true));
		assertThat(result.size(), is(3));
		assertThat(result.get(0), is(10));
		assertThat(result.get(1), is(31));
		assertThat(result.get(2), is(32));
		result.clear();

		assertThat(leaf.searchOff(result, 0, 7), is(false));
		assertThat(result.size(), is(6));
		assertThat(result.get(0), is(10));
		assertThat(result.get(1), is(31));
		assertThat(result.get(2), is(32));
		assertThat(result.get(3), is(100));
		assertThat(result.get(4), is(200));
		assertThat(result.get(5), is(255));
		result.clear();

		assertThat(leaf.searchOff(result, 60, 3), is(true));
		assertThat(result.size(), is(3));
		assertThat(result.get(0), is(100));
		assertThat(result.get(1), is(200));
		assertThat(result.get(2), is(255));
		result.clear();

		assertThat(leaf.searchOff(result, 10, -1), is(false));
		assertThat(result.size(), is(6));
		assertThat(result.get(0), is(10));
		assertThat(result.get(1), is(31));
		assertThat(result.get(2), is(32));
		assertThat(result.get(3), is(100));
		assertThat(result.get(4), is(200));
		assertThat(result.get(5), is(255));
		result.clear();

	}
}
