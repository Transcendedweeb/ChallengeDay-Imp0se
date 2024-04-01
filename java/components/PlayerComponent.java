package components;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayerComponent extends Component
{
    private PhysicsComponent physicsComponent;
    private final AnimatedTexture texture;
    private final AnimationChannel animIdle;
    private final AnimationChannel animWalk;
    char characterView = 'R';

    private int jumps = 2;

    public PlayerComponent()
    {
        Image image = image("sonicSheet.png");

        animIdle = new AnimationChannel(image, 6, 290 / 6, 52, Duration.seconds(0.4), 1, 5);

        animWalk = new AnimationChannel(image, 6, 290 / 6, 52, Duration.seconds(0.4), 0, 5);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded()
    {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);

        physicsComponent.onGroundProperty().addListener((observable, oldValue, isOnGround) -> {
            if (isOnGround) {
                jumps = 2;
            }
        });
        System.out.println(entity.getComponents());
    }

    @Override
    public void onUpdate(double tpf)
    {
        if (physicsComponent.isMovingX()) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
        } else {
            if (texture.getAnimationChannel() != animIdle) {
                texture.loopAnimationChannel(animWalk);
            }
        }
    }

    public void left()
    {
        getEntity().setScaleX(-1);
        physicsComponent.setVelocityX(-170);
        characterView = 'L';
    }

    public void right()
    {
        getEntity().setScaleX(1);
        physicsComponent.setVelocityX(170);
        characterView = 'R';
    }

    public void stop()
    {
        physicsComponent.setVelocityX(0);
    }

    public void jump()
    {
        if (jumps == 0)
            return;

        physicsComponent.setVelocityY(-300);

        jumps--;
    }

    public void shoot(){
        if (characterView == 'R'){
            Point2D center = entity.getCenter();
            Vec2 dir = Vec2.fromAngle(entity.getRotation());
            spawn("fireballR", new SpawnData(center.getX(), center.getY()-30).put("dir", dir.toPoint2D()));
        }else if (characterView == 'L'){
            Point2D center = entity.getCenter();
            Vec2 dir = Vec2.fromAngle(entity.getRotation());
            spawn("fireballL", new SpawnData(center.getX()-20, center.getY()-20).put("dir", dir.toPoint2D()));
        }
    }

    public void feather() {
        jumps = 2;
        physicsComponent.setVelocityY(-150);
    }
}
