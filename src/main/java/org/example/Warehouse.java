package org.example;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {

    enum ProductType {
        FRUITS, VEGETABLES, MEETS
    }

    enum ProductNames {
        BANANA, ORANGE, APPLE, POTATO, EGGPLANT, CUCUMBER, PORK, BEEF, CHICKEN
    }
    protected String name;
    protected volatile HashMap<ProductType, HashMap<ProductNames, Integer>> products;

    public Warehouse(String name){
        this.name = name;
        this.products = new HashMap<>();
        products.put(ProductType.FRUITS, new HashMap<>());
        products.get(ProductType.FRUITS).put(ProductNames.BANANA, startingQuantity());
        products.get(ProductType.FRUITS).put(ProductNames.ORANGE, startingQuantity());
        products.get(ProductType.FRUITS).put(ProductNames.APPLE, startingQuantity());
        products.put(ProductType.VEGETABLES, new HashMap<>());
        products.get(ProductType.VEGETABLES).put(ProductNames.POTATO, startingQuantity());
        products.get(ProductType.VEGETABLES).put(ProductNames.EGGPLANT, startingQuantity());
        products.get(ProductType.VEGETABLES).put(ProductNames.CUCUMBER, startingQuantity());
        products.put(ProductType.MEETS, new HashMap<>());
        products.get(ProductType.MEETS).put(ProductNames.PORK, startingQuantity());
        products.get(ProductType.MEETS).put(ProductNames.BEEF, startingQuantity());
        products.get(ProductType.MEETS).put(ProductNames.CHICKEN, startingQuantity());

    }
   public synchronized void sipi(){
        while (!hasDeficitProducts()) {
            System.out.println(Thread.currentThread().getName() + " - nqma da zarejdam, ima ot vsi4ko");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       System.out.println(Thread.currentThread().getName() + " - dostavka na lipsvashti produkti");
        supplyDeficitProducts();
        notifyAll();
   }

    private boolean hasDeficitProducts() {
        for (HashMap<ProductNames, Integer> p : products.values()){
            for(Integer i : p.values()) {
                if(i < minimumQuantity()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void supplyDeficitProducts() {
        for (HashMap<ProductNames, Integer> p : products.values()) {
            for (Map.Entry<ProductNames, Integer> e : p.entrySet()) {
                if (e.getValue() < minimumQuantity()) {
                    p.put(e.getKey(), e.getValue() + supplyQuantity());
                }
            }
        }
    }

    public synchronized void zemi(ProductNames name){
        while (isDeficit(name)){
            System.out.println(Thread.currentThread().getName() + " - " + name +  " nqma w nalichnot shte pochakam");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int sold = deliver(name);
        System.out.println(Thread.currentThread().getName() + " - vzeh " + sold + " " + name);
        if(isDeficit(name)) {
            notifyAll();
        }
    }

    private int deliver(ProductNames name) {
        int sold = deliverQuantity();
        for (HashMap<ProductNames, Integer> p : products.values()) {
            for (Map.Entry<ProductNames, Integer> e : p.entrySet()) {
                if (e.getKey() == name) {
                    p.put(e.getKey(), e.getValue() - sold);
                }
            }
        }
        return sold;
    }

    private boolean isDeficit(ProductNames name) {
        for (HashMap<ProductNames, Integer> p : products.values()) {
            for (Map.Entry<ProductNames, Integer> e : p.entrySet()) {
                if (e.getKey() == name && e.getValue() < minimumQuantity()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printAvailability() {
        System.out.println("===============" + name + "===============");
        for (HashMap<ProductNames, Integer> p : products.values()) {
            for (Map.Entry<ProductNames, Integer> e : p.entrySet()) {
                System.out.println(e.getKey() + " - " + e.getValue());
            }
        }
        System.out.println("--------------------------------------------------");
    }
    protected int deliverQuantity() {
        return 15;
    }

    protected int supplyQuantity() {
        return 25;
    }

    protected int startingQuantity() {
        return 50;
    }

    protected int minimumQuantity() {
        return 10;
    }

}
