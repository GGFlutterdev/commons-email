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
 * Tue May 09 22:09:31 GMT 2023
 */

package org.apache.commons.mail.resolver;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import javax.activation.DataSource;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.mock.java.net.MockURL;
import org.evosuite.runtime.testdata.EvoSuiteURL;
import org.evosuite.runtime.testdata.NetworkHandling;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class DataSourceUrlResolver_ESTest extends DataSourceUrlResolver_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      URL uRL0 = MockURL.getHttpExample();
      EvoSuiteURL evoSuiteURL0 = new EvoSuiteURL("http://www.someFakeButWellFormedURL.org/C");
      NetworkHandling.createRemoteTextFile(evoSuiteURL0, ",Y'__|eQGxw&#$O");
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0, false);
      DataSource dataSource0 = dataSourceUrlResolver0.resolve("C", true);
      assertNotNull(dataSource0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      URL uRL0 = MockURL.getHttpExample();
      EvoSuiteURL evoSuiteURL0 = new EvoSuiteURL("http://www.someFakeButWellFormedURL.org/C");
      NetworkHandling.createRemoteTextFile(evoSuiteURL0, ",Y'__|eQGxw&#$O");
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0, false);
      DataSource dataSource0 = dataSourceUrlResolver0.resolve("C");
      assertNotNull(dataSource0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver((URL) null);
      URL uRL0 = dataSourceUrlResolver0.getBaseUrl();
      assertNull(uRL0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      URLStreamHandler uRLStreamHandler0 = mock(URLStreamHandler.class, new ViolatedAssumptionAnswer());
      URL uRL0 = MockURL.URL("`d]", "", 0, "`d]", uRLStreamHandler0);
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0, true);
      URL uRL1 = dataSourceUrlResolver0.getBaseUrl();
      assertEquals("`d]", uRL1.getPath());
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      URLStreamHandler uRLStreamHandler0 = mock(URLStreamHandler.class, new ViolatedAssumptionAnswer());
      URL uRL0 = MockURL.URL("pf5ps4A>-AD0)Tz=", "dH;E&", 3656, "pf5ps4A>-AD0)Tz=", uRLStreamHandler0);
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0);
      URL uRL1 = dataSourceUrlResolver0.getBaseUrl();
      assertEquals("pf5ps4a>-ad0)tz=", uRL1.getProtocol());
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      URL uRL0 = MockURL.getFtpExample();
      URLStreamHandler uRLStreamHandler0 = mock(URLStreamHandler.class, new ViolatedAssumptionAnswer());
      URL uRL1 = MockURL.URL(uRL0, "", uRLStreamHandler0);
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL1);
      URL uRL2 = dataSourceUrlResolver0.getBaseUrl();
      assertSame(uRL1, uRL2);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      URL uRL0 = MockURL.getHttpExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0, false);
      URL uRL1 = dataSourceUrlResolver0.createUrl("_6b|J2kOOn@`");
      assertEquals("http://www.someFakeButWellFormedURL.org/_6b|J2kOOn@`", uRL1.toExternalForm());
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver((URL) null);
      try { 
        dataSourceUrlResolver0.resolve("?x8Uv", false);
        fail("Expecting exception: MalformedURLException");
      
      } catch(MalformedURLException e) {
         //
         // no protocol: ?x8Uv
         //
         verifyException("java.net.URL", e);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      URL uRL0 = MockURL.getHttpExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0);
      // Undeclared exception!
      try { 
        dataSourceUrlResolver0.resolve("file:/", true);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.net.URL", e);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      URL uRL0 = MockURL.getHttpExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0);
      // Undeclared exception!
      try { 
        dataSourceUrlResolver0.resolve("", false);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // No resource defined
         //
         verifyException("org.apache.commons.mail.resolver.DataSourceUrlResolver", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      URL uRL0 = MockURL.getFtpExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0);
      try { 
        dataSourceUrlResolver0.resolve("ie:/");
        fail("Expecting exception: MalformedURLException");
      
      } catch(MalformedURLException e) {
         //
         // unknown protocol: ie
         //
         verifyException("java.net.URL", e);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver((URL) null);
      try { 
        dataSourceUrlResolver0.createUrl("org.apache.commons.mail.resolver.DataSourceUrlResolver");
        fail("Expecting exception: MalformedURLException");
      
      } catch(MalformedURLException e) {
         //
         // no protocol: org.apache.commons.mail.resolver.DataSourceUrlResolver
         //
         verifyException("java.net.URL", e);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      URL uRL0 = MockURL.getHttpExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0, false);
      try { 
        dataSourceUrlResolver0.resolve("*#ul}O~C7+Qmm^#4;v", false);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Could not find: www.someFakeButWellFormedURL.org
         //
         verifyException("org.evosuite.runtime.mock.java.net.EvoHttpURLConnection", e);
      }
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      URL uRL0 = MockURL.getFtpExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0);
      DataSource dataSource0 = dataSourceUrlResolver0.resolve("cid:", true);
      assertNull(dataSource0);
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      URL uRL0 = MockURL.getFileExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0);
      try { 
        dataSourceUrlResolver0.resolve("https://");
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Could not find: 
         //
         verifyException("org.evosuite.runtime.mock.java.net.EvoHttpURLConnection", e);
      }
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      URL uRL0 = MockURL.getFtpExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0);
      // Undeclared exception!
      try { 
        dataSourceUrlResolver0.resolve("file:/");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.net.URL", e);
      }
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      URL uRL0 = MockURL.getFileExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0, true);
      // Undeclared exception!
      try { 
        dataSourceUrlResolver0.resolve("");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // No resource defined
         //
         verifyException("org.apache.commons.mail.resolver.DataSourceUrlResolver", e);
      }
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver((URL) null);
      DataSource dataSource0 = dataSourceUrlResolver0.resolve("KJ@1\"T{", true);
      assertNull(dataSource0);
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      URL uRL0 = MockURL.getFileExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0);
      DataSource dataSource0 = dataSourceUrlResolver0.resolve("cid:");
      assertNull(dataSource0);
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      URL uRL0 = MockURL.getFtpExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0);
      // Undeclared exception!
      try { 
        dataSourceUrlResolver0.createUrl((String) null);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // No resource defined
         //
         verifyException("org.apache.commons.mail.resolver.DataSourceUrlResolver", e);
      }
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      URL uRL0 = MockURL.getHttpExample();
      DataSourceUrlResolver dataSourceUrlResolver0 = new DataSourceUrlResolver(uRL0, false);
      URL uRL1 = dataSourceUrlResolver0.getBaseUrl();
      assertEquals("http://www.someFakeButWellFormedURL.org/fooExample", uRL1.toString());
  }
}
