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

    public void updateIfLongerWorkedTogetherOnProject(EmployeeData employeeData1, EmployeeData employeeData2){
        long days = calculateDay(employeeData1, employeeData2);
        if(days > employeesProjectPair.getDays()){
            updatePair(employeeData1, employeeData2, days);
        }

    }

    private void updatePair(EmployeeData employeeData1, EmployeeData employeeData2, long days) {
        this.employeesProjectPair = new EmployeesProjectPair(
                employeeData1.getEmpId(),
                employeeData2.getEmpId(),
                employeeData1.getProjectId(),
                days);
    }

    private long calculateDay(EmployeeData employeeData1, EmployeeData employeeData2) {
        return EmployeesProjectPair.calculateDay(employeeData1, employeeData2);

    }
}
