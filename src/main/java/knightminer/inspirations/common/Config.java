package knightminer.inspirations.common;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import knightminer.inspirations.Inspirations;
import knightminer.inspirations.library.InspirationsRegistry;
import knightminer.inspirations.library.util.RecipeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.client.CreativeTab;
import slimeknights.mantle.pulsar.config.ForgeCFG;

public class Config {

	public static ForgeCFG pulseConfig = new ForgeCFG("inspirationsModules", "Modules");

	private static Configuration configFile;

	// general
	public static boolean showAllVariants = true;

	// building
	public static boolean enableRope = true;
	public static boolean enableGlassDoor = true;
	public static boolean enableMulch = true;
	public static boolean enablePath = true;
	public static boolean enableFlowers = true;
	public static boolean enableEnlightenedBush = true;

	public static boolean enableBookshelf = true;
	public static boolean enableColoredBooks = true;
	private static String[] bookKeywords = {
			"atlas",
			"book",
			"catalogue",
			"guide",
			"journal",
			"lexicon",
			"manual",
			"tome"
	};
	private static String[] bookOverrides = {
			"defiledlands:book_wyrm_raw:0:false",
			"defiledlands:book_wyrm_cooked:0:false",
			"defiledlands:book_wyrm_scale:0:false",
			"defiledlands:book_wyrm_scale_golden:0:false",
			"defiledlands:book_wyrm_analyzer:0:false",
			"theoneprobe:probenote:0:true"
	};

	// utility
	public static boolean enableLock = true;
	public static boolean enableTorchLever = true;
	public static boolean enableRedstoneTorchLever = true;
	public static boolean enableRedstoneBook = true;
	public static boolean enableRedstoneCharge = true;
	public static boolean enableBricksButton = true;
	public static boolean enableRedstoneBarrel = true;
	public static boolean enableCarpetedTrapdoor = true;

	// recipes
	public static boolean enableAnvilSmashing = true;
	public static boolean dispensersPlaceAnvils = true;
	// cauldron
	public static boolean enableCauldronRecipes = true;
	public static boolean enableExtendedCauldron = true;
	public static boolean simpleCauldronRecipes = false;
	public static boolean enableCauldronDyeing = true;
	public static boolean patchVanillaDyeRecipes = true;
	public static boolean extraBottleRecipes = true;
	public static boolean enableCauldronPotions = true;
	public static boolean enableCauldronBrewing = true;
	public static boolean enableCauldronFluids = true;
	public static boolean spongeEmptyCauldron = true;
	public static boolean spongeCauldronFull = false;
	public static boolean betterCauldronItem = true;

	private static String[] anvilSmashing = {
			"# Stone",
			"minecraft:stone:0->minecraft:cobblestone",
			"minecraft:stonebrick->minecraft:cobblestone",
			"minecraft:stonebrick:1->minecraft:mossy_cobblestone",
			"minecraft:cobblestone->minecraft:gravel",
			"minecraft:stone:2->minecraft:stone:1",
			"minecraft:stone:4->minecraft:stone:3",
			"minecraft:stone:6->minecraft:stone:5",

			"# Sandstone",
			"minecraft:sandstone->minecraft:sand:0",
			"minecraft:red_sandstone->minecraft:sand:1",

			"# Ice",
			"minecraft:packed_ice->minecraft:ice",
			"minecraft:ice",
			"minecraft:frosted_ice",

			"# Plants",
			"minecraft:brown_mushroom_block",
			"minecraft:red_mushroom_block",
			"minecraft:leaves",
			"minecraft:leaves2",
			"minecraft:melon_block",
			"minecraft:pumpkin",
			"minecraft:lit_pumpkin",

			"# Concrete",
			"minecraft:concrete:0->minecraft:concrete_powder:0",
			"minecraft:concrete:1->minecraft:concrete_powder:1",
			"minecraft:concrete:2->minecraft:concrete_powder:2",
			"minecraft:concrete:3->minecraft:concrete_powder:3",
			"minecraft:concrete:4->minecraft:concrete_powder:4",
			"minecraft:concrete:5->minecraft:concrete_powder:5",
			"minecraft:concrete:6->minecraft:concrete_powder:6",
			"minecraft:concrete:7->minecraft:concrete_powder:7",
			"minecraft:concrete:8->minecraft:concrete_powder:8",
			"minecraft:concrete:9->minecraft:concrete_powder:9",
			"minecraft:concrete:10->minecraft:concrete_powder:10",
			"minecraft:concrete:11->minecraft:concrete_powder:11",
			"minecraft:concrete:12->minecraft:concrete_powder:12",
			"minecraft:concrete:13->minecraft:concrete_powder:13",
			"minecraft:concrete:14->minecraft:concrete_powder:14",
			"minecraft:concrete:15->minecraft:concrete_powder:15",

			"# Misc",
			"minecraft:planks->inspirations:mulch:0",
			"minecraft:prismarine:1->minecraft:prismarine:0",
			"minecraft:end_bricks->minecraft:end_stone",
			"minecraft:monster_egg"
	};
	private static String[] cauldronRecipes = {
			"minecraft:sticky_piston->minecraft:piston"
	};


