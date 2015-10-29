import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ReadFile {

	private String fileLocation;

	ArrayList<String> myArrayWithStrings = new ArrayList<String>();

	private BigDecimal SumaPLN= BigDecimal.ZERO;
	private BigDecimal SumaGrosz= BigDecimal.ZERO;
	private BigDecimal SumaGroszToPLN= BigDecimal.ZERO;
	private BigDecimal sumaTotal= BigDecimal.ZERO;


	ArrayList<String> myArrayWithUglyPrices = new ArrayList<String>(); // 21,43PLN

	ArrayList<String> myArrayWithPLN = new ArrayList<String>();
	ArrayList<String> myArrayWithGrosz = new ArrayList<String>();

	public ReadFile(String fileLocation) {

		this.fileLocation = fileLocation;

		readTheFile();
		sumUp();
		convert();

	}

	private void readTheFile() {
		

		try {
			File file = new File(fileLocation);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				
				if(line.length()>1){
					int currentIndexOfThePrice = 0;
					stringBuffer.append(line);
					stringBuffer.append("\n");
					currentIndexOfThePrice = line.indexOf("amount");

					myArrayWithUglyPrices.add(line.substring(currentIndexOfThePrice + 7));
					myArrayWithStrings.add(line);
					
				}
			}
			fileReader.close();

			for (String s : myArrayWithUglyPrices) {

				int indexOfComa = 0;

				indexOfComa = s.indexOf(",");

				// System.out.println(s.substring(0,indexOfComa));
				myArrayWithPLN.add(s.substring(0, indexOfComa)); // Array with
																	// PLN !!
																	// 12,12,14...

				myArrayWithGrosz.add(s.substring(indexOfComa + 1, s.length() - 3));

				s = s.substring(s.length() - 3, s.length()); // PLN

			}


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void sumUp() {

		for (String PLN : myArrayWithPLN) {

			BigDecimal bd=new BigDecimal(PLN);
			SumaPLN = SumaPLN.add(bd);
			//System.out.println("SumaPLN: "+ SumaPLN);

		}

		for (String Grosz : myArrayWithGrosz) {
			BigDecimal bd=new BigDecimal(Grosz);
			
			SumaGrosz = SumaGrosz.add(bd);
		//	System.out.println("SumaGrosz = " + SumaGrosz);
		}
	}
	
	private void convert(){
		BigDecimal bg2 = new BigDecimal("100");
		SumaGroszToPLN = SumaGrosz.divide(bg2,2, RoundingMode.CEILING);
		//System.out.println("SumaGroszToPLN " +SumaGroszToPLN);
		
		sumaTotal = SumaPLN.add(SumaGroszToPLN);
		System.out.println("Wynik w PLN to:");
		System.out.println(sumaTotal + " PLN");

	}


}
