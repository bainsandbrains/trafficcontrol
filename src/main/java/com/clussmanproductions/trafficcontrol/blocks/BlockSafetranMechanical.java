package com.clussmanproductions.trafficcontrol.blocks;

import com.clussmanproductions.trafficcontrol.ModTrafficControl;
import com.clussmanproductions.trafficcontrol.tileentity.SafetranMechanicalTileEntity;
import com.clussmanproductions.trafficcontrol.util.CustomAngleCalculator;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockSafetranMechanical extends Block implements ITileEntityProvider {
	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
	public BlockSafetranMechanical()
	{
		super(Material.IRON);
		setRegistryName("safetran_mechanical");
		setUnlocalizedName(ModTrafficControl.MODID + ".safetran_mechanical");
		setHardness(2f);
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(ModTrafficControl.CREATIVE_TAB);
	}
	
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ROTATION);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return CustomAngleCalculator.rotationToMeta(state.getValue(ROTATION));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(ROTATION, CustomAngleCalculator.metaToRotation(meta));
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(ROTATION, CustomAngleCalculator.getRotationForYaw(placer.rotationYaw));
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new SafetranMechanicalTileEntity();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		double x1 = 0.4325;
		double z1 = 0.375;
		double x2 = 0.5625;
		double z2 = 0.625;
		
		int rotation = state.getValue(ROTATION);
		boolean isNorthSouth = rotation >= 14 || rotation < 2 || (rotation >= 6 && rotation < 10); 
		
		if (isNorthSouth)
		{
			return new AxisAlignedBB(x1, 0, z1, x2, 0.25, z2);
		}
		else
		{
			return new AxisAlignedBB(z1, 0, x1, z2, 0.25, x2);
		}
		
	}

	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}
}
