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
package org.apache.commons.mail.resolver;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.apache.commons.mail.DataSourceResolver;
import org.junit.Test;
import javax.activation.DataSource;
import org.evosuite.runtime.mock.java.io.MockFile;
import static org.evosuite.runtime.EvoAssertions.*;

/**
 * JUnit test case for DateSourceResolver.
 *
 * @since 1.3
 */
public class DataSourceFileResolverTest extends AbstractDataSourceResolverTest
{
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver((File) null);
        File file0 = dataSourceFileResolver0.getBaseDir();
        assertNull(file0);
    }

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver();
        File file0 = dataSourceFileResolver0.getBaseDir();
        assertTrue(file0.exists());
    }

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver();
        // Undeclared exception!
        try {
            dataSourceFileResolver0.resolve((String) null, false);
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.mail.resolver.DataSourceBaseResolver", e);
        }
    }

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver();
        // Undeclared exception!
        try {
            dataSourceFileResolver0.resolve((String) null);
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.mail.resolver.DataSourceBaseResolver", e);
        }
    }

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver((File) null);
        try {
            dataSourceFileResolver0.resolve("", false);
            fail("Expecting exception: IOException");

        } catch (IOException e) {
            //
            // Cant resolve the following file resource
            // :/Users/luigiallocca/development/commons-email/.
            //
            verifyException("org.apache.commons.mail.resolver.DataSourceFileResolver", e);
        }
    }

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver();
        DataSource dataSource0 = dataSourceFileResolver0.resolve("/J@3Bl-+A)qj2", true);
        assertNull(dataSource0);
    }

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver((File) null, true);
        DataSource dataSource0 = dataSourceFileResolver0.resolve("cid:", true);
        assertNull(dataSource0);
    }

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        MockFile mockFile0 = new MockFile("", "");
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver(mockFile0);
        File file0 = dataSourceFileResolver0.getBaseDir();
        assertFalse(file0.isFile());
    }

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        MockFile mockFile0 = new MockFile("");
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver(mockFile0);
        DataSource dataSource0 = dataSourceFileResolver0.resolve("");
        assertNotNull(dataSource0);
    }

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver();
        try {
            dataSourceFileResolver0.resolve("oEN/kK7mNV/,v1e&/v");
            fail("Expecting exception: IOException");

        } catch (IOException e) {
            //
            // Cant resolve the following file resource
            // :/Users/luigiallocca/development/commons-email/./oEN/kK7mNV/,v1e&/v
            //
            verifyException("org.apache.commons.mail.resolver.DataSourceFileResolver", e);
        }
    }

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        DataSourceFileResolver dataSourceFileResolver0 = new DataSourceFileResolver((File) null, true);
        DataSource dataSource0 = dataSourceFileResolver0.resolve("cid:");
        assertNull(dataSource0);
    }

    @Test
    public void testResolvingFileLenient() throws Exception
    {
        final DataSourceResolver dataSourceResolver = new DataSourceFileResolver(new File("./src/test/resources"), true);
        assertTrue(toByteArray(dataSourceResolver.resolve("images/asf_logo_wide.gif")).length == IMG_SIZE);
        assertTrue(toByteArray(dataSourceResolver.resolve("./images/asf_logo_wide.gif")).length == IMG_SIZE);
        assertTrue(toByteArray(dataSourceResolver.resolve("../resources/images/asf_logo_wide.gif")).length == IMG_SIZE);
        assertNull(toByteArray(dataSourceResolver.resolve("/images/does-not-exist.gif")));
        assertNull(dataSourceResolver.resolve("./images/does-not-exist.gif"));
    }

    @Test
    public void testResolvingFileNonLenient() throws Exception
    {
        final DataSourceResolver dataSourceResolver = new DataSourceFileResolver(new File("."), false);
        assertNotNull(dataSourceResolver.resolve("./src/test/resources/images/asf_logo_wide.gif"));

        assertThrows(IOException.class, () -> dataSourceResolver.resolve("asf_logo_wide.gif"));
    }

}
