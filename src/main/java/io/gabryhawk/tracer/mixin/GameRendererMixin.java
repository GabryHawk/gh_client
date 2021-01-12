package io.gabryhawk.tracer.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.gabryhawk.tracer.TraceRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.SynchronousResourceReloadListener;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements AutoCloseable, SynchronousResourceReloadListener {
	@Inject(at = {
			@At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = Opcodes.GETFIELD, ordinal = 0) }, method = {
					"renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V" })
	private void onRenderWorld(float partialTicks, long finishTimeNano, MatrixStack matrixStack, CallbackInfo ci) {
		TraceRenderer.onRender(partialTicks);
	}
}
