import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import components.*;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.paint.Color;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayerFactory implements EntityFactory
{
    //Player factory
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);
        physicsComponent.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(20, 30)));

        physicsComponent.setFixtureDef(new FixtureDef().friction(0.0f));

        return entityBuilder(data)
                .type(GameType.PLAYER)
                .bbox(new HitBox(new Point2D(10, 25), BoundingShape.box(31, 17)))
                .with(physicsComponent)
                .with(new CollidableComponent(true))
                .with(new PlayerComponent())
                .zIndex(5)
                .build();
    }

    @Spawns("coin")
    public Entity newCoin(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .type(GameType.COIN)
                .viewWithBBox(new Circle(10, Color.TRANSPARENT))
                .scale(2, 2)
                .with(new CollidableComponent(true))
                .with(new CoinsComponent())
                .build();
    }


    @Spawns("fireballR")
    public Entity newFireballR(SpawnData data) throws FileNotFoundException {
        Point2D dir = data.get("dir");

        return entityBuilder(data)
                .view(texture("fire_ballR.png"))
                .type(GameType.FIREBALL)
                .with(new ProjectileComponent(dir, 500))
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(33, 17), BoundingShape.box(33, 17)))
                .build();
    }

    @Spawns("fireballL")
    public Entity newFireballL(SpawnData data) throws FileNotFoundException {
        Point2D dir = data.get("dir");


        return entityBuilder(data)
                .view(texture("fire_ballR.png"))
                .type(GameType.FIREBALL)
                .with(new ProjectileComponent(dir, -500))
                .bbox(new HitBox(new Point2D(33, 17), BoundingShape.box(33, 17)))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("OutOfBounce")
    public Entity newBounce1(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("outofbounce.png"))
                .type(GameType.DOOM)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(4000, 3)))
                .build();
    }
    //----


    //Portals
    @Spawns("TTportal")
    public Entity newPortal1(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("portal/TTportal.png"))
                .type(GameType.PORTALT)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(50, 50)))
                .build();
    }

    @Spawns("LV1portal")
    public Entity newPortal2(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("portal/LV1portal.png"))
                .type(GameType.PORTAL1)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(50, 50)))
                .build();
    }

    @Spawns("LV2portal")
    public Entity newPortal3(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("portal/LV2portal.png"))
                .type(GameType.PORTAL2)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(50, 50)))
                .build();
    }

    @Spawns("LV3portal")
    public Entity newPortal4(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("portal/LV3portal.png"))
                .type(GameType.PORTAL3)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(50, 50)))
                .build();
    }

    @Spawns("LV4portal")
    public Entity newPortal5(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("portal/LV4portal.png"))
                .type(GameType.PORTAL4)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(50, 50)))
                .build();
    }
    //----



    //tutorial factory
    @Spawns("basicPlatform")
    public Entity newPlatform1(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("tut/platform_basic.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(172, 44)))
                .build();
    }

    @Spawns("tutUnder")
    public Entity newUnderground1(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("tut/tutorial_underground.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .bbox(new HitBox(BoundingShape.box(1024, 70)))
                .build();
    }

    @Spawns("tutTree1")
    public Entity newTree1(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("tut/Ttree1.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(70, 78)))
                .build();
    }

    @Spawns("tutTree2")
    public Entity newTree2(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("tut/Ttree2.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(98, 171)))
                .build();
    }

    @Spawns("TtextJ")
    public Entity TXTjump(SpawnData data) throws FileNotFoundException{
        return entityBuilder(data)
                .view(texture("tut/jump.png"))
                .type(GameType.TEXT)
                .build();
    }

    @Spawns("TtextM")
    public Entity TXTmove(SpawnData data) throws FileNotFoundException{
        return entityBuilder(data)
                .view(texture("tut/movement.png"))
                .type(GameType.TEXT)
                .build();
    }

    @Spawns("TtextDJ")
    public Entity TXTdj(SpawnData data) throws FileNotFoundException{
        return entityBuilder(data)
                .view(texture("tut/dJump.png"))
                .type(GameType.TEXT)
                .build();}

    @Spawns("TtextSHOOT")
    public Entity TXTshoot(SpawnData data) throws FileNotFoundException{
        return entityBuilder(data)
                .view(texture("tut/shoot.png"))
                .type(GameType.TEXT)
                .build();}

    @Spawns("TtextP")
    public Entity TXTportal(SpawnData data) throws FileNotFoundException{
        return entityBuilder(data)
                .view(texture("tut/portalT.png"))
                .type(GameType.TEXT)
                .build();}
    //----


    //Dark Castle Factory (LV4)
    @Spawns("DCstatue")
    public Entity DCstatue(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("dc/statue.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(47, 96)))
                .build();
    }

    @Spawns("DCwallL")
    public Entity DCwallL(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("dc/wallL.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(30, 220)))
                .zIndex(1)
                .build();
    }

    @Spawns("DCwallS")
    public Entity DCwallS(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("dc/wallS.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(30, 100)))
                .zIndex(2)
                .build();
    }

    @Spawns("DCplatform")
    public Entity DCplatform(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("dc/platform.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(400, 50)))
                .build();
    }

    @Spawns("DCplatBoss")
    public Entity DCplatBoss(SpawnData data) throws FileNotFoundException {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.KINEMATIC);

        physicsComponent.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(940, 86), BoundingShape.box(940, 86)));
        physicsComponent.setFixtureDef(new FixtureDef().friction(1.0f));

        return entityBuilder(data)
                .view(texture("dc/bossplat.png"))
                .type(GameType.BOSSPLATFORM)
                .with(physicsComponent)
                .with(new CollidableComponent(true))
                .with(new BossPlatComponent())
                .bbox(new HitBox(BoundingShape.box(940, 86)))
                .build();
    }

    @Spawns("DCplatBoss2")
    public Entity DCplatBoss2(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("dc/bossplat.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(940, 86)))
                .build();
    }

    @Spawns("DClava")
    public Entity DClava(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("dc/lava.png"))
                .type(GameType.DOOM)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(300, 100)))
                .build();
    }
    //----

    //Factory Level1
    @Spawns("ground")
    public Entity newGround(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .type(GameType.GROUND)
                .view(texture("ground.png"))
                .with(new PhysicsComponent())
                .bbox(new HitBox(BoundingShape.box(1282, 85)))
                .build();
    }
    @Spawns("big_platform")
    public Entity newBigPlatform(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("big_platform.png"))
                .type(GameType.BIG_PLATFORM)
                .with(new PhysicsComponent())
                .bbox(new HitBox(BoundingShape.box(250, 200)))
                .build();
    }

    //noah lvl1
    @Spawns("high_platform")
    public Entity newHighPlatform(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("high_platform.png"))
                .type(GameType.HIGH_PLATFORM)
                .with(new PhysicsComponent())
                .bbox(new HitBox(BoundingShape.box(250, 400)))
                .build();
    }

    //noah lvl 1
    @Spawns("cloud_platform")
    public Entity newCloudPlatform(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("cloud_platform.png"))
                .type(GameType.CLOUD_PLATFORM)
                .with(new PhysicsComponent())
                .bbox(new HitBox(BoundingShape.box(247, 19)))
                .build();
    }

    //noah lvl1
    @Spawns("spikes")
    public Entity newSpikes(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .type(GameType.DOOM)
                .view("spikes.png")
                .bbox(new HitBox(BoundingShape.box(96, 18)))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }

    //noah lvl1
    @Spawns("star")
    public Entity newStar(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .type(GameType.STAR)
                .viewWithBBox(new Circle(25, Color.TRANSPARENT))
                .with(new CollidableComponent(true))
                .with(new StarComponent())
                .build();
    }

    @Spawns("pipe")
    public Entity newPipe(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .type(GameType.PIPE)
                .view(texture("pipe.png"))
                .with(new PhysicsComponent())
                .bbox(new HitBox(BoundingShape.box(63, 61)))
                .build();}

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) throws FileNotFoundException {

        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);
        physicsComponent.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(20, 30)));

        physicsComponent.setFixtureDef(new FixtureDef().friction(0.0f));

        return entityBuilder(data)
                .type(GameType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(126, 104)))
                .with(physicsComponent)
                .with(new CollidableComponent(true))
                .with(new EnemyComponent())
                .build();
    }

    @Spawns("DCboss")
    public Entity newBoss(SpawnData data) throws FileNotFoundException {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.KINEMATIC);

        physicsComponent.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(940, 86), BoundingShape.box(940, 86)));
        physicsComponent.setFixtureDef(new FixtureDef().friction(1.0f));

        return entityBuilder(data)
                .type(GameType.BOSS)
                .view(texture("dc/boss.png"))
                .bbox(new HitBox(BoundingShape.box(118, 97)))
                .with(physicsComponent)
                .with(new CollidableComponent(true))
                .with(new BossComponent())
                .build();
    }

    // Level 2 factory
    @Spawns("M_platform")
    public Entity newPlatform2(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("tut/Mossy_platform.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(172, 44)))
                .build();
    }

    @Spawns("M_pillar")
    public Entity newPillar1(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("tut/Mossy_pillar.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(300, 900)))
                .build();
    }

    @Spawns("Wall")
    public Entity newwall(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("tut/Mossy_wall.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(98, 171)))
                .build();
    }
    //----

    //Factory level3
    @Spawns("shadowplatform")
    public Entity newShadowplatform(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .viewWithBBox(texture("Level3/shadowplatform.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .scale(1.5, 1.5)
                .build();
    }

    @Spawns("shadowplatform_crumble")
    public Entity newShadowplatformCrumble(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .viewWithBBox(texture("Level3/shadowplatform_crumble.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .scale(1.5, 1.5)
                .build();
    }

    @Spawns("shadowstatue")
    public Entity newShadowstatue(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("Level3/shadowstatue.png"))
                .type(GameType.PLATFORM)
                .scale(1.5, 1.5)
                .build();
    }

    @Spawns("shadowstatue_text")
    public Entity newShadowstatueText(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("Level3/shadowstatue_text.png"))
                .type(GameType.PLATFORM)
                .scale(0.3, 0.3)
                .build();
    }

    @Spawns("shadowplatform_basic")
    public Entity newShadowplatformBasic(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .viewWithBBox(texture("Level3/shadowplatform_basic.png"))
                .type(GameType.PLATFORM)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("feather")
    public Entity newFeather(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .viewWithBBox(texture("Level3/feather_bubble.png"))
                .with(new CollidableComponent(true))
                .type(GameType.FEATHER)
                .scale(0.6, 0.6)
                .build();
    }
    //----

    //Boss Attack Factory
    @Spawns("Bslice")

    public Entity newSLICE(SpawnData data) throws FileNotFoundException {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.KINEMATIC);
        physicsComponent.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(126, 132), BoundingShape.box(118, 132)));
        physicsComponent.setFixtureDef(new FixtureDef().friction(1.0f));
        return entityBuilder(data)
                .view(texture("bossAttack/slice.png"))
                .type(GameType.BDOOM)
                .with(physicsComponent)
                .with(new SliceComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(126, 132), BoundingShape.box(126, 132)))
                .build();
    }

    @Spawns("Bspell")
    public Entity newSPELL(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("bossAttack/circle.png"))
                .with(new CircleComponent())
                .type(GameType.BDOOM)
                .build();
    }

    @Spawns("Bexplosion")
    public Entity newE(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .type(GameType.BDOOM)
                .with(new CollidableComponent(true))
                .with(new ExplosionComponent())
                .bbox(new HitBox(new Point2D(100, 100), BoundingShape.box(100, 100)))
                .build();
    }

    @Spawns("box")
    public Entity box(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .type(GameType.DOOM)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(20, 100), BoundingShape.box(20, 100)))
                .build();
    }

    @Spawns("ghost")
    public Entity newGhost(SpawnData data) throws FileNotFoundException {

        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);
        physicsComponent.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(20, 30)));

        physicsComponent.setFixtureDef(new FixtureDef().friction(0.0f));

        return entityBuilder(data)
                .type(GameType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(24, 24)))
                .with(physicsComponent)
                .with(new CollidableComponent(true))
                .with(new GhostComponent())
                .scale(2.2, 2.2)
                .build();
    }

    @Spawns("ghostspawner")
    public Entity newGhostSpawner(SpawnData data) throws FileNotFoundException {
        return entityBuilder(data)
                .view(texture("ghost_spawner.png"))
                .type(GameType.PLATFORM)
                .scale(2.2, 2.2)
                .build();
    }

}


