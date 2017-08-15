package net.blay09.mods.cookingforblockheads.block;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.tile.TileMilkJar;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockMilkJar extends BlockKitchen {

    public static final String name = "milk_jar";
    public static final ResourceLocation registryName = new ResourceLocation(CookingForBlockheads.MOD_ID, name);
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.3, 0, 0.3, 0.7, 0.5, 0.7);

    public BlockMilkJar() {
        super(Material.GLASS);

        setUnlocalizedName(registryName.toString());
        setSoundType(SoundType.GLASS);
        setHardness(0.6f);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, LOWERED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.withProperty(LOWERED, isLowered(world, pos));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        Block blockBelow = world.getBlockState(pos.down()).getBlock();
        if(blockBelow == ModBlocks.corner || blockBelow == ModBlocks.counter) {
            return BOUNDING_BOX.expand(0, -0.05, 0);
        }
        return BOUNDING_BOX;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileMilkJar tileMilkJar = (TileMilkJar) world.getTileEntity(pos);
        if(!(null == heldItem) && tileMilkJar != null) {
            if (heldItem.getItem() == Items.MILK_BUCKET) {
                if(tileMilkJar.getMilkAmount() <= tileMilkJar.getMilkCapacity() - 1000) {
                    tileMilkJar.fill(1000);
                    player.setHeldItem(hand, new ItemStack(Items.BUCKET));
                }
                return true;
            } else if(heldItem.getItem() == Items.BUCKET) {
                if(tileMilkJar.getMilkAmount() >= 1000) {
                    if (heldItem.stackSize == 1) {
                        tileMilkJar.drain(1000);
                        player.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
                    } else {
                        if(player.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET))) {
                            tileMilkJar.drain(1000);
                            heldItem.stackSize--;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        for (String s : I18n.format("tooltip." + registryName + ".description").split("\\\\n")) {
            tooltip.add(TextFormatting.GRAY + s);
        }
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileMilkJar();
    }

    public static boolean isLowered(IBlockAccess world, BlockPos pos) {
        Block blockBelow = world.getBlockState(pos.down()).getBlock();
        return blockBelow == ModBlocks.corner || blockBelow == ModBlocks.counter;
    }

}
