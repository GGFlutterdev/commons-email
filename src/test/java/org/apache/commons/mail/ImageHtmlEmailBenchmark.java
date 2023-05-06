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

import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.net.URL;

@State(Scope.Benchmark)
public class ImageHtmlEmailBenchmark extends AbstractEmailTest{
    private static final boolean TEST_IS_LENIENT = true;
    private static final URL TEST_IMAGE_URL = ImageHtmlEmailTest.class.getResource("/images/asf_logo_wide.gif");
    private static final File TEST_IMAGE_DIR = new File(TEST_IMAGE_URL.getPath()).getParentFile();

    /*@Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void allTestCases(){
        Result result = JUnitCore.runClasses(ImageHtmlEmailTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }*/

    //Verifico il tempo impiegato dal metodo send() nell'inviare una mail con un semplice testo
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchSendHtml(){
        JUnitCore junit= new JUnitCore();
        Request metodoSelezionato= Request.method(ImageHtmlEmailTest.class,"testSendHtml");
        Result result = junit.run(metodoSelezionato);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

    //Verifico il tempo impiegato dal metodo send() nell'inviare una mail con un URL
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchSendHtmlUrl(){
        JUnitCore junit= new JUnitCore();
        Request metodoSelezionato= Request.method(ImageHtmlEmailTest.class,"testSendHtmlUrl");
        Result result = junit.run(metodoSelezionato);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

    //Verifico il tempo impiegato dal metodo send() nell'inviare una mail con più immagini
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchSendHTMLAutoResolveMultipleFiles(){
        JUnitCore junit= new JUnitCore();
        Request metodoSelezionato= Request.method(ImageHtmlEmailTest.class,"testSendHTMLAutoResolveMultipleFiles");
        Result result = junit.run(metodoSelezionato);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

    //Testo il metodo buildMimeMessage() sovrascritto da ImageHtmlEmail
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchBuildMimeMessage() throws Exception{
        final ImageHtmlEmail imageEmail = new ImageHtmlEmail();
        imageEmail.setHostName(this.strTestMailServer);
        imageEmail.setSmtpPort(this.getMailServerPort());
        imageEmail.setFrom("a@b.com");
        imageEmail.addTo("c@d.com");
        imageEmail.setSubject("Subject");
        imageEmail.setDataSourceResolver(new DataSourceUrlResolver(TEST_IMAGE_DIR.toURI().toURL(), TEST_IS_LENIENT));

        // set the html message
        imageEmail.setHtmlMsg(
                "<html><body><img src=\"http://www.apache.org/images/feather.gif\"/></body></html>"
        );
        imageEmail.buildMimeMessage();
    }

}
