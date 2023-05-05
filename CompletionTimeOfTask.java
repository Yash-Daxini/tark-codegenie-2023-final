import java.util.*;

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

class CompletionTimeOfTask {
    static List<Task> tasks = new ArrayList<>();
    static HashMap<String, Integer> taskAndCompetionTime = new HashMap<>();

    static Task giveTaskObjFromName(String name) {
        for (Task task : tasks) {
            if (task.taskName.equals(name))
                return task;
        }
        return null;
    }

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

    static int computeCompletionTimeForGivenTask(Task task, int max) {
        System.out.println("Call for the " + task.taskName);
        for (String dependency : task.dependancy) {
            Task tempObj = giveTaskObjFromName(dependency);
            System.out.println("Call for the " + task.taskName + " dependancy on " + tempObj.taskName);
            if (!taskAndCompetionTime.containsKey(dependency)) {
                callForCompute(tempObj);
                max = Math.max(tempObj.time, computeCompletionTimeForGivenTask(tempObj, max));
            } else {
                max = Math.max(taskAndCompetionTime.get(tempObj.taskName), max);
            }
        }
        return max;
    }

    static void callForCompute(Task task) {
        if (taskAndCompetionTime.containsKey(task.taskName)) {
            return;
        } else {
            int x = computeCompletionTimeForGivenTask(task, 0);
            taskAndCompetionTime.put(task.taskName, x + task.time);
        }
    }

    public static void main(String[] args) {

        takeInput();

        for (Task task : tasks) {
            callForCompute(task);
        }

        System.out.println(taskAndCompetionTime);
        int totalTimeToCompletetTasks = !taskAndCompetionTime.isEmpty() ? Collections.max(taskAndCompetionTime.values())
                : 0;
        System.out.println("Time to complete all the task : " + totalTimeToCompletetTasks);
    }
}