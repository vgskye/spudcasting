package at.petrak.spudcasting;

import at.petrak.spudcasting.eval.SpudEvaluator;
import at.petrak.spudcasting.eval.Spudcode;
import at.petrak.spudcasting.eval.Spudcodes;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SpudcastingMod implements ModInitializer {
	public static final String MOD_ID = "spudcasting";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");

		UseBlockCallback.EVENT.register(SpudcastingMod::listenForPotatoWhack);
		ItemTooltipCallback.EVENT.register(SpudcastingMod::printOpcodeHelp);
	}

	private static ActionResult listenForPotatoWhack(PlayerEntity player, World world, Hand hand, BlockHitResult hit) {
		if (!(world instanceof ServerWorld sworld) || player.isSpectator() || !player.isSneaking()) {
			return ActionResult.PASS;
		}

		ItemStack inHand = player.getStackInHand(hand);
		if (!inHand.isOf(Items.POISONOUS_POTATO)) {
			return ActionResult.PASS;
		}

		BlockEntity be = sworld.getBlockEntity(hit.getBlockPos());
		if (!(be instanceof Inventory beInv)) {
			return ActionResult.PASS;
		}

		SpudEvaluator.evalInventory(sworld, beInv);
		return ActionResult.SUCCESS;
	}

	public static void printOpcodeHelp(ItemStack stack, TooltipContext ctx, List<Text> lines) {
		Spudcode code = Spudcodes.getSpudcode(stack);
		lines.add(Text.literal("Spudcode: ").append(code.name()));
		lines.add(code.description());
	}
}