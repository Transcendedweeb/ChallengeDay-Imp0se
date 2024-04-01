package components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class BossPlatComponent extends Component {
    private PhysicsComponent physicsComponent;

    public BossPlatComponent() {
        //
    }

    public void goMove()
    {
        getEntity().setScaleX(-1);
        physicsComponent.setVelocityY(-50);

    }

    public void stopMove(){
        physicsComponent.setVelocityY(0);
    }
}
