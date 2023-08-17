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

    /**
     * Put the provided argument in the bucket with key value employeeData.projectId
     * @param employeeData - the new object to put in the map
     * @return - true is successfully added, else false
     */
    public boolean addEmployeeProject(EmployeeData employeeData){
        if(!mapProjectIdEmployee.containsKey(employeeData.getProjectId())){
            mapProjectIdEmployee.put(employeeData.getProjectId(), new ArrayList<>());
        }
        return mapProjectIdEmployee.get(employeeData.getProjectId()).add(employeeData);
    }

    /**
     * Prints in a grid all projects the longest project worked pair of employees have been working on together
     */
    public void getProjectsOfTheLongestPair(){
        if(employeesProjectPair == null){
            getLongestEmployeeProjectCollaboration();
        }
        System.out.println("Employee ID #1, Employee ID #2, Project ID, Days worked");
        //Finds all project ids the longest worked together pair has worked on it
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

    /**
     * Computes the longest worked on the same project pairs
     * @return - the pair with the longest worked period on the same project
     */
    public LongestEmployeeProjectCollaboration getLongestEmployeeProjectCollaboration() {
        List<LongestEmployeeProjectCollaboration> longestEmployeeProjectCollaborations = new ArrayList<>();

        //Iterates over all key and values in the map
        for(List<EmployeeData> employeeDataList: this.mapProjectIdEmployee.values()){
            //calculate the longest period for each key and its values
            longestEmployeeProjectCollaborations.add(computeLongestEmployeeCollaboration(employeeDataList));
        }

        //finds the project with the employees worked longest together on a project
        LongestEmployeeProjectCollaboration longestEmployeeProjectCollaboration = longestEmployeeProjectCollaborations
                .stream()
                .max(Comparator.comparingLong(LongestEmployeeProjectCollaboration::getDays))
                .orElse(null);
        assert longestEmployeeProjectCollaboration != null;
        employeesProjectPair = longestEmployeeProjectCollaboration.getEmployeesProjectPair();
        return longestEmployeeProjectCollaboration;
    }

    /**
     * Iterate over all projects with the same id
     * @param employeeDataList - list of projects with the same id with employee information
     * @return - the pair of employees worked longest on it
     */
    private LongestEmployeeProjectCollaboration computeLongestEmployeeCollaboration(List<EmployeeData> employeeDataList) {

        EmployeeData[] employeeDataArray = employeeDataList.toArray(new EmployeeData[0]);
        LongestEmployeeProjectCollaboration longestEmployeeProjectCollaboration = new LongestEmployeeProjectCollaboration();
        //Iterates each employee with every other
        //Update the LongestEmployeeProjectCollaboration if the new pair of employees
        //worked together mre days than the current one
        for(int i = 0; i < employeeDataArray.length; i++){
            for(int y = i+1; y< employeeDataArray.length; y++){
                longestEmployeeProjectCollaboration.updateIfLongerWorkedTogetherOnProject(employeeDataArray[i], employeeDataArray[y]);
            }
        }
        return longestEmployeeProjectCollaboration;
    }
}
