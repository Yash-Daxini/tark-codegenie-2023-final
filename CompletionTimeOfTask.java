import java.util.*;

//Task class which contains all details of class
class Task {
    String taskName;
    int time;
    List<String> dependancy;

    Task(String taskName, int time, List<String> dependancy) {
        this.taskName = taskName;
        this.time = time;
        this.dependancy = dependancy;
    }

    public String toString() {
        return this.taskName + " " + this.time + " " + this.dependancy.toString();
    };

}

//Function which contains main method
class CompletionTimeOfTask {

    //List to store all tasks
    static List<Task> tasks = new ArrayList<>();

    //Hashmap to store taskname and it's total completion time
    static HashMap<String, Integer> taskAndCompetionTime = new HashMap<>();

    //Give whole task object from taskname
    static Task giveTaskObjFromName(String name) {
        for (Task task : tasks) {
            if (task.taskName.equals(name))
                return task;
        }
        return null;
    }

    //Take input
    static void takeInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of tasks:");
        int totalTasks = sc.nextInt();
        int i = 0;
        while (i++ < totalTasks) {
            System.out.print("Enter task and it's time in given format (Task1-10) : ");
            String details = sc.next();
            String[] detailsArray = details.split("-");
            System.out.print("Enter number of dependencies : ");
            int totaldependancies = sc.nextInt();
            if (totaldependancies != 0) {
                System.out.println("Enter Dependanceies Task Names For " + detailsArray[0] + " :");
            }
            List<String> dependency = new ArrayList<>();
            int j = 0;
            while (j++ < totaldependancies) {
                dependency.add(sc.next());
            }
            Task taskObj = new Task(detailsArray[0], Integer.valueOf(detailsArray[1]), dependency);
            tasks.add(taskObj);
        }
    }

    //Compute completion time according to dependancy
    static int computeCompletionTimeForGivenTask(Task task, int max) {
        for (String dependency : task.dependancy) {
            Task tempObj = giveTaskObjFromName(dependency);
            if (!taskAndCompetionTime.containsKey(dependency)) {
                callForCompute(tempObj);
            }
            max = Math.max(taskAndCompetionTime.get(tempObj.taskName), max);
        }
        return max;
    }

    //Call compute function and store value in hashmap
    static void callForCompute(Task task) {
        int x = computeCompletionTimeForGivenTask(task, 0);
        taskAndCompetionTime.put(task.taskName, x + task.time);
    }

    public static void main(String[] args) {

        takeInput();

        for (Task task : tasks) {
            if (!taskAndCompetionTime.containsKey(task))
                callForCompute(task);
        }

        int totalTimeToCompletetTasks = !taskAndCompetionTime.isEmpty() ? Collections.max(taskAndCompetionTime.values()) : 0;

        System.out.println("Time to complete all the task : " + totalTimeToCompletetTasks);
    }
}