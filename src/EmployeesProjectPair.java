import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesProjectPair {
    private long employee1;
    private long employee2;
    private long projectID;
    private long days;

    @Override
    public String toString(){
        if(employee1 == 0){
            return "No pair found";
        }
        return String.format("%1$s, %2$s, %3$s", employee1, employee2, projectID);
    }

    /**
     * Calculates the period of overlapping days
     * @param employeeData1
     * @param employeeData2
     * @return - the period of days
     */
    public static long calculateDay(EmployeeData employeeData1, EmployeeData employeeData2) {
        Date period1Start = employeeData1.getDateFrom();
        Date period1End = employeeData1.getDateTo();

        Date period2Start = employeeData2.getDateFrom();
        Date period2End = employeeData2.getDateTo();

        //check if the two time periods overlap
        if(!(period1Start.before(period2End) && period2Start.before(period1End))){
            return 0;
        }

        Date overlappingStart = period1Start.before(period2Start) ? period2Start : period1Start;
        Date overlappingEnd = period1End.before(period2End) ? period1End : period2End;

        // Calculate the number of overlapping days
        return ChronoUnit.DAYS.between(
                dateToLocalDateConverter(overlappingStart),
                dateToLocalDateConverter(overlappingEnd)
        ) + 1;
    }

    /**
     * Convert Date type to LocalDate type
     * @param dateToConvert - the date to convert
     * @return - the converted date to localdate type
     */
    private static LocalDate dateToLocalDateConverter(Date dateToConvert){
        return LocalDate.parse(dateToConvert.toString(), DateTimeFormatter.ofPattern("[yyyy-MM-dd]"));
    }
}
