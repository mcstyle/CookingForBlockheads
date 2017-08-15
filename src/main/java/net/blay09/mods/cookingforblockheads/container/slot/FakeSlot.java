package net.blay09.mods.cookingforblockheads.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FakeSlot extends Slot {

	private ItemStack displayStack = null;

	public FakeSlot(int slotId, int x, int y) {
		super(null, slotId, x, y);
	}

	public void setDisplayStack(ItemStack itemStack) {
		this.displayStack = itemStack;
	}

	@Override
	public ItemStack getStack() {
		return displayStack;
	}

	@Override
	public boolean getHasStack() {
		return !(null == displayStack);
	}

	@Override
	public void putStack(ItemStack stack) {
		// NOP
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		return null;
	}

	@Override
	public void onSlotChanged() {
		// NOP
	}

	@Override
	public int getSlotStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}
}
