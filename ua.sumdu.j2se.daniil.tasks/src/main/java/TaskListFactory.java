public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes listType) {
        switch (listType) {
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
        }
        return null;
    }
}
