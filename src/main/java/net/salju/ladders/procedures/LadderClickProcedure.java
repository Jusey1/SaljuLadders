package net.salju.ladders.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

@Mod.EventBusSubscriber
public class LadderClickProcedure {
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		LevelAccessor world = event.getLevel();
		Player player = event.getEntity();
		InteractionHand handy = player.getUsedItemHand();
		ItemStack ladder = player.getItemInHand(handy);
		double x = event.getPos().getX();
		double y = event.getPos().getY();
		double z = event.getPos().getZ();
		BlockState block = event.getLevel().getBlockState(event.getPos());
		BlockPos lower = (BlockPos.containing(x, y - 1, z));
		BlockPos upper = (BlockPos.containing(x, y + 1, z));
		if (block.is(BlockTags.create(new ResourceLocation("minecraft:ladders"))) && ladder.is(ItemTags.create(new ResourceLocation("minecraft:ladders"))) && block.getBlock().asItem() == ladder.getItem()) {
			if (world.isEmptyBlock(lower)) {
				world.setBlock(lower, block, 3);
				{
					Direction dr = (new Object() {
						public Direction getDirection(BlockPos pos) {
							BlockState bs = world.getBlockState(pos);
							Property<?> property = bs.getBlock().getStateDefinition().getProperty("facing");
							if (property != null && bs.getValue(property) instanceof Direction dr)
								return dr;
							property = bs.getBlock().getStateDefinition().getProperty("axis");
							if (property != null && bs.getValue(property) instanceof Direction.Axis _axis)
								return Direction.fromAxisAndDirection(_axis, Direction.AxisDirection.POSITIVE);
							return Direction.NORTH;
						}
					}.getDirection(BlockPos.containing(x, y, z)));
					BlockState bs = world.getBlockState(lower);
					Property<?> _property = bs.getBlock().getStateDefinition().getProperty("facing");
					if (_property instanceof DirectionProperty dp && dp.getPossibleValues().contains(dr)) {
						world.setBlock(lower, bs.setValue(dp, dr), 3);
					} else {
						_property = bs.getBlock().getStateDefinition().getProperty("axis");
						if (_property instanceof EnumProperty ap && ap.getPossibleValues().contains(dr.getAxis()))
							world.setBlock(lower, bs.setValue(ap, dr.getAxis()), 3);
					}
				}
				if (world instanceof Level lvl) {
					lvl.playSound(null, lower, SoundEvents.LADDER_PLACE, SoundSource.BLOCKS, 1, 1);
				}
				player.swing(handy, true);
				if (!player.getAbilities().instabuild) {
					ladder.shrink(1);
				}
			} else if (world.isEmptyBlock(upper)) {
				world.setBlock(upper, block, 3);
				{
					Direction dr = (new Object() {
						public Direction getDirection(BlockPos pos) {
							BlockState bs = world.getBlockState(pos);
							Property<?> property = bs.getBlock().getStateDefinition().getProperty("facing");
							if (property != null && bs.getValue(property) instanceof Direction dr)
								return dr;
							property = bs.getBlock().getStateDefinition().getProperty("axis");
							if (property != null && bs.getValue(property) instanceof Direction.Axis _axis)
								return Direction.fromAxisAndDirection(_axis, Direction.AxisDirection.POSITIVE);
							return Direction.NORTH;
						}
					}.getDirection(BlockPos.containing(x, y, z)));
					BlockState bs = world.getBlockState(upper);
					Property<?> _property = bs.getBlock().getStateDefinition().getProperty("facing");
					if (_property instanceof DirectionProperty dp && dp.getPossibleValues().contains(dr)) {
						world.setBlock(upper, bs.setValue(dp, dr), 3);
					} else {
						_property = bs.getBlock().getStateDefinition().getProperty("axis");
						if (_property instanceof EnumProperty ap && ap.getPossibleValues().contains(dr.getAxis()))
							world.setBlock(upper, bs.setValue(ap, dr.getAxis()), 3);
					}
				}
				if (world instanceof Level lvl) {
					lvl.playSound(null, upper, SoundEvents.LADDER_PLACE, SoundSource.BLOCKS, 1, 1);
				}
				player.swing(handy, true);
				if (!player.getAbilities().instabuild) {
					ladder.shrink(1);
				}
			}
		}
	}
}