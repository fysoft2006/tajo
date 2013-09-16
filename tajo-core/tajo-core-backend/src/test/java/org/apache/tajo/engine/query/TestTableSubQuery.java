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

package org.apache.tajo.engine.query;

import org.apache.tajo.IntegrationTest;
import org.apache.tajo.TpchTestBase;
import org.apache.tajo.client.ResultSetUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.sql.ResultSet;

import static org.junit.Assert.*;

@Category(IntegrationTest.class)
public class TestTableSubQuery {
  private static TpchTestBase tpch;
  public TestTableSubQuery() throws IOException {
    super();
  }

  @BeforeClass
  public static void setUp() throws Exception {
    tpch = TpchTestBase.getInstance();
  }

  @Test
  public final void testTableSubquery1() throws Exception {
    ResultSet res = tpch.execute(
        "select l_orderkey from (select * from lineitem) as l");
    try {
      int count = 0;
      for (;res.next();) {
        count++;
      }
      assertEquals(5, count);
    } finally {
      res.close();
    }
  }

  @Test
  public final void testGroupBySubQuery() throws Exception {
    ResultSet res = tpch.execute(
        "select sum(l_extendedprice * l_discount) as revenue from (select * from lineitem) as l");
    try {
      assertNotNull(res);
      assertTrue(res.next());
      assertTrue(12908 == (int) res.getDouble("revenue"));
      assertFalse(res.next());
    } finally {
      res.close();
    }
  }
}