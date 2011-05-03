/*
 * Java Genetic Algorithm Library (@!identifier!@).
 * Copyright (c) @!year!@ Franz Wilhelmstötter
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Author:
 *     Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 *     
 */
package org.jenetics.performance;

import org.jenetics.Float64Chromosome;
import org.jenetics.util.Timer;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id$
 */
public class ChromosomeTest extends PerfTest {

	private int N = 1000000;
	private final int LOOPS = 1000;
	
	public ChromosomeTest() {
		super("Chromosome");
	}

	@Override
	protected int calls() {
		return 0;
	}

	private void newInstance() {
		final Timer timer = newTimer("newInstance()");
		
		Float64Chromosome chromosome = new Float64Chromosome(0, 1, N);
		
		for (int i = LOOPS; --i >= 0;) {
			timer.start();
			chromosome.newInstance();
			timer.stop();
		}
	}
	
	private void newInstanceISeq() {
		final Timer timer = newTimer("newInstance(ISeq)");
		
		Float64Chromosome chromosome = new Float64Chromosome(0, 1, N);
		
		for (int i = LOOPS; --i >= 0;) {
			timer.start();
			chromosome.newInstance(chromosome.toSeq());
			timer.stop();
		}
	}
	
	private void isValid() {
		final Timer timer = newTimer("isValid");
		
		for (int i = LOOPS; --i >= 0;) {
			Float64Chromosome chromosome = new Float64Chromosome(0, 1, N);
			timer.start();
			chromosome.isValid();
			timer.stop();
		}
	}
	
	@Override
	protected ChromosomeTest measure() {
		newInstance();
		newInstanceISeq();
		isValid();
		
		return this;
	}

}
