package com.bioxx.tfc.WorldGen.GenLayers;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.IntCache;

import com.bioxx.tfc.WorldGen.TFCWorldType;

public class GenLayerBiomeTFC extends GenLayerTFC
{
	private BiomeGenBase[] hotBiomes;
	private BiomeGenBase[] wetWarmBiomes;
	private BiomeGenBase[] wetCoolBiomes;
	private BiomeGenBase[] coldBiomes;
	private static final String __OBFID = "CL_00000555";

	public GenLayerBiomeTFC(long par1, GenLayerTFC parent, TFCWorldType worldType)
	{
		super(par1);
		this.hotBiomes = new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.savanna, BiomeGenBase.savanna, BiomeGenBase.plains};
		this.wetWarmBiomes = new BiomeGenBase[] {BiomeGenBase.forest, BiomeGenBase.roofedForest, BiomeGenBase.extremeHills, BiomeGenBase.plains, BiomeGenBase.birchForest, BiomeGenBase.swampland};
		this.wetCoolBiomes = new BiomeGenBase[] {BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains};
		this.coldBiomes = new BiomeGenBase[] {BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.coldTaiga};
		this.parent = parent;
	}

	/**
	 * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
	 * amounts, or biomeList[] indices based on the particular GenLayer subclass.
	 */
	@Override
	public int[] getInts(int x, int z, int xMax, int zMax)
	{
		int[] parentNoise = this.parent.getInts(x, z, xMax, zMax);
		int[] cache = IntCache.getIntCache(xMax * zMax);

		for (int i = 0; i < zMax; ++i)
		{
			for (int k = 0; k < xMax; ++k)
			{
				this.initChunkSeed(k + x, i + z);
				int biomeID = parentNoise[k + i * xMax];
				int l1 = (biomeID & 3840) >> 8;
			biomeID &= -3841;

			if (isBiomeOceanic(biomeID))
			{
				cache[k + i * xMax] = biomeID;
			}
			else if (biomeID == BiomeGenBase.mushroomIsland.biomeID)
			{
				cache[k + i * xMax] = biomeID;
			}
			else if (biomeID == 1)
			{
				if (l1 > 0)
				{
					if (this.nextInt(3) == 0)
					{
						cache[k + i * xMax] = BiomeGenBase.mesaPlateau.biomeID;
					}
					else
					{
						cache[k + i * xMax] = BiomeGenBase.mesaPlateau_F.biomeID;
					}
				}
				else
				{
					cache[k + i * xMax] = this.hotBiomes[this.nextInt(this.hotBiomes.length)].biomeID;
				}
			}
			else if (biomeID == 2)
			{
				if (l1 > 0)
				{
					cache[k + i * xMax] = BiomeGenBase.jungle.biomeID;
				}
				else
				{
					cache[k + i * xMax] = this.wetWarmBiomes[this.nextInt(this.wetWarmBiomes.length)].biomeID;
				}
			}
			else if (biomeID == 3)
			{
				if (l1 > 0)
				{
					cache[k + i * xMax] = BiomeGenBase.megaTaiga.biomeID;
				}
				else
				{
					cache[k + i * xMax] = this.wetCoolBiomes[this.nextInt(this.wetCoolBiomes.length)].biomeID;
				}
			}
			else if (biomeID == 4)
			{
				cache[k + i * xMax] = this.coldBiomes[this.nextInt(this.coldBiomes.length)].biomeID;
			}
			else
			{
				cache[k + i * xMax] = BiomeGenBase.mushroomIsland.biomeID;
			}
			}
		}

		return cache;
	}
}