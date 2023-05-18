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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.mocks.MockHtmlEmailConcrete;
import org.apache.commons.mail.settings.EmailConfiguration;
import org.apache.commons.mail.util.MimeMessageParser;
import org.evosuite.runtime.EvoAssertions;
import org.evosuite.runtime.Random;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.net.MockURL;
import org.evosuite.runtime.testdata.EvoSuiteURL;
import org.evosuite.runtime.testdata.NetworkHandling;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * JUnit test case for HtmlEmail Class.
 *
 * @since 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { MockHtmlEmailConcrete.class })
public class HtmlEmailTest extends AbstractEmailTest
{
    private MockHtmlEmailConcrete email;

    @Before
    public void setUpHtmlEmailTest()
    {
        // reusable objects to be used across multiple tests
        this.email = new MockHtmlEmailConcrete();
    }

    @Test
    public void testGetSetTextMsg() throws EmailException
    {
        // ====================================================================
        // Test Success
        // ====================================================================
        for (final String validChar : testCharsValid)
        {
            this.email.setTextMsg(validChar);
            assertEquals(validChar, this.email.getTextMsg());
        }

        // ====================================================================
        // Test Exception
        // ====================================================================
        for (final String invalidChar : this.testCharsNotValid)
        {
            try
            {
                this.email.setTextMsg(invalidChar);
                fail("Should have thrown an exception");
            }
            catch (final EmailException e)
            {
                assertTrue(true);
            }
        }

    }

    @Test
    public void testGetSetHtmlMsg() throws EmailException
    {
        // ====================================================================
        // Test Success
        // ====================================================================
        for (final String validChar : testCharsValid)
        {
            this.email.setHtmlMsg(validChar);
            assertEquals(validChar, this.email.getHtmlMsg());
        }

        // ====================================================================
        // Test Exception
        // ====================================================================
        for (final String invalidChar : this.testCharsNotValid)
        {
            try
            {
                this.email.setHtmlMsg(invalidChar);
                fail("Should have thrown an exception");
            }
            catch (final EmailException e)
            {
                assertTrue(true);
            }
        }

    }

