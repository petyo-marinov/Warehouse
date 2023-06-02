package org.example;

public class Supplier extends Thread{

    private Warehouse warehouse;
    public Supplier(Warehouse warehouse) {
        super("Dostavchik");
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        while (true){
            warehouse.sipi();
        }
    }
}
