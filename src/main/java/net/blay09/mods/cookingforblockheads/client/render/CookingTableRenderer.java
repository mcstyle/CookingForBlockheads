package net.blay09.mods.cookingforblockheads.client.render;

import net.blay09.mods.cookingforblockheads.block.ModBlocks;
import net.blay09.mods.cookingforblockheads.tile.TileCookingTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class CookingTableRenderer extends TileEntitySpecialRenderer<TileCookingTable> {

    @Override
    public void renderTileEntityAt(TileCookingTable tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        IBlockState state = tileEntity.getWorld().getBlockState(tileEntity.getPos());
        if(state.getBlock() != ModBlocks.cookingTable) { // I don't know. But it seems for some reason the renderer gets called for minecraft:air in certain cases.
            return;
        }
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        ItemStack itemStack = tileEntity.getNoFilterBook();
        if (!(null == itemStack)) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1f, 1f, 1f, 1f);
            GlStateManager.translate(x + 0.5, y + 1, z + 0.5);
            GlStateManager.rotate(RenderUtils.getFacingAngle(state), 0f, 1f, 0f);
            GlStateManager.rotate(90f, 1f, 0f, 0f);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            RenderUtils.renderItem(itemRenderer, itemStack, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
            GlStateManager.popMatrix();
        }
    }

}
