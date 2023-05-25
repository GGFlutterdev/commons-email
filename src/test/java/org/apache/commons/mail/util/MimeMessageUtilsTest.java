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
package org.apache.commons.mail.util;

import org.evosuite.runtime.EvoAssertions;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.Assert;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.*;
import java.util.Properties;

public class MimeMessageUtilsTest {

    @Test
    public void test01() throws Throwable {
        try {
            MimeMessageUtils.writeMimeMessage((MimeMessage)null, (File)null);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var2) {
            EvoAssertions.verifyException("org.apache.commons.mail.util.MimeMessageUtils", var2);
        }

    }

    @Test
    public void test02() throws Throwable {
        File var1 = MockFile.createTempFile("./j+{DJU.21c2N=ZW'-", "j+{DJU.21c2N=ZW'-");

        try {
            MimeMessageUtils.writeMimeMessage((MimeMessage)null, var1);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("org.apache.commons.mail.util.MimeMessageUtils", var3);
        }

    }
    
    @Test
    public void test04() throws Throwable {
        try {
            MimeMessageUtils.createMimeMessage((Session)null, (byte[])null);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var2) {
            EvoAssertions.verifyException("java.io.ByteArrayInputStream", var2);
        }

    }

    @Test
    public void test05() throws Throwable {
        try {
            MimeMessageUtils.createMimeMessage((Session)null, (InputStream)null);
            Assert.fail("Expecting exception: MessagingException");
        } catch (MessagingException var2) {
            EvoAssertions.verifyException("javax.mail.internet.InternetHeaders", var2);
        }

    }

    @Test
    public void test06() throws Throwable {
        DataInputStream var1 = new DataInputStream((InputStream)null);

        try {
            MimeMessageUtils.createMimeMessage((Session)null, var1);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var3) {
            EvoAssertions.verifyException("java.io.DataInputStream", var3);
        }

    }

    @Test
    public void test07() throws Throwable {
        byte[] var1 = new byte[1];
        ByteArrayInputStream var2 = new ByteArrayInputStream(var1, 0, -5446);

        try {
            MimeMessageUtils.createMimeMessage((Session)null, var2);
            Assert.fail("Expecting exception: NegativeArraySizeException");
        } catch (NegativeArraySizeException var4) {
        }

    }

    @Test
    public void test08() throws Throwable {
        byte[] var1 = new byte[1];
        ByteArrayInputStream var2 = new ByteArrayInputStream(var1, -801, 1);

        try {
            MimeMessageUtils.createMimeMessage((Session)null, var2);
            Assert.fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException var4) {
        }

    }

    @Test
    public void test09() throws Throwable {
        try {
            MimeMessageUtils.createMimeMessage((Session)null, (File)null);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var2) {
            EvoAssertions.verifyException("org.evosuite.runtime.mock.java.io.MockFileInputStream", var2);
        }

    }

    @Test
    public void test10() throws Throwable {
        MockFile var1 = new MockFile("j+{DJU.21c2N=ZW'-", "j+{DJU.21c2N=ZW'-");

        try {
            MimeMessageUtils.createMimeMessage((Session)null, var1);
            Assert.fail("Expecting exception: FileNotFoundException");
        } catch (FileNotFoundException var3) {
            EvoAssertions.verifyException("org.evosuite.runtime.mock.java.io.MockFileInputStream", var3);
        }

    }
    /*
    @Test
    public void test12() throws Throwable {
        MockFile var1 = new MockFile("./src/test/java/org.apache.commons.mail.DefaultAuthenticator", "org.apache.commons.mail.DefaultAuthenticator");
        Properties var2 = new Properties();
        Session var3 = Session.getInstance(var2);
        MimeMessage var4 = MimeMessageUtils.createMimeMessage(var3, "./src/test/java/org.apache.commons.mail.DefaultAuthenticator");
        MimeMessage var5 = new MimeMessage(var4);
        long before = System.currentTimeMillis();
        MimeMessageUtils.writeMimeMessage(var5, var1);
        long now = System.currentTimeMillis();
        Assert.assertTrue(var1.lastModified()>=before && var1.lastModified()<=now);
        var1.delete();
        File directory = new File("./src/test/java/org.apache.commons.mail.DefaultAuthenticator");
        directory.delete();
        // Il valore di confronto non è corretto
        //Assert.assertEquals(1392409281320L, var1.lastModified())
    }*/

    @Test
    public void test13() throws Throwable {
        try {
            MimeMessageUtils.createMimeMessage((Session)null, (String)null);
            Assert.fail("Expecting exception: NullPointerException");
        } catch (NullPointerException var2) {
            EvoAssertions.verifyException("org.apache.commons.mail.util.MimeMessageUtils", var2);
        }

    }

    @Test
    public void test14() throws Throwable {
        Properties var1 = new Properties();
        Session var2 = Session.getDefaultInstance(var1);
        // PROBLEMA: le cartelle NON possono essere nominate con caratteri speciali!
        //File var3 = MockFile.createTempFile("9<f3:Qu$C^! ", "org.apache.commons.mail.DefaultAuthenticator");

        // Creo quindi delle cartelle corrette, perchè il test deve verificare solo che viene creato correttamente il MimeMessage
        File var3 = MockFile.createTempFile("./abc", "cde");
        MimeMessage var4 = MimeMessageUtils.createMimeMessage(var2, var3);
        Assert.assertNotNull(var4);
    }

    @Test
    public void test15() throws Throwable {
        byte[] var1 = new byte[4];
        MimeMessage var2 = MimeMessageUtils.createMimeMessage((Session) null, var1);

        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);

        if (os.contains("win")) {
            final MockFile mockfile = new MockFile("./src/test/java/org.apache.commons.mail.4U:@iBFSz", "q:W~Zb4&7s#.Z(VKP^");
            try {
                MimeMessageUtils.writeMimeMessage(var2, mockfile);
                Assert.fail("Expecting exception: IOException");
            } catch (IOException var5) {
                EvoAssertions.verifyException("org.apache.commons.mail.util.MimeMessageUtils", var5);
            }
        } else if (os.contains("linux")) {
            final MockFile mockfile = new MockFile("./src/test/java/org.apache.commons.mail.4U:@iBFSz", "q:W~Zb4&7s#.Z(VKP^");
            assertDoesNotThrow(() -> MimeMessageUtils.writeMimeMessage(var2, mockfile));
        } else {
            final MockFile mockfile = new MockFile("/Urs/luigialons:email!!/src", "q:W~Zb4&7s#.Z(VKP^");
            try {
                MimeMessageUtils.writeMimeMessage(var2, mockfile);
                Assert.fail("Expecting exception: IOException");
            } catch (IOException var5) {
                EvoAssertions.verifyException("org.apache.commons.mail.util.MimeMessageUtils", var5);
            }
        }
    }

}