	// tweaks
	public static boolean enablePigDesaddle = true;
	public static boolean enableFittedCarpets = true;
	public static boolean enableExtraBonemeal = true;
	public static boolean harvestHangingVines = true;
	public static boolean shearsReclaimMelons = true;
	public static boolean betterFlowerPot = true;
	public static boolean flowerPotComparator = true;
	public static boolean coloredEnchantedRibbons = true;
	public static boolean brewMissingPotions = true;
	public static boolean coloredFireworkItems = true;
	public static boolean lilypadBreakFall = true;
	// heartbeet
	public static boolean enableHeartbeet = true;
	public static boolean brewHeartbeet = true;

	public static String[] flowerOverrides = {};

	// compatibility
	public static boolean tanJuiceInCauldron = true;


	/**
	 * Loads the configuration file from the event
	 * @param event  PreInit event from main mod class
	 */
	public static void preInit(FMLPreInitializationEvent event) {
		configFile = new Configuration(event.getSuggestedConfigurationFile(), "0.1", false);

		showAllVariants = configFile.getBoolean("showAllVariants", "general", showAllVariants,
				"Shows all variants for dynamically textured blocks, like bookshelves. If false just the first will be shown");

		// building
		{
			// bookshelves
			enableBookshelf = configFile.getBoolean("bookshelf", "building", enableBookshelf, "Enables the bookshelf, a decorative block to display books");
			enableColoredBooks = configFile.getBoolean("coloredBooks", "building.bookshelf", enableColoredBooks, "Enables colored books, basically colored versions of the vanilla book to decorate bookshelves") && enableBookshelf;
			bookKeywords = configFile.getStringList("bookKeywords", "building.bookshelf", bookKeywords,
					"List of keywords for valid books, used to determine valid books in the bookshelf");
			InspirationsRegistry.setBookKeywords(bookKeywords);


			// rope
			enableRope = configFile.getBoolean("rope", "building", enableRope, "Enables rope, can be climbed like ladders and extended with additional rope");

			// glass door
			enableGlassDoor = configFile.getBoolean("glassDoor", "building", enableGlassDoor, "Enables glass doors and trapdoors. Basically doors, but made of glass. Not sure what you would expect.");

			// mulch
			enableMulch = configFile.getBoolean("mulch", "building", enableMulch, "Enables mulch, a craftable falling block which supports plants such as flowers");

			// path
			enablePath = configFile.getBoolean("path", "building", enablePath, "Enables stone paths: a carpet like decorative block for making decorative paths");

			// flowers
			enableFlowers = configFile.getBoolean("flowers", "building", enableFlowers, "Enables additional flower from breaking double flowers with shears.");

			// enlightenedBush
			enableEnlightenedBush = configFile.getBoolean("enlightenedBush", "building", enableEnlightenedBush, "Enables enlightned bushes: bushes with lights.");
		}

		// utility
		{
			enableRedstoneBook = configFile.getBoolean("redstoneBook", "utility", enableRedstoneBook, "Enables the trapped book, which will emit redstone power when placed in a bookshelf. Requires bookshelf.") && enableBookshelf;

			// torch lever
			enableTorchLever = configFile.getBoolean("torchLever", "utility", enableTorchLever, "Enables the torch lever. Basically a lever which looks like a torch");

			// redstone charge
			enableRedstoneCharge = configFile.getBoolean("redstoneCharge", "utility", enableRedstoneCharge, "Enables the redstone charge, a quick pulse created with a flint and steel like item");

			// lock
			enableLock = configFile.getBoolean("lock", "utility", enableLock, "Enables locks and keys, an item allowing you to lock a tile entity to only open for a special named item");

			// lock
			enableBricksButton = configFile.getBoolean("bricksButton", "utility", enableBricksButton, "Enables button blocks disguised as a full bricks or nether bricks block");

			// redstone barrel
			enableRedstoneBarrel = configFile.getBoolean("redstoneBarrel", "utility", enableRedstoneBarrel, "Enables the redstone barrel: a block wth gives a configurable comparator output and can be pushed by pistons");

			// redstone torch lever
			enableRedstoneTorchLever = configFile.getBoolean("redstoneTorchLever", "utility", enableRedstoneTorchLever, "Enables the redstone torch lever: a lever that toggles its state when the block it's on gets powered");

			// carpeted trapdoor
			enableCarpetedTrapdoor = configFile.getBoolean("carpetedTrapdoor", "utility", enableCarpetedTrapdoor, "Enables carpeted trapdoors: a trapdoor which appears to be a carpet when closed");
		}

		// recipes
		{
			// anvil smashing
			configFile.moveProperty("tweaks", "anvilSmashing", "recipes");
			enableAnvilSmashing = configFile.getBoolean("anvilSmashing", "recipes", enableAnvilSmashing, "Anvils break glass blocks and transform blocks into other blocks on landing. Uses a block override, so disable if another mod replaces anvils");

			// more cauldron uses
			String extendCauldron = configFile.getString("extendCauldron", "recipes", "true", "Allows additional recipes to be performed in the cauldron. Can be 'true', 'false', or 'simple'. If true, requires a block substitution. If simple, functionality will be limited to water in cauldrons.", new String[]{ "false", "simple", "true" });
			enableCauldronRecipes = !extendCauldron.equals("false");
			simpleCauldronRecipes = extendCauldron.equals("simple");
			enableExtendedCauldron = extendCauldron.equals("true");
			configFile.renameProperty("recipes.cauldron", "brewing", "potions");
			enableCauldronPotions = configFile.getBoolean("potions", "recipes.cauldron", enableCauldronPotions, "Allows cauldrons to be filled with potions and support brewing") && enableExtendedCauldron;
			enableCauldronDyeing = configFile.getBoolean("dyeing", "recipes.cauldron", enableCauldronDyeing, "Allows cauldrons to be filled with dyes and dye items using cauldrons") && enableExtendedCauldron;
			enableCauldronFluids = configFile.getBoolean("fluids", "recipes.cauldron", enableCauldronFluids, "Allows cauldrons to be filled with any fluid and use them in recipes") && enableExtendedCauldron;
			String spongeEmptyString = configFile.getString("spongeEmpty", "recipes.cauldron", "true", "Allows sponges to be used to empty the cauldron of dye, water, or potions. Can be 'true', 'false', or 'full'. If set to 'full', requires the cauldron to be full, prevents duplicating water but is less useful for removing unwanted fluids.", new String[]{ "false", "full", "true" });
			spongeEmptyCauldron = !spongeEmptyString.equals("false");
			spongeCauldronFull = spongeEmptyString.equals("full");

			patchVanillaDyeRecipes = configFile.getBoolean("patchVanillaRecipes", "recipes.cauldron.dyeing", patchVanillaDyeRecipes, "Makes crafting two dyed water bottles together produce a dyed water bottle. Requires modifying vanilla recipes to prevent a conflict") && enableCauldronDyeing;
			extraBottleRecipes = configFile.getBoolean("extraBottleRecipes", "recipes.cauldron.dyeing", extraBottleRecipes, "Adds extra dyed bottle recipes to craft green and brown") && enableCauldronDyeing;
			enableCauldronBrewing = configFile.getBoolean("brewing", "recipes.cauldron.potions", extraBottleRecipes, "Allows cauldrons to perform brewing recipes.") && enableCauldronBrewing;

		}

		// tweaks
		{
			// pig desaddle
			enablePigDesaddle = configFile.getBoolean("desaddlePig", "tweaks", enablePigDesaddle, "Allows pigs to be desaddled by shift-right click with an empty hand");

			// fitted carpets
			enableFittedCarpets = configFile.getBoolean("fittedCarpets", "tweaks", enableFittedCarpets, "Carpets fit to stairs. Uses a block override, so disable if another mod replaces carpets");

			// bonemeal
			enableExtraBonemeal = configFile.getBoolean("extraBonemeal", "tweaks", enableExtraBonemeal, "Bonemeal can be used on mycelium to produce mushrooms and on sand to produce dead bushes");

			// heartroot
			enableHeartbeet = configFile.getBoolean("heartbeet", "tweaks", enableHeartbeet, "Enables heartbeets: a rare drop from beetroots which can be eaten to restore a bit of health");
			brewHeartbeet = configFile.getBoolean("brewRegeneration", "tweaks.heartbeet", brewHeartbeet, "Allows heartbeets to be used as an alternative to ghast tears in making potions of regeneration") && enableHeartbeet;

			// dispensers place anvils
			dispensersPlaceAnvils = configFile.getBoolean("dispensersPlaceAnvils", "tweaks", dispensersPlaceAnvils, "Dispensers will place anvils instead of dropping them. Plays well with anvil smashing.");

			// harvest hanging vines
			harvestHangingVines = configFile.getBoolean("harvestHangingVines", "tweaks", harvestHangingVines, "When shearing vines, any supported vines will also be sheared instead of just broken");

			// better cauldron item
			betterCauldronItem = configFile.getBoolean("betterCauldronItemModel", "tweaks", betterCauldronItem, "Replaces the flat cauldron sprite with the 3D cauldron block model");

			// shears reclaim melons
			shearsReclaimMelons = configFile.getBoolean("shearsReclaimMelons", "tweaks", shearsReclaimMelons, "Breaking a melon block with shears will always return 9 slices");

			// better flower pots
			betterFlowerPot = configFile.getBoolean("betterFlowerPot", "tweaks", betterFlowerPot, "Flower pots can hold modded flowers");
			flowerPotComparator = configFile.getBoolean("comparator", "tweaks.betterFlowerPot", flowerPotComparator, "Flower pots will emit a comparator signal if they have a flower");

			// colored enchanted book ribbons
			coloredEnchantedRibbons = configFile.getBoolean("coloredEnchantedRibbons", "tweaks", coloredEnchantedRibbons, "The ribbon on enchanted books colors based on the enchantment rarity");

			// more potions
			brewMissingPotions = configFile.getBoolean("brewMissingPotions", "tweaks", brewMissingPotions, "Adds brewing recipes for vanilla potions which are missing a recipe");

			// colored fireworks
			coloredFireworkItems = configFile.getBoolean("coloredFireworkItems", "tweaks", coloredFireworkItems, "Colors the fireworks item based on the colors of the stars");

			// lilypad fall breaking
			lilypadBreakFall = configFile.getBoolean("lilypadBreakFall", "tweaks", lilypadBreakFall, "Lily pads prevent fall damage, but break in the process");
		}

		// compatibility
		{
			// TAN Plugin: make juice in cauldron
			tanJuiceInCauldron = configFile.getBoolean("tanJuiceInCauldron", "compatibility", tanJuiceInCauldron, "Enables making Tough as Nails juices in the cauldron. Requires enhanced cauldron") && enableCauldronFluids;
		}

		// saving
		if(configFile.hasChanged()) {
			configFile.save();
		}
	}

