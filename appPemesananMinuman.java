import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

// Kelas abstrak untuk merepresentasikan sebuah minuman
abstract class Beverage {
    protected String name;
    protected int price;

    public Beverage(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public abstract void displayMenu();

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

// Kelas turunan untuk merepresentasikan minuman kopi
class Coffee extends Beverage {
    public Coffee(String name, int price) {
        super(name, price);
    }

    @Override
    public void displayMenu() {
        System.out.println(name + " (Rp" + price + ")");
    }
}

// Kelas turunan untuk merepresentasikan minuman teh
class Tea extends Beverage {
    public Tea(String name, int price) {
        super(name, price);
    }

    @Override
    public void displayMenu() {
        System.out.println(name + " (Rp" + price + ")");
    }
}

package drinkshop; 

class DrinkShop {
    private Map<String, List<Beverage>> menu;
    private Map<String, Integer> orderCounts;
    private LocalDate orderDate;

    public DrinkShop() {
        menu = new HashMap<>();
        orderCounts = new HashMap<>();
        orderDate = LocalDate.now();
    }

    public void addBeverage(String category, Beverage beverage) {
        List<Beverage> categoryMenu = menu.getOrDefault(category, new ArrayList<>());
        categoryMenu.add(beverage);
        menu.put(category, categoryMenu);
    }

    public void displayMenu() {
        System.out.println("-------------------------------------------------");
        System.out.println("--                                             --");
        System.out.println("--        Selamat datang di DrinkShop!         --");
        System.out.println("--                                             --");
        System.out.println("-------------------------------------------------");

        System.out.println("\n================================================");
        System.out.println("==               Menu DrinkShop               ==");

        for (Map.Entry<String, List<Beverage>> entry : menu.entrySet()) {
            String category = entry.getKey();
            List<Beverage> categoryMenu = entry.getValue();

            System.out.println("==                  Menu " + category + "                 ==");
            for (int i = 0; i < categoryMenu.size(); i++) {
                Beverage beverage = categoryMenu.get(i);
                System.out.println("==          " + (i + 1) + ". " + beverage.getName() + " (Rp" + beverage.getPrice()
                        + ")" + "          ==");
            }
        }
        System.out.println("================================================\n");
    }

    public void placeOrder(String beverageName) {
        Beverage beverage = findBeverage(beverageName);
        if (beverage != null) {
            int count = orderCounts.getOrDefault(beverageName, 0) + 1;
            orderCounts.put(beverageName, count);
            System.out.println("  Anda telah memesan " + beverage.getName() + "dengan harga Rp" + beverage.getPrice());
        } else {
            System.out.println("  Maaf, minuman tidak ditemukan dalam menu kami.");
        }
    }

    private Beverage findBeverage(String beverageName) {
        for (List<Beverage> categoryMenu : menu.values()) {
            for (Beverage beverage : categoryMenu) {
                if (beverage.getName().trim().equalsIgnoreCase(beverageName)) {
                    return beverage;
                }
            }
        }
        return null;
    }

    public int calculateTotalPayment() {
        int total = 0;
        for (Map.Entry<String, Integer> entry : orderCounts.entrySet()) {
            String beverageName = entry.getKey();
            int count = entry.getValue();
            Beverage beverage = findBeverage(beverageName);
            if (beverage != null) {
                total += beverage.getPrice() * count;
            }
        }
        return total;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public int getOrderCount() {
        return orderCounts.size();
    }
}

public class appPemesananMinuman {
    public static void main(String[] args) {
        // Membuat objek drinkShop
        DrinkShop drinkShop = new DrinkShop();

        // Menambahkan minuman ke menu
        drinkShop.addBeverage("kopi", new Coffee("americano  ", 15000));
        drinkShop.addBeverage("kopi", new Coffee("cappuccino ", 20000));
        drinkShop.addBeverage("teeh", new Tea("jasmine tea", 10000));
        drinkShop.addBeverage("teeh", new Tea("green tea  ", 12000));

        // Menampilkan menu minuman
        drinkShop.displayMenu();

        Scanner scanner = new Scanner(System.in);
        String customerName = "";
        boolean addMoreOrder = true;

        System.out.print("  Masukkan nama pemesan\t: ");
        customerName = scanner.nextLine();

        while (addMoreOrder) {
            if (!customerName.isEmpty()) {
                System.out.println("\n  Nama pemesan\t\t: " + customerName);
            }

            System.out.print("  Masukkan Pesanan\t:  ");
            String beverageName = scanner.nextLine();
            drinkShop.placeOrder(beverageName);

            System.out.print("\n  Ingin Memesan Kembali? (Y/T): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("T")) {
                addMoreOrder = false;
            }
        }

        int totalPayment = drinkShop.calculateTotalPayment();
        int orderCount = drinkShop.getOrderCount();
        LocalDate orderDate = drinkShop.getOrderDate();

        System.out.println("\n----------------------------------------------------");
        System.out.println("--      Terima kasih telah memesan di DrinkShop   --");
        System.out.println("--      Tanggal pemesanan: " + orderDate + "             --");
        System.out.println("--      Pesanan ke-" + orderCount + "                              --");
        System.out.println("--      Total pembayaran: Rp" + totalPayment + "                 --");
        System.out.println("-- Pesanan atas nama " + customerName + " akan segera diproses " + " --");
        System.out.println("----------------------------------------------------");

        scanner.close();
    }
}