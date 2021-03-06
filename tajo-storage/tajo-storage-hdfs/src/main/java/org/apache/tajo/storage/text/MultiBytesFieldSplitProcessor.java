/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tajo.storage.text;

import io.netty.buffer.ByteBufProcessor;

public class MultiBytesFieldSplitProcessor implements ByteBufProcessor {

  private int index;
  private final byte[] delimiter;

  public MultiBytesFieldSplitProcessor(byte[] recordDelimiterByte) {
    this.delimiter = recordDelimiterByte;
  }

  @Override
  public boolean process(byte value) throws Exception {
    if (delimiter[index] != value) {
      index = 0;
      return true;
    }
    if (index != delimiter.length - 1) {
      index++;
      return true;
    }
    index = 0;
    return false;
  }
}
