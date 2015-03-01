package pl.gamedev.zsti;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class ZstiGame implements ApplicationListener, InputProcessor {

    SpriteBatch batch;
    SpriteBatch playerInfo;
    World world;
    OwnCharacter mc;
    OwnCharacter mc2;
    MoveControl moveController;

    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;


    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    BitmapFont font;
    String title = "ZSTI GAME x b";
    String test= "dads";

   // private Viewport viewport;
    private OrthographicCamera camera;
    private int currentFrame = 1;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        tiledMap = new TmxMapLoader().load("testmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        playerInfo = new SpriteBatch();
        batch = new SpriteBatch();

        world = new World(new Vector2(0, 0), true);

        MapBodyBuilder.buildShapes(tiledMap, 1, world);

        mc = new OwnCharacter(world, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, new Texture("hipek.png"));
        mc2 = new OwnCharacter(world, Gdx.graphics.getWidth()/2-100, Gdx.graphics.getHeight()/2, new Texture("hipek.png"));
        moveController = new MoveControl();
        moveController.setTo(mc);
        moveController.setCollisionLayer((TiledMapTileLayer) tiledMap.getLayers().get("layer1"));
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        //font.scale(1);

        //Gdx.input.setInputProcessor(this);

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                currentFrame++;
                if(currentFrame > 10)
                    currentFrame = 1;
                if(currentFrame % 2 == 1) font.setColor(Color.YELLOW);
                else font.setColor(Color.CYAN);
                title = Float.toString(mc.getSprite().getX())+" "+Float.toString(mc.getSprite().getY());
            }
        }, 0, 0.2f);

        debugRenderer = new Box2DDebugRenderer();

    }

    @Override
    public void dispose () {
        batch.dispose();
        font.dispose();
        world.dispose();
    }
    @Override
    public void resume () {
    }
    @Override
    public void pause () {
    }
    @Override
    public void resize (int a, int b) {
    }

	@Override
	public void render () {

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        mc.getSprite().setPosition(mc.getBody().getPosition().x-mc.getWidth()/2, mc.getBody().getPosition().y-mc.getHeight()/2);
        mc2.getSprite().setPosition(mc2.getBody().getPosition().x-mc2.getWidth()/2, mc2.getBody().getPosition().y-mc2.getHeight()/2);

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(mc.getSprite().getX(), mc.getSprite().getY(), 0);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        mc.getSprite().draw(batch);
        mc2.getSprite().draw(batch);

        batch.end();

        moveController.getStage().draw();


        playerInfo.begin();
        font.draw(playerInfo, title, 10, Gdx.graphics.getHeight());
        playerInfo.end();

        debugMatrix = batch.getProjectionMatrix().cpy().scale(1f,
                1f, 0);
        debugRenderer.render(world, debugMatrix);

    }

    @Override public boolean keyTyped(char character) {

        return false;
    }

    @Override public boolean keyDown(int key) {

        return false;
    }

    @Override public boolean keyUp(int key) {

        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
      /*  Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);
        mc.setPosition(position.x, position.y);
        */
        return false;
    }

    @Override public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override public boolean scrolled(int amount) {
        return false;
    }

}