	/**
	 * Anything which we need access to block or item registries
	 * @param event
	 */
	public static void init(FMLInitializationEvent event) {
		// building
		bookOverrides = configFile.get("building.bookshelf", "bookOverrides", bookOverrides,
				"List of itemstacks to override book behavior. Format is modid:name[:meta[:isBook]]. Unset meta will default wildcard. Unset isBook will default true").getStringList();
		processItemOverrides(enableBookshelf, bookOverrides, InspirationsRegistry::registerBook);

		// anvil smashing
		// skip the helper method so the defaults are not put in the comment
		configFile.moveProperty("tweaks.anvilSmashing", "recipes.anvilSmashing", "smashing");
		anvilSmashing = configFile.get("recipes.anvilSmashing", "smashing", anvilSmashing,
				"List of blocks to add to anvil smashing. Format is modid:input[:meta][->modid:output[:meta]]. If the output is excluded, it will default to air (breaking the block). If the meta is excluded, it will check all states for input and use the default for output").getStringList();
		processAnvilSmashing(anvilSmashing);

		// cauldron uses
		cauldronRecipes = configFile.get("recipes.cauldronRecipes", "recipes", cauldronRecipes,
				"List of recipes to add to the cauldron on right click. Format is (modid:input:meta|oreString)->modid:output:meta[->isBoiling]. If isBoiling is excluded, it defaults to false.").getStringList();
		processCauldronRecipes(cauldronRecipes);

		// flowers
		flowerOverrides = configFile.get("tweaks.betterFlowerPot", "flowerOverrides", flowerOverrides,
				"List of itemstacks to override flower behavior, which defaults to the block being BlockBush. Format is modid:name[:meta[:isFlower]]. Unset meta will default wildcard. Unset isFlower will default true").getStringList();
		processItemOverrides(betterFlowerPot, flowerOverrides, InspirationsRegistry::registerFlower);

		// saving
		if(configFile.hasChanged()) {
			configFile.save();
		}
	}

