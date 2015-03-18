package pl.gamedev.zsti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

public class OwnCharacter {
    Sprite sprite;
    Body body;
    Fixture fixture;
//animation
    private TextureAtlas textureAtlas;
    int animMc;
    int directionLR; //0-na wprost, 1-lewo, 2-prawo
    int directionTD; //0-w bok, 1-góra, 2-dół
    int lastDirection;


//animation

    OwnCharacter(/*TiledMap map, */World world, float x, float y, Texture texture){
       // sprite = new Sprite(texture);

        textureAtlas = new TextureAtlas(Gdx.files.internal("a_mc/a_mc.atlas"));
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion("a_mc1");
        sprite = new Sprite(region);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                animMc++;
                if(animMc==48) animMc=0;
                int mod = 6;
                int fChange=4;
                int sChange=2;


                String currentAtlasKey = "a_mc1";
                switch(lastDirection){
                    case 1: currentAtlasKey = "a_mc18";//LT
                            break;
                    case 2: currentAtlasKey = "a_mc19";//L
                            break;
                    case 3: currentAtlasKey = "a_mc20";//LD
                            break;
                    case 4: currentAtlasKey = "a_mc21";//T
                            break;
                    case 5: currentAtlasKey = "a_mc22";//D
                            break;
                    case 6: currentAtlasKey = "a_mc23";//RT
                            break;
                    case 7: currentAtlasKey = "a_mc1";//R
                            break;
                    case 8: currentAtlasKey = "a_mc25";//RD
                            break;
                }
                if(directionLR==0){
                    if(directionTD==1){//góra
                        if(animMc%mod>=fChange) currentAtlasKey= "a_mc12";
                        else if(animMc%mod>=sChange) currentAtlasKey= "a_mc13";
                        lastDirection = 4;
                    }
                    if(directionTD==2){//dół
                        if(animMc%mod>=fChange) currentAtlasKey= "a_mc4";
                        else if(animMc%mod>=sChange) currentAtlasKey= "a_mc5";
                        lastDirection = 5;
                    }

                }else if(directionLR==1){
                    if(directionTD==0){//lewo
                        if(animMc%mod>=fChange) currentAtlasKey= "a_mc6";
                        else if(animMc%mod>=sChange) currentAtlasKey= "a_mc7";
                        lastDirection = 2;
                    }
                    if(directionTD==1){//lewo-góra
                        if(animMc%mod>=fChange) currentAtlasKey= "a_mc8";
                        else if(animMc%mod>=sChange) currentAtlasKey= "a_mc9";
                        lastDirection = 1;
                    }
                    if(directionTD==2){//lewo-dół
                        if(animMc%mod>=fChange) currentAtlasKey= "a_mc10";
                        else if(animMc%mod>=sChange) currentAtlasKey= "a_mc11";
                        lastDirection = 3;
                    }
                }else if( directionLR==2){
                    if(directionTD==0){//prawo
                        if(animMc%mod>=fChange) currentAtlasKey= "a_mc3";
                        else if(animMc%mod>=sChange) currentAtlasKey= "a_mc2";
                        lastDirection = 7;
                    }
                    if(directionTD==1){//prawo-góra
                        if(animMc%6>=fChange) currentAtlasKey= "a_mc14";
                        else if(animMc%mod>=sChange) currentAtlasKey= "a_mc15";
                        lastDirection = 6;
                    }
                    if(directionTD==2){//prawo-dół
                        if(animMc%mod>=fChange) currentAtlasKey= "a_mc16";
                        else if(animMc%mod>=sChange) currentAtlasKey= "a_mc17";
                        lastDirection = 8;
                    }
                }


                sprite.setRegion(textureAtlas.findRegion(currentAtlasKey));

            }
        }, 0, 0.1f);


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
/*
    public Fixture getFixture() {
        return fixture;
    }
*/
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

    public void setDirectionLR(int d){ directionLR = d; }
    public void setDirectionTD(int d){ directionTD = d; }

//    public TextureAtlas getTextureAtlas(){ return textureAtlas; }


}