    @Test
    public void testGetSetMsg() throws EmailException
    {
        // ====================================================================
        // Test Success
        // ====================================================================
        for (final String validChar : testCharsValid)
        {
            this.email.setMsg(validChar);
            assertEquals(validChar, this.email.getTextMsg());

            assertTrue(
                    this.email.getHtmlMsg().contains(validChar));
        }

        // ====================================================================
        // Test Exception
        // ====================================================================
        for (final String invalidChar : this.testCharsNotValid)
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

    @Test
    public void testEmbedUrl() throws Exception
    {
        // ====================================================================
        // Test Success
        // ====================================================================

        final String strEmbed =
            this.email.embed(new URL(this.strTestURL), "Test name");
        assertNotNull(strEmbed);
        assertEquals(HtmlEmail.CID_LENGTH, strEmbed.length());

        // if we embed the same name again, do we get the same content ID
        // back?
        final String testCid =
            this.email.embed(new URL(this.strTestURL), "Test name");
        assertEquals(strEmbed, testCid);

        // if we embed the same URL under a different name, is the content ID
        // unique?
        final String newCid =
            this.email.embed(new URL(this.strTestURL), "Test name 2");
        assertFalse(strEmbed.equals(newCid));

        // ====================================================================
        // Test Exceptions
        // ====================================================================

        // Does an invalid URL throw an exception?
        try
        {
            this.email.embed(createInvalidURL(), "Bad URL");
            fail("Should have thrown an exception");
        }
        catch (final EmailException e)
        {
            // expected
        }

        // if we try to embed a different URL under a previously used name,
        // does it complain?
        try
        {
            this.email.embed(new URL("http://www.google.com"), "Test name");
            fail("shouldn't be able to use an existing name with a different URL!");
        }
        catch (final EmailException e)
        {
            // expected
        }
    }

    @Test
    public void testEmbedFile() throws Exception
    {
        // ====================================================================
        // Test Success
        // ====================================================================

        final File file = File.createTempFile("testEmbedFile", "txt");
        file.deleteOnExit();
        final String strEmbed = this.email.embed(file);
        assertNotNull(strEmbed);
        assertEquals("generated CID has wrong length",
                HtmlEmail.CID_LENGTH, strEmbed.length());

        // if we embed the same file again, do we get the same content ID
        // back?
        final String testCid =
            this.email.embed(file);
        assertEquals("didn't get same CID after embedding same file twice",
                strEmbed, testCid);

        // if we embed a new file, is the content ID unique?
        final File otherFile = File.createTempFile("testEmbedFile2", "txt");
        otherFile.deleteOnExit();
        final String newCid = this.email.embed(otherFile);
        assertFalse("didn't get unique CID from embedding new file",
                strEmbed.equals(newCid));
    }

    @Test
    public void testEmbedUrlAndFile() throws Exception
    {
        final File tmpFile = File.createTempFile("testfile", "txt");
        tmpFile.deleteOnExit();
        final String fileCid = this.email.embed(tmpFile);

        final URL fileUrl = tmpFile.toURI().toURL();
        final String urlCid = this.email.embed(fileUrl, "urlName");

        assertFalse("file and URL cids should be different even for same resource",
                fileCid.equals(urlCid));
    }

    @Test
    public void testEmbedDataSource() throws Exception
    {
        final File tmpFile = File.createTempFile("testEmbedDataSource", "txt");
        tmpFile.deleteOnExit();
        final FileDataSource dataSource = new FileDataSource(tmpFile);

        // does embedding a datasource without a name fail?
        try
        {
            this.email.embed(dataSource, "");
            fail("embedding with an empty string for a name should fail");
        }
        catch (final EmailException e)
        {
            // expected
        }

        // properly embed the datasource
        final String cid = this.email.embed(dataSource, "testname");

        // does embedding the same datasource under the same name return
        // the original cid?
        final String sameCid = this.email.embed(dataSource, "testname");
        assertEquals("didn't get same CID for embedding same datasource twice",
                cid, sameCid);

        // does embedding another datasource under the same name fail?
        final File anotherFile = File.createTempFile("testEmbedDataSource2", "txt");
        anotherFile.deleteOnExit();
        final FileDataSource anotherDS = new FileDataSource(anotherFile);
        try
        {
            this.email.embed(anotherDS, "testname");
        }
        catch (final EmailException e)
        {
            // expected
        }
    }

    /**
     * @throws EmailException when bad addresses and attachments are used
     * @throws IOException if creating a temp file, URL or sending fails
     */
    @Test
    public void testSend() throws EmailException, IOException
    {
        final EmailAttachment attachment = new EmailAttachment();

        /** File to used to test file attachments (Must be valid) */
        final File testFile = File.createTempFile("commons-email-testfile", ".txt");
        testFile.deleteOnExit();

        // ====================================================================
        // Test Success
        // ====================================================================
        this.getMailServer();

        String strSubject = "Test HTML Send #1 Subject (w charset)";

        this.email = new MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(this.getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);

        /** File to used to test file attachmetns (Must be valid) */
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
        // validate txt message
        validateSend(
            this.fakeMailServer,
            strSubject,
            this.email.getTextMsg(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            true);

        // validate html message
        validateSend(
            this.fakeMailServer,
            strSubject,
            this.email.getHtmlMsg(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            false);

        // validate attachment
        validateSend(
            this.fakeMailServer,
            strSubject,
            attachment.getName(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            false);

        this.getMailServer();

        this.email = new MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(this.getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);

        if (this.strTestUser != null && this.strTestPasswd != null)
        {
            this.email.setAuthentication(
                this.strTestUser,
                this.strTestPasswd);
        }

        strSubject = "Test HTML Send #1 Subject (wo charset)";
        this.email.setSubject(strSubject);
        this.email.setTextMsg("Test message");

        this.email.send();
        this.fakeMailServer.stop();
        // validate txt message
        validateSend(
            this.fakeMailServer,
            strSubject,
            this.email.getTextMsg(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            true);
    }

    @Test
    public void testSend2() throws Exception
    {
        // ====================================================================
        // Test Success
        // ====================================================================

        this.getMailServer();

        this.email = new MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(this.getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);

        if (this.strTestUser != null && this.strTestPasswd != null)
        {
            this.email.setAuthentication(
                this.strTestUser,
                this.strTestPasswd);
        }

        String strSubject = "Test HTML Send #2 Subject (wo charset)";
        this.email.setSubject(strSubject);
        this.email.setMsg("Test txt msg");

        this.email.send();
        this.fakeMailServer.stop();
        // validate txt message
        validateSend(
            this.fakeMailServer,
            strSubject,
            this.email.getTextMsg(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            true);

        // validate html message
        validateSend(
            this.fakeMailServer,
            strSubject,
            this.email.getHtmlMsg(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            false);

        this.getMailServer();

        this.email = new MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setFrom(this.strTestMailFrom);
        this.email.setSmtpPort(this.getMailServerPort());
        this.email.addTo(this.strTestMailTo);

        if (this.strTestUser != null && this.strTestPasswd != null)
        {
            this.email.setAuthentication(
                this.strTestUser,
                this.strTestPasswd);
        }

        strSubject = "Test HTML Send #2 Subject (w charset)";
        this.email.setCharset(EmailConstants.ISO_8859_1);
        this.email.setSubject(strSubject);
        this.email.setMsg("Test txt msg");

        this.email.send();
        this.fakeMailServer.stop();
        // validate txt message
        validateSend(
            this.fakeMailServer,
            strSubject,
            this.email.getTextMsg(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            true);

        // validate html message
        validateSend(
            this.fakeMailServer,
            strSubject,
            this.email.getHtmlMsg(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            false);

    }

    @Test
    @Ignore
    public void testSendWithDefaultCharset() throws Exception
    {
        // Test is disabled as its result is dependent on the execution order:
        // the mail.mime.charset property is normally cached by the MimeUtility
        // class, thus setting it to another value while running the tests
        // might not have the expected result.

        // ====================================================================
        // Test Success
        // ====================================================================

        System.setProperty(EmailConstants.MAIL_MIME_CHARSET, "iso-8859-15");

        this.getMailServer();

        this.email = new MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(this.getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);

        if (this.strTestUser != null && this.strTestPasswd != null)
        {
            this.email.setAuthentication(
                this.strTestUser,
                this.strTestPasswd);
        }

        final String strSubject = "Test HTML Send Subject (w default charset)";
        this.email.setSubject(strSubject);
        this.email.setMsg("Test txt msg ä"); // add non-ascii character, otherwise us-ascii will be used

        this.email.send();
        this.fakeMailServer.stop();
        // validate charset
        validateSend(
            this.fakeMailServer,
            strSubject,
            "charset=iso-8859-15",
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            true);

        System.clearProperty(EmailConstants.MAIL_MIME_CHARSET);

    }

    /**
     * Create a HTML email containing an URL pointing to a ZIP file
     * to be downloaded. According to EMAIL-93 the resulting URL
     * "http://paradisedelivery.homeip.net/delivery/?file=TZC268X93337..zip"
     * contains TWO dots instead of one dot which breaks the link.
     */
    @Test
    public void testAddZipUrl() throws Exception
    {
        final String htmlMsg =
                "Please click on the following link: <br><br>" +
                "<a href=\"http://paradisedelivery.homeip.net/delivery/?file=3DTZC268X93337.zip\">" +
                "http://paradisedelivery.homeip.net/delivery/?file=3DTZC268X93337.zip" +
                "</a><br><br>Customer satisfaction is very important for us.";

        this.getMailServer();

        this.email = new MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(this.getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        this.email.setCharset(EmailConstants.ISO_8859_1);

        if (this.strTestUser != null && this.strTestPasswd != null)
        {
            this.email.setAuthentication(
                this.strTestUser,
                this.strTestPasswd);
        }

        final String strSubject = "A dot (\".\") is appended to some ULRs of a HTML mail.";
        this.email.setSubject(strSubject);
        this.email.setHtmlMsg(htmlMsg);

        this.email.send();
        this.fakeMailServer.stop();

        // validate html message
        validateSend(
            this.fakeMailServer,
            strSubject,
            this.email.getHtmlMsg(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            false);

        // make sure that no double dots show up
        assertTrue(this.email.getHtmlMsg().contains("3DTZC268X93337.zip"));
        assertFalse(this.email.getHtmlMsg().contains("3DTZC268X93337..zip"));
    }

    /**
     * According to EMAIL-95 calling buildMimeMessage() before calling send()
     * causes duplicate mime parts - now we throw an exception to catch the
     * problem
     */
    @Test
    public void testCallingBuildMimeMessageBeforeSent() throws Exception {

        final String htmlMsg = "<b>Hello World</b>";

        this.email = new MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(this.getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        this.email.setCharset(EmailConstants.ISO_8859_1);

        if (this.strTestUser != null && this.strTestPasswd != null)
        {
            this.email.setAuthentication(
                this.strTestUser,
                this.strTestPasswd);
        }

        final String strSubject = "testCallingBuildMimeMessageBeforeSent";
        this.email.setSubject(strSubject);
        this.email.setHtmlMsg(htmlMsg);

        // this should NOT be called when sending a message
        this.email.buildMimeMessage();

        try
        {
            this.email.send();
        }
        catch(final IllegalStateException e)
        {
            return;
        }

        fail("Expecting an exception when calling buildMimeMessage() before send() ...");
    }

    /**
     * EMAIL-73 - check that providing a plain text content using setMsg()
     * creates a plain content and HTML content using {@code <pre>} tags.
     */
    @Test
    public void testSendWithPlainTextButNoHtmlContent() throws EmailException, IOException
    {
        this.getMailServer();

        final String strSubject = "testSendWithPlainTextButNoHtmlContent";

        this.email = new MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(this.getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        this.email.setCharset(EmailConstants.ISO_8859_1);
        this.email.setSubject(strSubject);
        this.email.setMsg("This is a plain text content : <b><&npsb;></html></b>");

        this.email.send();

        this.fakeMailServer.stop();

        // validate text message
        validateSend(
            this.fakeMailServer,
            strSubject,
            this.email.getTextMsg(),
            this.email.getFromAddress(),
            this.email.getToAddresses(),
            this.email.getCcAddresses(),
            this.email.getBccAddresses(),
            true);
    }

    /**
     * Test that the specified Content-ID is used when embedding a File
     * object in an HtmlEmail.
     *
     * Rolled back the changes since they broke real emails therefore
     * the test is currently disabled.
     *
     * see https://issues.apache.org/jira/browse/EMAIL-101
     */
    @Test
    public void testEmbedFileWithCID() throws Exception
    {
         // ====================================================================
         // Test Success
         // ====================================================================

         final File file = File.createTempFile("testEmbedFile", "txt");
         file.deleteOnExit();

         final String testCid = "Test CID";
         final String encodedCid = EmailUtils.encodeUrl(testCid);

         // if we embed a new file, do we get the content ID we specified back?
         final String strEmbed = this.email.embed(file, testCid);
         assertNotNull(strEmbed);
         assertEquals("didn't get same CID when embedding with a specified CID", encodedCid, strEmbed);

         // if we embed the same file again, do we get the same content ID
         // back?
         final String returnedCid = this.email.embed(file);
         assertEquals("didn't get same CID after embedding same file twice", encodedCid, returnedCid);
    }

    @Test
    public void testHtmlMailMimeLayout() throws Exception
    {
        assertCorrectContentType("contentTypeTest.gif", "image/gif");
        assertCorrectContentType("contentTypeTest.jpg", "image/jpeg");
        assertCorrectContentType("contentTypeTest.png", "image/png");
    }

    //Tests generated by EvoSuite

    //Il risultato di questo test non è deterministico in quanto var3 assume un valore casuale
/*
    @Test
    public void test00() throws Throwable {
        FileDataSource var1 = new FileDataSource("receiver.name");
        Random.setNextRandom(-1089614106);
        HtmlEmail var2 = new HtmlEmail();
        String var3 = var2.embed(var1, "HRRQjn?\u007f?vMaCw:J");
        Assert.assertNotNull(var3);
        Assert.assertEquals("cccccccccc", var3);
    }*/
    @Test
    public void test01() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        MockFile var2 = new MockFile("_s2 *P#", "CC }.6");

        try {
            var1.embed(var2, "CC }.6");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }

    }

    @Test
    public void test02() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setStartTLSEnabled(true);
        HtmlEmail var2 = var1.setTextMsg("text/html");
        Assert.assertFalse(var2.isSSLCheckServerIdentity());
    }

    @Test
    public void test03() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        Email var2 = var1.setSendPartial(true);
        HtmlEmail var3 = var1.setTextMsg("text/html");
        Assert.assertSame(var3, var2);
    }

    @Test
    public void test04() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSSLCheckServerIdentity(true);
        HtmlEmail var2 = var1.setTextMsg("mail.smtp.sendpartial");
        Assert.assertFalse(var2.isBoolHasAttachments());
    }

    @Test
    public void test05() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSSLOnConnect(true);
        HtmlEmail var2 = var1.setTextMsg("mxbu(F ");
        Assert.assertTrue(var2.isSSLOnConnect());
    }

    @Test(

            expected = EmailException.class
    )
    public void test06() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        EmailAttachment var2 = new EmailAttachment();
        var1.attach(var2);
        HtmlEmail var3 = var1.setTextMsg("W;?O)Q^w>>K{l}|K");
        Assert.assertFalse(var3.isSSLCheckServerIdentity());
    }

    @Test
    public void test07() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.socketTimeout = 0;
        HtmlEmail var2 = var1.setTextMsg("Wz#sX9!`aa%3H v");
        Assert.assertEquals(10L, 10L);
    }

    @Test
    public void test08() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSocketTimeout(-1317);
        HtmlEmail var2 = var1.setTextMsg(" doesn't exist");
        Assert.assertFalse(var2.isBoolHasAttachments());
    }

    @Test
    public void test09() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSocketConnectionTimeout(0);
        HtmlEmail var2 = var1.setTextMsg(":");
        Assert.assertNull(var2.getSubject());
    }

    @Test
    public void test10() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSocketConnectionTimeout(-589);
        HtmlEmail var2 = var1.setTextMsg("M");
        Assert.assertFalse(var2.isSSLCheckServerIdentity());
    }

    @Test
    public void test11() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setStartTLSRequired(true);
        HtmlEmail var2 = (HtmlEmail)var1.setMsg("<FF@jd4x");
        Assert.assertNull(var2.getSubType());
    }

    @Test
    public void test12() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        Email var2 = var1.setStartTLSEnabled(true);
        Email var3 = var1.setMsg("\" does not exist");
        Assert.assertSame(var3, var2);
    }

    @Test
    public void test13() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSendPartial(true);
        Email var2 = var1.setMsg("f lK(`Dwt]7");
        Assert.assertSame(var1, var2);
    }

