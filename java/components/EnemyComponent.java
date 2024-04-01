package components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class EnemyComponent extends Component
{
    private PhysicsComponent physicsComponent;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public EnemyComponent()
    {
        Image image = image("KawaiiDemon.png");
        Image walk_image = image("KawaiiDemon_move.png");


        animIdle = new AnimationChannel(image, 5, 630 / 5, 104, Duration.seconds(0.4), 1, 4);
        animWalk = new AnimationChannel(walk_image,4, 552 / 4, 71, Duration.seconds(0.4), 1, 3 );

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
        getEntity().setScaleX(-1);
        physicsComponent.setVelocityX(-50);
    }
}
