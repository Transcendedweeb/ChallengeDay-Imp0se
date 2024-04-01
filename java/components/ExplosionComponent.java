package components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class ExplosionComponent extends Component
{
    private PhysicsComponent physicsComponent;
    private AnimatedTexture texture;
    private AnimationChannel animIdle;

    public ExplosionComponent()
    {
        Image image = image("bossAttack/explosions.png");
        animIdle = new AnimationChannel(image, 6, 364 / 6, 132, Duration.seconds(1), 1, 5);
        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded()
    {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
        FXGL.getGameTimer().runAtInterval(() -> {
            getGameWorld().removeEntities(getGameWorld().getEntitiesByComponent(ExplosionComponent.class));
            FXGL.getGameTimer().clear();
        }, Duration.seconds(1));
    }

}