    @Test
    public void test14() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSSLCheckServerIdentity(true);
        Email var2 = var1.setMsg("\" does not exist");
        Assert.assertFalse(var2.isSSL());
    }

    @Test
    public void test15() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        Email var2 = var1.setSSLOnConnect(true);
        Email var3 = var1.setMsg("value can not be null or empty");
        Assert.assertSame(var3, var2);
    }

    @Test
    public void test16() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.socketTimeout = 0;
        HtmlEmail var2 = (HtmlEmail)var1.setMsg("\" does not exist");
        Assert.assertEquals(10L, 10L);
    }

    @Test
    public void test17() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSocketTimeout(-1160);
        Email var2 = var1.setMsg("o dzes no)t exist");
        Assert.assertFalse(var2.isSSLOnConnect());
    }

    @Test
    public void test18() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSocketConnectionTimeout(0);
        Email var2 = var1.setMsg("%22des%20not%20Yxist");
        Assert.assertFalse(var2.isSSLOnConnect());
    }

    @Test
    public void test19() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.socketConnectionTimeout = -2;
        Email var2 = var1.setMsg("\" does not exist");
        Assert.assertFalse(var2.isSSL());
    }

    @Test
    public void test20() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setStartTLSEnabled(true);
        HtmlEmail var2 = var1.setHtmlMsg("25");
        Assert.assertNull(var2.getSubType());
    }

    @Test
    public void test21() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSendPartial(true);
        HtmlEmail var2 = var1.setHtmlMsg("text/html");
        Assert.assertNull(var2.getSubject());
    }

    @Test
    public void test22() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSSLCheckServerIdentity(true);
        HtmlEmail var2 = var1.setHtmlMsg("!-L6x");
        Assert.assertEquals(60000L, (long)var2.getSocketConnectionTimeout());
    }

    @Test
    public void test23() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSSLOnConnect(true);
        HtmlEmail var2 = var1.setHtmlMsg(" JV9\"|+:C[5Cg");
        Assert.assertEquals(60000L, (long)var2.getSocketTimeout());
    }

    @Test(

            expected = EmailException.class
    )
    public void test24() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        EmailAttachment var2 = new EmailAttachment();
        MultiPartEmail var3 = var1.attach(var2);
        HtmlEmail var4 = var1.setHtmlMsg("inline");
        Assert.assertSame(var4, var3);
    }

    @Test
    public void test25() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.socketTimeout = 0;
        HtmlEmail var2 = var1.setHtmlMsg("Wz#sX9!`aa%3H v");
        Assert.assertSame(var1, var2);
    }

    @Test
    public void test26() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSocketTimeout(-1317);
        HtmlEmail var2 = var1.setHtmlMsg("Ir<'<#.1%N>^\u0001");
        Assert.assertFalse(var2.isSSLOnConnect());
    }

    @Test
    public void test27() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setSocketConnectionTimeout(-485);
        HtmlEmail var2 = var1.setHtmlMsg("\" does not exist");
        Assert.assertNull(var2.getBounceAddress());
    }

    @Test
    public void test28() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        URL var2 = MockURL.getFtpExample();
        URLDataSource var3 = new URLDataSource(var2);
        var1.embed(var3, "WhUsQy>W5", (String)null);
        String var4 = var1.embed(var2, "WhUsQy>W5");
        Assert.assertNull(var4);
    }

    @Test
    public void test29() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        URL var2 = MockURL.getHttpExample();
        URLDataSource var3 = new URLDataSource(var2);
        String var4 = var1.embed(var3, "WhJU,iQyV.W5", "");
        Assert.assertEquals("", var4);
        Assert.assertNotNull(var4);
        String var5 = var1.embed(var2, "WhJU,iQyV.W5");
        Assert.assertNotNull(var5);
        Assert.assertEquals("", var5);
    }

    @Test
    public void test30() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        File var2 = MockFile.createTempFile("org.apache.commons.mail.MultiPartEmail", "fEun");
        String var3 = var1.embed(var2, (String)null);
        Assert.assertNull(var3);
    }
