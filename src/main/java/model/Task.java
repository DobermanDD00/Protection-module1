package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
@AllArgsConstructor


public class Task {

    private int id;
    private String name;
    private String description;
    private int idLead;
    private int idPerformer;
    private byte[] signLead;
    private int idStatus;
    private String report;
    private byte[] signPerformer;


    public static void main(String[] args) {
        Task task = new Task(1, "f", "5", 55, 34, null, 333, "fdj", null);
        System.out.println("ddddddddddddddddddddddddddddddddd");
        System.out.println(task.getName());
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
    }

}
