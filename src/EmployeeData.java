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

    public EmployeeData(long empId, long projectId, Date dateFrom, Date dateTo) throws DateToBeforeDateFromException {
        if(dateFrom.before(dateFrom)){
            throw new DateToBeforeDateFromException("%1$s is not before %2$s".formatted(dateTo, dateFrom));
        }
        this.empId = empId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
