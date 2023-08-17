import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileLocation = scanner.nextLine();
        EmployeeProjectManager employeeProjectManager = null;
        LongestEmployeeProjectCollaboration longestEmployeeProjectCollaboration;
        try {
            employeeProjectManager = parseCSVData(fileLocation);
            if(employeeProjectManager.getMapProjectIdEmployee().isEmpty()){
                System.out.println("No Data Provided");
                return;
            }
            longestEmployeeProjectCollaboration = employeeProjectManager.getLongestEmployeeProjectCollaboration();
            System.out.println(longestEmployeeProjectCollaboration.toString());
            employeeProjectManager.getProjectsOfTheLongestPair();
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    public static EmployeeProjectManager parseCSVData(String fileLocation) {

        EmployeeProjectManager employeeProjectManager = new EmployeeProjectManager();
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if(!isNumeric(values[0]) || !isNumeric(values[1])){
                    continue;
                }

                EmployeeData employeeData = new EmployeeData(
                  Long.parseLong(cleanString(values[0])),
                  Long.parseLong(cleanString(values[1])),
                  convertDate(values[2]),
                  values.length == 3 || values[3]==null || values[3].toUpperCase().contains("NULL")? Date.valueOf(LocalDate.now()) : convertDate(values[3])
                );
                employeeProjectManager.addEmployeeProject(employeeData);
            }
        } catch (IOException | DataFormatException | ArrayIndexOutOfBoundsException | NullPointerException | NumberFormatException | DateTimeParseException | DateToBeforeDateFromException e) {
            e.printStackTrace();
        }
        return employeeProjectManager;
    }

    public static Date convertDate(String date) throws DataFormatException {
        date = date.trim();
        String datePattern = formatCreator(date);
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern(datePattern));
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterBuilder.toFormatter(Locale.ENGLISH);
        return Date.valueOf(LocalDate.parse(date, dateTimeFormatter));
    }

    public static String formatCreator(String date) throws DataFormatException {

        if(date.contains("-")){
            return formatBuilder("-", date.split("-"));
        }
        if(date.contains("/")){
            return formatBuilder("/", date.split("/"));
        }
        if(date.contains(".")){
            return formatBuilder(".", date.split("\\."));
        }
        if(date.contains(" ")){
            return formatBuilder(" ", date.split(" "));
        }
        throw new DataFormatException("Not in Existence Date Format Provided for value: " + date);
    }

    public static String formatBuilder(String delimiter, String[] values){
        StringBuilder format = new StringBuilder();

        if(values[0].length() == 4){
            format.append("yyyy");
            format.append(delimiter);
            for(int i = 0; i<values[1].length();i++){
                if(i < 4){
                    format.append("M");
                }
            }
            format.append(delimiter);
            for(int i = 0; i<values[2].length();i++){
                format.append("d");
            }

        } else {
            for(int i = 0; i<values[0].length();i++){
                format.append("d");
            }
            format.append(delimiter);
            for(int i = 0; i<values[1].length();i++){
                if(i < 4){
                    format.append("M");
                }
            }
            format.append(delimiter);
            format.append("yyyy");
        }

        return format.toString();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Long d = Long.parseLong(cleanString(strNum).toString());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String cleanString(String str){
        StringBuilder newStr = new StringBuilder();
        for(byte byteValue: str.trim().getBytes(StandardCharsets.UTF_8)){
            if(byteValue > 0){
                newStr.append(Character.toString(byteValue));
            }
        }
        return newStr.toString();
    }
}
