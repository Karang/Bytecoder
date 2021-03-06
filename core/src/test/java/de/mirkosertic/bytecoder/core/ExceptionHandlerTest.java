/*
 * Copyright 2017 Mirko Sertic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mirkosertic.bytecoder.core;

import de.mirkosertic.bytecoder.unittest.BytecoderUnitTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(BytecoderUnitTestRunner.class)
public class ExceptionHandlerTest {

    private static float flowTest() {
        float counter = 0f;
        try {
            counter += 1f;
            throw new RuntimeException();
        } catch (RuntimeException e1) {
            counter += 7f;
        } finally {
            counter+=1;
        }
        return counter;
    }

    private static void throwSomething() throws Exception {
        throw new Exception();
    }

    private static void doSomething() throws Exception {
        throwSomething();
    }

    @Test
    public void testSimpleExceptionFlow() {
        Assert.assertEquals(9f, flowTest(), 0);
    }

    @Test
    public void testRethrow() {
        float theCounter = 0;
        try {
            theCounter+= 1;

            doSomething();

            theCounter+=2;
        } catch (Exception e) {
            theCounter+=4;
        }

        Assert.assertEquals(5f, theCounter, 0f);
    }
}