	/**
	 * Parses the book overrides from the string array
	 * @param overrides  Input string array
	 */
	private static void processItemOverrides(boolean condition, String[] overrides, BiConsumer<ItemStack, Boolean> callback) {
		if(!condition) {
			return;
		}

		NonNullList<ItemStack> stacks;
		String[] parts;
		ItemStack stack;
		boolean isBook;
		// simply look through each entry
		for(String override : overrides) {
			// skip blank lines
			if("".equals(override) || override.startsWith("#")) {
				continue;
			}

			// split by semicolons, valid keys are length of 2, 3, or 4
			parts = override.split(":");
			if(parts.length < 2 || parts.length > 4) {
				Inspirations.log.error("Invalid override {}: must be in format modid:name[:meta[:value]]. ", override);
				continue;
			}

			String itemString = override;
			if(parts.length > 2) {
				itemString = itemString.substring(0, override.length() - parts[3].length() - 1);
			}
			if(!RecipeUtil.isValidItemStack(itemString, true)) {
				Inspirations.log.error("Invalid override {}: invalid item {}", override, itemString);
				continue;
			}
			stack = RecipeUtil.getItemStackFromString(itemString, true);
			if(stack.isEmpty()) {
				Inspirations.log.warn("Unable to find item {} for override", itemString);
				continue;
			}

			// finally, parse the isBook boolean. Pretty lazy here, just check if its not the string false
			isBook = parts.length > 3 ? !"false".equals(parts[3]) : true;

			// finally, add the entry
			if(stack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
				// wildcard iterates through stacks
				stacks = NonNullList.create();
				stack.getItem().getSubItems(CreativeTab.SEARCH, stacks);
				for(ItemStack sub : stacks) {
					callback.accept(sub, isBook);
				}
			} else {
				callback.accept(stack, isBook);
			}
		}
	}

