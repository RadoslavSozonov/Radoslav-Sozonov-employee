import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class EmployeeProjectManager {
    private Map<Long, List<EmployeeData>> mapProjectIdEmployee;
    private EmployeesProjectPair employeesProjectPair;

    public EmployeeProjectManager(){
        mapProjectIdEmployee = new HashMap<>();
    }

    public boolean addEmployeeProject(EmployeeData employeeData){
        if(!mapProjectIdEmployee.containsKey(employeeData.getProjectId())){
            mapProjectIdEmployee.put(employeeData.getProjectId(), new ArrayList<>());
        }
        return mapProjectIdEmployee.get(employeeData.getProjectId()).add(employeeData);
    }

    public void getProjectsOfTheLongestPair(){
        if(employeesProjectPair == null){
            getLongestEmployeeProjectCollaboration();
        }
        System.out.println("Employee ID #1, Employee ID #2, Project ID, Days worked");
        for(Map.Entry<Long, List<EmployeeData>> entry: this.mapProjectIdEmployee.entrySet()){
            List<EmployeeData> commonProject = entry
                    .getValue()
                    .stream()
                    .filter(
                            x ->
                                    x.getEmpId() == employeesProjectPair.getEmployee1() ||
                                            x.getEmpId() == employeesProjectPair.getEmployee2())
                    .toList();
            if(commonProject.size() == 2) {
                Long days = EmployeesProjectPair.calculateDay(commonProject.get(0), commonProject.get(1));
                System.out.println("%1$s, %2$s, %3$s, %4$s"
                        .formatted(
                                commonProject.get(0).getEmpId(),
                                commonProject.get(1).getEmpId(),
                                entry.getKey(),
                                days
                                ));
            }
        }

    }

    public LongestEmployeeProjectCollaboration getLongestEmployeeProjectCollaboration() {
        List<LongestEmployeeProjectCollaboration> longestEmployeeProjectCollaborations = new ArrayList<>();
        for(List<EmployeeData> employeeDataList: this.mapProjectIdEmployee.values()){
            longestEmployeeProjectCollaborations.add(computeLongestEmployeeCollaboration(employeeDataList));
        }
        LongestEmployeeProjectCollaboration longestEmployeeProjectCollaboration = longestEmployeeProjectCollaborations
                .stream()
                .max(Comparator.comparingLong(LongestEmployeeProjectCollaboration::getDays))
                .orElse(null);
        assert longestEmployeeProjectCollaboration != null;
        employeesProjectPair = longestEmployeeProjectCollaboration.getEmployeesProjectPair();
        return longestEmployeeProjectCollaboration;
    }

    private LongestEmployeeProjectCollaboration computeLongestEmployeeCollaboration(List<EmployeeData> employeeDataList) {
        EmployeeData[] employeeDataArray = employeeDataList.toArray(new EmployeeData[0]);
        LongestEmployeeProjectCollaboration longestEmployeeProjectCollaboration = new LongestEmployeeProjectCollaboration();
        for(int i = 0; i < employeeDataArray.length; i++){
            for(int y = i+1; y< employeeDataArray.length; y++){
                longestEmployeeProjectCollaboration.updateIfLongerWorkedTogetherOnProject(employeeDataArray[i], employeeDataArray[y]);
            }
        }
        return longestEmployeeProjectCollaboration;
    }
}
