package components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class CoinsComponent extends Component
{
    private AnimatedTexture texture;
    private AnimationChannel animIdle;


    public CoinsComponent()
    {
        Image image = image("coins_sprite.png");

        animIdle = new AnimationChannel(image, 5, 80 / 5, 16, Duration.seconds(0.4), 1, 4);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded()
    {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }
}
