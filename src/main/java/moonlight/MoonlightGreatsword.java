package moonlight;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.enchants.ToolItemModifiers;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.lootTable.presets.CloseRangeWeaponsLootTable;
import necesse.level.maps.Level;

public class MoonlightGreatsword
  extends SwordToolItem {
  public MoonlightGreatsword() {
    super(2300, CloseRangeWeaponsLootTable.closeRangeWeapons);
    this.rarity = Item.Rarity.LEGENDARY;
    this.attackAnimTime.setBaseValue(350);
    this.attackDamage.setBaseValue(88.0F).setUpgradedValue(1.0F, 110.0F);
    this.attackRange.setBaseValue(110);
    this.knockback.setBaseValue(75);
    this.resilienceGain.setBaseValue(1.0F);
  }
  
  public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
    item = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);

    
    float rangeMod = 7.0F;
    float velocity = 140.0F;
    float finalVelocity = (float)Math.round((Float)this.getEnchantment(item).applyModifierLimited(ToolItemModifiers.VELOCITY, (Float)ToolItemModifiers.VELOCITY.defaultBuffManagerValue) * velocity * (Float)attackerMob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY));
    
    MoonlightProjectile moonlightProjectile = new MoonlightProjectile(level, attackerMob.x, attackerMob.y, x, y, finalVelocity, (int)(getAttackRange(item) * rangeMod), getAttackDamage(item), attackerMob);
    GameRandom random = new GameRandom(seed);
    moonlightProjectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item) / 2.0F));
    moonlightProjectile.resetUniqueID(new GameRandom((long)seed));
    attackerMob.addAndSendAttackerProjectile(moonlightProjectile, 20);
    
    return item;
  }
  
  public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
    ListGameTooltips tooltips = super.getTooltips(item, perspective, null);
    tooltips.add(Localization.translate("itemtooltip", "moonlightgs"));
    return tooltips;
  }
}





