package com.servletStore.library.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utility.SysDate;

import com.dbconnect.DBConnection;

public class AddBookImpl implements AddBookDAO{
	DBConnection dbConnect = new DBConnection();
	static Connection connection;
	PreparedStatement pstmt=null;
	public AddBookImpl()
	{
		connection=dbConnect.getConnection();
		
	}
	@Override
	public int insertBook(AddBookPOJO adbk) throws SQLException {
		String query="insert into book_info_master(cat_id,date,book_name,author_name,publisher_name,edition,price,cupboard_no,quantity,language) values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps=connection.prepareStatement(query);
	//	ps.setInt(1, adbk.getBookNo());
		ps.setInt(1, adbk.getCat_id());
		ps.setString(2, adbk.getDate());
		ps.setString(3, adbk.getBookName());
		ps.setString(4, adbk.getAuthorName());
		ps.setString(5, adbk.getPublisherName());
		ps.setString(6, adbk.getEdition());
		ps.setInt(7, adbk.getPrice());
		ps.setString(8, adbk.getCupboardNo());
		ps.setInt(9, adbk.getQuantity());
		ps.setString(10, adbk.getLanguage());
		System.out.println("Language"+adbk.getLanguage());
		int st=ps.executeUpdate();
		//System.out.println("@@@@@@@@@@@"+st);
		return st;
	}
	//   For insert books as per quantity in book_details table        ////////
	@Override
	public int insertBookDetails(int bNo)throws SQLException {
		
		int status=0;
		
		String query1="INSERT INTO book_details_master (`book_no`) VALUES ("+bNo+")";
		PreparedStatement ps=connection.prepareStatement(query1);
		//System.out.println(qty+">>>>>>>>>>>>>>>");
		System.out.println(query1);
		
		status=ps.executeUpdate();
		
		
		return status;
	}
	
