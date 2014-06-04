package org.sparcs.gnu.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * This class has information about user's taken courses.
 * @author alphamin
 *
 */
public class GradeInfo {
	private Connection conn;
	public GradeInfo(Connection conn)
	{
		this.conn = conn;
	}

	public boolean preprocess(){
		try
		{
			PreparedStatement stmt = conn.prepareStatement("SELECT `credit`,`grade` FROM `grade`");
			long intcredit = 0;
			ResultSet result = stmt.executeQuery();
			double totalCredit = 0.0;
			double totalGrade = 0.0;
			result_loop: while(result.next())
			{
				double credit = Double.parseDouble(result.getString("credit"));
				
				double grade = 0.0;
				switch(result.getString("grade"))
				{
				case "A+":
					grade = 4.3;
					break;
				case "A0":
				case "A":
					grade = 4.0;
					break;
				case "A-":
					grade = 3.7;
					break;
				case "B+":
					grade = 3.3;
					break;
				case "B":
				case "B0":
					grade = 3.0;
					break;
				case "B-":
					grade = 2.7;
					break;
				case "C+":
					grade = 2.3;
					break;
				case "C":
				case "C0":
					grade = 2.0;
					break;
				case "C-":
					grade = 1.7;
					break;
				case "D+":
					grade = 1.3;
					break;
				case "D":
				case "D0":
					grade = 1.0;
					break;
				case "D-":
					grade = 0.7;
					break;
				case "F":
					grade = 0.0;
					break;
				case "S":
					intcredit += Long.parseLong(result.getString("credit"));
				default:
					continue result_loop;
				}

				intcredit += Long.parseLong(result.getString("credit"));
				totalCredit += credit;
				totalGrade += grade * credit;
			}
			result.close();

			PreparedStatement updateStmt = conn.prepareStatement("INSERT INTO `metadata`(`index`,`value`) VALUES (?,?)");
			
			{
				updateStmt.clearParameters();
				long intgpa = Math.round(Math.floor((totalGrade / totalCredit) * 100));
				String gpa = String.format("%d.%02d", intgpa/100 , intgpa%100);
				updateStmt.setString(1, "gpa");
				updateStmt.setString(2, gpa);
				updateStmt.executeUpdate();
			}
			{
				updateStmt.clearParameters();
				String total_credit = Long.toString(intcredit);
				updateStmt.setString(1, "total_credit");
				updateStmt.setString(2, total_credit);
				updateStmt.executeUpdate();
			}
			
			
			updateStmt.close();
			stmt.close();
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace(System.err);
			return false;
		}
	}
	public String check(String sql){
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();

			String ret = null;
			if(result.next())
			{
				ret = result.getString(1);
			}
			result.close();
			stmt.close();
			return ret;
		}
		catch(java.lang.Exception e)
		{
			e.printStackTrace(System.err);
			return null;
		}
	}
	
	public boolean insertMutualRecog(String checkSQL, String departmentCode, String originalCode, String newCode)
	{
		try
		{
			PreparedStatement checkStatement = conn.prepareStatement(checkSQL);
			ResultSet result = checkStatement.executeQuery();
			if(result.next() && result.getBoolean(1))
			{
				result.close();
				checkStatement.close();
				
				PreparedStatement updateStatement = conn.prepareStatement("INSERT INTO `grade` (`number`, `code`, `type`, `replace_from`) VALUES (?,?,?,?)");
				updateStatement.setString(1, departmentCode);
				updateStatement.setString(2, newCode);
				updateStatement.setString(3, "상호인정");
				updateStatement.setString(4, originalCode);
				updateStatement.executeUpdate();
				updateStatement.close();
				
				return true;
			}
			result.close();
			checkStatement.close();
			return false;		
		}
		catch(java.lang.Exception e)
		{
			e.printStackTrace(System.err);
			return false;
		}
	}
	
	public List<String> checkList(String sql){
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();

			List<String> ret = new LinkedList<>();
			while(result.next())
			{
				String code = result.getString(1);
				String credit = result.getString(2);
				if(credit.trim().equals("0"))
					continue;
				ret.add(code + ": " + credit);
			}
			result.close();
			stmt.close();
			return ret;
		}
		catch(java.lang.Exception e)
		{
			e.printStackTrace(System.err);
			return null;
		}
	}
}
