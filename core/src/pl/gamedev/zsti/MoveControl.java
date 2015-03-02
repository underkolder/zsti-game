package pl.gamedev.zsti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;


public class MoveControl {
    Stage stage;
    Button btnLeft;
    Button btnRight;
    Button btnTop;
    Button btnDown;

    Skin skin_left;
    TextureAtlas atlas_left;
    OwnCharacter controlledObject;
    TiledMapTileLayer collisionLayer;

    public MoveControl(){
        stage = new Stage();
        atlas_left = new TextureAtlas("buttons/buttons.pack");
        skin_left=new Skin(atlas_left);
        TextureRegionDrawable drLeftUp = new TextureRegionDrawable( atlas_left.findRegion("button_left.u") );
        TextureRegionDrawable drLeftDown = new TextureRegionDrawable( atlas_left.findRegion("button_left.d") );
        TextureRegionDrawable drRightUp = new TextureRegionDrawable( atlas_left.findRegion("button_right.u") );
        TextureRegionDrawable drRightDown = new TextureRegionDrawable( atlas_left.findRegion("button_right.d") );
        TextureRegionDrawable drTopUp = new TextureRegionDrawable( atlas_left.findRegion("button_top.u") );
        TextureRegionDrawable drTopDown = new TextureRegionDrawable( atlas_left.findRegion("button_top.d") );
        TextureRegionDrawable drDownUp = new TextureRegionDrawable( atlas_left.findRegion("button_down.u") );
        TextureRegionDrawable drDownDown = new TextureRegionDrawable( atlas_left.findRegion("button_down.d") );

        Button.ButtonStyle btnLStyle = new Button.ButtonStyle(drLeftUp,drLeftDown, drLeftUp);
        Button.ButtonStyle btnRStyle = new Button.ButtonStyle(drRightUp,drRightDown, drRightUp);
        Button.ButtonStyle btnTStyle = new Button.ButtonStyle(drTopUp,drTopDown, drTopUp);
        Button.ButtonStyle btnDStyle = new Button.ButtonStyle(drDownUp,drDownDown, drDownUp);
        btnLeft = new Button(btnLStyle);
        btnLeft.setWidth(100f);
        btnLeft.setHeight(100f);
        btnLeft.setPosition(10, 50);
        btnLeft.setColor(btnLeft.getColor().r, btnLeft.getColor().g, btnLeft.getColor().b, 0.5f);
        btnRight = new Button(btnRStyle);
        btnRight.setWidth(100f);
        btnRight.setHeight(100f);
        btnRight.setPosition(210, 50);
        btnRight.setColor(btnLeft.getColor().r, btnLeft.getColor().g, btnLeft.getColor().b, 0.5f);
        btnTop = new Button(btnTStyle);
        btnTop.setWidth(100f);
        btnTop.setHeight(100f);
        btnTop.setPosition(110, 100);
        btnTop.setColor(btnLeft.getColor().r, btnLeft.getColor().g, btnLeft.getColor().b, 0.5f);
        btnDown = new Button(btnDStyle);
        btnDown.setWidth(100f);
        btnDown.setHeight(100f);
        btnDown.setPosition(110, 0);
        btnDown.setColor(btnLeft.getColor().r, btnLeft.getColor().g, btnLeft.getColor().b, 0.5f);
     /*   btnLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
     */
        stage.addActor(btnLeft);
        stage.addActor(btnRight);
        stage.addActor(btnTop);
        stage.addActor(btnDown);

        Gdx.input.setInputProcessor(stage);
    }

    public void setCollisionLayer(TiledMapTileLayer layer){
        collisionLayer = layer;
    }

    public void setTo(OwnCharacter mc){
        controlledObject = mc;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                float x = controlledObject.getSprite().getX();
                float y = controlledObject.getSprite().getY();
                float cx = x+controlledObject.getSprite().getWidth();
                //controlledObject.getBody().setTransform(x-10, y, a);
                if (btnLeft.isPressed() && (int)((x-10))>=0) {
                    //if (collisionLayer.getCell((int)((x-10)/32), (int)(y/32)).getTile().getProperties().get("walkable", "daa", String.class).compareTo("1") == 0)
                    //controlledObject.setTransform(x - 10, y, 10);
                //    controlledObject.getBody().applyForceToCenter(0f,100f,true);
                    controlledObject.getBody().setLinearVelocity(-200f, controlledObject.getBody().getLinearVelocity().y);

                }
                if (btnRight.isPressed() && (int)((cx+10)/32)<=collisionLayer.getWidth()) {
                   // if (collisionLayer.getCell((int)((cx+10)/32), (int)(y/32)).getTile().getProperties().get("walkable", "daa", String.class).compareTo("1") == 0)
                        controlledObject.getBody().setLinearVelocity(200f, controlledObject.getBody().getLinearVelocity().y);
                }
                if (btnTop.isPressed() && (int)((y+10)/32)<=collisionLayer.getHeight()) {
                   // if (collisionLayer.getCell((int)(x/32), (int)((y+10)/32)).getTile().getProperties().get("walkable", "daa", String.class).compareTo("1") == 0)
                        controlledObject.getBody().setLinearVelocity(controlledObject.getBody().getLinearVelocity().x, 200f);
                }
                if (btnDown.isPressed() && (int)((y-10))>=0) {
                   // if (collisionLayer.getCell((int)(x/32), (int)((y-10)/32)).getTile().getProperties().get("walkable", "daa", String.class).compareTo("1") == 0)
                        controlledObject.getBody().setLinearVelocity(controlledObject.getBody().getLinearVelocity().x, -200f);
                }
            }
        }, 0, 0.03f);

    }
    public Stage getStage(){
        return stage;
    }

}