/*
    @Test(

            expected = IOException.class
    )
    public void test31() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        File var2 = MockFile.createTempFile("oG.-98G<3G\u007f8e^$QK", "", (File)null);
        String var3 = var1.embed(var2, "");
        Assert.assertEquals("", var3);
        Assert.assertNotNull(var3);
    }

*/
    //Il risultato di questo caso di test non è deterministico in quanto è un valore casuale
 /*  @Test
    public void test32() throws Throwable {
        Random.setNextRandom(45);
        HtmlEmail var1 = new HtmlEmail();
        File var2 = MockFile.createTempFile("org.apache.commons.mail.EmailAttachment", "org.apache.commons.mail.EmailAttachment");
        String var3 = var1.embed(var2);
        Assert.assertEquals("mmmmmmmmmm", var3);
        Assert.assertNotNull(var3);
    }*/

    @Test
    public void test33() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.embed((DataSource)null, "text/html", "text/html");

        try {
            var1.embed((DataSource)null, "text/html");
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var3);
        }

    }

    @Test
    public void test34() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.embed((DataSource)null, "\" does not exist", "US-ASCII");
        URL var2 = MockURL.getHttpExample();

        try {
            var1.embed(var2, "\" does not exist");
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }

    }

    @Test
    public void test35() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();

        try {
            var1.embed((URL)null, "attachment");
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
        }

    }

    @Test
    public void test36() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        FileDataSource var2 = new FileDataSource("http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html");
        var1.embed(var2, "http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html", "http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html");
        URL var3 = MockURL.getFtpExample();

        try {
            var1.embed(var3, "http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html");
            Assert.fail("Expecting exception: ClassCastException");
        } catch (ClassCastException var5) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var5);
        }

    }

    @Test
    public void test37() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();

        try {
            var1.embed("org.apache.commons.mail.EmailException", "org.apache.commons.mail.EmailException");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var3);
        }

    }

    @Test
    public void test38() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.inlineEmbeds = null;

        try {
            var1.embed("http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html", "http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html");
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var3);
        }

    }

    @Test
    public void test39() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();

        try {
            var1.embed((File)null, "mail.smtp.starttls.required");
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var3);
        }

    }

    @Test
    public void test40() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        URL var2 = MockURL.getHttpExample();
        URLDataSource var3 = new URLDataSource(var2);
        var1.embed(var3, "<html><body><pre>", "<html><body><pre>");
        MockFile var4 = new MockFile("<html><body><pre>", "<html><body><pre>");

        try {
            var1.embed(var4, "text/:t");
            Assert.fail("Expecting exception: ClassCastException");
        } catch (ClassCastException var6) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var6);
        }

    }

    @Test
    public void test41() throws Throwable {
        Random.setNextRandom(-66);
        HtmlEmail var1 = new HtmlEmail();
        MockFile var2 = new MockFile("Lu@-a.P2", "");

        try {
            var1.embed(var2);
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }

    }

    @Test
    public void test42() throws Throwable {
        Random.setNextRandom(-3055);
        HtmlEmail var1 = new HtmlEmail();

        try {
            var1.embed((File)null);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var3);
        }

    }

    @Test
    public void test43() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setInitialized(true);

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var3);
        }

    }

    @Test
    public void test44() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        Properties var2 = new Properties();
        Session var3 = Session.getDefaultInstance(var2);
        MimeMessage var4 = var1.createMimeMessage(var3);
        var1.message = var4;

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException var6) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var6);
        }

    }

    @Test
    public void test45() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.embed((DataSource)null, "' is already bound to file ");
    }

    //Test case generato senza assert
   /* @Test
    public void test46() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        EvoSuiteURL var2 = new EvoSuiteURL("http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html");
        NetworkHandling.createRemoteTextFile(var2, "embedded DataSource '");
        URL var3 = MockURL.getFtpExample();
        URL var4 = MockURL.URL(var3, "http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html");
        var1.embed(var4, "embedded DataSource '");
    }*/

    @Test(expected = Exception.class)
    public void test47() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        //URL var2 = MockURL.getHttpExample();
        URL var2 = new URL("http://www.google.com");

        //try {
            var1.embed(var2, "");
            Assert.fail("Expecting exception: Exception");
        /*} catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }*/

    }

    @Test
    public void test48() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        Email var2 = var1.setMsg("T5");
        var2.charset = "T5";

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var4);
        }

    }

    @Test(

            expected = EmailException.class
    )
    public void test49() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        EmailAttachment var2 = new EmailAttachment();
        var1.attach(var2);
        var1.setMsg("alternative");

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var4);
        }

    }

    @Test
    public void test50() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.setTextMsg("' is already bound to file ");

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var3);
        }

    }
