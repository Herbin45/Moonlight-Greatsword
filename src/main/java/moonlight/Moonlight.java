 package moonlight;
 
 import necesse.engine.modLoader.annotations.ModEntry;
 import necesse.engine.registries.ItemRegistry;
 import necesse.engine.registries.ProjectileRegistry;
 import necesse.engine.registries.RecipeTechRegistry;
 import necesse.inventory.item.Item;
import necesse.inventory.item.miscItem.CoinItem;
import necesse.inventory.recipe.Ingredient;
 import necesse.inventory.recipe.Recipe;
 import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.CoinPileObject;
 
 
 
 @ModEntry
 public class Moonlight
 {
   public void initResources() {}
   
   public void init() {
     ProjectileRegistry.registerProjectile("moonlight", MoonlightProjectile.class, "moonlight", (String)null);
     ItemRegistry.registerItem("moonlightgs", (Item)new MoonlightGreatsword(), 2300.0F, true);
     System.out.println((ProjectileRegistry.getProjectile("moonlight")).idData.getStringID());
     
     
   }
   
   public void postInit() {
     Recipes.registerModRecipe(new Recipe("moonlightgs", 1, RecipeTechRegistry.TUNGSTEN_ANVIL, new Ingredient[] { new Ingredient("glacialbar", 10), new Ingredient("myceliumbar", 10), new Ingredient("tungstenbar", 10) }));
   }
 }





