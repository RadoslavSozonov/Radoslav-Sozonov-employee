import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class LongestEmployeeProjectCollaboration {

    private EmployeesProjectPair employeesProjectPair;

    public long getDays(){
        return employeesProjectPair.getDays();
    }

    public LongestEmployeeProjectCollaboration(){
        this.employeesProjectPair = new EmployeesProjectPair();
        employeesProjectPair.setDays(0);
    }

    @Override
    public String toString(){
        return employeesProjectPair.toString();
    }

    /**
     * Update the current pair if a longer period is calculated
     * @param employeeData1
     * @param employeeData2
     */
    public void updateIfLongerWorkedTogetherOnProject(EmployeeData employeeData1, EmployeeData employeeData2){
        long days = calculateDay(employeeData1, employeeData2);
        if(days > employeesProjectPair.getDays()){
            updatePair(employeeData1, employeeData2, days);
        }

    }

    /**
     * Update the EmployeesProjectPair with the two employees, days and project worked the longest together
     * @param employeeData1
     * @param employeeData2
     * @param days
     */
    private void updatePair(EmployeeData employeeData1, EmployeeData employeeData2, long days) {
        this.employeesProjectPair = new EmployeesProjectPair(
                employeeData1.getEmpId(),
                employeeData2.getEmpId(),
                employeeData1.getProjectId(),
                days);
    }

    /**
     * Calculates the period of overlapping days
     * @param employeeData1
     * @param employeeData2
     * @return
     */
    private long calculateDay(EmployeeData employeeData1, EmployeeData employeeData2) {
        return EmployeesProjectPair.calculateDay(employeeData1, employeeData2);

    }
}