/*
    @Test(

            expected = IOException.class
    )
    public void test51() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        File var2 = MockFile.createTempFile("\"des not Yxist", "\"des not Yxist");
        var1.embed(var2, "\"des not Yxist");
        HtmlEmail var3 = var1.setHtmlMsg("%22des%20not%20Yxist");

        try {
            var3.buildMimeMessage();
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var5) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var5);
        }

    }
*/
    @Test
    public void test52() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        URL var2 = MockURL.getFileExample();
        URLDataSource var3 = new URLDataSource(var2);

        try {
            var1.embed(var3, "", "");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var5) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var5);
        }

    }

    @Test
    public void test53() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        URL var2 = MockURL.getFileExample();
        URLDataSource var3 = new URLDataSource(var2);
        var1.embed(var3, "text/html", "|N");
        MockFile var4 = new MockFile((File)null, "tetplain");
        FileDataSource var5 = new FileDataSource(var4);

        try {
            var1.embed(var5, "text/html");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var7) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var7);
        }

    }

    @Test
    public void test54() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        URL var2 = MockURL.getHttpExample();
        URLDataSource var3 = new URLDataSource(var2);
        String var4 = var1.embed(var3, "7jhXr-v*H@gq", "7jhXr-v*H@gq");
        Assert.assertEquals("7jhXr-v*H@gq", var4);
        Assert.assertNotNull(var4);
        String var5 = var1.embed(var3, "7jhXr-v*H@gq");
        Assert.assertEquals("7jhXr-v*H@gq", var5);
        Assert.assertNotNull(var5);
    }
