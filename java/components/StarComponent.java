package components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class StarComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animIdle;


    public StarComponent()
    {
        Image image = image("star_sprite.png");

        animIdle = new AnimationChannel(image, 9, 586 / 9, 67, Duration.seconds(0.4), 1, 8);

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
