package com.techelevator.view;

import com.techelevator.Drink;
import com.techelevator.Gum;
import com.techelevator.Item;
import com.techelevator.VendingMachine;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class VendingMachineTest {

    private VendingMachine objectOutput;
    private Map<String, Item> inventory = new HashMap<>();

    @Before
    public void new_object() { objectOutput = new VendingMachine(); }

    @Test
    public void test_feed_me_method() {
    String amountInput = "2";
    double output = objectOutput.feedMe(amountInput);
    Assert.assertEquals(2.0, output, 0.0);

    }

    @Test
    public void test_feed_me_method_unacceptable_string() {
        String amountInput = "five";
        double output = objectOutput.feedMe(amountInput);
        Assert.assertEquals(0.0, output, 0.0);

    }

    @Test
    public void test_VM_stocked_method() {
        String inventoryFilePath = "src/test/resources/VM stocked test";
        Map <String, Item> expected = new HashMap<>();
        Drink drink = new Drink ("C4", "Heavy", 1.5);
        expected.put("C4", drink);
        Map output = objectOutput.getVendingMachineStocked(inventoryFilePath);
        Assert.assertEquals(expected, output);

    }

    @Test
    public void test_VM_stocked_method_unacceptable_input_file() {
        String inventoryFilePath = "src/test/java/com/techelevator/view/VMstockingMethodToFail.txt";
        Map <String, Item> expected = new HashMap<>();
        Map output = objectOutput.getVendingMachineStocked(inventoryFilePath);
        Assert.assertEquals(expected, output);

    }

}
