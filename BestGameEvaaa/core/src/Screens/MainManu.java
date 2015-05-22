package Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BestGameEvaa;

public class MainManu extends PlayScreenFields implements Screen {

	private Stage stage;
	private TextButton newGameButton, exitButton;
	private BestGameEvaa _game;

	public MainManu(BestGameEvaa game) {
		this._game = game;
		batch = new SpriteBatch();
		background = new Texture("logo.png");
		stage = new Stage();

		createNewGameButton();
		createExitGameButton();
	}

	private void createExitGameButton() {
		exitButton = new TextButton("Exit", _game.skin);
		exitButton.setBounds(Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()/2 - 175, 200, 100);
		exitButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
					Gdx.app.exit();
				return true;
			}
		});

		stage.addActor(exitButton);
		
	}

	private void createNewGameButton() {
		newGameButton = new TextButton("New Game", _game.skin);
		newGameButton.setBounds(Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()/2 - 45, 200, 100);
		newGameButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
					_game.setScreen(_game.play);
				return true;
			}
		});

		stage.addActor(newGameButton);
	}

	@Override
	public void render(float delta) {
		Gdx.input.setInputProcessor(stage);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
		
		Gdx.input.setInputProcessor(stage);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		batch.dispose();
	}

}
