package io.gabryhawk.tracer;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TraceRenderer {

    public static void onRender(float dt) {
        GL11.glPushMatrix();

        MinecraftClient MC = MinecraftClient.getInstance();

        ClientPlayerEntity player = MC.player;
        float f = 0.017453292F;
        float pi = (float) Math.PI;

        float f1 = MathHelper.cos(-player.yaw * f - pi);
        float f2 = MathHelper.sin(-player.yaw * f - pi);
        float f3 = -MathHelper.cos(-player.pitch * f);
        float f4 = MathHelper.sin(-player.pitch * f);

        // GL settings
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(2);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        RenderUtils.applyRenderOffset();
        Vec3d start = new Vec3d(f2 * f3, f4, f1 * f3).add(RenderUtils.getCameraPos());

        for (Entity e : MC.world.getEntities()) {

            if (e.getType() == EntityType.PLAYER && e.getUuid() != player.getUuid()) {

                PlayerEntity nearPlayer = (PlayerEntity) e;

                float healthPercentage = nearPlayer.getHealth() / nearPlayer.getMaxHealth();

                drawTrace(start, e.getBlockPos(), (1f - healthPercentage), healthPercentage, 0.25f);
                drawBlock(start, e.getBlockPos(), (1f - healthPercentage), healthPercentage, 0.25f);

            }

            // System.out.println("Entity: " + e.toString() + " X: " + e.lastRenderX + " Y:
            // " + e.lastRenderY + " Z: "
            // + e.lastRenderZ);
        }

        for (BlockEntity be : MC.world.blockEntities) {
            if (be.getType() == BlockEntityType.CHEST) {
                drawBlock(start, be.getPos(), 0f, 1f, 1f);
            }
        }

        // GL resets
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    private static void drawTrace(Vec3d start, BlockPos pos, float red, float green, float blue) {
        Vec3d end = Vec3d.ofCenter(pos);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor4f(red, green, blue, 0.5f);
        GL11.glVertex3d(start.x, start.y, start.z);
        GL11.glVertex3d(end.x, end.y, end.z);
        GL11.glEnd();
    }

    private static void drawBlock(Vec3d start, BlockPos pos, float red, float green, float blue) {
        GL11.glPushMatrix();
        GL11.glTranslated(pos.getX(), pos.getY(), pos.getZ());
        GL11.glColor4f(red, green, blue, 0.5f);
        RenderUtils.drawOutlinedBox();
        GL11.glColor4f(red, green, blue, 0.25f);
        RenderUtils.drawSolidBox();
        GL11.glPopMatrix();

    }

}
