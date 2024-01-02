package moonlight;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.enchants.ToolItemModifiers;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.level.maps.Level;

public class MoonlightGreatsword
  extends SwordToolItem {
  public MoonlightGreatsword() {
    super(2300);
    this.rarity = Item.Rarity.EPIC;
    this.animSpeed = 450;
    this.attackDamage = new GameDamage(DamageTypeRegistry.MELEE, 75.0F);
    this.attackRange = 70;
    this.knockback = 75;
  }
  
  public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
    item = super.onAttack(level, x, y, player, attackHeight, item, slot, animAttack, seed, contentReader);

    
    float rangeMod = 7.0F;
    float velocity = 140.0F;
    float finalVelocity = Math.round(((Float)getEnchantment(item).applyModifier(ToolItemModifiers.VELOCITY, ToolItemModifiers.VELOCITY.defaultBuffManagerValue)).floatValue() * velocity * ((Float)player.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY)).floatValue());
    
    MoonlightProjectile moonlightProjectile = new MoonlightProjectile(level, player.x, player.y, x, y, finalVelocity, (int)(getAttackRange(item) * rangeMod), getDamage(item), (Mob)player);
    GameRandom random = new GameRandom(seed);
    moonlightProjectile.resetUniqueID(random);
    level.entityManager.projectiles.addHidden(moonlightProjectile);
    moonlightProjectile.moveDist(20.0D);
    if (level.isServer()) {
      (level.getServer()).network.sendToClientsAtExcept((Packet)new PacketSpawnProjectile(moonlightProjectile), player.getServerClient(), player.getServerClient());
    }
    
    return item;
  }
  
  public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
    if (target.getDistance((Mob)mob) <= getAttackRange(item)) {
      
      item = super.onSettlerAttack(level, mob, target, attackHeight, seed, item);
    } else {
      mob.attackItem(target.getX(), target.getY(), item);
    } 
    
    float rangeMod = 7.0F;
    float velocity = 140.0F;
    float finalVelocity = Math.round(((Float)getEnchantment(item).applyModifier(ToolItemModifiers.VELOCITY, ToolItemModifiers.VELOCITY.defaultBuffManagerValue)).floatValue() * velocity * ((Float)mob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY)).floatValue());
    
    MoonlightProjectile moonlightProjectile = new MoonlightProjectile(level, mob.x, mob.y, target.x, target.y, finalVelocity, (int)(getAttackRange(item) * rangeMod), getDamage(item), (Mob)mob);
    GameRandom random = new GameRandom(seed);
    moonlightProjectile.resetUniqueID(random);
    level.entityManager.projectiles.addHidden(moonlightProjectile);
    moonlightProjectile.moveDist(20.0D);
    if (level.isServer()) {
      (level.getServer()).network.sendToClientsAt((Packet)new PacketSpawnProjectile(moonlightProjectile), level);
    }
    
    return item;
  }
  
  public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
    ListGameTooltips tooltips = super.getTooltips(item, perspective);
    tooltips.add(Localization.translate("itemtooltip", "moonlightgs"));
    return tooltips;
  }
}





