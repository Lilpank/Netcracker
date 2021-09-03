public class TaskListFactory implements AbstractFactory {
    public static AbstractTaskList createTaskList(ListTypes listType) {
        if ("linked".equals(listType.getType())) {
            return new LinkedTaskList();
        } else if ("array".equals(listType.getType())) {
            return new ArrayTaskList();
        }
        return null;
    }
}
