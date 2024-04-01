package components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class GhostComponent extends Component
{
    private PhysicsComponent physicsComponent;
    private AnimatedTexture texture;
    private AnimationChannel animIdle;

    public GhostComponent()
    {
        Image image = image("ghost_anim.png");


        animIdle = new AnimationChannel(image, 4, 24, 24, Duration.seconds(0.4), 0, 3);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded()
    {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    public void goMove()
    {
        getEntity().setScaleX(-2.2);
        physicsComponent.setVelocityX(-60);
    }
}
