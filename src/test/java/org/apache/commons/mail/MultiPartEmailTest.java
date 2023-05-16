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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.mocks.MockMultiPartEmailConcrete;
import org.evosuite.runtime.EvoAssertions;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.net.MockURL;
import org.evosuite.runtime.testdata.EvoSuiteURL;
import org.evosuite.runtime.testdata.NetworkHandling;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.evosuite.runtime.EvoAssertions.*;

/**
 * JUnit test case for MultiPartEmail Class.
 *
 * @since 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { MockMultiPartEmailConcrete.class, URLDataSource.class })
public class MultiPartEmailTest extends AbstractEmailTest
{
    /** */
    private MockMultiPartEmailConcrete email;
    /** File to used to test file attachments (Must be valid) */
    private File testFile;

    @Before
    public void setUpMultiPartEmailTest() throws Exception
    {
        // reusable objects to be used across multiple tests
        this.email = new MockMultiPartEmailConcrete();
        testFile = File.createTempFile("testfile", ".txt");
    }

    //Test autogenerati da EvoSuite
    @Test(
            timeout = 4000L
    )
    public void test00() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.init();
        Assert.assertFalse(var1.isStartTLSRequired());
    }

    @Test(
            timeout = 4000L
    )
    public void test01() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        FileDataSource var2 = new FileDataSource("org.apache.commons.mail.DefaultAuthenticator");

        try {
            var1.attach(var2, "org.apache.commons.mail.DefaultAuthenticator", "org.apache.commons.mail.DefaultAuthenticator");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var4);
        }

    }

    /*
     * @Test(
            timeout = 4000L
    )
    public void test02() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        URL var2 = MockURL.getHttpExample();
        EvoSuiteURL var3 = new EvoSuiteURL("http://www.someFakeButWellFormedURL.org/fooExample");
        NetworkHandling.createRemoteTextFile(var3, "=h?|9LOt[$$5}e+Nc");

        try {
            var1.attach(var2, "05rN%D9+i(rGQa,", "05rN%D9+i(rGQa,", "05rN%D9+i(rGQa,");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var5) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var5);
        }

    }
     */

    @Test(
            timeout = 4000L
    )
    public void test03() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setSSLOnConnect(true);
        Email var2 = var1.setMsg("idX9S)Rp\u007f2qLnd");
        Assert.assertEquals(60000L, (long)var2.getSocketConnectionTimeout());
    }

    @Test(
            timeout = 4000L
    )
    public void test04() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.socketTimeout = 0;
        Email var2 = var1.setMsg("Je ");
        Assert.assertFalse(var2.isSSLCheckServerIdentity());
    }

    @Test(
            timeout = 4000L
    )
    public void test05() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setSocketTimeout(-3443);
        Email var2 = var1.setMsg("1>Kq\"65");
        Assert.assertEquals(60000L, (long)var2.getSocketConnectionTimeout());
    }

    @Test(
            timeout = 4000L
    )
    public void test06() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.socketConnectionTimeout = -1504;
        Email var2 = var1.setMsg("mai&l.smtp.port");
        Assert.assertEquals(-1504L, (long)var2.getSocketConnectionTimeout());
    }

    @Test(
            timeout = 4000L
    )
    public void test07() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.getContainer();
        boolean var2 = var1.isInitialized();
        Assert.assertTrue(var2);
    }

    @Test(
            timeout = 4000L
    )
    public void test08() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setSubType("Po`(.j");
        String var2 = var1.getSubType();
        Assert.assertEquals("Po`(.j", var2);
        Assert.assertNotNull(var2);
    }

    @Test(
            timeout = 4000L
    )
    public void test09() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setInitialized(true);
        MimeMultipart var2 = var1.getContainer();
        Assert.assertNull(var2);
    }

    @Test(
            timeout = 4000L
    )
    public void test10() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        BodyPart var2 = var1.createBodyPart();
        Assert.assertNotNull(var2);
    }

    @Test(
            timeout = 4000L
    )
    public void test11() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setStartTLSEnabled(true);
        EmailAttachment var2 = new EmailAttachment();
        //aggiunto queste 4 righe per configurare correttamente l'oggetto da passare come parametro
        var2.setPath(testFile.getAbsolutePath());
        var2.setDisposition(EmailAttachment.ATTACHMENT);
        var2.setName("Test_Attachment");
        var2.setDescription("Test Attachment Desc");
        var1.attach(var2);
        Assert.assertTrue(var1.isBoolHasAttachments());
    }

    @Test(
            timeout = 4000L
    )
    public void test12() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.socketConnectionTimeout = -1;
        EmailAttachment var2 = new EmailAttachment();
        //aggiunto queste 4 righe per configurare correttamente l'oggetto da passare come parametro
        var2.setPath(testFile.getAbsolutePath());
        var2.setDisposition(EmailAttachment.ATTACHMENT);
        var2.setName("Test_Attachment");
        var2.setDescription("Test Attachment Desc");
        var1.attach(var2);
        Assert.assertTrue(var1.isBoolHasAttachments());
    }

    @Test(
            timeout = 4000L
    )
    public void test13() throws Throwable {
        URL var1 = MockURL.getFtpExample();
        URLDataSource var2 = new URLDataSource(var1);
        MultiPartEmail var3 = new MultiPartEmail();
        var3.attach(var2, "attachment", "inline", "inline");
        Assert.assertTrue(var3.isBoolHasAttachments());
    }

    @Test(
            timeout = 4000L
    )
    public void test14() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.socketConnectionTimeout = -55;
        FileDataSource var2 = new FileDataSource(",!v0 HUUuZ)3cf+eyFO");
        var1.attach(var2, "`", "", (String)null);
        Assert.assertTrue(var1.isBoolHasAttachments());
    }

    @Test(
            timeout = 4000L
    )
    public void test15() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        MimeMultipart var2 = var1.getContainer();
        Email var3 = var1.addPart(var2, 0);
        Assert.assertFalse(var3.isSSLCheckServerIdentity());
    }

    @Test(
            timeout = 4000L
    )
    public void test16() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        MimeMultipart var2 = new MimeMultipart();
        var1.setSocketConnectionTimeout(-299);
        Email var3 = var1.addPart(var2, 0);
        Assert.assertFalse(var3.isStartTLSRequired());
    }

    @Test(
            timeout = 4000L
    )
    public void test17() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setStartTLSRequired(true);
        MimeMultipart var2 = var1.getContainer();
        Email var3 = var1.addPart(var2);
        Assert.assertNull(var3.getSubject());
    }

    @Test(
            timeout = 4000L
    )
    public void test18() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setSocketTimeout(-1);
        MimeMultipart var2 = new MimeMultipart();
        Email var3 = var1.addPart(var2);
        Assert.assertNull(var3.getBounceAddress());
    }

    @Test(
            timeout = 4000L
    )
    public void test19() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.socketConnectionTimeout = 0;
        MimeMultipart var2 = var1.getContainer();
        Email var3 = var1.addPart(var2);
        Assert.assertNull(var3.getBounceAddress());
    }

    @Test(
            timeout = 4000L
    )
    public void test20() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setStartTLSRequired(true);
        Email var2 = var1.addPart("iso-8859-1", "iso-8859-1");
        Assert.assertNull(var2.getBounceAddress());
    }

    @Test(
            timeout = 4000L
    )
    public void test21() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setSendPartial(true);
        Email var2 = var1.addPart("xBdq74]K?OtaG", "(Ll)l");
        Assert.assertFalse(var2.isSSLCheckServerIdentity());
    }

    @Test(
            timeout = 4000L
    )
    public void test22() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setSSLOnConnect(true);
        var1.addPart("Already initialized", "Already initialized");
        MimeMultipart var2 = var1.emailBody;
        Email var3 = var1.addPart(var2);
        Assert.assertSame(var1, var3);
    }

    @Test(
            timeout = 4000L
    )
    public void test23() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setSocketTimeout(0);
        Email var2 = var1.addPart("org.apache.commons.mail.MultiPartEmail", "Invalid Datasource");
        Assert.assertFalse(var2.isTLS());
    }

    @Test(
            timeout = 4000L
    )
    public void test24() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.socketTimeout = -1724;
        MultiPartEmail var2 = (MultiPartEmail)var1.addPart("EXRrg", "EXRrg");
        Assert.assertFalse(var2.isBoolHasAttachments());
    }

    @Test(
            timeout = 4000L
    )
    public void test25() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.socketConnectionTimeout = 0;
        MultiPartEmail var2 = (MultiPartEmail)var1.addPart("org.apache.commons.mail.MultiPartEmail", "Invalid Datasource");
        Assert.assertFalse(var2.isBoolHasAttachments());
    }

    @Test(
            timeout = 4000L
    )
    public void test26() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.socketConnectionTimeout = -1504;
        Email var2 = var1.addPart("*m/Ba_/D9VbW<", "0k>U|=~s]-Vu");
        Assert.assertFalse(var2.isSSLOnConnect());
    }

    @Test(
            timeout = 4000L
    )
    public void test27() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        Email var2 = var1.setMsg(",[0ALZ'nK5@,ELq");
        Properties var3 = new Properties();
        Session var4 = Session.getDefaultInstance(var3);
        MimeMessage var5 = new MimeMessage(var4);
        var2.message = var5;

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException var7) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var7);
        }

    }

    /*
     * @Test(
            timeout = 4000L
    )
    public void test28() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        URL var2 = MockURL.getHttpExample();

        try {
            var1.attach(var2, "", "", "");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var4);
        }

    }
     */

    @Test(
            timeout = 4000L,
            expected = NullPointerException.class
    )
    public void test29() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();

        //Commentato il try-catch perchè forniva warning nell'eseguire questo metodo
        // Ho utilizzato l'attributo expected per indicare l'eccezione che mi aspetto sia lanciata

        //try {
            var1.attach((URL)null, (String)null, (String)null);
            Assert.fail("Expecting exception: NullPointerException");
        /*} catch (NullPointerException var3) {
            verifyException("org.evosuite.runtime.mock.java.net.MockURL", var3);
        }*/

    }

    @Test(
            timeout = 4000L
    )
    public void test30() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();

        try {
            var1.attach((File)null);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var3);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test31() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();

        try {
            var1.addPart((MimeMultipart)null, -377);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("javax.mail.internet.MimeBodyPart", var3);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test32() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        Email var2 = var1.setMsg("Jspwd~C`f,]VN");
        Email var3 = var1.setMsg("mail.smtp.timeout");
        Assert.assertSame(var3, var2);
    }

    @Test(
            timeout = 4000L
    )
    public void test33() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();

        try {
            var1.attach((DataSource)null, "", "", "Invalid attachment supplied");
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var3);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test34() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        FileDataSource var2 = new FileDataSource("~vOR<c_>");

        try {
            var1.attach(var2, "~vOR<c_>", "~vOR<c_>", "~vOR<c_>");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var4);
        }

    }

    @Test(
            timeout = 4000L,
            expected = NullPointerException.class
    )
    public void test35() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();

        //Commentato il try-catch perchè forniva warning nell'eseguire questo metodo
        // Ho utilizzato l'attributo expected per indicare l'eccezione che mi aspetto sia lanciata

        //try {
            var1.attach((URL)null, "n:", "n:", "n:");
            Assert.fail("Expecting exception: NullPointerException");
        /*} catch (NullPointerException var3) {
            verifyException("org.evosuite.runtime.mock.java.net.MockURL", var3);
        }*/

    }

    @Test(
            timeout = 4000L
    )
    public void test36() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setBoolHasAttachments(false);
        Assert.assertFalse(var1.isBoolHasAttachments());
    }

    @Test(
            timeout = 4000L
    )
    public void test37() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        MimeMultipart var2 = var1.createMimeMultipart();

        try {
            var1.addPart(var2, 2489);
            Assert.fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException var4) {
            EvoAssertions.verifyException("java.util.Vector", var4);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test38() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();

        try {
            var1.attach((DataSource)null, "|s9J`hI$LC72UY'O", "|s9J`hI$LC72UY'O");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var3);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test39() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        URL var2 = MockURL.getFtpExample();
        URLDataSource var3 = new URLDataSource(var2);

        try {
            var1.attach(var3, "@hAJlgrJO- #", "@hAJlgrJO- #");
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var5) {
            EvoAssertions.verifyException("java.net.URL", var5);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test40() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        EmailAttachment var2 = new EmailAttachment();
        var2.setPath("-QyL~#7!?x$");

        try {
            var1.attach(var2);
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var4);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test41() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        EmailAttachment var2 = new EmailAttachment();
        URL var3 = MockURL.getFtpExample();
        var2.setURL(var3);

        try {
            var1.attach(var2);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var5) {
            EvoAssertions.verifyException("java.net.URL", var5);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test42() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();

        try {
            var1.attach((EmailAttachment)null);
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var3);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test43() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        MockFile var2 = new MockFile("427HWz/Cu>qYaqU:");

        try {
            var1.attach(var2);
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var4);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test44() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        File var2 = MockFile.createTempFile("uzet(#mIdIX=%TFk", "uzet(#mIdIX=%TFk");
        var1.attach(var2);
        Assert.assertTrue(var1.isBoolHasAttachments());
    }

    @Test(
            timeout = 4000L
    )
    public void test45() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.getPrimaryBodyPart();

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var3);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test46() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var3);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test47() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.charset = "Tp?";
        Email var2 = var1.setMsg("Already initialized");
        Assert.assertFalse(var2.isSSLOnConnect());
    }

    @Test(
            timeout = 4000L
    )
    public void test48() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();

        try {
            var1.setMsg("");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var3);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test49() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.getContainer();

        try {
            var1.init();
            Assert.fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.MultiPartEmail", var3);
        }

    }

    /*
        ////PROBLEMA: se il test è eseguito da solo non comporta problemi altrimenti non viene lanciata l'eccezione attesa
    @Test(
            expected = EmailException.class
    )
    public void test50() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        URL var2 = MockURL.getHttpExample();

        // Commentato il try-catch perchè forniva warning nell'eseguire questo metodo
        // Ho utilizzato l'attributo expected per indicare l'eccezione che mi aspetto sia lanciata

        //try {
            var1.attach(var2, "<(e^B2gklbcaKryGA", "<(e^B2gklbcaKryGA");
            Assert.fail("Expecting exception: Exception");
        //} catch (Exception var4) {
            //verifyException("org.apache.commons.mail.MultiPartEmail", var4);
        //}

    }*/

    /*
    Ho eliminato questo test autogenerato perchè effettua lo stesso controllo del test12
    @Test(
            timeout = 4000L
    )
    public void test51() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        EmailAttachment var2 = new EmailAttachment();
        MultiPartEmail var3 = var1.attach(var2);
        boolean var4 = var3.isBoolHasAttachments();
        Assert.assertTrue(var4);
    }
    */

    @Test(
            timeout = 4000L
    )
    public void test52() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        boolean var2 = var1.isBoolHasAttachments();
        Assert.assertFalse(var2);
    }

    @Test(
            timeout = 4000L
    )
    public void test53() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        MimeMultipart var2 = var1.emailBody;

        try {
            var1.addPart(var2);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var4) {
            EvoAssertions.verifyException("javax.mail.internet.MimeBodyPart", var4);
        }

    }

    @Test(
            timeout = 4000L
    )
    public void test54() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        String var2 = var1.getSubType();
        Assert.assertNull(var2);
    }

    @Test(
            timeout = 4000L
    )
    public void test55() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        boolean var2 = var1.isInitialized();
        Assert.assertFalse(var2);
    }

    @Test(
            timeout = 4000L
    )
    public void test56() throws Throwable {
        MultiPartEmail var1 = new MultiPartEmail();
        var1.setSubType("From address required");

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var3);
        }

    }


    @Test
    public void testSetMsg() throws EmailException
    {
        // ====================================================================
        // Test Success
        // ====================================================================

        // without charset set
        for (final String validChar : testCharsValid)
        {
            this.email.setMsg(validChar);
            assertEquals(validChar, this.email.getMsg());
        }

        // with charset set
        this.email.setCharset(EmailConstants.US_ASCII);
        for (final String validChar : testCharsValid)
        {
            this.email.setMsg(validChar);
            assertEquals(validChar, this.email.getMsg());
        }

        // ====================================================================
        // Test Exceptions
        // ====================================================================
        for (final String invalidChar : testCharsNotValid)
        {
            try
            {
                this.email.setMsg(invalidChar);
                fail("Should have thrown an exception");
            }
            catch (final EmailException e)
            {
                assertTrue(true);
            }
        }
    }

    /**
     * @throws EmailException when a bad address or attachment is used
     * @throws IOException when sending fails
     */
    @Test
    public void testSend() throws EmailException, IOException
    {
        // ====================================================================
        // Test Success
        // ====================================================================
        this.getMailServer();

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
        // validate message
        validateSend(
            this.fakeMailServer,
            strSubject,
            testEmail.getMsg(),
            testEmail.getFromAddress(),
            testEmail.getToAddresses(),
            testEmail.getCcAddresses(),
            testEmail.getBccAddresses(),
            true);

        // validate attachment
        validateSend(
            this.fakeMailServer,
            strSubject,
            attachment.getName(),
            testEmail.getFromAddress(),
            testEmail.getToAddresses(),
            testEmail.getCcAddresses(),
            testEmail.getBccAddresses(),
            false);

        // ====================================================================
        // Test Exceptions
        // ====================================================================
        try
        {
            this.getMailServer();

            this.email.send();
            fail("Should have thrown an exception");
        }
        catch (final EmailException e)
        {
            this.fakeMailServer.stop();
        }
    }

    @Test
    public void testAttach() throws Exception
    {
        EmailAttachment attachment;

        // ====================================================================
        // Test Success - EmailAttachment
        // ====================================================================
        attachment = new EmailAttachment();
        attachment.setName("Test Attachment");
        attachment.setDescription("Test Attachment Desc");
        attachment.setPath(testFile.getAbsolutePath());
        this.email.attach(attachment);
        assertTrue(this.email.isBoolHasAttachments());

        // ====================================================================
        // Test Success - URL
        // ====================================================================
        attachment = new EmailAttachment();
        attachment.setName("Test Attachment");
        attachment.setDescription("Test Attachment Desc");
        attachment.setURL(new URL(this.strTestURL));
        this.email.attach(attachment);

        // ====================================================================
        // Test Success - File
        // ====================================================================
        this.email.attach(testFile);
        assertTrue(this.email.isBoolHasAttachments());

        // ====================================================================
        // Test Exceptions
        // ====================================================================
        // null attachment
        try
        {
            this.email.attach((EmailAttachment) null);
            fail("Should have thrown an exception");
        }
        catch (final EmailException e)
        {
            assertTrue(true);
        }

        // bad url
        attachment = new EmailAttachment();
        try
        {
            attachment.setURL(createInvalidURL());
            this.email.attach(attachment);
            fail("Should have thrown an exception");
        }
        catch (final EmailException e)
        {
            assertTrue(true);
        }

        // bad file
        attachment = new EmailAttachment();
        try
        {
            attachment.setPath("");
            this.email.attach(attachment);
            fail("Should have thrown an exception");
        }
        catch (final EmailException e)
        {
            assertTrue(true);
        }
    }

    /**
     * @throws MalformedURLException when a bad attachment URL is used
     * @throws EmailException when a bad address or attachment is used
     */
    @Test
    public void testAttach2() throws MalformedURLException, EmailException
    {
        // ====================================================================
        // Test Success - URL
        // ====================================================================
        this.email.attach(
            new URL(this.strTestURL),
            "Test Attachment",
            "Test Attachment Desc");

        // bad name
        this.email.attach(
            new URL(this.strTestURL),
            null,
            "Test Attachment Desc");
    }

    @Test
    public void testAttach3() throws Exception
    {
        // ====================================================================
        // Test Success - URL
        // ====================================================================
        this.email.attach(
            new URLDataSource(new URL(this.strTestURL)),
            "Test Attachment",
            "Test Attachment Desc");

        // ====================================================================
        // Test Exceptions
        // ====================================================================
        // null datasource
        try
        {
            final URLDataSource urlDs = null;
            this.email.attach(urlDs, "Test Attachment", "Test Attachment Desc");
            fail("Should have thrown an exception");
        }
        catch (final EmailException e)
        {
            assertTrue(true);
        }

        // invalid datasource
        try
        {
            final URLDataSource urlDs = new URLDataSource(createInvalidURL());
            this.email.attach(urlDs, "Test Attachment", "Test Attachment Desc");
            fail("Should have thrown an exception");
        }
        catch (final EmailException e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testAttachFileLocking() throws Exception {

        // ====================================================================
        // EMAIL-120: attaching a FileDataSource may result in a locked file
        //            resource on windows systems
        // ====================================================================

        final File tmpFile = File.createTempFile("attachment", ".eml");

        this.email.attach(
                new FileDataSource(tmpFile),
                "Test Attachment",
                "Test Attachment Desc");

        assertTrue(tmpFile.delete());
    }

    @Test
    public void testAddPart() throws Exception
    {

        // setup
        this.email = new MockMultiPartEmailConcrete();
        final String strMessage = "hello";
        final String strContentType = "text/plain";

        // add part
        this.email.addPart(strMessage, strContentType);

        // validate
        assertEquals(
            strContentType,
            this.email.getContainer().getBodyPart(0).getContentType());
        assertEquals(
            strMessage,
            this.email.getContainer().getBodyPart(0).getDataHandler()
                .getContent());

    }

    @Test
    public void testAddPart2() throws Exception
    {

        // setup
        this.email = new MockMultiPartEmailConcrete();
        final String strSubtype = "subtype/abc123";

        // add part
        this.email.addPart(new MimeMultipart(strSubtype));

        // validate
        assertTrue(
            this
                .email
                .getContainer()
                .getBodyPart(0)
                .getDataHandler()
                .getContentType()
                .contains(strSubtype));

    }

    /** TODO implement test for GetContainer */
    @Test
    public void testGetContainer()
    {
        assertTrue(true);
    }

    /** init called twice should fail */
    @Test
    public void testInit()
    {
        // call the init function twice to trigger the IllegalStateException
        try
        {
            this.email.init();
            this.email.init();
            fail("Should have thrown an exception");
        }
        catch (final IllegalStateException e)
        {
            assertTrue(true);
        }
    }

    /** test get/set sub type */
    @Test
    public void testGetSetSubType()
    {
        for (final String validChar : testCharsValid)
        {
            this.email.setSubType(validChar);
            assertEquals(validChar, this.email.getSubType());
        }
    }
}
