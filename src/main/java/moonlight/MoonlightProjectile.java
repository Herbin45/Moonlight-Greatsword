 package moonlight;
 
 import java.awt.Color;
 import java.util.List;

 import necesse.engine.gameLoop.tickManager.TickManager;
 import necesse.entity.mobs.GameDamage;
 import necesse.entity.mobs.Mob;
 import necesse.entity.mobs.PlayerMob;
 import necesse.entity.projectile.Projectile;
 import necesse.entity.trails.Trail;
 import necesse.gfx.camera.GameCamera;
 import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
 import necesse.gfx.drawables.EntityDrawable;
 import necesse.gfx.drawables.LevelSortedDrawable;
 import necesse.gfx.drawables.OrderableDrawables;
 import necesse.level.maps.Level;
 import necesse.level.maps.light.GameLight;
 
 
 public class MoonlightProjectile
   extends Projectile
 {
   public MoonlightProjectile() {}
   
   public MoonlightProjectile(Level level, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, Mob owner) {
     setLevel(level);
     this.x = x;
     this.y = y;
     setTarget(targetX, targetY);
     this.speed = speed;
     setDamage(damage);
     setOwner(owner);
     setDistance(distance);
   }
   
   public Color getParticleColor() {
     return new Color(20, 50, 120);
   }
   
   public void init() {
     super.init();
     this.piercing = 1;
     this.height = 16.0F;
     setWidth(45.0F, true);
     this.isSolid = true;
     this.givesLight = true;
     this.particleRandomOffset = 14.0F;
   }
   
   public Trail getTrail() {
     return null;
   }
 
   
   public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
	        if (removed())
	          return;  float alpha = getFadeAlphaTime(300, 200);
	        GameLight light = level.getLightLevel(this);
	        int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
	        int drawY = camera.getDrawY(this.y - getHeight()) - this.texture.getHeight() / 2;
	    
	    
	    
	        
	        final TextureDrawOptionsEnd options = this.texture.initDraw().light(light.minLevelCopy(Math.min(light.getLevel() + 100.0F, 150.0F))).rotate(getAngle() - 135.0F, this.texture.getWidth() / 2, this.texture.getHeight() / 2).alpha(alpha).pos(drawX, drawY);
	        
	        list.add(new EntityDrawable(this)
	            {
	              public void draw(TickManager tickManager) {
	                options.draw();
	              }
	            });
	      }
   
 }