	/**
	 * Parses the anvil smashing array into the registry
	 * @param transformations  Input array
	 */
	@SuppressWarnings("deprecation")
	private static void processAnvilSmashing(String[] transformations) {
		if(!enableAnvilSmashing) {
			return;
		}

		main:
			for(String transformation : transformations) {
				// skip blank lines
				if("".equals(transformation) || transformation.startsWith("#")) {
					continue;
				}

				// first, ensure we have the right number of inputs
				// it should be 1 for plain old smashing or two for a transformation
				String[] transformParts = transformation.split("->");
				if(transformParts.length > 2 || transformParts.length < 1) {
					Inspirations.log.error("Invalid anvil smashing {}: must be in the format of modid:input[:meta][->modid:output[:meta]]", transformation);
					continue;
				}

				// find blockstates for the input and output
				// loop so I am not doing this twice
				Block[] blocks = new Block[2];
				IBlockState[] states = new IBlockState[2];
				int meta;
				for(int i = 0; i < transformParts.length; i++) {
					// split into parts
					String transformPart = transformParts[i];
					String[] parts = transformPart.split(":");

					// should have name and ID with optional meta
					if(parts.length > 3 || parts.length < 2) {
						Inspirations.log.warn("Invalid anvil smashing {}: invalid parameter length for {}, expected modid:blockid[:meta]",
								transformation, transformPart);
						continue main;
					}

					// try parsing the metadata
					meta = -1;
					if(parts.length > 2) {
						try {
							meta = Integer.parseInt(parts[2]);
						} catch(NumberFormatException e) {
							meta = -1;
						}
						// handle invalid numbers and negatives here
						if(meta < 0) {
							Inspirations.log.error("Invalid anvil smashing {}: invalid metadata for {}", transformation, transformPart);
							continue main;
						}
					}

					// next, try finding the block
					blocks[i] = GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(parts[0], parts[1]));
					if(blocks[i] == Blocks.AIR) {
						Inspirations.log.warn("Unable to find block {}:{} for transformation {}", parts[0], parts[1], transformation);
						continue main;
					}

					// if we have meta, parse the blockstate
					if(meta > -1) {
						states[i] = blocks[i].getStateFromMeta(meta);
					}
				}
				// if the length is 1, this is block breaking, so use air for the output
				if(transformParts.length == 1) {
					blocks[1] = Blocks.AIR;
				}

				// if no result state, just grab the default state. That is all the registry does anyways
				if(states[1] == null) {
					states[1] = blocks[1].getDefaultState();
				}

				// determine whether to use block or blockstate parameter
				if(states[0] == null) {
					InspirationsRegistry.registerAnvilSmashing(blocks[0], states[1]);
				} else {
					InspirationsRegistry.registerAnvilSmashing(states[0], states[1]);
				}
			}
	}

	private static void processCauldronRecipes(String[] cauldronRecipes) {
		if(!enableCauldronRecipes) {
			return;
		}

		for(String recipe : cauldronRecipes) {
			// skip blank lines
			if("".equals(recipe) || recipe.startsWith("#")) {
				continue;
			}

			String[] parts = recipe.split("->");
			if(parts.length < 2 || parts.length > 3) {
				Inspirations.log.error("Invalid cauldron recipe {}: must be in format input->output[->isBoiling]", recipe);
				continue;
			}

			// input
			ItemStack input = null;
			if(parts[0].contains(":")) {
				if(!RecipeUtil.isValidItemStack(parts[0], true)) {
					Inspirations.log.error("Invalid cauldron recipe {}: invalid input {}", recipe, parts[0]);
					continue;
				}

				input = RecipeUtil.getItemStackFromString(parts[0], true);
				if(input.isEmpty()) {
					Inspirations.log.error("Unable to find item {} for recipe {}", parts[0], recipe);
					continue;
				}
			}

			// output
			if(!RecipeUtil.isValidItemStack(parts[1], false)) {
				Inspirations.log.error("Invalid cauldron recipe {}: invalid output {}", recipe, parts[1]);
				continue;
			}
			ItemStack output = RecipeUtil.getItemStackFromString(parts[1], false);
			if(output.isEmpty()) {
				Inspirations.log.error("Unable to find item {} for recipe {}", parts[0], recipe);
				continue;
			}

			// add recipe
			boolean boiling = parts.length > 2 ? parts[2].equals("true") : false;
			// if the input is empty, we are using an oreString
			if(input == null) {
				InspirationsRegistry.addCauldronRecipe(parts[0], output, boiling);
			} else {
				InspirationsRegistry.addCauldronRecipe(input, output, boiling);
			}
		}
	}


	/*
	 * Factories for recipe conditions
	 */

	public static class PulseLoaded implements IConditionFactory {
		@Override
		public BooleanSupplier parse(JsonContext context, JsonObject json) {
			String pulse = JsonUtils.getString(json, "pulse");
			return () -> Inspirations.pulseManager.isPulseLoaded(pulse);
		}
	}

	public static class ConfigProperty implements IConditionFactory {
		@Override
		public BooleanSupplier parse(JsonContext context, JsonObject json) {
			String prop = JsonUtils.getString(json, "prop");
			return () -> propertyEnabled(prop);
		}

		private static boolean propertyEnabled(String property) {
			switch(property) {
				// building
				case "bookshelf": return enableBookshelf;
				case "colored_books": return enableColoredBooks;
				case "enlightened_bush": return enableEnlightenedBush;
				case "flowers": return enableFlowers;
				case "glass_door": return enableGlassDoor;
				case "mulch": return enableMulch;
				case "path": return enablePath;
				case "rope": return enableRope;

				// utility
				case "bricks_button": return enableBricksButton;
				case "carpeted_trapdoor": return enableCarpetedTrapdoor;
				case "lock": return enableLock;
				case "redstone_barrel": return enableRedstoneBarrel;
				case "redstone_book": return enableRedstoneBook;
				case "redstone_charge": return enableRedstoneCharge;
				case "redstone_torch_lever": return enableRedstoneTorchLever;
				case "torch_lever": return enableTorchLever;

				// recipes
				case "cauldron_dyeing": return enableCauldronDyeing;
				case "cauldron_fluids": return enableCauldronFluids;
				case "cauldron_potions": return enableCauldronPotions;
				case "extra_dyed_bottle_recipes": return extraBottleRecipes;
				case "patch_vanilla_dye_recipes": return patchVanillaDyeRecipes;
			}

			throw new JsonSyntaxException("Invalid propertyname '" + property + "'");
		}
	}
}
