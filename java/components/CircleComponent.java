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
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;


public class CircleComponent extends Component
{
    public CircleComponent()
    {
        //
    }

    @Override
    public void onAdded()
    {
        Point2D a = entity.getCenter();
        FXGL.getGameTimer().runAtInterval(() -> {
            FXGL.getGameTimer().clear();
            spawn("Bexplosion", a.getX()+50, a.getY()+50);
            getGameWorld().removeEntities(getGameWorld().getEntitiesByComponent(CircleComponent.class));
        }, Duration.seconds(3));
    }
}
