package org.example;

import java.util.Random;

public class Client extends Thread{

    private Shop shop;

    public Client(Shop shop, String name) {
        super(name);
        this.shop = shop;
    }

    @Override
    public void run() {
        while (true){
            int productsVariety = Warehouse.ProductNames.values().length;
            int randomProductIdx = new Random().nextInt(productsVariety);
            Warehouse.ProductNames name = Warehouse.ProductNames.values()[randomProductIdx];
            shop.zemi(name);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
