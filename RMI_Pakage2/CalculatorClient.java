package RMI_Pakage2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Calculator calculator = (Calculator) registry.lookup("CalculatorService");

            // Adding two numbers
            int result = calculator.add(5, 3);
            System.out.println("Result of 5 + 3: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}