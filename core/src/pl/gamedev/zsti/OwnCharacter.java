package pl.gamedev.zsti;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class OwnCharacter {
    Sprite sprite;
    Body body;
    Fixture fixture;


    OwnCharacter(/*TiledMap map, */World world, float x, float y, Texture texture){
       // texture = new Texture("hipek.png");
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        bodyDef.linearDamping = 20f;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
/*      MapObjects objects = map.getLayers().get("obstacles").getObjects();
        for(MapObject object : objects) {
            if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject) object);
                shape.set(((PolygonMapObject) object).getPolygon().getVertices());
            }
        }
*/

        shape.set(new float[]{
                -15f, -30f,
                15f, -30f,
                25f, 0f,
                20f, 30f,
                -5f, 30f,
                -25f, 0f} );
        //shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2-5);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixture = body.createFixture(fixtureDef);
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

/*
    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i];
        }

        polygon.set(worldVertices);
        return polygon;
    }
*/
}
