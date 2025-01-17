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

/*
 * This file was automatically generated by EvoSuite
 * Tue May 09 21:11:26 GMT 2023
 */

package org.apache.commons.mail.util;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.util.MimeMessageUtils;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.testdata.FileSystemHandling;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class MimeMessageUtils_ESTest extends MimeMessageUtils_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      MimeMessage mimeMessage0 = MimeMessageUtils.createMimeMessage((Session) null, "e");
      FileSystemHandling.shouldAllThrowIOExceptions();
      MockFile mockFile0 = new MockFile("e");
      MockFile mockFile1 = new MockFile(mockFile0, "8{ypPJ=KS");
      try { 
        MimeMessageUtils.writeMimeMessage(mimeMessage0, mockFile1);
        fail("Expecting exception: MessagingException");
      
      } catch(MessagingException e) {
      }
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      // Undeclared exception!
      try { 
        MimeMessageUtils.writeMimeMessage((MimeMessage) null, (File) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.apache.commons.mail.util.MimeMessageUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      File file0 = MockFile.createTempFile("j+{DJU.21c2N=ZW'-", "j+{DJU.21c2N=ZW'-");
      // Undeclared exception!
      try { 
        MimeMessageUtils.writeMimeMessage((MimeMessage) null, file0);
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
      MockFile mockFile0 = new MockFile("E4\"PpYbVN<", "E4\"PpYbVN<");
      mockFile0.mkdirs();
      try { 
        MimeMessageUtils.writeMimeMessage((MimeMessage) null, mockFile0);
        fail("Expecting exception: FileNotFoundException");
      
      } catch(FileNotFoundException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.evosuite.runtime.mock.java.io.MockFileOutputStream", e);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      // Undeclared exception!
      try { 
        MimeMessageUtils.createMimeMessage((Session) null, (byte[]) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.io.ByteArrayInputStream", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      try { 
        MimeMessageUtils.createMimeMessage((Session) null, (InputStream) null);
        fail("Expecting exception: MessagingException");
      
      } catch(MessagingException e) {
         //
         // Error in input stream
         //
         verifyException("javax.mail.internet.InternetHeaders", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      DataInputStream dataInputStream0 = new DataInputStream((InputStream) null);
      // Undeclared exception!
      try { 
        MimeMessageUtils.createMimeMessage((Session) null, (InputStream) dataInputStream0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.io.DataInputStream", e);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      byte[] byteArray0 = new byte[1];
      ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(byteArray0, 0, (-5446));
      // Undeclared exception!
      try { 
        MimeMessageUtils.createMimeMessage((Session) null, (InputStream) byteArrayInputStream0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      byte[] byteArray0 = new byte[1];
      ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(byteArray0, (-801), 1);
      // Undeclared exception!
      try { 
        MimeMessageUtils.createMimeMessage((Session) null, (InputStream) byteArrayInputStream0);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      // Undeclared exception!
      try { 
        MimeMessageUtils.createMimeMessage((Session) null, (File) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.evosuite.runtime.mock.java.io.MockFileInputStream", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      MockFile mockFile0 = new MockFile("j+{DJU.21c2N=ZW'-", "j+{DJU.21c2N=ZW'-");
      try { 
        MimeMessageUtils.createMimeMessage((Session) null, (File) mockFile0);
        fail("Expecting exception: FileNotFoundException");
      
      } catch(FileNotFoundException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.evosuite.runtime.mock.java.io.MockFileInputStream", e);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      Session session0 = Session.getDefaultInstance((Properties) null);
      byte[] byteArray0 = new byte[6];
      ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(byteArray0);
      MimeMessage mimeMessage0 = MimeMessageUtils.createMimeMessage(session0, (InputStream) byteArrayInputStream0);
      MockFile mockFile0 = new MockFile("'r:n`N)Wmsi-aFvPO_", "{r;EudG>hlm:/evJ");
      try { 
        MimeMessageUtils.writeMimeMessage(mimeMessage0, mockFile0);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Failed to create the following parent directories: C:\\Users\\rocco\\commons-email\\'r:n`N)Wmsi-aFvPO_\\{r;EudG>hlm:
         //
         verifyException("org.apache.commons.mail.util.MimeMessageUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      MockFile mockFile0 = new MockFile("org.apache.commons.mail.DefaultAuthenticator", "org.apache.commons.mail.DefaultAuthenticator");
      Properties properties0 = new Properties();
      Session session0 = Session.getInstance(properties0);
      MimeMessage mimeMessage0 = MimeMessageUtils.createMimeMessage(session0, "org.apache.commons.mail.DefaultAuthenticator");
      MimeMessage mimeMessage1 = new MimeMessage(mimeMessage0);
      MimeMessageUtils.writeMimeMessage(mimeMessage1, mockFile0);
      assertEquals(1392409281320L, mockFile0.lastModified());
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      // Undeclared exception!
      try { 
        MimeMessageUtils.createMimeMessage((Session) null, (String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.apache.commons.mail.util.MimeMessageUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      Properties properties0 = new Properties();
      Session session0 = Session.getDefaultInstance(properties0);
      File file0 = MockFile.createTempFile("9<f3:Qu$C^! ", "org.apache.commons.mail.DefaultAuthenticator");
      MimeMessage mimeMessage0 = MimeMessageUtils.createMimeMessage(session0, file0);
      assertNotNull(mimeMessage0);
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      byte[] byteArray0 = new byte[4];
      MimeMessage mimeMessage0 = MimeMessageUtils.createMimeMessage((Session) null, byteArray0);
      MockFile mockFile0 = new MockFile("4U:@iBFSz", "q:W~Zb4&7s#.Z(VKP^");
      try { 
        MimeMessageUtils.writeMimeMessage(mimeMessage0, mockFile0);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Error in writing to file
         //
         verifyException("org.evosuite.runtime.mock.java.io.NativeMockedIO", e);
      }
  }
}