/*
    @Test(

            expected = IOException.class
    )
    public void test55() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        File var2 = MockFile.createTempFile("\" es not exist", "\" es not exist");
        var2.setReadable(false);

        try {
            var1.embed(var2, "\" es not exist");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }

    }
*/
    @Test(

            expected = IOException.class
    )
    public void test56() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        MockFile var2 = new MockFile("Z$+y", "GpI0(lW/DUlF");
        MockFile.createTempFile("Z$+y", "GpI0(lW/DUlF", var2);

        try {
            var1.embed(var2, "GpI0(lW/DUlF");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }

    }

    @Test
    public void test57() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        MockFile var2 = new MockFile("EELls3Y;\"@Or73");

        try {
            var1.embed(var2, "EELls3Y;\"@Or73");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }

    }

    @Test(

            expected = IOException.class
    )
    public void test58() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        MockFile var2 = new MockFile("-5/f");
        File var3 = MockFile.createTempFile("-5/f", "-5/f");
        var1.embed(var3, "-5/f");

        try {
            var1.embed(var2, "-5/f");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var5) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var5);
        }

    }

    @Test
    public void test59() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        MockFile var2 = new MockFile("");

        try {
            var1.embed(var2, "");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }

    }

    //Test case generato senza assert (aggiunta a mano)
    @Test(expected = Exception.class)
    public void test60() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        EvoSuiteURL var2 = new EvoSuiteURL("http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html");
        String var3 = "C:\\Users\\sdell\\commons-emil\\mail.smtp.socketFactory.class\\file:\\some\\fake\\but\\wellformed\\url\\mail.smp.socketFactory.class0file:\\some\\fake\\but\\wellformed\\url";
        NetworkHandling.createRemoteTextFile(var2, var3);
        var1.embed("http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html", "http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html");
        Assert.fail("Expecting exception: Exception");
    }

    @Test
    public void test61() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        URL var2 = MockURL.getFileExample();
        URLDataSource var3 = new URLDataSource(var2);
        String var4 = var1.embed(var3, ").5?R@kiqKs#", ").5?R@kiqKs#");
        Assert.assertNotNull(var4);
        Assert.assertEquals(").5%3FR@kiqKs%23", var4);
        String var5 = var1.embed(var2, ").5?R@kiqKs#");
        Assert.assertNotNull(var5);
        Assert.assertEquals(").5%3FR@kiqKs%23", var5);
    }

    @Test
    public void test62() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        URL var2 = MockURL.getHttpExample();
        URL var3 = MockURL.getFileExample();
        URLDataSource var4 = new URLDataSource(var3);
        var1.embed(var4, "WhJU,iQyV.W5", "WhJU,iQyV.W5");

        try {
            var1.embed(var2, "WhJU,iQyV.W5");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var6) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var6);
        }

    }

    @Test
    public void test63() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        URL var2 = MockURL.getFileExample();

        try {
            var1.embed(var2, "");
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }

    }

    @Test
    public void test64() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();

        try {
            var1.setMsg((String)null);
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var3);
        }

    }

    @Test
    public void test65() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();

        try {
            var1.setHtmlMsg((String)null);
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var3);
        }

    }

    @Test
    public void test66() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();

        try {
            var1.setTextMsg((String)null);
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var3);
        }

    }
