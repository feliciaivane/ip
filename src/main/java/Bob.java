import Exceptions.*;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Bob {
    public static void main(String[] args){
        try {
            System.out.println("Howwwwwwdy! I'm Bob");
            System.out.println("What do you want?\n");

            String currDirectory = new File("").getAbsolutePath();
            File dataDirectory = new File(currDirectory + "/data");
            boolean directoryExists = dataDirectory.exists(); //Check for data directory
            if (!directoryExists) { //Make data directory if it does not exist yet
                dataDirectory.mkdir();
            }

            boolean fileExists = new File(dataDirectory + "/bob.txt").exists(); //Check for bob.txt
            if (!fileExists) { //Make bob.txt file if it does not exist yet
                new File(dataDirectory, "bob.txt").createNewFile();
            }

            Scanner scanner = new Scanner(System.in);
            TaskList taskList = new TaskList();
            taskList.initialiseTaskList();

            String response = scanner.nextLine();

            while (!Objects.equals(response, "bye")) {
                try {
                    inputChecker(response, taskList);

                    if (Objects.equals(response, "list")) { //show list of tasks
                        System.out.println(taskList.getList());
                        response = scanner.nextLine();
                    } else if (response.matches("done(.*)")) { //complete a task
                        String[] splitResponse = response.split(" ", 2);
                        System.out.println(taskList.markIndexCompleted(Integer.parseInt(splitResponse[1]) - 1));
                        response = scanner.nextLine();
                    } else if (response.matches("delete(.*)")) {
                        String[] splitResponse = response.split(" ", 2);
                        System.out.println(taskList.deleteIndex(Integer.parseInt(splitResponse[1]) - 1));
                        response = scanner.nextLine();
                    } else if (response.matches("todo(.*)") || response.matches("deadline(.*)") //add a new task
                            || response.matches("event(.*)")) {
                        String[] splitResponse = response.split(" ", 2);
                        Task newTask;

                        if (Objects.equals(splitResponse[0], "todo")) {
                            newTask = new Todo(splitResponse[1]);
                        } else if (Objects.equals(splitResponse[0], "deadline")) {
                            String[] splitAgain = splitResponse[1].split(" /by ", 2);
                            newTask = new Deadline(splitAgain[0], splitAgain[1]);
                        } else {
                            String[] splitAgain = splitResponse[1].split(" /at ", 2);
                            newTask = new Event(splitAgain[0], splitAgain[1]);
                        }

                        System.out.println(taskList.addTask(newTask)); //add a task
                        response = scanner.nextLine();
                    }
                } catch (InvalidInputException e) {
                    System.out.println("That doesn't make any sense! >:(\n");
                    response = scanner.nextLine();
                } catch (NoTaskException e) {
                    System.out.println("You didn't specify your task! >:(\n");
                    response = scanner.nextLine();
                } catch (NoDeadlineException e) {
                    System.out.println("When is the deadline? >:(\n");
                    response = scanner.nextLine();
                } catch (NoEventTimingException e) {
                    System.out.println("When is the event? >:(\n");
                    response = scanner.nextLine();
                } catch (OutOfBoundsException e) {
                    System.out.println("Huh what task is that :/\n");
                    response = scanner.nextLine();
                }
            }

            System.out.println("Bye! Shoo!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void inputChecker(String response, TaskList tasklist) throws InvalidInputException, NoTaskException,
            NoDeadlineException, NoEventTimingException, OutOfBoundsException{
        if (Objects.equals(response, "list")) {
            //correct input checker, do nothing
        } else if (response.matches("done(.*)") || response.matches("delete(.*)")) {
            String[] splitResponse = response.split(" ", 2);
            if (Integer.parseInt(splitResponse[1]) <= 0
                    || Integer.parseInt(splitResponse[1]) > Integer.parseInt(tasklist.noOfTasks())) {
                throw new OutOfBoundsException();
            }
        } else if (response.matches("todo(.*)")) {
            String[] splitResponse = response.split(" ", 2);
            if (splitResponse.length == 1) {
                throw new NoTaskException();
            }
        } else if (response.matches("deadline(.*)")) {
            String[] splitResponse = response.split(" ", 2);
            if (splitResponse.length == 1) {
                throw new NoTaskException();
            } else if (!response.contains("/by")) {
                throw new NoDeadlineException();
            }
        } else if (response.matches("event(.*)")) {
            String[] splitResponse = response.split(" ", 2);
            if (splitResponse.length == 1) {
                throw new NoTaskException();
            } else if (!response.contains("/at")) {
                throw new NoEventTimingException();
            }
        } else {
            throw new InvalidInputException();
        }
    }
}
