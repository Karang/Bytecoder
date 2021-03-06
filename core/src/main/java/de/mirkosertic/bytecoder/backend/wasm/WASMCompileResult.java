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
package de.mirkosertic.bytecoder.backend.wasm;

import de.mirkosertic.bytecoder.backend.CompileResult;
import de.mirkosertic.bytecoder.core.BytecodeLinkerContext;
import de.mirkosertic.bytecoder.core.BytecodeObjectTypeRef;

import java.util.List;

public class WASMCompileResult implements CompileResult<String> {

    private final WASMMemoryLayouter memoryLayouter;
    private final BytecodeLinkerContext linkerContext;
    private final List<String> generatedFunctions;
    private final String data;

    public WASMCompileResult(BytecodeLinkerContext aLinkerContext, List<String> aGeneratedFunctions, String aData, WASMMemoryLayouter aMemoryLayout) {
        linkerContext = aLinkerContext;
        generatedFunctions = aGeneratedFunctions;
        data = aData;
        memoryLayouter = aMemoryLayout;
    }

    public int getTypeIDFor(BytecodeObjectTypeRef aObjecType) {
        return linkerContext.linkClass(aObjecType).getUniqueId();
    }

    public int getSizeOf(BytecodeObjectTypeRef aObjectType) {
        WASMMemoryLayouter.MemoryLayout theLayout = memoryLayouter.layoutFor(aObjectType);
        return theLayout.instanceSize();
    }

    public int getVTableIndexOf(BytecodeObjectTypeRef aObjectType) {
        String theClassName = WASMWriterUtils.toClassName(aObjectType);

        String theMethodName = theClassName + "__resolvevtableindex";
        return generatedFunctions.indexOf(theMethodName);
    }

    @Override
    public String getData() {
        return data;
    }
}
