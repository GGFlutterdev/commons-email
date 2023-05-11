/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.mail;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class EmailBenchmark extends HtmlEmailTest {

    @Setup(Level.Iteration)
    public void setUp() {
        // inizializza lo stato della classe di benchmark qui
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void setMyProperty(EmailBenchmark state) throws Exception {
        // imposta la proprietà usando il setter
        state.testEmbedFile();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void ok(EmailBenchmark state) throws Exception {
        // imposta la proprietà usando il setter
        state.testEmbedUrl();
    }

}