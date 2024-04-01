import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.TimerAction;
import components.*;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import components.GhostComponent;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Game extends GameApplication
{
    private Entity player;
    private Entity enemy;
    private Entity bossWall;
    public Entity bossFloor;
    public Entity bossSpawn;
    private Entity ghost;
    private boolean isMouseEvents = true;

    @Override
    protected void initSettings(GameSettings settings)
    {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setMainMenuEnabled(true);
        settings.setIntroEnabled(true);
        settings.setTitle("totally not mario");
        settings.setVersion("1.0");
    }

    @Override
    public void initGame()
    {
        getGameWorld().addEntityFactory(new PlayerFactory());
        TutorialWorld();
    }

    @Override
    public void initInput()
    {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                isMouseEvents = true;
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                isMouseEvents = true;
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                isMouseEvents = false;
                player.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.W);

        getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onActionEnd() {
                isMouseEvents = false;
                player.getComponent(PlayerComponent.class).shoot();
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initUI() {
        Label myText = new Label("hello there");
        myText.setTextFill(Color.PURPLE);
        myText.setStyle("-fx-font-size: 20pt");
        myText.setTranslateX(80);
        myText.setTranslateY(5);
        myText.textProperty().bind(FXGL.getWorldProperties().intProperty("coins").asString());

        Label scoreLabel = new Label("Score:");
        scoreLabel.setTextFill(Color.PURPLE);
        scoreLabel.setStyle("-fx-font-size: 20pt");
        scoreLabel.setTranslateX(5);
        scoreLabel.setTranslateY(4);

        Label doomCounter = new Label("DOOM");
        doomCounter.setTextFill(Color.PURPLE);
        doomCounter.setStyle("-fx-font-size: 30pt");
        doomCounter.setTranslateX(715);
        doomCounter.setTranslateY(12);
        doomCounter.textProperty().bind(FXGL.getWorldProperties().intProperty("DOOM").asString());

        Label doomLabel = new Label("Deaths:");
        doomLabel.setTextFill(Color.PURPLE);
        doomLabel.setStyle("-fx-font-size: 30pt");
        doomLabel.setTranslateX(580);
        doomLabel.setTranslateY(10);

        FXGL.getGameScene().addUINode(myText);
        FXGL.getGameScene().addUINode(scoreLabel);
        FXGL.getGameScene().addUINode(doomCounter);
        FXGL.getGameScene().addUINode(doomLabel);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("coins", 0);
        vars.put("DOOM", 0);
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.10);
        loopBGM("soundtrack.wav");
    }


    @Override
    protected void initPhysics()
    {
        getPhysicsWorld().setGravity(0, 500);

        //global collisions
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.COIN) {
            @Override
            protected void onCollision(Entity player, Entity coin) {
                FXGL.inc("coins", +5);
                coin.removeFromWorld();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBALL, GameType.ENEMY) {
            @Override
            protected void onCollision(Entity a, Entity b) {
                b.removeFromWorld();
                a.removeFromWorld();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.STAR) {
            @Override
            protected void onCollision(Entity player, Entity star) {
                FXGL.inc("coins", +50);
                star.removeFromWorld();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBALL, GameType.PLATFORM) {
            protected void onCollision(Entity fireball, Entity platform) {
                fireball.removeFromWorld();
            }});

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOM) {
            protected void onCollision(Entity a, Entity b) {
                getGameWorld().removeEntities(a);
                FXGL.getGameTimer().clear();
                FXGL.set("coins", 0);
                FXGL.inc("DOOM", +1);
                TutorialWorld();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY) {
            protected void onCollision(Entity a, Entity b) {
                getGameWorld().removeEntities(a);
                FXGL.getGameTimer().clear();
                FXGL.set("coins", 0);
                FXGL.inc("DOOM", +1);
                TutorialWorld();
            }
        });
        //----


        // Tutorial collisions

        //----

        //Build LV1
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.PORTALT) {
            protected void onCollision(Entity a, Entity portal) {
                getGameWorld().removeEntities(a);
                Level1();
            }
        });//Build LV1 collisions here:


        //----


        //Build lv2
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.PORTAL1) {
            protected void onCollision(Entity a, Entity portal) {
                getGameWorld().removeEntities(a);
                FXGL.getGameTimer().clear();
                Level2();
            }
        });//Build LV2 collisions here:

        //----



        //Build lv3
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.PORTAL2) {
            protected void onCollision(Entity a, Entity portal) {
                getGameWorld().removeEntities(a);
                Level3();
            }
        });//Build LV3 collisions here:
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FEATHER) {
            protected void onCollision(Entity player, Entity feather) {
                feather.removeFromWorld();
                player.getComponent(PlayerComponent.class).feather();
            }
        });
        //----


        //Build LV4 collisions here:
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.PORTAL3) {
            protected void onCollision(Entity a, Entity portal) {
                getGameWorld().removeEntities(a);
                getGameTimer().clear();
                Level4();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.PORTAL4) {
            protected void onCollision(Entity a, Entity portal) {
                int x = FXGL.geti("coins");
                File scoreFILE = new File("Highscores.txt");
                try {
                    FileWriter verkoopSchrijver = new FileWriter(scoreFILE);
                    verkoopSchrijver.write(Integer.toString(x));
                    verkoopSchrijver.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

                getDisplay().showMessageBox("You completed the game",
                        Platform::exit
                );

            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLATFORM, GameType.BOSSPLATFORM) {
            protected void onCollision(Entity a, Entity b) {
                b.getComponent(BossPlatComponent.class).stopMove();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPLATFORM) {
            protected void onCollision(Entity a, Entity b) {
                bossWall = spawn("DCwallL", 2130, -300);
                b.getComponent(BossPlatComponent.class).goMove();
                FXGL.getAudioPlayer().stopAllMusic();
                if (b.isColliding(bossWall)){
                    b.removeFromWorld();
                    loopBGM("hollow.wav");
                    bossFloor = spawn("DCplatBoss2", b.getX(), b.getY());
                    bossSpawn = spawn("DCboss", 3850, -200);
                }
            }
        });



        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.BOSS, GameType.FIREBALL) {
            protected void onCollision(Entity a, Entity b) {
                a.getComponent(BossComponent.class).bossHP(1);
                b.removeFromWorld();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.BDOOM, GameType.PLAYER) {
            protected void onCollision(Entity a, Entity b) {
                getGameWorld().removeEntities(a);
                FXGL.getGameTimer().clear();
                FXGL.getAudioPlayer().stopAllMusic();
                loopBGM("soundtrack.wav");
                FXGL.inc("DOOM", +1);
                FXGL.set("coins", 0);
                TutorialWorld();
            }
        });
        //----
    }

    private void TutorialWorld(){
        //Build tutorial world
        getGameWorld().removeEntities(getGameWorld().getEntitiesByType(GameType.PLATFORM, GameType.PORTAL1, GameType.PORTALT, GameType.PORTAL2, GameType.PORTAL3, GameType.PORTAL4, GameType.PLAYER,GameType.ENEMY,
                GameType.FIREBALL, GameType.GROUND, GameType.PIPE, GameType.BIG_PLATFORM, GameType.HIGH_PLATFORM, GameType.CLOUD_PLATFORM, GameType.TEXT, GameType.DOOM,
                GameType.COIN, GameType.STAR, GameType.BOSSPLATFORM, GameType.BOSS, GameType.FEATHER, GameType.BDOOM));
        player = null;
        player = spawn("player", 50, 650);
        set("player", player);

        //spawns
        spawn("OutOfBounce", -1500, 720);
        spawn("tutUnder",0, 700);
        spawn("tutUnder",1024, 705);
        spawn("tutUnder",2048, 705);
        spawn("TtextM", 100, 500);
        spawn("TtextJ", 1000, 500);
        spawn("tutTree1", 1400, 618);
        spawn("TtextDJ", 1900, 500);
        spawn("tutTree2", 2300, 535);
        spawn("TtextSHOOT", 2500, 500);
        spawn("TtextP", 3200, 400);
        spawn("TTportal", 3200, 500);
        //----

        //getGameScene().setCursor(Cursor.NONE);
        getGameScene().setBackgroundRepeat("background/TTbg.png");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, -1500, 250 * 70, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() /2, getAppHeight() /2);
        viewport.setLazy(true);
        //----
    }

    private void Level1(){
        getGameWorld().removeEntities(getGameWorld().getEntitiesByType(GameType.PLATFORM, GameType.PORTAL1, GameType.PORTALT, GameType.PORTAL2, GameType.PORTAL3, GameType.PORTAL4, GameType.PLAYER,GameType.ENEMY,
                GameType.FIREBALL, GameType.GROUND, GameType.PIPE, GameType.BIG_PLATFORM, GameType.HIGH_PLATFORM, GameType.CLOUD_PLATFORM, GameType.TEXT, GameType.DOOM,
                GameType.COIN, GameType.STAR, GameType.FEATHER, GameType.BOSSPLATFORM, GameType.BOSS, GameType.BDOOM));
        player = spawn("player", -600, 675);
        set("player", player);

        spawn("OutOfBounce", -1500, 720);
        spawn("ground", 750, 700);
        spawn("ground", 500, 699);
        spawn("ground", 200, 700);
        spawn("ground", -200, 699);
        spawn("ground", -500, 700);
        spawn("ground", -750, 699);
        spawn("ground", -1000, 700);
        spawn("ground", -1250, 699);

        spawn("basicPlatform", -600, 400);
        spawn("coin", -575, 375);
        spawn("coin", -550, 375);
        spawn("coin", -525, 375);
        spawn("coin", -500, 375);
        spawn("coin", -475, 375);
        spawn("coin", -450, 375);

        spawn("basicPlatform", -250, 250);
        spawn("coin", -225, 225);
        spawn("coin", -200, 225);
        spawn("coin", -175, 225);
        spawn("coin", -150, 225);
        spawn("coin", -125, 225);
        spawn("coin", -100, 225);

        spawn("basicPlatform", -450, 500);
        spawn("coin", -425, 500);
        spawn("coin", -400, 500);
        spawn("coin", -375, 500);
        spawn("coin", -350, 500);
        spawn("coin", -325, 500);
        spawn("coin", -300, 500);

        spawn("basicPlatform", -225, 450);
        spawn("coin", -200, 450);
        spawn("coin", -175, 450);
        spawn("coin", -150, 450);
        spawn("coin", -125, 450);
        spawn("coin", -100, 450);
        spawn("coin", -75, 450);

        spawn("basicPlatform", 100, 325);
        spawn("spikes", 100, 365);
        spawn("spikes", 175, 365);
        spawn("star", 150, 250);

        spawn("big_platform", 100, 580);
        spawn("pipe", 300, 500);
        spawn("high_platform", 350, 450);
        spawn("coin", 375, 425);
        spawn("coin", 400, 425);
        spawn("coin", 425, 425);
        spawn("coin", 450, 425);
        spawn("coin", 475, 425);
        spawn("coin", 500, 425);
        spawn("coin", 525, 425);
        spawn("coin", 550, 425);
        spawn("coin", 575, 425);
        spawn("coin", 600, 425);

        spawn("cloud_platform", 650, 450);
        spawn("coin", 675, 425);
        spawn("coin", 700, 425);
        spawn("coin", 725, 425);
        spawn("coin", 750, 425);
        spawn("coin", 775, 425);
        spawn("coin", 800, 425);
        spawn("coin", 825, 425);
        spawn("coin", 850, 425);
        spawn("coin", 875, 425);
        spawn("coin", 900, 425);

        spawn("cloud_platform", 1000, 550);
        spawn("coin", 1025, 525);
        spawn("coin", 1050, 525);
        spawn("coin", 1075, 525);
        spawn("coin", 1100, 525);
        spawn("coin", 1125, 525);
        spawn("coin", 1150, 525);
        spawn("coin", 1175, 525);
        spawn("coin", 1200, 525);
        spawn("coin", 1225, 525);
        spawn("coin", 1250, 525);

        spawn("star", 1100, 600);

        spawn("cloud_platform", 1350, 450);
        spawn("coin", 1375, 425);
        spawn("coin", 1400, 425);
        spawn("coin", 1425, 425);
        spawn("coin", 1450, 425);
        spawn("coin", 1475, 425);
        spawn("coin", 1500, 425);
        spawn("coin", 1525, 425);
        spawn("coin", 1550, 425);
        spawn("coin", 1575, 425);
        spawn("coin", 1600, 425);

        spawn("pipe", 1850, 500);
        spawn("high_platform", 1900, 450);
        spawn("big_platform", 2150, 600);
        spawn("LV1portal", 2300, 450);

        spawnEnemyFirstPipe();
        spawnEnemySecondPipe();

        getGameScene().setBackgroundRepeat("background/level1-background.png");
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, -1500, 250 * 70, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() /2, getAppHeight() /2);
        viewport.setLazy(true);
    }
    private void spawnEnemyFirstPipe() {
        enemy = spawn("enemy", 1800, 500);
        enemy.getComponent(EnemyComponent.class).goMove();

        FXGL.getGameTimer().runAtInterval(() -> {
            enemy = spawn("enemy", 1800, 500);
            enemy.getComponent(EnemyComponent.class).goMove();
        }, Duration.seconds(5));
    }
    private void spawnEnemySecondPipe() {
        enemy = spawn("enemy", 250, 500);
        enemy.getComponent(EnemyComponent.class).goMove();

        FXGL.getGameTimer().runAtInterval(() -> {
            enemy = spawn("enemy", 250, 500);
            enemy.getComponent(EnemyComponent.class).goMove();
            }, Duration.seconds(5));
    }


    private void Level2(){
        getGameWorld().removeEntities(getGameWorld().getEntitiesByType(GameType.PLATFORM, GameType.PORTAL1, GameType.PORTALT, GameType.PORTAL2, GameType.PORTAL3, GameType.PORTAL4, GameType.PLAYER,GameType.ENEMY,
                GameType.FIREBALL, GameType.GROUND, GameType.PIPE, GameType.BIG_PLATFORM, GameType.HIGH_PLATFORM, GameType.CLOUD_PLATFORM, GameType.TEXT, GameType.DOOM,
                GameType.COIN, GameType.STAR, GameType.FEATHER, GameType.BOSSPLATFORM, GameType.BOSS, GameType.BDOOM));

        player = spawn("player", 50, 50);
        set("player", player);

        spawn("M_platform", 50 , 150);

        spawn("LV2portal", 50, -1000);

        spawn("OutOfBounce", -1500, 720);
        spawn("M_platform", 50 , 150);
        spawn("coin", 525, -25);
        spawn("coin", 550, -50);
        spawn("coin", 575, -75);
        spawn("coin", 600, -100);
        spawn("coin", 625, -75);
        spawn("coin", 650, -50);
        spawn("coin", 675, -25);
        spawn("coin", 850, -25);
        spawn("coin", 875, -25);
        spawn("coin", 900, -25);
        spawn("coin", 925, -25);
        spawn("coin", 950, -25);
        spawn("M_platform", 200, 100);
        spawn ("M_platform", 350, 50);
        spawn("M_platform", 725, 50);
        spawn("tutTree2", 725, -110);
        spawn("M_platform", 875, 50);
        spawn("M_platform", 1050, 50);
        spawn("M_platform", 1250, -100);
        spawn("M_platform", 1250, -200);
        spawn("M_platform", 1250, -300);
        spawn("M_platform", 1250, -400);
        spawn("M_platform", 1050, -500);
        spawn("M_platform", 925, -500);
        spawn("M_platform", 925, -600);
        spawn("M_platform", 925, -700);
        spawn("M_platform", 925, -800);
        spawn("M_platform", 925, -900);
        spawn("M_platform", 925, -1000);
        spawn("M_platform", 650, -1100);
        spawn("M_platform", 50, -900);
        spawn("M_pillar", 1300, -100);
        spawn("M_pillar", 1300, -300);
        spawn("M_pillar", 1300, -600);
        spawn("M_pillar", 1300, -1400);
        spawn("M_pillar", 750, -1100);
        spawn("M_pillar", -240, -300);
        spawn("M_pillar", -240, -600);
        spawn("M_pillar", -240, -900);

        getGameScene().setBackgroundRepeat("background/fantasywood.jpg");
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, -1500, 250 * 70, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() /2, getAppHeight() /2);
    }

    private void Level3(){
        getGameWorld().removeEntities(getGameWorld().getEntitiesByType(GameType.PLATFORM, GameType.PORTAL1, GameType.PORTALT, GameType.PORTAL2, GameType.PORTAL3, GameType.PORTAL4, GameType.PLAYER,GameType.ENEMY,
                GameType.FIREBALL, GameType.GROUND, GameType.PIPE, GameType.BIG_PLATFORM, GameType.HIGH_PLATFORM, GameType.CLOUD_PLATFORM, GameType.TEXT, GameType.DOOM,
                GameType.COIN, GameType.STAR, GameType.FEATHER, GameType.BOSSPLATFORM, GameType.BOSS, GameType.BDOOM));

        player = spawn("player", 75, -200);
        set("player", player);

        spawn("OutOfBounce", -1500, 720);
        spawn("shadowplatform_basic", 20, 150);

        spawn("shadowplatform_crumble", 430, 100);

        spawn("coin", 500, 100);
        spawn("coin", 500, 200);
        spawn("coin", 500, 300);
        spawn("coin", 500, 400);
        spawn("shadowplatform_crumble", 480, 500);

        spawn("coin", 450, 450);
        spawn("coin", 410, 430);
        spawn("coin", 370, 420);
        spawn("coin", 330, 435);
        spawn("coin", 310, 470);
        spawn("coin", 280, 510);
        spawn("coin", 255, 560);
        spawn("coin", 230, 610);
        spawn("coin", 190, 600);
        spawn("coin", 150, 590);

        spawn("shadowplatform", 50, 625);
        spawn("star", 35, 550);
        spawn("shadowplatform", 50, 525);

        spawn("shadowplatform_basic", 680, 450);
        spawn("shadowstatue", 735, 355);
        spawn("shadowstatue_text", 610, 310);

        spawn("shadowplatform_basic", 950, 300);

        spawnGhostSpawner();

        spawn("feather", 1500, 200);

        spawn("shadowplatform_basic", 1900, 150);
        spawn("feather", 1800, -90);

        spawn("shadowplatform", 2050, -190);
        spawn("shadowplatform_basic", 2300, -300);
        spawn("LV3portal", 2400, -400);



        getGameScene().setBackgroundRepeat("background/shadowbg.jpg");
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, -1500, 250 * 70, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() /2, getAppHeight() /2);
        viewport.setLazy(true);
    }
    private void spawnGhostSpawner() {

        spawn("ghostspawner", 1075, 250);
        ghost = spawn("ghost", 1075, 265);
        ghost.getComponent(GhostComponent.class).goMove();

        FXGL.getGameTimer().runAtInterval(() -> {
            ghost = spawn("ghost", 1075, 265);
            ghost.getComponent(GhostComponent.class).goMove();
        }, Duration.seconds(4));
    }


    //build level4
    private void Level4(){
        getGameWorld().removeEntities(getGameWorld().getEntitiesByType(GameType.PLATFORM, GameType.PORTAL1, GameType.PORTALT, GameType.PORTAL2, GameType.PORTAL3, GameType.PORTAL4, GameType.PLAYER,GameType.ENEMY,
                GameType.FIREBALL, GameType.GROUND, GameType.PIPE, GameType.BIG_PLATFORM, GameType.HIGH_PLATFORM, GameType.CLOUD_PLATFORM, GameType.TEXT, GameType.DOOM,
                GameType.COIN, GameType.STAR, GameType.FEATHER, GameType.BOSSPLATFORM, GameType.BOSS, GameType.BDOOM));
        player = spawn("player", -1450, 650);
        set("player", player);

        //Random blocks
        spawn("DCwallL", 350, 110);
        spawn("DCwallL", 350, 330);
        spawn("DCwallL", 350, 550);
        spawn("DCwallL", -130, 110);
        spawn("DCwallL", -130, 330);
        spawn("DCwallL", -130, 550);
        spawn("DCwallL", 350, -200);//0
        spawn("DCwallS", 430, -350);//1
        spawn("DCwallS", 430, -250);
        spawn("DCwallS", 460, -450);//2
        spawn("DCwallS", 460, -350);
        spawn("DCwallS", 460, -250);
        spawn("DCwallS", 490, -550);//3
        spawn("DCwallS", 490, -450);
        spawn("DCwallS", 490, -350);
        spawn("DCwallS", 490, -250);
        spawn("DCwallS", 520, -650);//4
        spawn("DCwallS", 520, -550);
        spawn("DCwallS", 520, -450);
        spawn("DCwallS", 520, -350);
        spawn("DCwallS", 520, -250);
        spawn("DCwallS", 550, -650);
        spawn("DCwallS", 550, -550);
        spawn("DCwallS", 550, -450);
        spawn("DCwallS", 550, -350);
        spawn("DCwallS", 550, -250);
        spawn("DCwallL", 1310, -620);
        spawn("DCwallL", 1310, -840);
        spawn("DCwallL", 1310, -1060);
        spawn("DCwallL", 1310, -1280);
        spawn("DCwallL", 1310, -1500);
        spawn("DCwallL", 720, -160);
        spawn("DCwallL", 720, -380);
        spawn("DCwallL", 720, -600);
        spawn("DCwallS", 720, -680);
        spawn("DClava", 990, 0);
        spawn("DCwallL", 1260, -220);
        spawn("DCwallS", 1260, -320);
        spawn("DClava",-530,700);
        spawn("DClava",-430,700);
        spawn("DClava",-330,700);
        spawn("DClava",-230,700);
        spawn("DClava",-130,700);
        spawn("DClava",-30,700);
        spawn("DClava",30,700);
        spawn("DClava",130,700);
        spawn("DClava",230,700);
        spawn("DClava",330,700);
        spawn("DClava",430,700);
        spawn("DClava",530,700);
        spawn("DClava",630,700);
        spawn("DClava",730,700);
        spawn("DClava",830,700);
        spawn("DClava",930,700);
        spawn("DClava",1030,700);
        spawn("DClava",1130,700);
        spawn("DClava",1230,700);
        spawn("DClava",1330,700);
        spawn("DClava",1430,700);
        spawn("DClava",1530,700);
        spawn("DClava",1630,700);
        spawn("DClava",1730,700);
        //----

        spawn("DCwallS", -1450, 680);
        spawn("DCwallS", -930, 500);
        spawn("DCwallS", -1150, 500);
        spawn("DCwallL", -1300, 550);
        spawn("DClava",-1500,700);
        spawn("DClava",-1200, 700);
        spawn("DCplatform", -900, 700);
        spawn("coin", -875, 330);
        spawn("coin", -850, 330);
        spawn("coin", -825, 330);
        spawn("coin", -800, 330);
        spawn("star", -775, 290);
        spawn("DCwallL", -500, 550);
        spawn("DCwallL", -500, 330);
        spawn("DCwallL", -500, 110);
        spawn("DCstatue", -700, 604);
        spawn("DCplatform", -1300, 450);
        spawn("DCwallL", -1300, 230);
        spawn("DCwallL", -1300, 10);
        spawn("DCplatform", -1110, 350);
        spawn("DCplatform", -1100, 250);
        spawn("DCstatue", -900, 154);
        spawn("DCplatform", -500, 60);
        spawn("coin", -550, 0);
        spawn("coin", -575, 0);
        spawn("coin", -600, 0);
        spawn("coin", -625, 0);
        spawn("star", 100, -100);
        spawn("DCplatform", 350, 60);
        spawn("DCstatue", 550, -36);
        spawn("DCwallS", 550, -750);//5 -> random block
        spawn("DCplatform", 580, -700);
        spawn("DCplatform", 700, -700);
        spawn("DCwallS", 1280, -400);
        spawn("DCwallS", 1310, -400);
        spawn("DCplatform", 1340, -400);
        spawn("DCwallS", 1280, -400);
        spawn("DCwallS", 880, 0);
        spawn("DCplatform", 880, 100);
        spawn("coin", 930, 50);
        spawn("coin", 955, 50);
        spawn("coin", 980, 50);
        spawn("coin", 1005, 50);
        spawn("DClava", 910, 0);
        spawn("DCwallL", 1260, 0);
        spawn("DClava",-130,700);
        spawn("DCstatue", 700, 604);
        spawn("coin", 725, 554);
        spawn("DCstatue", 850, 604);
        spawn("coin", 875, 554);
        spawn("DCstatue", 1000, 604);
        spawn("coin", 1025, 554);
        spawn("DCstatue", 1150, 604);//1730
        spawn("coin", 1175, 554);
        spawn("DCstatue", 1300, 604);
        spawn("coin", 1325, 554);
        spawn("DCstatue", 1450, 604);
        spawn("coin", 1475, 554);
        spawn("DCstatue", 1600, 604);
        spawn("coin", 1625, 554);
        spawn("DCplatform", 1730, 700);
        spawn("DCplatBoss", 2135, 700);
        spawn("DCplatBoss2", 3076, -80);
        spawn("DCplatform", 2700, -200);
        spawn("DCplatform", 3250, -200);
        spawn("DCplatform", 2900, -300);
        spawn("star", 3000, -400);
        spawn("star", 3100, -400);
        spawn("star", 3200, -400);
        spawn("DCwallL", 4000, -480);
        spawn("DCwallL", 4000, -260);
        spawn("DCwallL", 4000, -40);
        spawn("box", 3850, -390);



        //world settings
        getGameScene().setBackgroundRepeat("background/Castlebg.png");
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, -1500, 250 * 70, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() /2, getAppHeight() /2);
        viewport.setLazy(true);


    }
    //----


    public static void main(String[] args)
    {
        launch(args);
    }
}