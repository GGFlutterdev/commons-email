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

import org.apache.commons.mail.mocks.MockHtmlEmailConcrete;
import org.apache.commons.mail.settings.EmailConfiguration;
import org.openjdk.jmh.annotations.*;
import javax.activation.FileDataSource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@State(Scope.Benchmark)
public class BenchmarkHtmlEmail extends AbstractEmailTest{

    private MockHtmlEmailConcrete email;


    // PROBLEMA: nel test chiama 3 volte lo stesso metodo per effettuare alcuni controlli come:
    // verificare che il CiD dello stesso url sia uguale e diverso da un URL con nome diverso.
    // Per questo motivo ho eseguito solo la parte di test utile per testare il metodo in esame
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchEmbedUrl() throws MalformedURLException, EmailException {
        final String strEmbed = this.email.embed(new URL(this.strTestURL), "Test name");
    }


    // PROBLEMA: nel test chiama 3 volte lo stesso metodo per effettuare alcuni controlli come:
    // verificare che il CiD di 2 file è diverso
    // Per questo motivo ho eseguito solo la parte di test utile per testare il metodo in esame
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchEmbedFile() throws Exception{
        final File file = File.createTempFile("testEmbedFile", "txt");
        final String strEmbed = this.email.embed(file);
        file.delete();
    }

    // Problema: invoca più volte lo stesso metodo
    // Per questo motivo ho eseguito solo la parte di test utile per testare il metodo in esame
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchEmbedDataSource() throws Exception{
        final File tmpFile = File.createTempFile("testEmbedDataSource", "txt");
        final FileDataSource dataSource = new FileDataSource(tmpFile);
        final String cid = this.email.embed(dataSource, "testname"); //embed() corretta
        tmpFile.delete();
    }

    // Problema: effettua il send() 2 volte
    // Per questo motivo ho eseguito solo la parte di test utile per testare il metodo in esame
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchSend() throws Exception{
        final EmailAttachment attachment = new EmailAttachment();
        final File testFile = File.createTempFile("commons-email-testfile", ".txt");
        try {
            //E' possibile che il server non viene stoppato in tempo
            // prima di far partire la prossima iterazione di benchmark
            this.getMailServer();
        }
        catch (RuntimeException e){
            System.out.println("Server in uso!");
            fakeMailServer.stop();
            this.getMailServer();
        }

        String strSubject = "Test HTML Send #1 Subject (w charset)";

        this.email = new MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(this.getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);

        attachment.setName("Test Attachment");
        attachment.setDescription("Test Attachment Desc");
        attachment.setPath(testFile.getAbsolutePath());
        this.email.attach(attachment);

        //this.email.setAuthentication(this.strTestUser, this.strTestPasswd);

        this.email.setCharset(EmailConstants.ISO_8859_1);
        this.email.setSubject(strSubject);

        final URL url = new URL(EmailConfiguration.TEST_URL);
        final String cid = this.email.embed(url, "Apache Logo");

        final String strHtmlMsg =
                "<html>The Apache logo - <img src=\"cid:" + cid + "\"><html>";

        this.email.setHtmlMsg(strHtmlMsg);
        this.email.setTextMsg(
                "Your email client does not support HTML emails");

        this.email.send();
        this.fakeMailServer.stop();
        testFile.delete();
    }

    // Problema: chiama 2 volte lo stesso metodo:
    // 1 volta con file e cid e una volta solo con il file per verificare che
    // per lo stesso file fornisce lo stesso cid
    // Per questo motivo ho eseguito solo la parte di test utile per testare il metodo in esame
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchEmbedFileWithCID() throws Exception{
        final File file = File.createTempFile("testEmbedFile", "txt");

        final String testCid = "Test CID";
        final String encodedCid = EmailUtils.encodeUrl(testCid);

        // if we embed a new file, do we get the content ID we specified back?
        final String strEmbed = this.email.embed(file, testCid);
        file.delete();
    }

    //Testo il metodo buildMimeMessage come: testHtmlMailMimeLayout ma non fa parte dei metodi di test della classe
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchBuildMimeMessage() throws Exception{
        final HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(this.strTestMailServer);
        htmlEmail.setSmtpPort(this.getMailServerPort());
        htmlEmail.setFrom("a@b.com");
        htmlEmail.addTo("c@d.com");
        final String picture= "contentTypeTest.jpg";
        final String cid = htmlEmail.embed(new File("./src/test/resources/images/" + picture), "Apache Logo");
        final String htmlMsg = "<html><img src=\"cid:" + cid + "\"><html>";
        htmlEmail.setHtmlMsg(htmlMsg);
        htmlEmail.buildMimeMessage();
    }


    @Setup(Level.Invocation)
    public void setUp() {
        email = new MockHtmlEmailConcrete();
    }

    @TearDown(Level.Invocation)
    public void doTearDown() {
        if (this.fakeMailServer!= null && this.fakeMailServer.getServer().isRunning()) {
            this.fakeMailServer.stop();
            if (this.fakeMailServer.getServer().isRunning())
                throw new RuntimeException("Server NON stoppato!!!");
        }
        fakeMailServer=null;
    }
}
