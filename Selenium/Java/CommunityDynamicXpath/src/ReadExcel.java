import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 
 */

/**
 * @author Lee Shoham
 * @date Jul 26, 2016
 */
public class ReadExcel {

	private String inputFile;

	public ReadExcel(String input) {
		this.inputFile = input;
	}

	public Sheet getSheet(int sheetNum) throws Exception {
		File inputWorkbook = new File(inputFile);
	    Workbook w;
	    try {
	      w = Workbook.getWorkbook(inputWorkbook);
	      // Get the first sheet
	      Sheet sheet = w.getSheet(sheetNum);
	      
	      return sheet;
	     
	    } catch (BiffException | IOException e) {
	      e.printStackTrace();
	      throw e;
	    }
	}
	// this is a way of reading a .txt file
	// BufferedReader reader = new BufferedReader(new
	// FileReader("table.txt"));
	// String line = reader.readLine();
	// String[] lineStrArr;
	// while ((line = reader.readLine()) != null) {
	//
	// // splitting String line into string array without whitespaces
	// lineStrArr = line.split("\\s+");
	//
	// String xpath = "(//input[@id='lotto_playslip_line_" +
	// lineStrArr[1] + "_pool_0_col_" + lineStrArr[0]
	// + "'])[1]";
	// driver.findElement(By.xpath(xpath)).sendKeys(lineStrArr[2]);
	// }
	// reader.close();

	// this is a way of reading an .xls file
//	ReadExcel readExcel = new ReadExcel("codebeautify.xls");
//	Sheet sheet = readExcel.getSheet(0);
//	System.out.println("Num of rows is: " + sheet.getRows());
//	System.out.println("Num of cols is: " + sheet.getColumns());
	
//	for (int i = 1; i < sheet.getRows(); i++) {
//		String xpath = "(//input[@id='lotto_playslip_line_" + sheet.getCell(1, i).getContents() + "_pool_0_col_"
//				+ sheet.getCell(0, i).getContents() + "'])[1]";
//		driver.findElement(By.xpath(xpath)).sendKeys(sheet.getCell(2, i).getContents());
//	}

}
