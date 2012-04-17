/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.entity;

import java.io.ByteArrayOutputStream;

import org.apache.http.Consts;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link StringEntity}.
 *
 */
public class TestStringEntity {

    @Test
    public void testBasics() throws Exception {
        String s = "Message content";
        StringEntity httpentity = new StringEntity(s, ContentType.TEXT_PLAIN);

        byte[] bytes = s.getBytes(Consts.ISO_8859_1.name());
        Assert.assertEquals(bytes.length, httpentity.getContentLength());
        Assert.assertNotNull(httpentity.getContent());
        Assert.assertTrue(httpentity.isRepeatable());
        Assert.assertFalse(httpentity.isStreaming());
    }

    @Test
    public void testIllegalConstructor() throws Exception {
        try {
            new StringEntity(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    @Test
    public void testDefaultContent() throws Exception {
        String s = "Message content";
        StringEntity httpentity = new StringEntity(s, ContentType.create("text/csv", "ANSI_X3.4-1968"));
        Assert.assertEquals("text/csv; charset=US-ASCII",
                httpentity.getContentType().getValue());
        httpentity = new StringEntity(s, Consts.ASCII.name());
        Assert.assertEquals("text/plain; charset=US-ASCII",
                httpentity.getContentType().getValue());
        httpentity = new StringEntity(s);
        Assert.assertEquals("text/plain; charset=ISO-8859-1",
                httpentity.getContentType().getValue());
    }

    @Test
    public void testWriteTo() throws Exception {
        String s = "Message content";
        byte[] bytes = s.getBytes(Consts.ISO_8859_1.name());
        StringEntity httpentity = new StringEntity(s);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        httpentity.writeTo(out);
        byte[] bytes2 = out.toByteArray();
        Assert.assertNotNull(bytes2);
        Assert.assertEquals(bytes.length, bytes2.length);
        for (int i = 0; i < bytes.length; i++) {
            Assert.assertEquals(bytes[i], bytes2[i]);
        }

        out = new ByteArrayOutputStream();
        httpentity.writeTo(out);
        bytes2 = out.toByteArray();
        Assert.assertNotNull(bytes2);
        Assert.assertEquals(bytes.length, bytes2.length);
        for (int i = 0; i < bytes.length; i++) {
            Assert.assertEquals(bytes[i], bytes2[i]);
        }

        try {
            httpentity.writeTo(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

}
