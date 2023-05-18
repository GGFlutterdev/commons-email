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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.evosuite.runtime.Random;
import org.evosuite.runtime.mock.java.io.MockFile;

import static org.evosuite.runtime.EvoAssertions.*;

/**
 * JUnit test case for EmailUtils Class
 *
 * @since 1.3
 */
public class EmailUtilsTest {

    @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      String string0 = EmailUtils.replaceEndOfLineCharactersWithSpaces("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      String string0 = EmailUtils.encodeUrl("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      File file0 = MockFile.createTempFile("cRSAnYc5ZWStdWQ", "4cRSAnYc5ZWStdWQ");
      // Undeclared exception!
      try { 
        EmailUtils.writeMimeMessage(file0, (MimeMessage) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.apache.commons.mail.util.MimeMessageUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      MockFile mockFile0 = new MockFile("../D", "");
      Properties properties0 = new Properties();
      Session session0 = Session.getInstance(properties0);
      MimeMessage mimeMessage0 = new MimeMessage(session0);
      try { 
        EmailUtils.writeMimeMessage(mockFile0, mimeMessage0);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // No content
         //
         verifyException("javax.mail.internet.MimePartDataSource", e);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      File file0 = MockFile.createTempFile("a_'wR7", "");
      MockFile mockFile0 = new MockFile(file0, "{1G");
      try { 
        EmailUtils.writeMimeMessage(mockFile0, (MimeMessage) null);
        fail("Expecting exception: FileNotFoundException");
      
      } catch(FileNotFoundException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.evosuite.runtime.mock.java.io.MockFileOutputStream", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      String string0 = EmailUtils.encodeUrl((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      String string0 = EmailUtils.encodeUrl("i)D#?KMQ.9XWv0p]Wz");
      assertEquals("i)D%23%3FKMQ.9XWv0p%5DWz", string0);
      assertNotNull(string0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      String string0 = EmailUtils.replaceEndOfLineCharactersWithSpaces((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      String string0 = EmailUtils.replaceEndOfLineCharactersWithSpaces("9\"OMD;bh2aNQHpD*");
      assertEquals("9\"OMD;bh2aNQHpD*", string0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      Random.setNextRandom(1800);
      String string0 = EmailUtils.randomAlphabetic(1800);
      assertNotNull(string0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      // Undeclared exception!
      try { 
        EmailUtils.randomAlphabetic((-2221));
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Requested random string length -2221 is less than 0.
         //
         verifyException("org.apache.commons.mail.EmailUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      String string0 = EmailUtils.randomAlphabetic(0);
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      // Undeclared exception!
      try { 
        EmailUtils.notNull((Object) null, "org.apache.commons.mail.EmailUtils");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // org.apache.commons.mail.EmailUtils
         //
         verifyException("org.apache.commons.mail.EmailUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void testNotNullOnNonNullParameter()  throws Throwable  {
      Object object0 = new Object();
      assertDoesNotThrow(() -> EmailUtils.notNull(object0, "x)GO]}5ME yrVrQ5S"));
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      boolean boolean0 = EmailUtils.isNotEmpty("");
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      boolean boolean0 = EmailUtils.isNotEmpty("org.apache.commons.mail.util.MimeMessageUtils");
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      boolean boolean0 = EmailUtils.isNotEmpty((String) null);
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      boolean boolean0 = EmailUtils.isEmpty("");
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      boolean boolean0 = EmailUtils.isEmpty("9\"OMD;bh2aNQHpD*");
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      boolean boolean0 = EmailUtils.isEmpty((String) null);
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void testRandomAlphabeticGeneration()  throws Throwable  {
      // Undeclared exception!
      final String randomAlphabetic = EmailUtils.randomAlphabetic(1800);
      assertTrue("Error in generating random alphabetic", randomAlphabetic.getClass().equals(String.class) && randomAlphabetic.length() == 1800);
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      // Undeclared exception!
      try { 
        EmailUtils.writeMimeMessage((File) null, (MimeMessage) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.apache.commons.mail.util.MimeMessageUtils", e);
      }
  }

    @Test
    public void testClearEndOfLineCharacters() {
        assertEquals(null, EmailUtils.replaceEndOfLineCharactersWithSpaces(null));
        assertEquals("", EmailUtils.replaceEndOfLineCharactersWithSpaces(""));
        assertEquals("   ", EmailUtils.replaceEndOfLineCharactersWithSpaces("   "));
        assertEquals("abcdefg", EmailUtils.replaceEndOfLineCharactersWithSpaces("abcdefg"));
        assertEquals("abc defg", EmailUtils.replaceEndOfLineCharactersWithSpaces("abc\rdefg"));
        assertEquals("abc defg", EmailUtils.replaceEndOfLineCharactersWithSpaces("abc\ndefg"));
        assertEquals("abc  defg", EmailUtils.replaceEndOfLineCharactersWithSpaces("abc\r\ndefg"));
        assertEquals("abc  defg", EmailUtils.replaceEndOfLineCharactersWithSpaces("abc\n\rdefg"));
    }

    @Test
    public void testUrlEncoding() throws UnsupportedEncodingException {
        assertEquals("abcdefg", EmailUtils.encodeUrl("abcdefg"));
        assertEquals("0123456789", EmailUtils.encodeUrl("0123456789"));
        assertEquals("Test%20CID", EmailUtils.encodeUrl("Test CID"));
        assertEquals("joe.doe@apache.org", EmailUtils.encodeUrl("joe.doe@apache.org"));
        assertEquals("joe+doe@apache.org", EmailUtils.encodeUrl("joe+doe@apache.org"));
        assertEquals("peter%26paul%26mary@oldmusic.org", EmailUtils.encodeUrl("peter&paul&mary@oldmusic.org"));
    }
}
