package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import org.apache.log4j.Logger;


public class MarkGenerator {
	private static final Logger log = Logger.getLogger(MarkGenerator.class);
		DbConnect dbobj = new DbConnect();
		
		
	public void firstMark() throws SQLException  {
		log.info("Entered Assignment mark method");
		ResultSet rs = dbobj.getCourseStructure();
		weightCalc(rs, 1);
		}
		
		public void SecondMark() throws SQLException {
			log.info("Entered Mid Term mark method");
			ResultSet rs = dbobj.getCourseStructure();
			weightCalc(rs, 2);
		}
		
		public void ThirdMark() throws SQLException {
			log.info("Entered Project and Final Exam mark method");
			ResultSet rs = dbobj.getCourseStructure();
			weightCalc(rs, 3);
			dbobj.hasFinal();
			dbobj.gradeCalc();
		}
		
		
		public void weightCalc(ResultSet rs, int state) throws SQLException {
			int slno = 0;
	        float assigno = 0;
	        float mdtno = 0;
	        float pjtno = 0;
	        float feno  = 0;
	        float total = 0;
	        float dvfactor = 0;
	        Random rand = new Random();
			while(rs.next()) {
				slno = rs.getInt("idmark");
				assigno = rs.getInt("assignment");
				mdtno = rs.getInt("midterm");
				pjtno = rs.getInt("project");
				log.info("Project number: "+pjtno);
				feno = rs.getInt("FinalExam");
				log.info("Final exam number: "+feno);
				total = assigno * 10 + mdtno * 10 + pjtno * 60 + feno * 60;
				dvfactor = 100 / total;
				log.info("Divisionfactor: "+dvfactor);
				
				
				if(state == 1) {
					assigno *= (5 + rand.nextInt(5)) * dvfactor;
					log.info("Assignment mark: "+assigno);
					dbobj.firstMarkWrite(assigno,slno);
				}
				
				if(state == 2) {
					mdtno *= (5 + rand.nextInt(5)) * dvfactor;
					log.info("Midterm mark: "+mdtno);
					dbobj.secondMarkWrite(mdtno,slno);
				}
				
				if(state == 3) {
					pjtno *= (30 + rand.nextInt(30)) * dvfactor;
					feno *= (30 + rand.nextInt(30)) * dvfactor;
					log.info("Project mark: "+pjtno);
					log.info("Final exam mark: "+feno);
					dbobj.thirdMarkWrite(pjtno,feno,slno);
				}
				
			}
		}
}