package components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class BossComponent extends Component {
    private PhysicsComponent physicsComponent;
    private int hp = 1;

    public BossComponent() {
        //
    }

    @Override
    public void onAdded() {
        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 2800, -300);
        }, Duration.seconds(3));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 3600, -300);
        }, Duration.seconds(3));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 3200, -300);
        }, Duration.seconds(3));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 3450, -300);
        }, Duration.seconds(3));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 2850, -550);
        }, Duration.seconds(3));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 3297, -550);
        }, Duration.seconds(3));

        FXGL.getGameTimer().runAtInterval(() -> {
            FXGL.getGameTimer().clear();
            Entity a = spawn("Bslice", 3500, -175);
            a.getComponent(SliceComponent.class).onMove();
        }, Duration.seconds(6));

    }

    //spawn("DCplatBoss2", 3076, -80);

    @Override
    public void onUpdate(double tpf) {
        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 2800, -300);
        }, Duration.seconds(4));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 3600, -300);
        }, Duration.seconds(4));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 3200, -300);
        }, Duration.seconds(4));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 3450, -300);
        }, Duration.seconds(4));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 2850, -550);
        }, Duration.seconds(4));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("Bspell", 3297, -550);
        }, Duration.seconds(4));

        FXGL.getGameTimer().runAtInterval(() -> {
            FXGL.getGameTimer().clear();
            Entity a = spawn("Bslice", 3700, -175);
            a.getComponent(SliceComponent.class).onMove();
        }, Duration.seconds(5));
    }


    public void bossHP(int a) {
        if (hp > 0) {
            hp = hp - a;
        } else {
            getGameWorld().removeEntities(getGameWorld().getEntitiesByComponent(ExplosionComponent.class));
            getGameWorld().removeEntities(getGameWorld().getEntitiesByComponent(SliceComponent.class));
            entity.removeFromWorld();
            spawn("LV4portal", 3175, -222);
        }
    }
}