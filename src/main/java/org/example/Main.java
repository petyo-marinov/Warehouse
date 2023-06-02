package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse("Sklada");

        Supplier supplier = new Supplier(warehouse);
        supplier.start();

        Shop.warehouse = warehouse;

        Shop shop1 = new Shop("Magazin v Mladost");
        Shop shop2 = new Shop("Magazin v Simeonovo");
        Shop shop3 = new Shop("Magazin v OvchaKupel");
        new Thread(shop1, "Magazin v Mladost").start();
        new Thread(shop2, "Magazin v Simeonovo").start();
        new Thread(shop3, "Magazin v OvchaKupel").start();

        Client c1 = new Client(shop1, "Mia ot Mladost");
        Client c2 = new Client(shop1, "Iva ot Mladost");
        Client c3 = new Client(shop1, "Radina ot Mladost");
        Client c4 = new Client(shop2, "Asen ot Simeonovo");
        Client c5 = new Client(shop2, "Darina ot Simeonovo");
        Client c6 = new Client(shop2, "Kalin ot Simeonovo");
        Client c7 = new Client(shop3, "Irina ot OvchaKupel");
        Client c8 = new Client(shop3, "Silvia ot OvchaKupel");
        Client c9 = new Client(shop3, "Boris ot OvchaKupel");

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();
        c6.start();
        c7.start();
        c8.start();
        c9.start();

        Thread demon = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    warehouse.printAvailability();
                    shop1.printAvailability();
                    shop2.printAvailability();
                    shop3.printAvailability();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        demon.setDaemon(true);
        demon.start();

    }
}