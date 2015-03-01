package pl.gamedev.zsti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by xander on 25.02.15.
 */
public class OwnCharacter {
    Texture texture;
    Sprite sprite;
    Body body;
    Fixture fixture;

    OwnCharacter(World world, float x, float y, Texture texture){
       // texture = new Texture("hipek.png");
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        bodyDef.linearDamping = 20f;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Fixture getFixture() {
        return fixture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }

    public float getHeight(){
        return sprite.getHeight();
    }

    public float getWidth(){
        return sprite.getWidth();
    }

}
