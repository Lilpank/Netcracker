package Function;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
        try {
            out.write(getByteOfTasks(tasks));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (DataInputStream dataInputStream = new DataInputStream(in)) {
            while (dataInputStream.available() > 0) {
                int numberOfTasks = dataInputStream.readInt();
                String title = dataInputStream.readUTF();
                int lengthOfTheName = dataInputStream.readInt();
                int interval = dataInputStream.readInt();
                String[] executionTime = dataInputStream.readUTF().split("\\.");
                int year = Integer.parseInt(executionTime[0]);
                int month = Integer.parseInt(executionTime[1]);
                int dayOfMonth = Integer.parseInt(executionTime[2]);
                int hour = Integer.parseInt(executionTime[3]);
                int minute = Integer.parseInt(executionTime[4]);

                if (interval == 0) {
                    LocalDateTime dateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
                    tasks.add(new Task(title, dateTime));
                } else {
                    LocalDateTime dateStartTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);

                    executionTime = dataInputStream.readUTF().split("\\.");
                    year = Integer.parseInt(executionTime[0]);
                    month = Integer.parseInt(executionTime[1]);
                    dayOfMonth = Integer.parseInt(executionTime[2]);
                    hour = Integer.parseInt(executionTime[3]);
                    minute = Integer.parseInt(executionTime[4]);
                    LocalDateTime dateEndTIme = LocalDateTime.of(year, month, dayOfMonth, hour, minute);

                    tasks.add(new Task(title, dateStartTime, dateEndTIme, interval));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getByteOfTasks(AbstractTaskList tasks) throws IOException {
        if (tasks.size() == 0) {
            throw new IllegalArgumentException();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(byteArrayOutputStream);
        int id = 0;
        for (Task task : tasks) {
            //The number of tasks - Как я понял, номер таски, как id будет использоваться мб
            writer.append(Character.highSurrogate(id));

            writer.append(String.valueOf('\n')).append(task.getTitle()).append(String.valueOf('\n')) // Title
                    .append(task.getActive()) // Active 0/1
                    .append(Character.highSurrogate('\n' + Character.highSurrogate(task.getTitle().length()))) // length of the name
                    .append(Character.highSurrogate('\n' + Character.highSurrogate(task.getRepeatInterval()))) // interval
                    .append(task.getExecutionTime());
            ++id;
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            write(tasks, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            read(tasks, fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        for (Task task : tasks) {
            out.write(gson.toJson(task));
        }
        out.flush();
        out.close();
    }

    // Читаем из потока, формат JSON.
    public static void read(AbstractTaskList tasks, Reader in) {
        Gson gson = new GsonBuilder().create();

        JsonStreamParser jsonStreamParser = new JsonStreamParser(in);

        while (jsonStreamParser.hasNext()) {
            JsonElement temp = jsonStreamParser.next();
            if (temp.isJsonObject()) {
                tasks.add(gson.fromJson(temp, Task.class));
            }
        }
    }

    // Запись объекта Function.Task в формате JSON.
    public static void writeText(AbstractTaskList tasks, File file) {
        try (Writer writer = new FileWriter(file)) {
            ArrayList<Task> list = new ArrayList<>();
            for (Task task : tasks) {
                list.add(task);
            }
            writer.write(new Gson().toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Чтение объекта из файла в формате JSON.
    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file.getPath()), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Task> arrayList = new Gson().fromJson(contentBuilder.toString(), new TypeToken<List<Task>>() {
        }.getType());

        for (Task task : arrayList) {
            tasks.add(task);
        }
    }
}
