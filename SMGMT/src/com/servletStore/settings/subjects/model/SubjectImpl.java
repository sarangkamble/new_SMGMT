package com.servletStore.settings.subjects.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbconnect.DBConnection;

public class SubjectImpl implements SubjectDAO{
	
	DBConnection dbconnect=new DBConnection();
	Connection connection;
	PreparedStatement ps;
	
	public SubjectImpl() {
		connection=dbconnect.getConnection();
	}
	
	@Override
	public int addSubject(SubjectPOJO SubjectPojo) throws SQLException {
		
		String insertQuery = "INSERT INTO `subject_master`(`subject_name`) VALUES (?)";
		int i=0;
		try {

			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, SubjectPojo.getSubjectName());
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			connection.close();
		}
		return i;
	}

	@Override
	public List<SubjectPOJO> getSubjectDetails() {
		
		List<SubjectPOJO> subjectList = new ArrayList<>();
		String selectQuery = "SELECT `id`, `subject_name` FROM `subject_master`";
		try {
			ps = connection.prepareStatement(selectQuery);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				SubjectPOJO subPojo = new SubjectPOJO();
				subPojo.setSubjectId(rs.getInt(1));
				subPojo.setSubjectName(rs.getString(2));
				subjectList.add(subPojo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectList;
	}
	
}
