import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Currency> currencies = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCurrencies();
        runMenu();
    }

    private static void initializeCurrencies() {
        currencies.add(new Currency("MXN", "Peso Mexicano"));
        currencies.add(new Currency("USD", "Dólar Estadounidense"));
        currencies.add(new Currency("ARS", "Peso Argentino"));
        currencies.add(new Currency("COP", "Peso Colombiano"));
        currencies.add(new Currency("EUR", "Euro"));
        currencies.add(new Currency("GBP", "Libra Esterlina"));
    }

    private static void runMenu() {
        while(true){
            System.out.println("\n=== Conversor de Monedas ===");
            System.out.println("Selecciona la moneda de origen:");
            for(int i = 0; i < currencies.size(); i++){
                System.out.println((i+1) + ". " + currencies.get(i).getName() + " (" + currencies.get(i).getCode() + ")");
            }
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            int fromChoice = getUserChoice(currencies.size());
            if(fromChoice == 0){
                System.out.println("¡Hasta luego!");
                break;
            }
            Currency fromCurrency = currencies.get(fromChoice - 1);

            System.out.println("\nSelecciona la moneda de destino:");
            for(int i = 0; i < currencies.size(); i++){
                System.out.println((i+1) + ". " + currencies.get(i).getName() + " (" + currencies.get(i).getCode() + ")");
            }
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            int toChoice = getUserChoice(currencies.size());
            if(toChoice == 0){
                System.out.println("¡Hasta luego!");
                break;
            }
            Currency toCurrency = currencies.get(toChoice - 1);

            System.out.print("\nIngresa la cantidad a convertir: ");
            double amount = getUserAmount();

            CurrencyConverter converter = new CurrencyConverter(fromCurrency.getCode());
            double result = converter.convert(fromCurrency.getCode(), toCurrency.getCode(), amount);

            if(result != -1){
                System.out.printf("\n%.2f %s = %.2f %s\n", amount, fromCurrency.getCode(), result, toCurrency.getCode());
            }
        }
    }

    private static int getUserChoice(int maxOption){
        int choice = -1;
        while(true){
            try{
                choice = Integer.parseInt(scanner.nextLine());
                if(choice >= 0 && choice <= maxOption){
                    break;
                } else {
                    System.out.print("Por favor, ingresa una opción válida: ");
                }
            } catch(NumberFormatException e){
                System.out.print("Entrada inválida. Por favor, ingresa un número: ");
            }
        }
        return choice;
    }

    private static double getUserAmount(){
        double amount = -1;
        while(true){
            try{
                amount = Double.parseDouble(scanner.nextLine());
                if(amount >= 0){
                    break;
                } else {
                    System.out.print("La cantidad debe ser positiva. Ingresa nuevamente: ");
                }
            } catch(NumberFormatException e){
                System.out.print("Entrada inválida. Por favor, ingresa un número: ");
            }
        }
        return amount;
    }
}