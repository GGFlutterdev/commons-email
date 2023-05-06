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

import org.apache.commons.mail.mocks.MockMultiPartEmailConcrete;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;


@State(Scope.Benchmark)
public class MultipartEmailBenchmark extends AbstractEmailTest{

    private MockMultiPartEmailConcrete email;
    private File testFile;

    @Setup(Level.Iteration)
    public void setEnvironment() throws IOException {
        this.email = new MockMultiPartEmailConcrete();
        testFile = File.createTempFile("testfile", ".txt");
    }



    //Verifica del tempo impigato dal metodo setMsg() senza settare il charset
    @Benchmark
    @Fork(value = 1,warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    public void testSetMsgNoCharset() throws EmailException{
        for (final String validChar : testCharsValid)
        {
            this.email.setMsg(validChar);
        }
    }

    //Verifica del tempo impigato dal metodo setMsg() settando il charset
   @Benchmark
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    public void testSetMsgCharset()throws EmailException{
        this.email.setCharset(EmailConstants.US_ASCII);
        for (final String validChar : testCharsValid)
        {
            this.email.setMsg(validChar);
        }
    }


   //Verifica del tempo impiegato dal metodo send()
    @Fork(value=1,warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void testSend() throws Exception{
        try{
            this.getMailServer();
        }catch (Exception e){
            System.out.println("Server ancora attivo dall'iterazione precedente!!");
            fakeMailServer.stop();
            this.getMailServer();
        }

        final String strSubject = "Test Multipart Send Subject";

        final EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(testFile.getAbsolutePath());
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setName("Test_Attachment");
        attachment.setDescription("Test Attachment Desc");

        final MockMultiPartEmailConcrete testEmail =
                new MockMultiPartEmailConcrete();
        testEmail.setHostName(this.strTestMailServer);
        testEmail.setSmtpPort(this.getMailServerPort());
        testEmail.setFrom(this.strTestMailFrom);
        testEmail.addTo(this.strTestMailTo);
        testEmail.attach(attachment);
        testEmail.setSubType("subType");

        if (EmailUtils.isNotEmpty(this.strTestUser)
                && EmailUtils.isNotEmpty(this.strTestPasswd))
        {
            testEmail.setAuthentication(
                    this.strTestUser,
                    this.strTestPasswd);
        }

        testEmail.setSubject(strSubject);

        testEmail.setMsg("Test Message");

        final Map<String, String> ht = new HashMap<>();
        ht.put("X-Priority", "2");
        ht.put("Disposition-Notification-To", this.strTestMailFrom);
        ht.put("X-Mailer", "Sendmail");

        testEmail.setHeaders(ht);

        testEmail.send();

        this.fakeMailServer.stop();
    }

    //Verifica del tempo impiegato dal metodo attach(final EmailAttachment attachment) con path
    @Benchmark
    @Fork(value = 1,warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    public void testAttachEmailAttachment() throws Exception{
        EmailAttachment attachment;
        attachment = new EmailAttachment();
        attachment.setName("Test Attachment");
        attachment.setDescription("Test Attachment Desc");
        attachment.setPath(testFile.getAbsolutePath());
        this.email.attach(attachment);
    }

    //Verifica del tempo impiegato dal metodo attach(final EmailAttachment attachment) con URL
    @Benchmark
    @Fork(value = 1,warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    public void testAttachURL() throws Exception {
        EmailAttachment attachment;
        attachment = new EmailAttachment();
        attachment.setName("Test Attachment");
        attachment.setDescription("Test Attachment Desc");
        attachment.setURL(new URL(this.strTestURL));
        this.email.attach(attachment);
    }


    //Verifica del tempo impiegato dal metodo attach(final File file)
    @Benchmark
    @Fork(value = 1,warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    public void testAttachFile() throws Exception{
        this.email.attach(testFile);
        assertTrue(this.email.isBoolHasAttachments());
    }


    //Verifica del tempo impiegato dal metodo addPart(final String partContent, final String partContentType)
    @Benchmark
    @Fork(value = 1,warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    public void testAddPart() throws Exception {
        JUnitCore junit = new JUnitCore();
        Request metodoSelezionato = Request.method(MultiPartEmailTest.class, "testAddPart");
        Result result = junit.run(metodoSelezionato);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

    //Verifica del tempo impiegato dal metodo addPart(final MimeMultipart multipart)
    @Benchmark
    @Fork(value = 1,warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    public void testAddPart2() throws Exception {
        JUnitCore junit = new JUnitCore();
        Request metodoSelezionato = Request.method(MultiPartEmailTest.class, "testAddPart2");
        Result result = junit.run(metodoSelezionato);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
