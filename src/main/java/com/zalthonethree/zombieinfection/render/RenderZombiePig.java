package com.zalthonethree.zombieinfection.render;

import com.zalthonethree.zombieinfection.Reference;
import com.zalthonethree.zombieinfection.entity.EntityZombiePig;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderZombiePig extends RenderLiving
{
    private static final ResourceLocation zombiepigTextures = new ResourceLocation(Reference.MOD_ID, "textures/entity/zombiepig.png");

    public RenderZombiePig(ModelBase p_i1253_1_, float p_i1253_2_)
    {
        super(p_i1253_1_, p_i1253_2_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityZombiePig p_110775_1_)
    {
        return zombiepigTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityZombiePig)p_110775_1_);
    }
}