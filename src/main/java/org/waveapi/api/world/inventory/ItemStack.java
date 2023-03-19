package org.waveapi.api.world.inventory;

public class ItemStack {

    private final net.minecraft.item.ItemStack itemStack;
    public ItemStack (net.minecraft.item.ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getName() {
        return this.itemStack.getDisplayName();
    }
    public int getAmount() {
        return this.itemStack.getCount();
    }
    public void setAmount(int amount) {
        this.itemStack.setCount(amount);
    }

}