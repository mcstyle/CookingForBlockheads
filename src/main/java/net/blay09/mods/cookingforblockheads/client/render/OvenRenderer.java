package net.blay09.mods.cookingforblockheads.client.render;

import net.blay09.mods.cookingforblockheads.block.ModBlocks;
import net.blay09.mods.cookingforblockheads.tile.TileOven;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class OvenRenderer extends TileEntitySpecialRenderer<TileOven> {

	public static IBakedModel modelDoor;
	public static IBakedModel modelDoorActive;

	@Override
	public void renderTileEntityAt(TileOven tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
		if(!tileEntity.hasWorldObj()) {
			return;
		}
		BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

		EnumFacing facing = tileEntity.getFacing();
		float blockAngle = RenderUtils.getFacingAngle(facing);
		float doorAngle = tileEntity.getDoorAnimator().getRenderAngle(partialTicks);

		// Render the oven door
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5f, y, z + 0.5f);
		GlStateManager.rotate(blockAngle, 0f, 1f, 0f);
		GlStateManager.translate(-0.5f, 0f, -0.5f);
		GlStateManager.rotate(-(float) Math.toDegrees(doorAngle), 1f, 0f, 0f);
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		IBakedModel model = doorAngle < 0.3f && tileEntity.isBurning() ? modelDoorActive : modelDoor;
		dispatcher.getBlockModelRenderer().renderModelBrightnessColor(model, 1f, 1f, 1f, 1f);
		GlStateManager.popMatrix();

		// Render the oven tools
		GlStateManager.pushMatrix();
		GlStateManager.color(1f, 1f, 1f, 1f);
		GlStateManager.translate(x + 0.5, y + 1.05, z + 0.5);
		GlStateManager.rotate(blockAngle, 0f, 1f, 0f);
		GlStateManager.scale(0.4f, 0.4f, 0.4f);
		ItemStack itemStack = tileEntity.getToolItem(0);
		if (!(null == itemStack)) {
			RenderUtils.renderItem(itemRenderer, itemStack, -0.55f, 0f, 0.5f, 45f, 1f, 0f, 0f);
		}
		itemStack = tileEntity.getToolItem(1);
		if (!(null == itemStack)) {
			RenderUtils.renderItem(itemRenderer, itemStack, 0.55f, 0f, 0.5f, 45f, 1f, 0f, 0f);
		}
		itemStack = tileEntity.getToolItem(2);
		if (!(null == itemStack)) {
			RenderUtils.renderItem(itemRenderer, itemStack, -0.55f, 0f, -0.5f, 45f, 1f, 0f, 0f);
		}
		itemStack = tileEntity.getToolItem(3);
		if (!(null == itemStack)) {
			RenderUtils.renderItem(itemRenderer, itemStack, 0.55f, 0f, -0.5f, 45f, 1f, 0f, 0f);
		}
		GlStateManager.popMatrix();

		// Render the oven content when the door is open
		if (doorAngle > 0f) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.4, z + 0.5);
			GlStateManager.rotate(blockAngle, 0f, 1f, 0f);
			GlStateManager.scale(0.3f, 0.3f, 0.3f);
			float offsetX = 0.825f;
			float offsetZ = 0.8f;
			for (int i = 0; i < 9; i++) {
				itemStack = tileEntity.getItemHandler().getStackInSlot(7 + i);
				if (!(null == itemStack)) {
					RenderUtils.renderItem(itemRenderer, itemStack, offsetX, 0f, offsetZ, 90f, 1f, 0f, 0f);
				}
				offsetX -= 0.8f;
				if (offsetX < -0.8f) {
					offsetX = 0.825f;
					offsetZ -= 0.8f;
				}
			}
			GlStateManager.popMatrix();
		}
	}

}
