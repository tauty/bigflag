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

import org.junit.Test;

public class BigflagTest {

	private Bigflag model = new Bigflag();

	@Test
	public void test() throws Exception {
		assertThat(model, is(notNullValue()));
	}

	@Test
	public void x2y() throws Exception {

		for (int i = 0; i < 1000; i++)
			model.on(i);
		for (int i = 0; i < 1000; i++)
			assertThat("i:" + i, model.get(i), is(true));
		assertThat(model.get(1000), is(false));
		assertThat(model.getSize(), is(1000L));

		model.off(100);
		assertThat(model.get(100), is(false));
		assertThat(model.getSize(), is(999L));

	}

	@Test
	public void testForEach() throws Exception {
		StopWatch sw = new StopWatch();

		sw.start("set is called 1,000,000 times.");
		for (int i = 0; i < 1000000; i++) {
			model.set(i, i % 7 == 1);
		}
		sw.end("set is called 1,000,000 times.");

		int result = 1;
		sw.start("for each is performed.");
		for (int i : model) {
			sw.start("assertThat is called.");
			assertThat(i, is(result));
			sw.end("assertThat is called.");
			result += 7;
		}
		sw.end("for each is performed.");

		System.out.println(sw);
	}

	@Test
	public void testSort() throws Exception {

		StopWatch sw = new StopWatch();

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 100000; i++)
			list.add(i);

		Collections.shuffle(list);

		sw.start("BigFlag");
		for (int i : list)
			model.on(i);
		sw.end("BigFlag");
		int count = 0;
		for (int i : model)
			assertThat(i, is(count++));

		sw.start("Collections.sort");
		Collections.sort(list);
		sw.end("Collections.sort");
		count = 0;
		for (int i : list)
			assertThat(i, is(count++));

		System.out.println(sw);
	}
}