/*
    @Test(

            expected = IOException.class
    )
    public void test67() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        File var2 = MockFile.createTempFile("\" does not exist", "\" does not exist");
        var1.embed(var2, "\" does not exist");
        var1.setMsg("%22%20does%20not%20exist");

        try {
            var1.buildMimeMessage();
            Assert.fail("Expecting exception: Exception");
        } catch (Exception var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.Email", var4);
        }

    }
*/
    @Test
    public void test68() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        FileDataSource var2 = new FileDataSource("Tig6*?");
        var1.embed(var2, "Tig6*?", "Tig6*?");

        try {
            var1.embed("http:/ewww.UmFakeButWellFredURLgorgtextb3pwt/html", "Tig6*?");
            Assert.fail("Expecting exception: ClassCastException");
        } catch (ClassCastException var4) {
            EvoAssertions.verifyException("org.apache.commons.mail.HtmlEmail", var4);
        }

    }
/*
    @Test(

            expected = IOException.class
    )
    public void test69() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        File var2 = MockFile.createTempFile("\" does not exist", "\" does not exist");
        String var3 = var1.embed(var2, "\" does not exist");
        Assert.assertNotNull(var3);
        Assert.assertEquals("%22%20does%20not%20exist", var3);
        String var4 = var1.embed(var2, "%22%20does%20not%20exist");
        Assert.assertEquals("%22%20does%20not%20exist", var4);
        Assert.assertNotNull(var4);
    }
*/
    @Test(
            expected = EmailException.class
    )
    public void test70() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();

        try {
            var1.embed("file://some/fake/but/wellformed/url", "file://some/fake/but/wellformed/url");
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
        }

    }



    //L'assert è stata aggiunta a mano perché il caso di test è stato generato senza
    @Test(

            expected = NullPointerException.class

    )
    public void test71() throws Throwable {
        HtmlEmail var1 = new HtmlEmail();
        var1.embed((File)null);
        Assert.fail("Expecting exception: NullPointerException");
    }



    private void assertCorrectContentType(final String picture, final String contentType) throws Exception {
        final HtmlEmail htmlEmail = createDefaultHtmlEmail();
        final String cid = htmlEmail.embed(new File("./src/test/resources/images/" + picture), "Apache Logo");
        final String htmlMsg = "<html><img src=\"cid:" + cid + "\"><html>";
        htmlEmail.setHtmlMsg(htmlMsg);
        htmlEmail.buildMimeMessage();

        final MimeMessage mm = htmlEmail.getMimeMessage();
        mm.saveChanges();
        final MimeMessageParser mmp = new MimeMessageParser(mm);
        mmp.parse();

        final List<?> attachments = mmp.getAttachmentList();
        assertEquals("Attachment size", 1, attachments.size());

        final DataSource ds = (DataSource) attachments.get(0);
        assertEquals("Content type", contentType, ds.getContentType());
    }

    private HtmlEmail createDefaultHtmlEmail() throws EmailException {
        final HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(this.strTestMailServer);
        htmlEmail.setSmtpPort(this.getMailServerPort());
        htmlEmail.setFrom("a@b.com");
        htmlEmail.addTo("c@d.com");
        return htmlEmail;
    }
}
