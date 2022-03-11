/**
 * Copyright (C) Glitchfiend
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package terrablender.core;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.Region;
import terrablender.config.TerraBlenderConfig;
import terrablender.worldgen.surface.NamespacedSurfaceRuleSource;

import java.util.function.BiConsumer;

public class TerraBlender
{
    public static final String MOD_ID = "terrablender";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static TerraBlenderConfig CONFIG;

    public TerraBlender()
    {
    }

    public static void register(BiConsumer<ResourceKey<Biome>, Biome> registerBiome)
    {
        registerBiome.accept(Region.DEFERRED_PLACEHOLDER, OverworldBiomes.theVoid());
        Registry.register(Registry.RULE, new ResourceLocation(terrablender.core.TerraBlender.MOD_ID, "merged"), NamespacedSurfaceRuleSource.CODEC);
    }

    public static void setConfig(TerraBlenderConfig config)
    {
        TerraBlender.CONFIG = config;
    }
}
