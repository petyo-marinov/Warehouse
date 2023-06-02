package org.example;

import java.util.*;

public class Shop extends Warehouse implements Runnable{

    public static Warehouse warehouse;

    public Shop(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true){
            this.sipi();
        }
    }

    @Override
    protected void supplyDeficitProducts() {
        List<ProductNames> deficits = getDeficitProducts();
        System.out.println("Deficitni produkti " + name + " sa " + deficits);
        for (ProductNames name : deficits) {
            warehouse.zemi(name);
        }
        super.supplyDeficitProducts();
    }

    private List<ProductNames> getDeficitProducts() {
        List<ProductNames> deficits = new ArrayList<>();
        for (HashMap<ProductNames, Integer> p : products.values()) {
            for (Map.Entry<ProductNames, Integer> e : p.entrySet()) {
                if (e.getValue() < minimumQuantity()) {
                    deficits.add(e.getKey());
                }
            }
        }
        return deficits;
    }

    @Override
    protected int deliverQuantity() {
        //рандом връща от 0 в случая до 3 (0, 1, 2, 3) = 4 възможни стойности и като им кажа + 1, ако е 0 ще върне 1 тн.
        return new Random().nextInt(4) + 1;
    }

    @Override
    protected int supplyQuantity() {
        return 15;
    }

    @Override
    protected int startingQuantity() {
        return 20;
    }

    @Override
    protected int minimumQuantity() {
        return 5;
    }
}
