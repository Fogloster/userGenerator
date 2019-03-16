import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Builder {

    public ArrayList<String> arrayFromFile(String FileName) throws IOException {
        ArrayList myArrayList = new ArrayList();
        File file = getFile(FileName);


        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                myArrayList.add(line);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
            myArrayList.add(st);

        return myArrayList;
    }


    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private String INNGenerator(){

        int numbers[] = new int[12];

        numbers[0] = 7;
        numbers[1] = 7;

        for ( int j = 2; j < 10; j++){
            numbers[j] = getRandomNumberInRange(0,9);
        }

        numbers[10] = ((numbers[0]*7+numbers[1]*2+numbers[2]*4+numbers[3]*10+numbers[4]*3 +numbers[5]*5+numbers[6]*9+numbers[7]*4+numbers[8]*6+numbers[9]*8)%11)%10;
        numbers[11] = ((numbers[0]*3+numbers[1]*7+numbers[2]*2+numbers[3]*4 +numbers[4]*10+numbers[5]*3+numbers[6]*5+numbers[7]*9+numbers[8]*4+numbers[9]*6+numbers[10]*8)%11)%10;

        String result = "";

        for (int i = 0; i < numbers.length; i++){
            result += Integer.toString(numbers[i]);
        }

        return result;
    }


    private File getFile(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file;
    }


    public void generateData() throws IOException {
        ArrayList<String> nameMale = arrayFromFile("nameMale.txt");
        ArrayList<String> surnameMale = arrayFromFile("surnameMale.txt");
        ArrayList<String> lastnameMale = arrayFromFile("lastnameMale.txt");
        ArrayList<String> nameFemale = arrayFromFile("nameFemale.txt");
        ArrayList<String> surnameFemale = arrayFromFile("surnameFemale.txt");
        ArrayList<String> lastnameFemale = arrayFromFile("lastnameFemale.txt");
        ArrayList<String> flat = arrayFromFile("flat.txt");
        ArrayList<String> city = arrayFromFile("city.txt");
        ArrayList<String> region = arrayFromFile("region.txt");
        ArrayList<String> country = arrayFromFile("country.txt");
        ArrayList<String> street = arrayFromFile("street.txt");
        ArrayList<String> house = arrayFromFile("house.txt");


        try {
            String filename = "GeneratedData" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");

            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("Имя");
            rowhead.createCell(1).setCellValue("Фамилия");
            rowhead.createCell(2).setCellValue("Отчество");
            rowhead.createCell(3).setCellValue("Возраст");
            rowhead.createCell(4).setCellValue("Пол");
            rowhead.createCell(5).setCellValue("Дата рождения");
            rowhead.createCell(6).setCellValue("ИНН");
            rowhead.createCell(7).setCellValue("Почтовый Индекс");
            rowhead.createCell(8).setCellValue("Страна");
            rowhead.createCell(9).setCellValue("Область");
            rowhead.createCell(10).setCellValue("Город");
            rowhead.createCell(11).setCellValue("Улица");
            rowhead.createCell(12).setCellValue("Дом");
            rowhead.createCell(13).setCellValue("Квартира");

            int myValue = getRandomNumberInRange(1,30);
            DateGen dateGen = new DateGen();
            for (int i = 0; i < myValue; i++) {
                int sex = getRandomNumberInRange(0,1);


                HSSFRow row = sheet.createRow((short)(i + 2));
                if (sex == 0) {
                    row.createCell(0).setCellValue(nameMale.get(getRandomNumberInRange(0, nameMale.size()-1)));
                    row.createCell(1).setCellValue(surnameMale.get(getRandomNumberInRange(0, surnameMale.size()-1)));
                    row.createCell(2).setCellValue(lastnameMale.get(getRandomNumberInRange(0, lastnameMale.size()-1)));
                    row.createCell(4).setCellValue("М");
                } else {
                    row.createCell(0).setCellValue(nameFemale.get(getRandomNumberInRange(0, nameFemale.size()-1)));
                    row.createCell(1).setCellValue(surnameFemale.get(getRandomNumberInRange(0, surnameFemale.size()-1)));
                    row.createCell(2).setCellValue(lastnameFemale.get(getRandomNumberInRange(0, lastnameFemale.size()-1)));
                    row.createCell(4).setCellValue("Ж");
                }

                LocalDate birthDay = dateGen.randomBirthday(1950,2000);

                row.createCell(3).setCellValue(dateGen.getAge(birthDay));

                row.createCell(5).setCellValue(dateGen.formatDate(birthDay));
                row.createCell(6).setCellValue(INNGenerator());
                row.createCell(7).setCellValue(getRandomNumberInRange(100000, 200000));

                row.createCell(8).setCellValue(country.get(getRandomNumberInRange(0, country.size()-1)));
                row.createCell(9).setCellValue(region.get(getRandomNumberInRange(0, region.size()-1)));
                row.createCell(10).setCellValue(city.get(getRandomNumberInRange(0, city.size()-1)));
                row.createCell(11).setCellValue(street.get(getRandomNumberInRange(0, street.size()-1)));
                row.createCell(12).setCellValue(house.get(getRandomNumberInRange(0, house.size()-1)));
                row.createCell(13).setCellValue(flat.get(getRandomNumberInRange(0, flat.size()-1)));

            }


            FileOutputStream fileOut = new FileOutputStream(filename + ".xls");
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Файл создан. Путь: " + System.getProperty("user.dir") + "\\" + filename + ".xls");


            Document document = new Document(PageSize.A4.rotate());

            try {
                PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));

                BaseFont bf = BaseFont.createFont(System.getProperty("user.dir") + "//arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font font = new Font(bf);
                document.open();

                PdfPTable table = new PdfPTable(14);
                table.setWidthPercentage(100);

                table.addCell(new PdfPCell(new Paragraph("Имя", font)));
                table.addCell(new PdfPCell(new Paragraph("Фамилия", font)));
                table.addCell(new PdfPCell(new Paragraph("Отчество", font)));
                table.addCell(new PdfPCell(new Paragraph("Возраст", font)));
                table.addCell(new PdfPCell(new Paragraph("Пол", font)));
                table.addCell(new PdfPCell(new Paragraph("Дата рождения", font)));
                table.addCell(new PdfPCell(new Paragraph("ИНН", font)));
                table.addCell(new PdfPCell(new Paragraph("Почтовый индекс", font)));
                table.addCell(new PdfPCell(new Paragraph("Страна", font)));
                table.addCell(new PdfPCell(new Paragraph("Область", font)));
                table.addCell(new PdfPCell(new Paragraph("Город", font)));
                table.addCell(new PdfPCell(new Paragraph("Улица", font)));
                table.addCell(new PdfPCell(new Paragraph("Дом", font)));
                table.addCell(new PdfPCell(new Paragraph("Квартира", font)));

                for (int i = 0; i < myValue; i++) {
                    int sex = getRandomNumberInRange(0,1);
                    LocalDate birthDay = dateGen.randomBirthday(1950,2000);

                    if (sex == 0) {

                        table.addCell(new PdfPCell(new Paragraph(nameMale.get(getRandomNumberInRange(0, nameMale.size()-1)), font)));
                        table.addCell(new PdfPCell(new Paragraph(surnameMale.get(getRandomNumberInRange(0, surnameMale.size()-1)), font)));
                        table.addCell(new PdfPCell(new Paragraph(lastnameMale.get(getRandomNumberInRange(0, lastnameMale.size()-1)), font)));
                        table.addCell(new PdfPCell(new Paragraph(String.valueOf(dateGen.getAge(birthDay)), font)));
                        table.addCell(new PdfPCell(new Paragraph("М", font)));


                    } else {

                        table.addCell(new PdfPCell(new Paragraph(nameFemale.get(getRandomNumberInRange(0, nameFemale.size()-1)), font)));
                        table.addCell(new PdfPCell(new Paragraph(surnameFemale.get(getRandomNumberInRange(0, surnameFemale.size()-1)), font)));
                        table.addCell(new PdfPCell(new Paragraph(lastnameFemale.get(getRandomNumberInRange(0, lastnameFemale.size()-1)), font)));
                        table.addCell(new PdfPCell(new Paragraph(String.valueOf(dateGen.getAge(birthDay)), font)));
                        table.addCell(new PdfPCell(new Paragraph("Ж", font)));


                    }

                    table.addCell(new PdfPCell(new Paragraph(dateGen.formatDate(birthDay), font)));
                    table.addCell(new PdfPCell(new Paragraph(INNGenerator(), font)));
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(getRandomNumberInRange(100000, 200000)), font)));
                    table.addCell(new PdfPCell(new Paragraph(country.get(getRandomNumberInRange(0, country.size()-1)), font)));
                    table.addCell(new PdfPCell(new Paragraph(region.get(getRandomNumberInRange(0, region.size()-1)), font)));
                    table.addCell(new PdfPCell(new Paragraph(city.get(getRandomNumberInRange(0, city.size()-1)), font)));
                    table.addCell(new PdfPCell(new Paragraph(street.get(getRandomNumberInRange(0, street.size()-1)), font)));
                    table.addCell(new PdfPCell(new Paragraph(house.get(getRandomNumberInRange(0, house.size()-1)), font)));
                    table.addCell(new PdfPCell(new Paragraph(flat.get(getRandomNumberInRange(0, flat.size()-1)), font)));

                }

                document.add(table);

                document.close();

                System.out.println("Файл создан. Путь: " + System.getProperty("user.dir") + "\\" + filename+ ".pdf");

            } catch(Exception e){

            }

        } catch ( Exception ex ) {
            System.out.println(ex);
        }
    }

}
