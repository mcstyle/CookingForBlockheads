package net.blay09.mods.cookingforblockheads.container.slot;

import com.google.common.collect.Lists;
import net.blay09.mods.cookingforblockheads.ItemUtils;
import net.blay09.mods.cookingforblockheads.balyware.NonNullList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import javax.annotation.Nullable;

public class FakeSlotCraftMatrix extends FakeSlot {

	private static final float ITEM_SWITCH_TIME = 80f;

	private final NonNullList<ItemStack> visibleStacks = NonNullList.create();

	private float visibleItemTime;
	private int visibleItemIndex;
	private boolean isLocked;

	public FakeSlotCraftMatrix(int slotId, int x, int y) {
		super(slotId, x, y);
	}

	public void setIngredient(@Nullable NonNullList<ItemStack> ingredients) {
		ItemStack prevLockStack = isLocked ? getStack() : null;
		visibleStacks.clear();
		if(ingredients != null) {
			for(ItemStack itemStack : ingredients) {
				if(!(null == itemStack)) {
					if (itemStack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
						NonNullList<ItemStack> subItems = NonNullList.create();
						CreativeTabs tab = itemStack.getItem().getCreativeTab();
						if(tab != null) {
							itemStack.getItem().getSubItems(itemStack.getItem(), itemStack.getItem().getCreativeTab(), subItems);
						}
						visibleStacks.addAll(subItems);
					} else {
						itemStack.stackSize = 1;
						visibleStacks.add(itemStack);
					}
				}
			}
		}
		visibleItemTime = 0;
		visibleItemIndex = 0;
		isLocked = false;
		if(!(null == prevLockStack)) {
			for(int i = 0; i < visibleStacks.size(); i++) {
				if(ItemUtils.areItemStacksEqualWithWildcard(visibleStacks.get(i), prevLockStack)) {
					visibleItemIndex = i;
					isLocked = true;
				}
			}
		}
	}

	public void updateSlot(float partialTicks) {
		if(!isLocked) {
			visibleItemTime += partialTicks;
			if (visibleItemTime >= ITEM_SWITCH_TIME) {
				visibleItemIndex++;
				if (visibleItemIndex >= visibleStacks.size()) {
					visibleItemIndex = 0;
				}
				visibleItemTime = 0;
			}
		}
	}

	@Override
	public ItemStack getStack() {
		return visibleStacks.size() > 0 ? visibleStacks.get(visibleItemIndex) : null;
	}

	@Override
	public boolean getHasStack() {
		return visibleStacks.size() > 0;
	}

	@Override
	public boolean canBeHovered() {
		return visibleStacks.size() > 0;
	}

	public NonNullList<ItemStack> getVisibleStacks() {
		return visibleStacks;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}

	public void scrollDisplayList(int i) {
		isLocked = true;
		visibleItemIndex += i;
		if(visibleItemIndex >= visibleStacks.size()) {
			visibleItemIndex = 0;
		} else if (visibleItemIndex < 0) {
			visibleItemIndex = visibleStacks.size() - 1;
		}
	}

}
