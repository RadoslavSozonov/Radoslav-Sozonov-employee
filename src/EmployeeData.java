import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmployeeData {
    private long empId;
    private long projectId;
    private Date dateFrom;
    private Date dateTo;

    /**
     * If the dateTo is before dateFrom throw error
     * @param empId
     * @param projectId
     * @param dateFrom
     * @param dateTo
     * @throws DateToBeforeDateFromException
     */
    public EmployeeData(long empId, long projectId, Date dateFrom, Date dateTo) throws DateToBeforeDateFromException {
        if(dateTo.before(dateFrom)){
            throw new DateToBeforeDateFromException("DateTo %1$s is before DateFrom %2$s".formatted(dateTo, dateFrom));
        }
        this.empId = empId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
