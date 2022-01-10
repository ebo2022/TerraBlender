/*******************************************************************************
 * Copyright 2022, the Glitchfiend Team.
 * Creative Commons Attribution-NonCommercial-NoDerivatives 4.0.
 ******************************************************************************/
package terrablender.api;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.SurfaceRules;
import terrablender.worldgen.BiomeProviderUtils;
import terrablender.worldgen.TBClimate;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A source of modded biome parameters and other biome-related data.
 * Each mod should implement one or more biome providers.
 */
public abstract class BiomeProvider extends WeightedEntry.IntrusiveBase
{
    private final ResourceLocation name;

    public BiomeProvider(ResourceLocation name, int weight)
    {
        super(weight);
        this.name = name;
    }

    /**
     * A place to register biome parameter mappings for the overworld. This may be used in conjunction with {@link #addBiome(Consumer, Climate.Parameter, Climate.Parameter, Climate.Parameter, Climate.Parameter, Climate.Parameter, Climate.Parameter, float, ResourceKey) addBiome}.
     * Mojang's approach to biome parameter mappings may be found in {@link net.minecraft.world.level.biome.OverworldBiomeBuilder OverworldBiomeBuilder}.
     * @param registry the biome registry.
     * @param mapper the mapper used to construct a list of {@link terrablender.worldgen.TBClimate.ParameterPoint ParameterPoint} to biome mappings.
     */
    public void addOverworldBiomes(Registry<Biome> registry, Consumer<Pair<TBClimate.ParameterPoint, ResourceKey<Biome>>> mapper) {}

    /**
     * A place to register biome parameter mappings for the nether. Functions the same as the overworld equivalent {@link #addOverworldBiomes(Registry, Consumer) addOverworldBiomes}.
     * @param registry the biome registry.
     * @param mapper the mapper used to construct a list of {@link terrablender.worldgen.TBClimate.ParameterPoint ParameterPoint} to biome mappings.
     */
    public void addNetherBiomes(Registry<Biome> registry, Consumer<Pair<TBClimate.ParameterPoint, ResourceKey<Biome>>> mapper) {}

    /**
     * Gets a list of {@link terrablender.worldgen.TBClimate.ParameterPoint ParameterPoints} suitable for spawning the player in.
     * @return a list of parameter points.
     */
    public List<TBClimate.ParameterPoint> getSpawnTargets()
    {
        return ImmutableList.of();
    }

    /**
     * Get any custom surface rules for the overworld. If unspecified, the {@link terrablender.api.GenerationSettings#getDefaultOverworldSurfaceRules() default surface rules} will be used.
     * @return the overworld surface rules.
     */
    public Optional<SurfaceRules.RuleSource> getOverworldSurfaceRules()
    {
        return Optional.empty();
    }

    /**
     * Get any custom surface rules for the nether. If unspecified, the {@link GenerationSettings#getDefaultNetherSurfaceRules() default surface rules} will be used.
     * @return the nether surface rules.
     */
    public Optional<SurfaceRules.RuleSource> getNetherSurfaceRules()
    {
        return Optional.empty();
    }

    /**
     * Get the name for this biome provider.
     * @return the biome provider name.
     */
    public ResourceLocation getName()
    {
        return this.name;
    }

    /**
     * Get the index specific to this biome provider.
     * @return the biome provider's index.
     */
    public final int getIndex()
    {
        return BiomeProviders.getIndex(this.getName());
    }

    /**
     * Get the uniqueness parameter specific to this biome provider.
     * @return the biome provider's uniqueness parameter.
     */
    protected final Climate.Parameter getUniquenessParameter()
    {
        return BiomeProviderUtils.getUniquenessParameter(this.getIndex());
    }

    /**
     * Adds a biome using the provided mapper.
     * @param mapper the mapper used to construct a list of {@link terrablender.worldgen.TBClimate.ParameterPoint ParameterPoint} to biome mappings.
     * @param temperature the temperature parameter value.
     * @param humidity the humidity parameter value.
     * @param continentalness the continentalness parameter value.
     * @param erosion the erosion parameter value.
     * @param weirdness the weirdness parameter value
     * @param depth the depth parameter value.
     * @param offset the offset parameter value.
     * @param biome the biome to be added.
     */
    protected final void addBiome(Consumer<Pair<TBClimate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, Climate.Parameter depth, float offset, ResourceKey<Biome> biome)
    {
        addBiome(mapper, TBClimate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, getUniquenessParameter(), offset), biome);
    }

    /**
     * Adds a biome using the provided mapper.
     * @param mapper the mapper used to construct a list of {@link terrablender.worldgen.TBClimate.ParameterPoint ParameterPoint} to biome mappings.
     * @param parameters the parameters corresponding to the biome.
     * @param biome the biome to be added.
     */
    protected final void addBiome(Consumer<Pair<TBClimate.ParameterPoint, ResourceKey<Biome>>> mapper, TBClimate.ParameterPoint parameters, ResourceKey<Biome> biome)
    {
        mapper.accept(Pair.of(parameters, biome));
    }
}