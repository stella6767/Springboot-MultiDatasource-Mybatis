package net.lunalabs.central;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CentralApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralApplication.class, args);
		
//		
//	          // 1. DB접속
//	          // -Connection 클래스
//	          Connection conn = null;
//	   
//	          // 2. 연결 문자열 생성
//	          // -접속에 필요한 정보로 구성된 문자열, Connection String
//	          String url = "jdbc:oracle:thin:@db.rigel.kr:1521:xe"; //localhost대신 ip주소가 들어갈수도
//	          String id = "bilab";
//	          String pw = "1234";
//	   
//	          // DB작업 > 외부 입출력 > try-catch 필수
//	   
//	          try {
//	              // 3. JDBC 드라이버 로딩
//	              Class.forName("oracle.jdbc.driver.OracleDriver");
//	   
//	              // 4. 접속
//	              // - Connection 객체 생성 + 접속 작업.
//	              conn = DriverManager.getConnection(url, id, pw);
//	              System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
//	   
//	              // 5. SQL
//	   
//	              //2. SQL 실행
//	              Statement stat=conn.createStatement();
//	              
//	              //SQL -> 문자열 -> Statement 전달 -> 오라클
////	              String sql = "insert into tblAddress(seq,name,age,tel,address,regdate) values"
////	                      + "(addressSeq.nextval,'홍길동',20,'010-3234-5150','서울시 강남구 역삼동',default)";
//	              String sql = "select 123 from dual";
//	           
//	              
//	              //a. 반환값이 없는 쿼리용
//	              //-select 제외한 전용
//	              //-반환값 : 해당 쿼리를 실행했을 때 적용된 행의 갯수
//	              ResultSet result = stat.executeQuery(sql); //Ctrl + Enter
//	              
//	              while(result.next()){
//	                  System.out.println(result.getString(1));
//	              }
//	              
//	              //b. 반환값이 있는 쿼리용
//	              //-select 전용.
////	              stat.executeQuery(sql);
//	              
//	              //3. 접속종료(자원해제)
//	              stat.close(); 
//	              
//	              
//	              
//	              // 6. 접속종료
//	              conn.close();
//	              System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
//	   
//	          } catch (Exception e) {
//	              e.printStackTrace();
//	          }
	  
	}

	
}
