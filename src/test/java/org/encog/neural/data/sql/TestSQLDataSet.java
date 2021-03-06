/*
 * Encog(tm) Core Unit Tests v3.0 - Java Version
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2011 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.neural.data.sql;

import java.sql.Connection;
import java.sql.Statement;

import junit.framework.TestCase;

import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.XOR;
import org.encog.platformspecific.j2se.data.SQLNeuralDataSet;
import org.encog.util.HSQLUtil;

public class TestSQLDataSet extends TestCase {
	
	public void testSQLDataSet() throws Exception
	{
		HSQLUtil.loadDriver();
		//DerbyUtil.cleanup();
		Connection conn = HSQLUtil.getConnection();
		
		conn.setAutoCommit(true);

		Statement s = conn.createStatement();

		// We create a table...
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE \"XOR\" (");
		sql.append(" \"ID\" int GENERATED BY DEFAULT AS IDENTITY,");
		sql.append(" \"IN1\" int,");
		sql.append(" \"IN2\" int,");
		sql.append(" \"IDEAL1\" int");
		sql.append(" )");
		s.execute(sql.toString());
		
		s.execute("INSERT INTO xor(in1,in2,ideal1) VALUES(0,0,0)");
		s.execute("INSERT INTO xor(in1,in2,ideal1) VALUES(1,0,1)");
		s.execute("INSERT INTO xor(in1,in2,ideal1) VALUES(0,1,1)");
		s.execute("INSERT INTO xor(in1,in2,ideal1) VALUES(1,1,0)");
		
		SQLNeuralDataSet data = new SQLNeuralDataSet(
				"SELECT in1,in2,ideal1 FROM xor ORDER BY id",
				2,
				1, 
				HSQLUtil.DRIVER, 
				HSQLUtil.URL, 
				HSQLUtil.UID,
				HSQLUtil.PWD);
		
		XOR.testXORDataSet((MLDataSet)data);
				
		HSQLUtil.shutdown();
		//DerbyUtil.cleanup();

	}
}
