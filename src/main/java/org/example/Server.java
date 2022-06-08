package org.example;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;


public class Server {

    static ArrayList<Car> car= new ArrayList<>();
    static int portNumber = 1234;
    static PrintWriter out;
    static Gson gson = new Gson();
    static String json;

    public static void main(String[] args) {

        System.out.println("Server Sarted");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println("socket failed");
            e.printStackTrace();
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Socket not accepted");
            e.printStackTrace();
        }

        System.out.println("Accepted");
        buildCarList();
        try {
            out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("failed");
            e.printStackTrace();
        }


        BufferedReader in = null;

        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            System.out.println("failed");
            e.printStackTrace();
        }

        String s = "";
        try {
            while ((s = in.readLine()) != null) {


                ControlMessage(s);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void ControlMessage(String s) {
        switch (s) {
            case "more_expensive":

                int index =checkExpensiveProduct();
                json = gson.toJson(car.get(index));
                out.println(json);

                break;
            case "all":

                json = gson.toJson(car);
                out.println(json);


                break;

            case "all_sorted":
                System.out.println("all_sorted");
                orderList();

                break;
            default:
                out.println("Command not found");
        }

    }

    private static void orderList() {
        ArrayList<Car> orderList=new ArrayList<>();
        for(Car c:car)
            orderList.add(c);

        Collections.sort(orderList);
        json = gson.toJson(orderList);
        out.println(json);

    }

    private static int checkExpensiveProduct() {
        double price=car.get(0).getPrice();
        int index=0;
        for(int i=0;i<car.size();i++) {
            if (price<car.get(i).getPrice())
            {
                price=car.get(i).getPrice();
                index=i;
            }
        }
        return index;
    }

    private static void buildCarList() {
        car.add(new Car(123,"bmw", 3594.9));
        car.add(new Car(3634,"audi", 38346.9));
        car.add(new Car(135,"ferrari", 130000.4));
        connectionDatabase();
    }

    private static void connectionDatabase() {

    }
}