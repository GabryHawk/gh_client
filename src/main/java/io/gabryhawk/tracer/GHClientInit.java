package io.gabryhawk.tracer;

import net.fabricmc.api.ModInitializer;

public class GHClientInit implements ModInitializer {

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		/*
		 * MinecraftClient MC = MinecraftClient.getInstance();
		 * 
		 * CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
		 * dispatcher.register(CommandManager.literal("foo").executes(context -> { for
		 * (Entity e : MC.world.getEntities()) { context.getSource() .sendFeedback(new
		 * LiteralText( "Entity: " + e.getType() + " X: " + e.prevX + " Y: " + e.prevY +
		 * " Z: " + e.prevZ), false); System.out.println( "Entity: " + e.toString() +
		 * " X: " + e.prevX + " Y: " + e.prevY + " Z: " + e.prevZ); } return 1; })); });
		 */
		System.out.println("GH Client succesfully loaded!");
	}
}