		///    To get the Maximum ID from book_info_master
		@Override
		public int getMaxBookInfoMaster() throws SQLException {
			
			ArrayList list=new ArrayList<>();
			PreparedStatement ps;
			int finalId=0;
			try{
				String maxId="SELECT MAX(book_info_master.book_no) FROM book_info_master";
				System.out.println(maxId);
				pstmt = connection.prepareStatement(maxId);
				//System.out.println(">><<.>");
				ResultSet rs=pstmt.executeQuery();
				//System.out.println("executed>>>>>>>>>>>>>>>>>>>>>>>>"+rs);
				while(rs.next())
				{
					System.out.println("dsadsadsadsadsad");
					finalId = rs.getInt(1);
					
				}
				
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return finalId;
		}
	@Override
	public List<AddBookPOJO> getBookDetails() throws SQLException {
		List<AddBookPOJO> list=new ArrayList<AddBookPOJO>();
		String query="select `cat_id`,`date`,`book_no`,`book_name`,`author_name`,`publisher_name`,`edition`,`price`,`cupboard_no`,`quantity`,`language` from book_info_master";
	
		PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
		
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			AddBookPOJO addb=new AddBookPOJO();
			
			//bcp.setCatName(rs.getString("cat_type"));
			addb.setCat_id(rs.getInt("cat_id"));
			addb.setDate(rs.getString("date"));
			addb.setBookNo(rs.getInt("book_no"));
			addb.setBookName(rs.getString("book_name"));
			addb.setAuthorName(rs.getString("author_name"));
			addb.setPublisherName(rs.getString("publisher_name"));
			addb.setEdition(rs.getString("edition"));
			addb.setPrice(rs.getInt("price"));
			addb.setCupboardNo(rs.getString("cupboard_no"));
			addb.setQuantity(rs.getInt("quantity"));
			addb.setLanguage(rs.getString("language"));
			list.add(addb);
		}

		return list;
	}
	public int updateBook(AddBookPOJO ad) { 
		    int status=0;  
		    String query="update book_info_master set date=?, book_name=?,author_name=?,publisher_name=?,edition=?,price=?,cupboard_no=?,quantity=?,language=? where book_no=?";
		  	try {
				System.out.println("hi");
				pstmt = connection.prepareStatement(query);
				
				pstmt.setString(1,ad.getDate());
				pstmt.setString(2,ad.getBookName());
				pstmt.setString(3, ad.getAuthorName());
				pstmt.setString(4, ad.getPublisherName());		         
				pstmt.setString(5, ad.getEdition());
				pstmt.setInt(6,	ad.getPrice());
				pstmt.setString(7, ad.getCupboardNo());
		        pstmt.setInt(8, ad.getQuantity());
		        pstmt.setString(9, ad.getLanguage());
		        pstmt.setInt(10, ad.getBookNo());
		            
		        System.out.println("bookaname:"+ad.getBookName());
		        status=pstmt.executeUpdate();  
		          System.out.println("Updated:"+status);
		       
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		    return status;  
		
	}
	
	@Override
	public void deleteCategory(int bookNo) {
		try {
			pstmt=connection.prepareStatement("DELETE FROM book_info_master WHERE book_no=?");
			pstmt.setInt(1, bookNo);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List selectBookInfo(AddBookPOJO pojo,int id) {
		List<AddBookPOJO> list=new ArrayList<AddBookPOJO>();
		String select_query="SELECT book_info_master.book_no,book_info_master.date,book_info_master.cat_id,book_info_master.book_name,book_info_master.author_name,book_info_master.publisher_name,book_info_master.edition,book_info_master.price,book_info_master.cupboard_no,book_info_master.quantity,book_info_master.language FROM book_info_master WHERE book_info_master.book_no=?";
		try {
			pstmt=connection.prepareStatement(select_query);
			pstmt.setInt(1, id);
		//	System.out.println("hi:"+id);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next())
		{
			pojo.setBookNo(rs.getInt(1));
			pojo.setDate(rs.getString(2));
			pojo.setCat_id(rs.getInt(3));			
			pojo.setBookName(rs.getString(4));
			pojo.setAuthorName(rs.getString(5));
			pojo.setPublisherName(rs.getString(6));
			pojo.setEdition(rs.getString(7));
			pojo.setPrice(rs.getInt(8));
			pojo.setCupboardNo(rs.getString(9));
			pojo.setQuantity(rs.getInt(10));
			pojo.setLanguage(rs.getString(11));
			list.add(pojo);
			
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public int searchBook(AddBookPOJO adb) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public List searchBookDetails(String bookDetail){
		List list=new ArrayList();
		ResultSet rs=null;
		try {
			// by book name
			String query1="SELECT book_details_master.book_id,book_info_master.book_name,book_info_master.author_name FROM book_info_master,book_details_master WHERE book_details_master.book_no=book_info_master.book_no AND book_details_master.issue_status=0 AND book_info_master.book_name LIKE '"+bookDetail+"%'";
			PreparedStatement ps1=(PreparedStatement) connection.prepareStatement(query1);
			rs=ps1.executeQuery();
				if(!rs.isBeforeFirst()){
					// by author name
					String query2="SELECT book_details_master.book_id,book_info_master.book_name,book_info_master.author_name FROM book_info_master,book_details_master WHERE book_details_master.book_no=book_info_master.book_no AND book_details_master.issue_status=0 AND book_info_master.author_name LIKE '"+bookDetail+"%'";
					PreparedStatement ps2=(PreparedStatement) connection.prepareStatement(query2);
					rs=ps2.executeQuery();
				if(!rs.isBeforeFirst()){
					// by book Id
					String query="SELECT book_details_master.book_id,book_info_master.book_name,book_info_master.author_name FROM book_info_master,book_details_master WHERE book_details_master.book_no=book_info_master.book_no AND book_details_master.issue_status=0 AND book_details_master.book_id LIKE '"+bookDetail+"%'";
					PreparedStatement ps;
					ps = (PreparedStatement) connection.prepareStatement(query);
					rs=ps.executeQuery();
				}
			}
			while(rs.next())
			{
				list.add(rs.getInt(1));
			    list.add(rs.getString(2));
				list.add((rs.getString(3)));
				
			}
			//System.out.println("list "+list);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List searchStudDetails(String studDetail) {
		List list1=new ArrayList();
		ResultSet rs=null;
		
		try {
			//System.out.println("Search by Name");
			String query="SELECT student_details.id,student_official_details.gr_no,student_details.first_name,student_details.last_name,std_master.name,classroom_master.division,classroom_master.shift FROM student_details,student_official_details,class_allocation,fk_class_master,fk_school_section_details,classroom_master,std_master WHERE student_details.first_name LIKE '"+studDetail+"%' AND student_official_details.student_id=student_details.id AND student_official_details.lc_status=0 AND class_allocation.student_id=student_details.id AND class_allocation.academic_year='2017-2018' AND class_allocation.catalog_status=0 AND classroom_master.id=class_allocation.classroom_master AND fk_class_master.id=classroom_master.fk_class_master_id AND std_master.id=fk_class_master.std_id AND fk_school_section_details.id=fk_class_master.fk_school_sec_id AND fk_school_section_details.school_id=1";
			PreparedStatement ps2=connection.prepareStatement(query);
			rs=ps2.executeQuery();
			if(!rs.isBeforeFirst()){
				//System.out.println("By Icard");
				String query1="SELECT student_details.id,student_official_details.gr_no,student_details.first_name,student_details.last_name,std_master.name,classroom_master.division,classroom_master.shift FROM student_details,student_official_details,class_allocation,fk_class_master,fk_school_section_details,classroom_master,std_master WHERE student_official_details.gr_no LIKE '"+studDetail+"%' AND student_official_details.student_id=student_details.id AND student_official_details.lc_status=0 AND class_allocation.student_id=student_details.id AND class_allocation.academic_year='2017-2018' AND class_allocation.catalog_status=0 AND classroom_master.id=class_allocation.classroom_master AND fk_class_master.id=classroom_master.fk_class_master_id AND std_master.id=fk_class_master.std_id AND fk_school_section_details.id=fk_class_master.fk_school_sec_id AND fk_school_section_details.school_id=1";
				PreparedStatement ps1= connection.prepareStatement(query1);
				rs=ps1.executeQuery();
			}
			while(rs.next())
			{
				list1.add(rs.getInt(1));
				list1.add(rs.getInt(2));
			    list1.add(rs.getString(3));
			    list1.add(rs.getString(4));
			    list1.add((rs.getString(5)));
				list1.add((rs.getString(6)));
				list1.add((rs.getString(7)));
			}
				//System.out.println("list "+list1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return list1;
	}
	 
	
	
	@Override
	public int insertIssueBook(IssueBookPOJO pojo) throws SQLException {
		//System.out.println("pojo "+pojo.toString());
		String a = String.valueOf(pojo.getStudId());
		if(a.equals("0")){
			  a="NULL";
		   }
		
		String query="INSERT INTO `issue_book`(`bookdetails_id`, `stud_id`, `issue_date`, `due_date`) values("+ pojo.getBookId()+","+a+",'"+pojo.getIssueDate()+"','"+ pojo.getDueDate()+"')";
		PreparedStatement ps=connection.prepareStatement(query);
			  System.out.println("$$"+query);
				// System.out.println("staffid : "+pojo.getStaffId());
			   
			   /*if(pojo.getStaffId()==0){
				   ps.setString(3, "NULL");
			   }
			   else {
				   ps.setInt(3, pojo.getStaffId());
			   }*/
			   int st=ps.executeUpdate();
			   return pojo.getBookId();
	}
	
	@Override
	public int changeStatus(int bookdetails_id) throws SQLException {
		int status=0;
		String sql="UPDATE `book_details_master` SET `issue_status`=1 WHERE book_details_master.book_id="+bookdetails_id;
		try {
			pstmt = connection.prepareStatement(sql);
			//System.out.println("bookaname:"+ad.getBookName());
	        status=pstmt.executeUpdate();  
	         System.out.println("Updated:"+status);
	       	} catch (SQLException e) {
			e.printStackTrace();
		}
	   return status;  
	}
	
	@Override
	public List<IssueBookPOJO> getIssueBookDetails() {
		List<IssueBookPOJO> list=new ArrayList<IssueBookPOJO>();
		 SysDate sd=new SysDate();
		 String query="SELECT `id`, `bookdetails_id`, `stud_id`, `staff_id`, `issue_date`, `due_date` FROM `issue_book`, book_details_master WHERE issue_book.issue_date='"+sd.todayDate()+"' AND  book_details_master.book_id=issue_book.bookdetails_id AND book_details_master.issue_status=1 ORDER BY issue_book.id DESC ";
		 PreparedStatement ps, ps1, ps2, ps3;
		 try {
			ps =connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery(query);
			while(rs.next())
			{
				
				IssueBookPOJO pojo=new IssueBookPOJO();
				pojo.setBookId(rs.getInt("id"));
				
				pojo.setIssueDate(rs.getString("issue_date"));
				pojo.setDueDate(rs.getString("due_date"));
				
				String query1="SELECT book_info_master.book_name FROM book_info_master, book_details_master WHERE book_info_master.book_no=book_details_master.book_no AND book_details_master.book_id="+rs.getInt("bookdetails_id");
				//System.out.println("1  : "+query1);
				ps1 =connection.prepareStatement(query1);
				ResultSet rs1=ps1.executeQuery(query1);
				
				while(rs1.next()){
					pojo.setBookName(rs1.getString("book_name"));
				}
				
				if(String.valueOf(rs.getInt("stud_id"))!=null)
				{
					String query2="SELECT student_details.first_name FROM student_details WHERE student_details.id="+rs.getInt("stud_id");
					ps2 =connection.prepareStatement(query2);
					ResultSet rs3=ps2.executeQuery(query2);
					while(rs3.next()){
						pojo.setStudName(rs3.getString("first_name"));
					}
					
				}else{
					pojo.setStudName("-");
				}
				
				
				if(String.valueOf(rs.getInt("staff_id"))!=null)
				{
					String query2="SELECT staff.firstname FROM staff WHERE staff.staff_id="+rs.getInt("staff_id");

					ps3 =connection.prepareStatement(query2);
					ResultSet rs2=ps3.executeQuery(query2);
					while(rs2.next()){
						pojo.setStaffName(rs2.getString("firstname"));
					}
				}else{
					pojo.setStaffName("-");
				}
				list.add(pojo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(list);
		return list;

	}
	
/*	public List<IssueBookPOJO> getIssueBookList(String query) {
		List<IssueBookPOJO> list=new ArrayList<IssueBookPOJO>();
		 SysDate sd=new SysDate();
		
		//String query="select * from issue_book where issue_book.issue_date="+sd.todayDate().split("-")[2]+"-"+sd.todayDate().split("-")[1]+"-"+sd.todayDate().split("-")[0]+"'";
		//String query="SELECT *FROM issue_book WHERE  issue_date >= '"+issueDate+"' AND  due_date <= '"+dueDate+"'";
		PreparedStatement ps;
		//System.out.println("************"+query);
		try {
			ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				IssueBookPOJO pojo=new IssueBookPOJO();
				
				//bcp.setCatName(rs.getString("cat_type"));
				pojo.setBookId(rs.getInt("bookdetails_id"));
				pojo.setStudId(rs.getInt("stud_id"));
				pojo.setStaffId(rs.getInt("staff_id"));
				pojo.setIssueDate(rs.getString("issue_date"));
				pojo.setDueDate(rs.getString("due_date"));
				pojo.setReturnDate(rs.getString("return_date"));
								
				list.add(pojo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("5*******************"+list);
		return list;

	}*/
		@Override
	public List getDatewiseIssueList(String date1, String date2)
			throws SQLException {
		
		List list = new ArrayList();
		
		String query="";
		pstmt=connection.prepareStatement("SELECT student_details.first_name,book_info_master.book_name,issue_book.issue_date,issue_book.due_date FROM student_details,issue_book,book_info_master,book_details_master WHERE book_info_master.book_no=book_details_master.book_no AND issue_book.bookdetails_id=book_details_master.book_id AND issue_book.stud_id=student_details.id AND issue_date between ? AND ?");
		pstmt.setString(1, date1);
		pstmt.setString(2, date2);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			list.add(rs.getString("first_name"));
			list.add(rs.getString("book_name"));
			list.add(rs.getString("issue_date"));
			list.add(rs.getString("due_date"));
		}
		
		
		return list;
	}
	
		@Override
		public List searchBookInfo(String bookDetail, String bookName, String authorName) {
			List list=new ArrayList();
			ResultSet rs=null;
			AddBookPOJO pojo=new AddBookPOJO();
			try {
		
				// by author name
				System.out.println("*********");
				String query="SELECT book_info_master.book_no,book_info_master.book_name,book_info_master.author_name,issue_book.issue_date,issue_book.due_date,issue_book.id,issue_book.stud_id,issue_book.staff_id FROM issue_book,book_info_master,book_details_master WHERE issue_book.bookdetails_id=book_details_master.book_id AND book_info_master.book_no=book_details_master.book_no AND book_details_master.book_id=? AND book_info_master.book_name=? AND book_info_master.author_name=?";
				//String query="SELECT book_info_master.book_no,book_info_master.book_name,book_info_master.author_name,issue_book.issue_date,issue_book.due_date FROM issue_book,book_info_master,book_details_master WHERE issue_book.bookdetails_id=book_details_master.book_id AND book_info_master.book_no=book_details_master.book_no AND book_details_master.book_id=? AND book_info_master.book_name=? AND book_info_master.author_name=?";
				pstmt=(PreparedStatement) connection.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(bookDetail));
				pstmt.setString(2, bookName);
				pstmt.setString(3, authorName);
			//	System.out.println("**^^^^^^^^^^^^^^^^^^^^^^^^^^"+query);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					list.add(rs.getInt(1));
					list.add(rs.getString(2));
					list.add(rs.getString(3));
					list.add(rs.getString(4));
					list.add(rs.getString(5));
					list.add(rs.getInt(6));
					list.add(rs.getInt(7));
					list.add(rs.getInt(8));
					
					System.out.println("List"+list);
				}
				
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return list;
	}
		@Override
		public int daysCount(Object todayDate, Object dueDate) throws SQLException {
			int totalDays=0;
			int fine=0;
			try{
			//String str="SELECT DATEDIFF(issue_book.return_date,issue_book.due_date )AS DAYS FROM issue_book WHERE issue_book.id=?";
				String dateDifference="SELECT DATEDIFF(?,?) AS DiffDate";
				pstmt=(PreparedStatement) connection.prepareStatement(dateDifference);
				pstmt.setObject(1, todayDate);
				pstmt.setObject(2, dueDate);
				System.out.println(todayDate+"dsadsad "+dueDate);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next())
				{
					totalDays=rs.getInt(1);
					fine=totalDays*5;
					System.out.println("true "+totalDays);
					System.out.println("fine"+fine);
				}
			}catch(SQLException e)
			{
				
			}
			return totalDays;
		}
	
		@Override
	public int insertFine(SetFinePOJO pojo) throws SQLException {
	//	System.out.println("************");
		String query="insert into set_library_fine(date,fine_amount) values(?,?)";
	
			pstmt=connection.prepareStatement(query);
			pstmt.setString(1, pojo.getDate());
			pstmt.setInt(2, pojo.getFine());
			int status= pstmt.executeUpdate();
			return status;
	}
	
	@Override
	public SetFinePOJO getFineDetails() {
		// TODO Auto-generated method stub
		SetFinePOJO fine_pojo=new SetFinePOJO();
		try 
		{
			pstmt=connection.prepareStatement("SELECT * FROM set_library_fine");
			ResultSet rs= pstmt.executeQuery();
			while(rs.next())
			{
				fine_pojo.setId(rs.getInt("id"));
				fine_pojo.setDate(rs.getString("date"));
				fine_pojo.setFine(rs.getInt("fine_amount"));
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return fine_pojo;
	}
	@Override
	public void updateFineDetails(SetFinePOJO sp) 
	{
		try 
		{
			pstmt=connection.prepareStatement("UPDATE set_library_fine set date=?,fine_amount=? WHERE id=?");
			//pstmt.setString(1,cp.getRoute_name());
			pstmt.setString(1,sp.getDate());
			pstmt.setInt(2,sp.getFine());
			pstmt.setInt(3,sp.getId());
			pstmt.executeUpdate();
			System.out.println("INSERTED..."+sp.getId());
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	@Override
	public int getFine() throws SQLException {
		int fineAmount=0;
		String str="select fine_amount from set_library_fine";
		pstmt=connection.prepareStatement(str);
		
		ResultSet rs=pstmt.executeQuery();
		while(rs.next())
		{
			fineAmount=rs.getInt("fine_amount");
		}
		return fineAmount;
	}
	
	@Override
	public int getPreviousFine(String studentId) throws SQLException {
		int previousfine=0;
		//pass studId in setString
		String query="SELECT fine_master_details.remaining_fine_amt FROM fine_master_details WHERE fine_master_details.stud_id=? ORDER BY fine_master_details.id DESC LIMIT 1";
		System.out.println(query);
		pstmt=connection.prepareStatement(query);
		pstmt.setString(1,studentId);
		 ResultSet rs=pstmt.executeQuery();
		 System.out.println("stud Id********"+studentId);
		
		 while(rs.next())
		 {
			 System.out.println("IN WHILE");
			 previousfine=rs.getInt(1);
			 System.out.println("Remain Fine in impl"+previousfine);
		 }
		
		
		return previousfine;
	}
	
	@Override
	public List getReturnBookDetails(String returnBookDetails)
			throws SQLException {
		List list = new ArrayList();
		ResultSet rs=null;
		try {
			// by book name
			String query2="SELECT book_details_master.book_id,book_info_master.book_name,book_info_master.author_name FROM book_info_master,book_details_master,issue_book WHERE book_info_master.book_no=book_details_master.book_no AND issue_book.bookdetails_id=book_details_master.book_id AND book_details_master.issue_status=1 AND book_info_master.book_name LIKE '"+returnBookDetails+"%' GROUP BY book_details_master.book_id";
			PreparedStatement ps2=(PreparedStatement) connection.prepareStatement(query2);
			rs=ps2.executeQuery();
				if(!rs.isBeforeFirst()){
					// by author name
					String query1="SELECT book_details_master.book_id,book_info_master.book_name,book_info_master.author_name FROM book_info_master,book_details_master,issue_book WHERE book_info_master.book_no=book_details_master.book_no AND issue_book.bookdetails_id=book_details_master.book_id AND book_details_master.issue_status=1 AND book_info_master.author_name LIKE '"+returnBookDetails+"%' GROUP BY book_details_master.book_id";
					PreparedStatement ps1=(PreparedStatement) connection.prepareStatement(query1);
					rs=ps1.executeQuery();
				if(!rs.isBeforeFirst()){
					// by book Id
					String query="SELECT book_details_master.book_id,book_info_master.book_name,book_info_master.author_name FROM book_info_master,book_details_master,issue_book WHERE book_details_master.book_no=book_info_master.book_no AND issue_book.bookdetails_id=book_details_master.book_id AND book_details_master.issue_status=1 AND book_details_master.book_id LIKE '"+returnBookDetails+"%'  GROUP BY book_details_master.book_id";
					PreparedStatement ps;
					ps = (PreparedStatement) connection.prepareStatement(query);
					rs=ps.executeQuery();
				}
			}
			while(rs.next())
			{
				list.add(rs.getInt(1));
			    list.add(rs.getString(2));
				list.add((rs.getString(3)));
				
			}
			//System.out.println("list "+list);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getId(int studId) throws SQLException {
		int id=0;
		System.out.println("@@@@@@@@@@@@"+studId);
		String str="SELECT student_official_details.student_id FROM student_official_details WHERE student_official_details.gr_no=?";
		
		 pstmt=connection.prepareStatement(str);
		 pstmt.setInt(1, studId);
		 System.out.println("str");
		 ResultSet rs=pstmt.executeQuery();
		 while(rs.next())
		 {
			 id=rs.getInt(1);
		 }
	   return id;
	}
	@Override
	public int getStudId(String grNo, String firstName, String std, String div, String shift, String lastName) throws SQLException {
		int id=0;
		String str="SELECT student_details.id FROM student_details,student_official_details,class_allocation,fk_class_master,fk_school_section_details,classroom_master,std_master WHERE student_official_details.gr_no=? AND student_details.first_name=? AND student_details.last_name=? AND std_master.name=? ANd classroom_master.division=? AND classroom_master.shift=?  AND student_official_details.student_id=student_details.id AND student_official_details.lc_status=0 AND class_allocation.student_id=student_details.id AND class_allocation.academic_year='2017-2018' AND class_allocation.catalog_status=0 AND classroom_master.id=class_allocation.classroom_master AND fk_class_master.id=classroom_master.fk_class_master_id AND std_master.id=fk_class_master.std_id AND fk_school_section_details.id=fk_class_master.fk_school_sec_id AND fk_school_section_details.school_id=1";
		 pstmt=connection.prepareStatement(str);
		 pstmt.setInt(1, Integer.parseInt(grNo.trim()));
		 pstmt.setString(2, firstName);
		 pstmt.setString(3, lastName);
		 pstmt.setString(4, std);
		 pstmt.setString(5, div);
		 pstmt.setString(6, shift);
		 
		 ResultSet rs=pstmt.executeQuery();
		 while(rs.next())
		 {
			 id=rs.getInt(1);
			 //System.out.println("@@@@@@@@@@@@@@@@"+id);
		 }
		return id;
	}
	
	@Override
	public int insertFineDetails(FineMasterPOJO pojo) throws SQLException{
		System.out.println("in Fine section");
		String query="INSERT into fine_master_details(stud_id,issue_id,due_days,fine_amt,discount,fine_paid_amt,remaining_fine_amt) VALUES(?,?,?,?,?,?,?)";
		pstmt=connection.prepareStatement(query);
		pstmt.setInt(1, pojo.getStudId());
		pstmt.setInt(2, pojo.getIssueId());
		pstmt.setInt(3, pojo.getDueDays());
		pstmt.setInt(4, pojo.getFineAmount());
		pstmt.setInt(5, pojo.getDiscount());
		pstmt.setInt(6, pojo.getFinePaidAmount());
		pstmt.setInt(7, pojo.getRemainingFine());
		System.out.println( "Detailssssssssss"+pojo.getStudId()+" "+pojo.getIssueId()+" "+pojo.getDueDays()+" "+pojo.getFineAmount()+" "+ pojo.getDiscount()+" "+pojo.getFinePaidAmount()+" "+pojo.getRemainingFine());
		int status= pstmt.executeUpdate();
		return status;
		
	}
	
	@Override
	public int getDate(FineMasterPOJO pojo) throws SQLException {
		
		String query1="UPDATE `issue_book` SET `return_date`=? WHERE issue_book.id=?";
		System.out.println(query1);
		pstmt=connection.prepareStatement(query1);
		pstmt.setString(1, pojo.getReturnDate());
		pstmt.setInt(2, pojo.getIssueId());
		int st=pstmt.executeUpdate();
		
		return st;
	}
	
	@Override
	public List<FineMasterPOJO> getFineMaster() throws SQLException {
		List< FineMasterPOJO> list = new ArrayList<FineMasterPOJO>();
		String query="SELECT student_details.id,issue_book.id,fine_master_details.due_days,fine_master_details.fine_amt,fine_master_details.discount,fine_master_details.fine_paid_amt,fine_master_details.remaining_fine_amt FROM student_details, issue_book, fine_master_details WHERE student_details.id= fine_master_details.stud_id AND issue_book.id=fine_master_details.issue_id AND fine_master_details.id=1;";
		pstmt=connection.prepareStatement(query);
		ResultSet rs=pstmt.executeQuery();
		FineMasterPOJO pojo=new FineMasterPOJO();
		while(rs.next())
		{
			pojo.setDueDays(rs.getInt("due_days"));
			pojo.setFineAmount(rs.getInt("fine_amt"));
			pojo.setDiscount(rs.getInt("discount"));
			pojo.setFinePaidAmount(rs.getInt("fine_paid_amt"));
			pojo.setRemainingFine(rs.getInt("remaining_fine_amt"));
			
			list.add(pojo);
		}
		return list;
	}
	@Override
	public List getStudentId(int bookId) throws SQLException
	{
		int id=0;
		int studId=0;
		List list=new ArrayList();
		String str="SELECT issue_book.id,issue_book.stud_id FROM issue_book WHERE issue_book.bookdetails_id=? ORDER BY issue_book.id DESC LIMIT 1";
		
		 pstmt=connection.prepareStatement(str);
		 pstmt.setInt(1, bookId);
		
		 
		 ResultSet rs=pstmt.executeQuery();
		 while(rs.next())
		 {
			 list.add(rs.getInt(1));
			 list.add(rs.getInt(2));
			 //System.out.println("@@@@@@@@@@@@@@@@"+id);
		 }
		return list;
	}
	@Override
	public int changeStatusReturn(int id) throws SQLException {
		int status=0;
		String query="UPDATE `book_details_master`,issue_book SET `issue_status`=0 WHERE issue_book.bookdetails_id=book_details_master.book_id AND issue_book.id="+id;
		pstmt=connection.prepareStatement(query);
		status=pstmt.executeUpdate();
		return status;
	}
	@Override
	public int updateRenewBook(FineMasterPOJO pojo, String date) throws SQLException {
		int status=0;
		//String query="UPDATE issue_book,fine_master_details SET due_date=? WHERE fine_master_details.issue_id=issue_book.id AND fine_master_details.issue_id="+id;
		String query="UPDATE `issue_book` SET`issue_date`=? ,`due_date`=? WHERE issue_book.id=?";
		pstmt=connection.prepareStatement(query);
		pstmt.setString(1, date);
		pstmt.setString(2, pojo.getDueDate());
		pstmt.setInt(3, pojo.getIssueId());
		System.out.println(query);
		status=pstmt.executeUpdate();
		System.out.println("Execute");
		return status;
	}
	
	@Override
	public List getStudent(int id) throws SQLException {
		List list=new ArrayList();
		FineMasterPOJO pojo=new FineMasterPOJO();

		String query="SELECT student_details.first_name,student_details.last_name,std_master.name,classroom_master.division FROM student_details,student_official_details,class_allocation,fk_class_master,fk_school_section_details,classroom_master,std_master WHERE student_official_details.student_id=student_details.id AND student_official_details.lc_status=0 AND class_allocation.student_id=student_details.id AND class_allocation.academic_year='2017-2018' AND class_allocation.catalog_status=0 AND classroom_master.id=class_allocation.classroom_master AND fk_class_master.id=classroom_master.fk_class_master_id AND std_master.id=fk_class_master.std_id AND fk_school_section_details.id=fk_class_master.fk_school_sec_id AND fk_school_section_details.school_id=1 AND student_details.id=?";
		System.out.println("Query "+query);
		pstmt=connection.prepareStatement(query);
		/*pstmt.setString(1, "first_name");
		pstmt.setString(2, "last_name");
		pstmt.setString(3, "std");
		pstmt.setString(4, "div");*/
		pstmt.setInt(1, id);
		ResultSet rs=pstmt.executeQuery();
		System.out.println("***execute");
		while(rs.next())
		{
			list.add(rs.getString(1));
			list.add(rs.getString(2));
			list.add(rs.getString(3));
			list.add(rs.getString(4));
			//id=rs.getInt(1);
		}
		return list;
	}
	
	@Override
	public List getFineSubmission(int id) throws SQLException {
		System.out.println("1");
		List list=new ArrayList();
		String query="SELECT student_details.first_name,student_details.middle_name,student_details.last_name,std_master.name,classroom_master.division,fine_master_details.remaining_fine_amt FROM fine_master_details,student_details,student_official_details,class_allocation,fk_class_master,fk_school_section_details,classroom_master,std_master WHERE fine_master_details.id=(SELECT MAX(fine_master_details.id) FROM fine_master_details WHERE fine_master_details.stud_id=?) AND student_details.id=fine_master_details.stud_id AND student_official_details.student_id=student_details.id AND student_official_details.lc_status=0 AND class_allocation.student_id=student_details.id AND class_allocation.academic_year='2017-2018' AND class_allocation.catalog_status=0 AND classroom_master.id=class_allocation.classroom_master AND fk_class_master.id=classroom_master.fk_class_master_id AND std_master.id=fk_class_master.std_id AND fk_school_section_details.id=fk_class_master.fk_school_sec_id AND fk_school_section_details.school_id=1";
		pstmt=connection.prepareStatement(query);
		System.out.println("Qury"+query);
		pstmt.setInt(1, id);
		ResultSet rs=pstmt.executeQuery();
		System.out.println("2");
		while(rs.next())
		{
			list.add(rs.getString(1));
			list.add(rs.getString(2));
			list.add(rs.getString(3));
			list.add(rs.getString(4));
			list.add(rs.getString(5));
			list.add(rs.getInt(6));
		}
			return list;
	}
	
}

